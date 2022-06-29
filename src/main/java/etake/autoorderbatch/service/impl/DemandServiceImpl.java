package etake.autoorderbatch.service.impl;

import etake.autoorderbatch.service.AttributeService;
import etake.autoorderbatch.service.AverageSalesCalculationService;
import etake.autoorderbatch.service.DemandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static etake.autoorderbatch.util.Constants.AM;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_CSKU;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_CSKU_END;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_CSKU_START;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_SKU;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_SKU_END;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_SKU_START;
import static etake.autoorderbatch.util.Constants.DEMAND_COEFFICIENT;
import static etake.autoorderbatch.util.Constants.MIN_NUMBER;
import static etake.autoorderbatch.util.Constants.REST_CSKU;
import static etake.autoorderbatch.util.Constants.REST_SKU;
import static etake.autoorderbatch.util.Constants.R_PRICE;
import static etake.autoorderbatch.util.Constants.SALES_CSKU_END;
import static etake.autoorderbatch.util.Constants.SALES_CSKU_START;
import static etake.autoorderbatch.util.Constants.SALES_SKU_END;
import static etake.autoorderbatch.util.Constants.SALES_SKU_START;
import static etake.autoorderbatch.util.Constants.SEASON_COEFFICIENT;

@Service
public class DemandServiceImpl implements DemandService {
    @Value("${auto-order-system.demand-days}")
    private Long demandDays;
    @Value("${auto-order-system.new-coefficient}")
    private Long newCoefficient;
    private final AverageSalesCalculationService averageSalesCalculationService;
    private final AttributeService attributeService;

    public DemandServiceImpl(AverageSalesCalculationService averageSalesCalculationService,
                             AttributeService attributeService) {
        this.averageSalesCalculationService = averageSalesCalculationService;
        this.attributeService = attributeService;
    }

    @Override
    public BigDecimal calculateDemand() {
        final BigDecimal startSalesSKU = getAttributeValue(SALES_SKU_START);
        final BigDecimal endSalesSKU = getAttributeValue(SALES_SKU_END);
        final BigDecimal startDaysSKU = getAttributeValue(DAY_ON_STOCK_SKU_START);
        final BigDecimal endDaysSKU = getAttributeValue(DAY_ON_STOCK_SKU_END);
        final BigDecimal matrix = getAttributeValue(AM);
        final BigDecimal daysOnStockSKU = getAttributeValue(DAY_ON_STOCK_SKU);
        final BigDecimal restSKU = getAttributeValue(REST_SKU);

        final BigDecimal startSalesCSKU = getAttributeValue(SALES_CSKU_START);
        final BigDecimal endSalesCSKU = getAttributeValue(SALES_CSKU_END);
        final BigDecimal startDaysCSKU = getAttributeValue(DAY_ON_STOCK_CSKU_START);
        final BigDecimal endDaysCSKU = getAttributeValue(DAY_ON_STOCK_CSKU_END);
        final BigDecimal daysOnStockCSKU = getAttributeValue(DAY_ON_STOCK_CSKU);
        final BigDecimal restCSKU = getAttributeValue(REST_CSKU);
        final BigDecimal minQuantity = getAttributeValue(MIN_NUMBER);

        final BigDecimal averageSalesSKU = averageSalesCalculationService.calculateAvgSales(startSalesSKU, startDaysSKU, endSalesSKU, endDaysSKU);
        final BigDecimal averageSalesCSKU = averageSalesCalculationService.calculateAvgSales(startSalesCSKU, startDaysCSKU, endSalesCSKU, endDaysCSKU);

        final BigDecimal skuComponent = getSKUComponent(matrix, daysOnStockSKU, averageSalesSKU);
        final BigDecimal cskuComponent = getCSKUComponent(matrix, daysOnStockCSKU, averageSalesCSKU, restCSKU, minQuantity);

        return getFinalDemand(skuComponent, cskuComponent, matrix, minQuantity, daysOnStockSKU, restSKU);
    }

