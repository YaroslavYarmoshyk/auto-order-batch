package etake.autoorderbatch.service.impl;

import etake.autoorderbatch.dto.NameValuePair;
import etake.autoorderbatch.service.AverageSalesCalculationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static etake.autoorderbatch.util.Constants.CSKU;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_CSKU_END;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_CSKU_START;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_SKU_END;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_SKU_START;
import static etake.autoorderbatch.util.Constants.SALES_CSKU_END;
import static etake.autoorderbatch.util.Constants.SALES_CSKU_START;
import static etake.autoorderbatch.util.Constants.SALES_SKU_END;
import static etake.autoorderbatch.util.Constants.SALES_SKU_START;

@Service
public class AverageSalesCalculationServiceImpl implements AverageSalesCalculationService {
    @Value("${avg-sales.start:1}")
    private Double startTrend;
    @Value("${avg-sales.end}")
    private Double endTrend;

    @Override
    public Double calculateAvgSales(Set<NameValuePair<String, String>> attributes, String positionType) {
        String startSalesAttribute = SALES_SKU_START;
        String endSalesAttribute = SALES_SKU_END;
        String startDaysOnStockAttribute = DAY_ON_STOCK_SKU_START;
        String endDaysOnStockAttribute = DAY_ON_STOCK_SKU_END;

        if (Objects.equals(positionType, CSKU)) {
            startSalesAttribute = SALES_CSKU_START;
            endSalesAttribute = SALES_CSKU_END;
            startDaysOnStockAttribute = DAY_ON_STOCK_CSKU_START;
            endDaysOnStockAttribute = DAY_ON_STOCK_CSKU_END;
        }


        final Double startSales = getAttributeValue(attributes, startSalesAttribute);
        final Double startDaysOnStock = getAttributeValue(attributes, startDaysOnStockAttribute);

        final Double avgSalesStart = getAvgSales(startSales, startDaysOnStock);

        final Double endSales = getAttributeValue(attributes, endSalesAttribute);
        final Double endDaysOnStock = getAttributeValue(attributes, endDaysOnStockAttribute);

        final Double avgSalesEnd = getAvgSales(endSales, endDaysOnStock);

        return getFinalAvgSales(avgSalesStart, avgSalesEnd);
    }

    private Double getAvgSales(Double sales, Double daysOnStock) {
        if (sales == 0.0 || daysOnStock == 0.0) {
            return 0.0;
        }

        return sales / daysOnStock;
    }

    private Double getAttributeValue(Set<NameValuePair<String, String>> attributes, String attribute) {
        return attributes.stream()
                .filter(a -> Objects.equals(a.getName(), attribute))
                .findFirst()
                .map(NameValuePair::getValue)
                .map(value -> value.isBlank() ? 0.0 : Double.parseDouble(value))
                .orElseThrow(() -> new RuntimeException("Cannot get attribute " + attribute));
    }

    private Double getFinalAvgSales(Double avgSalesStart, Double avgSalesEnd) {
        if (avgSalesStart == 0.0) {
            return avgSalesEnd;
        }

        if (avgSalesEnd == 0.0) {
            return avgSalesStart;
        }

        return (avgSalesStart * startTrend) + (avgSalesEnd * endTrend);
    }
}
