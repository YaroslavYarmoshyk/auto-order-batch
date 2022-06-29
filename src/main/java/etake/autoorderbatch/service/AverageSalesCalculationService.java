package etake.autoorderbatch.service;

import java.math.BigDecimal;

public interface AverageSalesCalculationService {
    BigDecimal calculateAvgSales(BigDecimal startSales, BigDecimal startDaysOnStock, BigDecimal endSales, BigDecimal endDaysOnStock);

}
