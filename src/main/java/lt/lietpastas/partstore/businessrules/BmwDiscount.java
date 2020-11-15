package lt.lietpastas.partstore.businessrules;

import lt.lietpastas.partstore.repository.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

public class BmwDiscount implements Discount{

    @Override
    public BigDecimal calculateDiscount(List<CarPartDTO> cartItems) {
        return cartItems
                .stream()
                .filter(x -> x.getBrand().equals(BusinessService.BMW))
                .map(x -> x.getFinalPrice()
                        .multiply(new BigDecimal(BMW_DISCOUNT)
                                .multiply(x.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
