package lt.lietpastas.partstore.businesslayer;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MarginCalculator {
    private final BigDecimal VW_MARGIN = new BigDecimal("1.225");
    private final BigDecimal AUDI_MARGIN = new BigDecimal("1.152");
    private final BigDecimal BMW_MARGIN = new BigDecimal("1.17");
    public static final String BMW = "BMW";
    public static final String AUDI = "Audi";
    public static final String VW = "Volkswagen";

    public BigDecimal calculateFinalPrice(String brand, BigDecimal part) {

        /* Default value - no added profit margin */
        BigDecimal margin;

        switch (brand) {
            case BMW: margin = BMW_MARGIN; break;
            case AUDI: margin = AUDI_MARGIN; break;
            case VW: margin = VW_MARGIN; break;
            default: margin = new BigDecimal("1");
        }
        return part.multiply(margin);
    }
}
