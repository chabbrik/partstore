package lt.lietpastas.partstore.businessrules;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessService implements Discount {
    public enum carBrands {
        BMW,
        Audi,
        Volkswagen
    }
    private Map<carBrands, ProfitMargin> brandProfitMargins = new HashMap();

    public BusinessService() {
        brandProfitMargins.put(carBrands.BMW, new BMWProfitMargin());
        brandProfitMargins.put(carBrands.Audi, new AudiProfitMargin());
        brandProfitMargins.put(carBrands.Volkswagen, new VWProfitMargin());
    }

    @Override
    public BigDecimal calculateCartValue() {
        return null;
    }

    @Override
    public BigDecimal calculateDiscount() {
        return null;
    }

    public BigDecimal calculateFinalPrice(String brand, BigDecimal part) {
        ProfitMargin profitMarginStrategy = brandProfitMargins.get(carBrands.valueOf(brand));
        profitMarginStrategy.calculateFinalPrice(part);
        return part;
    }
}
