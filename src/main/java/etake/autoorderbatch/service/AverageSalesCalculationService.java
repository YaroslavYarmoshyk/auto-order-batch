package etake.autoorderbatch.service;

import etake.autoorderbatch.dto.NameValuePair;

import java.util.Set;

public interface AverageSalesCalculationService {
    Double calculateAvgSales(Set<NameValuePair<String, String>> attributes, String positionType);

}
