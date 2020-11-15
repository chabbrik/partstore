package lt.lietpastas.partstore.businessrules;

import lt.lietpastas.partstore.repository.CarPartDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class VwDiscount implements Discount {
    @Override
    public BigDecimal calculateDiscount(List<CarPartDTO> cartItems) {
        List<BigDecimal> vwCartItemValues = cartItems
                .stream()
                .filter(x -> x.getBrand().equals(BusinessService.VW))
                .map(x -> x.getFinalPrice()
                        .multiply(x.getAmount()))
                .collect(Collectors.toList());

        BigDecimal VWCartValue = vwCartItemValues.stream().reduce(BigDecimal.ZERO, BigDecimal::add);


        if (VWCartValue.compareTo(new BigDecimal(VW_THRESHOLD)) >= 0) {
            return VWCartValue.multiply(new BigDecimal(VM_DISCOUNT));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
