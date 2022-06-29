package etake.autoorderbatch.util;

import java.util.List;

public final class Constants {
    public static final String NULL_VALUE = "#NULL!";

    public static final String SPACE = " ";
    public static final String ANALYST = "аналітик";

    public static final String COMPLIANCE_BASIC_DEMAND = "Відповідність базової потреби";
    public static final String COMPLIANCE_COEF_DEMAND = "Відповідність потреби з коефіцієнтами";
    public static final String COMPLIANCE_FINAL_DEMAND = "Відповідність фінальної потреби";

    public static final String YES = "Так";
    public static final String NO = "Ні";

    public static final String STORE = "Магазин";
    public static final String CATEGORY = "Категорія";
    public static final String GROUP_3 = "Група 3";
    public static final String CODE_SKU = "Код";
    public static final String NAME_SKU = "Номенклатура SKU";
    public static final String CODE_CSKU = "Код CSKU";
    public static final String NAME_CSKU = "Номенклатура CSKU";

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

    public static final String DEMAND_COEFFICIENT = "Коефіцієнт \"Потреби\"";
    public static final String SEASON_COEFFICIENT = "Коефіцієнт сезонності";
    public static final String DEMAND_MAIN = "Потреба базова";
    public static final String DEMAND_WITH_COEFFICIENTS = "Потреба з коефіцієнтами";
    public static final String DEMAND = "Потреба";
    public static final String R_PRICE = "Ціна роздрібна";
    public static final List<String> ATTRIBUTES = List.of(AM, MIN_NUMBER, DAY_ON_STOCK_SKU_START, DAY_ON_STOCK_SKU_END,
            DAY_ON_STOCK_SKU, DAY_ON_STOCK_CSKU_START, DAY_ON_STOCK_CSKU_END, DAY_ON_STOCK_CSKU, REST_SKU, REST_CSKU,
            SALES_SKU_START, SALES_SKU_END, SALES_SKU, SALES_CSKU_START, SALES_CSKU_END, SALES_CSKU, AVG_SALES_SKU_START,
            AVG_SALES_SKU_END, AVG_SALES_SKU, AVG_SALES_CSKU_START, AVG_SALES_CSKU_END, AVG_SALES_CSKU, DEMAND, DEMAND_MAIN,
            DEMAND_WITH_COEFFICIENTS, DEMAND_COEFFICIENT, SEASON_COEFFICIENT, R_PRICE, CODE_CSKU, NAME_CSKU);


}