    @Override
    public BigDecimal calculateDemandWithCoefficient(BigDecimal demand) {
        final BigDecimal demandCoefficient = getAttributeValue(DEMAND_COEFFICIENT);
        final BigDecimal seasonCoefficient = getAttributeValue(SEASON_COEFFICIENT);

        return demand.multiply(BigDecimal.valueOf(newCoefficient)).multiply(demandCoefficient).multiply(seasonCoefficient);
    }

    @Override
    public BigDecimal calculateFinalDemand(BigDecimal demand) {
        final BigDecimal matrix = getAttributeValue(AM);
        final BigDecimal price = getAttributeValue(R_PRICE);
        final BigDecimal minQuantity = getAttributeValue(MIN_NUMBER);
        final BigDecimal daysOnStock = getAttributeValue(DAY_ON_STOCK_SKU);

        if (Objects.equals(BigDecimal.ZERO, matrix)) {
            return BigDecimal.ZERO;
        }

        if (demand.compareTo(BigDecimal.valueOf(2)) < 1 && minQuantity.compareTo(BigDecimal.TEN) >= 0) {
            return BigDecimal.ZERO;
        }

        if (demand.compareTo(BigDecimal.valueOf(1)) < 1 && minQuantity.compareTo(BigDecimal.valueOf(5)) >= 0 && minQuantity.compareTo(BigDecimal.valueOf(9)) < 1) {
            return BigDecimal.ZERO;
        }

        if (demand.compareTo(BigDecimal.valueOf(1)) < 1 && minQuantity.compareTo(BigDecimal.ONE) == 0 && price.compareTo(BigDecimal.valueOf(100)) < 1 && daysOnStock.compareTo(BigDecimal.valueOf(3)) < 1) {
            return BigDecimal.valueOf(2);
        }

        return demand;
    }

    private BigDecimal getSKUComponent(BigDecimal matrix, BigDecimal daysOnStock, BigDecimal averageSales) {
        if (Objects.equals(BigDecimal.ZERO, matrix)) {
            return BigDecimal.ZERO;
        }

        if (daysOnStock.compareTo(BigDecimal.valueOf(3)) > 0) {
            return averageSales.multiply(BigDecimal.valueOf(demandDays));
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getCSKUComponent(BigDecimal matrix, BigDecimal daysOnStock, BigDecimal averageSales, BigDecimal rest, BigDecimal minQuantity) {
        if (Objects.equals(BigDecimal.ZERO, matrix)) {
            return BigDecimal.ZERO;
        }

        if (daysOnStock.compareTo(BigDecimal.valueOf(2)) > 0 && averageSales.compareTo(BigDecimal.ZERO) > 0) {
            return averageSales.multiply(BigDecimal.valueOf(demandDays));
        }

        if (daysOnStock.compareTo(BigDecimal.valueOf(2)) < 1 && averageSales.compareTo(BigDecimal.ZERO) > 0) {
            return minQuantity.compareTo(rest) > 0 ? minQuantity.subtract(rest) : BigDecimal.ZERO;
        }

        return BigDecimal.ZERO;
    }

    private BigDecimal getFinalDemand(BigDecimal skuComponent, BigDecimal cskuComponent, BigDecimal matrix, BigDecimal minQuantity, BigDecimal daysOnStock, BigDecimal rest) {
        if (Objects.equals(BigDecimal.ZERO, matrix)) {
            return BigDecimal.ZERO;
        }

        if (minQuantity.compareTo(BigDecimal.ZERO) == 0 && rest.compareTo(BigDecimal.ZERO) == 0 && daysOnStock.compareTo(BigDecimal.valueOf(3)) < 1) {
            return BigDecimal.ONE;
        }

        if (daysOnStock.compareTo(BigDecimal.valueOf(3)) > 0) {
            final BigDecimal demand = skuComponent.add(cskuComponent).add(minQuantity).subtract(rest);

            return demand.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : demand;
        }

        return minQuantity.compareTo(rest) < 1 ? BigDecimal.ZERO : minQuantity.subtract(rest);
    }

    private BigDecimal getAttributeValue(String attribute) {
        return attributeService.getAttributeValue(attribute).isEmpty() ? BigDecimal.ZERO : BigDecimal.valueOf(Double.parseDouble(attributeService.getAttributeValue(attribute)));
    }
}
