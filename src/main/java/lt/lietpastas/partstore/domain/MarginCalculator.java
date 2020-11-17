package lt.lietpastas.partstore.businesslayer;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static lt.lietpastas.partstore.constants.Constants.*;

@Service
public class MarginCalculator {
    public BigDecimal calculateFinalPrice(String brand, BigDecimal part) {

        /* Default value - no added profit margin */
        BigDecimal margin;

        switch (brand) {
            case BMW: margin = BMW_MARGIN; break;
            case AUDI: margin = AUDI_MARGIN; break;
            case VW: margin = VW_MARGIN; break;
            default: margin = BigDecimal.ONE;
        }
        return part.multiply(margin);
    }
}
