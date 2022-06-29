package etake.autoorderbatch.service;

import java.math.BigDecimal;

public interface DemandService {
    BigDecimal calculateDemand();

    BigDecimal calculateDemandWithCoefficient(BigDecimal demand);

    BigDecimal calculateFinalDemand(BigDecimal demand);
}
