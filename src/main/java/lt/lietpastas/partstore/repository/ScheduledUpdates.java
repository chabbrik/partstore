package lt.lietpastas.partstore.repository;

import lt.lietpastas.partstore.businessrules.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ScheduledUpdates {

    @Autowired
    private StoreDatabaseService storeDatabaseService;

    @Autowired
    private BusinessService businessService;

    @Scheduled(cron = "0 0 1/4 ? * *")
    public void updateProductDatabase() {
        List<CarPartDTO> results  = storeDatabaseService.getInventoryList();
        for (CarPartDTO part : results) {
            part.setAmount(storeDatabaseService.getLatestCount(part.getAmountItemCode()));

            BigDecimal newPrice = (storeDatabaseService.getLatestPrice(part.getItemCode()));
            part.setPrice(newPrice);
            part.setFinalPrice(businessService.calculateFinalPrice(part.getBrand(), newPrice));

            storeDatabaseService.save(part);
        }

    }
}
