package lt.lietpastas.partstore.dataloaders;

import lt.lietpastas.partstore.domain.MarginCalculator;
import lt.lietpastas.partstore.entities.CarPartDTO;
import lt.lietpastas.partstore.persistence.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class InitialDataLoader {

    @Autowired
    private CsvDataLoader csvDataLoader;

    @Autowired
    private HttpDataLoader httpDataLoader;

    @Autowired
    private HibernateUtil dbUtil;

    @Autowired
    private MarginCalculator marginCalculator;

    public void loadDBData() {
        Consumer<CarPartDTO> inserter = carPartEntry -> {
            String[] ids = carPartEntry.getItemCode().split(",");
            /*  The data is very tricky.
                Sometimes there are a few ids for a product.
                Then price is set for all IDs, but amount is individual
             */
            carPartEntry.setPrice(httpDataLoader.getLatestPrice(carPartEntry.getItemCode()));
            carPartEntry.setFinalPrice(marginCalculator.calculateFinalPrice(carPartEntry.getBrand(), carPartEntry.getPrice()));
            if (ids.length > 1) {

                for (String id : ids) {
                    CarPartDTO carPart = carPartEntry.clone();
                    carPart.setItemCode(carPartEntry.getItemCode());

                    carPart.setPrice(carPartEntry.getPrice());
                    carPart.setFinalPrice(marginCalculator.calculateFinalPrice(carPart.getBrand(), carPartEntry.getPrice()));

                    carPart.setAmountItemCode(id);
                    carPart.setAmount(httpDataLoader.getLatestCount(carPart.getAmountItemCode()));

                    dbUtil.save(carPart);
                }
            } else {
                carPartEntry.setAmountItemCode(carPartEntry.getItemCode());
                carPartEntry.setAmount(httpDataLoader.getLatestCount(carPartEntry.getItemCode()));
                dbUtil.save(carPartEntry);
            }
        };

        csvDataLoader.parseCsv().forEach(inserter);
    }
}
