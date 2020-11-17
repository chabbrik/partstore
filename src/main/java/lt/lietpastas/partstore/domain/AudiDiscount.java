package lt.lietpastas.partstore.domain;

import lt.lietpastas.partstore.entities.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

import static lt.lietpastas.partstore.constants.Constants.*;

public class AudiDiscount implements Discount {
    @Override
    public BigDecimal calculateDiscount(List<CarPartDTO> cartItems) {
        /* Calculating value of all parts in the cart */
        BigDecimal totalCartValue = cartItems
                .stream()
                .map(x -> x.getFinalPrice()
                        .multiply(x.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCartValue.compareTo(AUDI_THRESHOLD) >= 0) {
            BigDecimal AudiPartValue = cartItems
                    .stream()
                    .filter(x -> x.getBrand().equals(AUDI))
                    .map(x -> x.getFinalPrice()
                            .multiply(x.getAmount()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return AudiPartValue.multiply(AUDI_DISCOUNT);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
