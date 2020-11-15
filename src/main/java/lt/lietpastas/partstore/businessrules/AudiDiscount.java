package lt.lietpastas.partstore.businessrules;

import lt.lietpastas.partstore.repository.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;

public class AudiDiscount implements Discount {
    @Override
    public BigDecimal calculateDiscount(List<CarPartDTO> cartItems) {
        /* Calculating value of all parts in the cart*/
        BigDecimal totalCartValue = cartItems
                .stream()
                .map(x -> x.getFinalPrice()
                        .multiply(x.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCartValue.compareTo(new BigDecimal(AUDI_THRESHOLD)) >= 0) {
            BigDecimal AudiPartValue = cartItems
                    .stream()
                    .filter(x -> x.getBrand().equals(BusinessService.AUDI))
                    .map(x -> x.getFinalPrice()
                            .multiply(x.getAmount()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return AudiPartValue.multiply(new BigDecimal(AUDI_DISCOUNT));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
