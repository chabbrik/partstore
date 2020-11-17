package lt.lietpastas.partstore.constants;

import java.math.BigDecimal;

public class Constants {
    public static final BigDecimal BMW_DISCOUNT = new BigDecimal("0.05");
    public static final BigDecimal AUDI_THRESHOLD = new BigDecimal("200");
    public static final BigDecimal AUDI_DISCOUNT = new BigDecimal("0.088");
    public static final BigDecimal VW_THRESHOLD = new BigDecimal("150");
    public static final BigDecimal VW_DISCOUNT = new BigDecimal("0.035");

    public static final BigDecimal VW_MARGIN = new BigDecimal("1.225");
    public static final BigDecimal AUDI_MARGIN = new BigDecimal("1.152");
    public static final BigDecimal BMW_MARGIN = new BigDecimal("1.17");

    public static final String BMW = "BMW";
    public static final String AUDI = "Audi";
    public static final String VW = "Volkswagen";
}
