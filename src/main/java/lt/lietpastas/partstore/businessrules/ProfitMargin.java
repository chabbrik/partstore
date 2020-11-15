package lt.lietpastas.partstore.businessrules;

import java.math.BigDecimal;

public interface ProfitMargin {
    BigDecimal calculateFinalPrice(BigDecimal part);
}
