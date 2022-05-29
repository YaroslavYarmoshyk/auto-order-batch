package etake.autoorderbatch.util;

import java.util.List;

public final class Constants {
    public static final String SKU = "SKU";
    public static final String CSKU = "CSKU";
    public static final String AM = "АМ";
    public static final String MIN_NUMBER = "Мін. кол.";
    public static final String DAY_ON_STOCK_SKU_START = "Днів на залишку SKU початок тенденції";
    public static final String DAY_ON_STOCK_SKU_END = "Днів на залишку SKU кінець тенденції";
    public static final String DAY_ON_STOCK_SKU = "Днів на залишку SKU";
    public static final String DAY_ON_STOCK_CSKU_START = "Днів на залишку CSKU початок тенденції";
    public static final String DAY_ON_STOCK_CSKU_END = "Днів на залишку CSKU кінець тенденції";
    public static final String DAY_ON_STOCK_CSKU = "Днів на залишку CSKU";

    public static final String REST_SKU = "Залишок SKU";
    public static final String REST_CSKU = "Залишок CSKU";

    public static final String SALES_SKU_START = "Продажі SKU початок тенденції";
    public static final String SALES_SKU_END = "Продажі SKU кінець тенденції";
    public static final String SALES_SKU = "Продажі SKU";
    public static final String SALES_CSKU_START = "Продажі CSKU початок тенденції";
    public static final String SALES_CSKU_END = "Продажі CSKU кінець тенденції";
    public static final String SALES_CSKU = "Продажі CSKU";

    public static final String AVG_SALES_SKU_START = "СПД SKU початку";
    public static final String AVG_SALES_SKU_END = "СПД SKU кінця";
    public static final String AVG_SALES_SKU = "СПД SKU";
    public static final String AVG_SALES_CSKU_START = "СПД CSKU початку";
    public static final String AVG_SALES_CSKU_END = "СПД CSKU кінця";
    public static final String AVG_SALES_CSKU = "СПД CSKU";

    public static final String DEMAND = "Потреба";
    public static final List<String> ATTRIBUTES = List.of(AM, MIN_NUMBER, DAY_ON_STOCK_SKU_START, DAY_ON_STOCK_SKU_END,
            DAY_ON_STOCK_SKU, DAY_ON_STOCK_CSKU_START, DAY_ON_STOCK_CSKU_END, DAY_ON_STOCK_CSKU, REST_SKU, REST_CSKU,
            SALES_SKU_START, SALES_SKU_END, SALES_SKU, SALES_CSKU_START, SALES_CSKU_END, SALES_CSKU, AVG_SALES_SKU_START,
            AVG_SALES_SKU_END, AVG_SALES_SKU, AVG_SALES_CSKU_START, AVG_SALES_CSKU_END, AVG_SALES_CSKU, DEMAND);


}
