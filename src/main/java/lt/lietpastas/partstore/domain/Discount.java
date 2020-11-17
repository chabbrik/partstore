package lt.lietpastas.partstore.domain;

import lt.lietpastas.partstore.entities.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

public abstract class Discount {

    BigDecimal calculateBrandTotalValue(List<CarPartDTO> cartItems, String brand, BigDecimal discount) {
        return cartItems
                .stream()
                .filter(x -> x.getBrand().equals(brand))
                .map(x -> x.getFinalPrice().multiply(discount.multiply(x.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    abstract BigDecimal calculateDiscount(List<CarPartDTO> cartItems);
}
