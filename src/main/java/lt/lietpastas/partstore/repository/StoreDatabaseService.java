package lt.lietpastas.partstore.repository;

import com.opencsv.bean.CsvToBeanBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class StoreDatabaseService {
    private final String DATASOURCE = "partsData.csv";
    private final String PROVIDER_URL = "http://localhost:8085";
    private final WebClient client;

    private void save(CarPartDTO part) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(part);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public StoreDatabaseService() {
        client = WebClient.create(PROVIDER_URL);
    }

    public List<CarPartDTO> getInventoryList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CarPartDTO", CarPartDTO.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void loadDBData() {
        Consumer<CarPartDTO> inserter = carPartEntry -> {
            String[] ids = carPartEntry.getItemCode().split(",");
            /*  The data is very tricky.
                Sometimes there are a few ids for a product.
                Then price is set for all IDs, but amount is individual
             */
            if (ids.length > 1) {
                BigDecimal price = getLatestPrice(carPartEntry.getItemCode());
                for (String id : ids) {
                    CarPartDTO carPart = carPartEntry.clone();
                    carPart.setItemCode(id);
                    carPart.setPrice(price);
                    carPart.setAmount(getLatestCount(carPart.getItemCode()));
                    save(carPart);
                }
            } else {
                carPartEntry.setPrice(getLatestPrice(carPartEntry.getItemCode()));
                carPartEntry.setAmount(getLatestCount(carPartEntry.getItemCode()));
                save(carPartEntry);
            }



        };

        try {
            Reader reader = new InputStreamReader(new FileInputStream(DATASOURCE), "UTF-8");
            List<CarPartDTO> beans = new CsvToBeanBuilder(reader)
                    .withType(CarPartDTO.class).build().parse();
            beans.forEach(inserter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BigDecimal getLatestPrice(String itemId) {
        String PRICE_URL = "/item/price/" + itemId;
        try {
            PriceDTO result = client.get()
                    .uri(PRICE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(PriceDTO.class)
                    .block();
            System.out.println(PRICE_URL + " = " + result.getValue());

            return result.getMonetaryValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new BigDecimal(0);
    }

    private int getLatestCount(String itemId) {
        String ITEM_URL = "/item/count/" + itemId;
        try {
            CountDTO result = client.get()
                    .uri(ITEM_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(CountDTO.class)
                    .block();
            System.out.println(ITEM_URL + " = " + result.getValue());
            return result.getValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
