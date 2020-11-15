package lt.lietpastas.partstore.businessrules;

import lt.lietpastas.partstore.repository.CarPartDTO;
import org.springframework.beans.MutablePropertyValues;

import java.math.BigDecimal;
import java.util.List;

public interface Discount {
    String BMW_DISCOUNT = "0.05";
    String AUDI_THRESHOLD = "200";
    String AUDI_DISCOUNT = "0.088";
    String VW_THRESHOLD = "150";
    String VM_DISCOUNT = "0.035";

    BigDecimal calculateDiscount(List<CarPartDTO> cartItems);
}
