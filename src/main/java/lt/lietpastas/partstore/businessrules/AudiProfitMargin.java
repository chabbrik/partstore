package lt.lietpastas.partstore.businessrules;

import java.math.BigDecimal;

public class AudiProfitMargin implements ProfitMargin{
    private final BigDecimal MARGIN = new BigDecimal("1.152");

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal part) {
        return part.multiply(MARGIN);
    }

}
