package lt.lietpastas.partstore.schedulers;

import lt.lietpastas.partstore.businesslayer.MarginCalculator;
import lt.lietpastas.partstore.dataloaders.HttpDataLoader;
import lt.lietpastas.partstore.entities.CarPartDTO;
import lt.lietpastas.partstore.persistence.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ScheduledUpdates {

    @Autowired
    private MarginCalculator marginCalculator;

    @Autowired
    private HibernateUtil dbUtil;

    @Autowired
    private HttpDataLoader httpDataLoader;

    @Scheduled(cron = "0 0 1/4 ? * *")
    public void updateProductDatabase() {
        List<CarPartDTO> results  = dbUtil.getInventoryList();
        for (CarPartDTO part : results) {
            part.setAmount(httpDataLoader.getLatestCount(part.getAmountItemCode()));

            BigDecimal newPrice = httpDataLoader.getLatestPrice(part.getItemCode());
            part.setPrice(newPrice);
            part.setFinalPrice(marginCalculator.calculateFinalPrice(part.getBrand(), newPrice));

            dbUtil.save(part);
        }

    }
}
