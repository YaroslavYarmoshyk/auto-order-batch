package etake.autoorderbatch.service.impl;

import etake.autoorderbatch.dto.NameValuePair;
import etake.autoorderbatch.service.AttributeService;
import etake.autoorderbatch.service.AverageSalesCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

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

@SpringBootTest(classes = AverageSalesCalculationService.class)
class AverageSalesCalculationServiceImplTest {
    @Value("${auto-order-system.avg-sales.start}")
    private Double startTrend;
    @Value("${auto-order-system.avg-sales.end}")
    private Double endTrend;
    private AverageSalesCalculationService averageSalesCalculationService;
    private AttributeService attributeService;

    @BeforeEach
    void setUp() {
//        attributeService = new AttributeServiceImpl(attributes);
//        averageSalesCalculationService = new AverageSalesCalculationServiceImpl(attributeService);
//        ReflectionTestUtils.setField(averageSalesCalculationService, "startTrend", startTrend);
//        ReflectionTestUtils.setField(averageSalesCalculationService, "endTrend", endTrend);
    }

    @Test()
    @DisplayName("Test average sales calculation SKU")
    void testCalculate_bothValues_SKU() {
//        Set<NameValuePair<String, String>> attributes = getAttributes(getDefaultSettingMap());
//
//        BigDecimal actual = averageSalesCalculationService.calculateAvgSales(attributes, SKU);
//
//        assertEquals(BigDecimal.valueOf(1.41), actual);


    }

    private Set<NameValuePair<String, String>> getAttributes(Map<String, String> settingMap) {
        Set<NameValuePair<String, String>> attributes = new HashSet<>();

        for (Map.Entry<String, String> entry: settingMap.entrySet()) {
            attributes.add(new NameValuePair<>(entry.getKey(), entry.getValue()));
        }

        return attributes;
    }

    private Map<String, String> getDefaultSettingMap() {
        Map<String, String> settingMap = new HashMap<>();
        settingMap.put(SALES_SKU_START, "6");
        settingMap.put(SALES_SKU_END, "3");
        settingMap.put(SALES_CSKU_START, "1");
        settingMap.put(SALES_CSKU_END, "");
        settingMap.put(DAY_ON_STOCK_SKU_START, "5");
        settingMap.put(DAY_ON_STOCK_SKU_END, "2");
        settingMap.put(DAY_ON_STOCK_CSKU_START, "3");
        settingMap.put(DAY_ON_STOCK_CSKU_END, "2");

        return settingMap;
    }


}