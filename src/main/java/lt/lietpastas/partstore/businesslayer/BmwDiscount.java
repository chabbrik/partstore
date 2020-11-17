package lt.lietpastas.partstore.businesslayer;

import lt.lietpastas.partstore.entities.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

import static lt.lietpastas.partstore.constants.Constants.BMW;
import static lt.lietpastas.partstore.constants.Constants.BMW_DISCOUNT;

public class BmwDiscount implements Discount{

    @Override
    public BigDecimal calculateDiscount(List<CarPartDTO> cartItems) {
        return cartItems
                .stream()
                .filter(x -> x.getBrand().equals(BMW))
                .map(x -> x.getFinalPrice()
                        .multiply(BMW_DISCOUNT
                                .multiply(x.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
