package lt.lietpastas.partstore.businessrules;

import java.math.BigDecimal;

public interface Discount {

    BigDecimal calculateCartValue();
    BigDecimal calculateDiscount();
}
