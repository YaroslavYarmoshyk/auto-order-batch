package etake.autoorderbatch.service.impl;

import etake.autoorderbatch.dto.NameValuePair;
import etake.autoorderbatch.service.AverageSalesCalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_CSKU_END;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_CSKU_START;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_SKU_END;
import static etake.autoorderbatch.util.Constants.DAY_ON_STOCK_SKU_START;
import static etake.autoorderbatch.util.Constants.SALES_CSKU_END;
import static etake.autoorderbatch.util.Constants.SALES_CSKU_START;
import static etake.autoorderbatch.util.Constants.SALES_SKU_END;
import static etake.autoorderbatch.util.Constants.SALES_SKU_START;
import static etake.autoorderbatch.util.Constants.SKU;

class AverageSalesCalculationServiceImplTest {
    private final AverageSalesCalculationService averageSalesCalculationService;

    AverageSalesCalculationServiceImplTest(AverageSalesCalculationService service) {
        this.averageSalesCalculationService = service;
    }


    @Test()
    @DisplayName("Test average sales calculation")
    void testCalculate_bothValues() {
        Map<String, String> settingMap = new HashMap<>();
        settingMap.put(SALES_SKU_START, "6");
        settingMap.put(SALES_SKU_END, "3");
        settingMap.put(SALES_CSKU_START, "1");
        settingMap.put(SALES_CSKU_END, "");
        settingMap.put(DAY_ON_STOCK_SKU_START, "5");
        settingMap.put(DAY_ON_STOCK_SKU_END, "");
        settingMap.put(DAY_ON_STOCK_CSKU_START, "3");
        settingMap.put(DAY_ON_STOCK_CSKU_END, "2");

        Set<NameValuePair<String, String>> attributes = getAttributes(settingMap);

        Double calculateAvgSales = averageSalesCalculationService.calculateAvgSales(attributes, SKU);


    }

    private Set<NameValuePair<String, String>> getAttributes(Map<String, String> settingMap) {
        Set<NameValuePair<String, String>> attributes = new HashSet<>();

        for (Map.Entry<String, String> entry: settingMap.entrySet()) {
            attributes.add(new NameValuePair<>(entry.getKey(), entry.getValue()));
        }

        return attributes;
    }


}