package lt.lietpastas.partstore.domain;

import lt.lietpastas.partstore.entities.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

import static lt.lietpastas.partstore.constants.Constants.BMW;
import static lt.lietpastas.partstore.constants.Constants.BMW_DISCOUNT;

public class BmwDiscount extends Discount{

    @Override
    public BigDecimal calculateDiscount(List<CarPartDTO> cartItems) {
        return calculateBrandTotalValue(cartItems, BMW, BMW_DISCOUNT);
    }
}
