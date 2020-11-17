package lt.lietpastas.partstore.domain;

import lt.lietpastas.partstore.entities.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

import static lt.lietpastas.partstore.constants.Constants.*;

public class VwDiscount implements Discount {
    @Override
    public BigDecimal calculateDiscount(List<CarPartDTO> cartItems) {
        BigDecimal VWCartValue = cartItems
                .stream()
                .filter(x -> x.getBrand().equals(VW))
                .map(x -> x.getFinalPrice()
                        .multiply(x.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (VWCartValue.compareTo(VW_THRESHOLD) >= 0) {
            return VWCartValue.multiply(VW_DISCOUNT);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
