package lt.lietpastas.partstore.businesslayer;

import lt.lietpastas.partstore.entities.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

public interface Discount {

    BigDecimal calculateDiscount(List<CarPartDTO> cartItems);
}
