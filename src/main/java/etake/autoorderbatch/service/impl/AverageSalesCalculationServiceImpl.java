package etake.autoorderbatch.service.impl;

import etake.autoorderbatch.service.AverageSalesCalculationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
public class AverageSalesCalculationServiceImpl implements AverageSalesCalculationService {
    @Value("${auto-order-system.avg-sales.start}")
    private Double startTrend;
    @Value("${auto-order-system.avg-sales.end}")
    private Double endTrend;

    @Override
    public BigDecimal calculateAvgSales(BigDecimal startSales, BigDecimal startDaysOnStock, BigDecimal endSales, BigDecimal endDaysOnStock) {
        final BigDecimal avgSalesStart = getAvgSales(startSales, startDaysOnStock);
        final BigDecimal avgSalesEnd = getAvgSales(endSales, endDaysOnStock);

        return getFinalAvgSales(avgSalesStart, avgSalesEnd);
    }

    private BigDecimal getAvgSales(BigDecimal sales, BigDecimal daysOnStock) {
        if (Objects.equals(sales, BigDecimal.ZERO) || Objects.equals(daysOnStock, BigDecimal.ZERO) ) {
            return BigDecimal.ZERO;
        }

        return sales.divide(daysOnStock, 5, RoundingMode.HALF_UP);
    }

    /**
     * Get final average sales, with is result of combine start and end of tendentious
     * If start value is null then return end and vice versa
     * if both values are present - calculate result using corresponding shares
     */
    private BigDecimal getFinalAvgSales(BigDecimal avgSalesStart, BigDecimal avgSalesEnd) {
        if (Objects.equals(avgSalesStart, BigDecimal.ZERO)) {
            return avgSalesEnd;
        }

        if (Objects.equals(avgSalesEnd, BigDecimal.ZERO)) {
            return avgSalesStart;
        }

        return avgSalesStart.multiply(BigDecimal.valueOf(startTrend)).add(avgSalesEnd.multiply(BigDecimal.valueOf(endTrend)));
    }

}
