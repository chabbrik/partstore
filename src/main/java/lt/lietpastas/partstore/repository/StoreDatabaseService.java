package lt.lietpastas.partstore.repository;

import com.opencsv.bean.CsvToBeanBuilder;
import lt.lietpastas.partstore.businessrules.BusinessService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class StoreDatabaseService {

    @Autowired
    private HibernateUtil dbUtil;

    @Autowired
    private BusinessService businessService;

    private final WebClient client;

    public void save(CarPartDTO part) {
        Transaction transaction = null;
        try (Session session = dbUtil.getSessionFactory().openSession()) {
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
        String PROVIDER_URL = "http://localhost:8085";
        client = WebClient.create(PROVIDER_URL);
    }

    public List<CarPartDTO> getInventoryList() {
        try (Session session = dbUtil.getSessionFactory().openSession()) {
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
            carPartEntry.setPrice(getLatestPrice(carPartEntry.getItemCode()));
            carPartEntry.setFinalPrice(businessService.calculateFinalPrice(carPartEntry.getBrand(), carPartEntry.getPrice()));
            if (ids.length > 1) {

                for (String id : ids) {
                    CarPartDTO carPart = carPartEntry.clone();
                    carPart.setItemCode(carPartEntry.getItemCode());

                    carPart.setPrice(carPartEntry.getPrice());
                    carPart.setFinalPrice(businessService.calculateFinalPrice(carPart.getBrand(), carPartEntry.getPrice()));

                    carPart.setAmountItemCode(id);
                    carPart.setAmount(getLatestCount(carPart.getAmountItemCode()));

                    save(carPart);
                }
            } else {
                carPartEntry.setAmountItemCode(carPartEntry.getItemCode());
                carPartEntry.setAmount(getLatestCount(carPartEntry.getItemCode()));
                save(carPartEntry);
            }
        };

        try {
            String DATASOURCE = "partsData.csv";
            Reader reader = new InputStreamReader(new FileInputStream(DATASOURCE), StandardCharsets.UTF_8);
            List<CarPartDTO> beans = new CsvToBeanBuilder(reader)
                    .withType(CarPartDTO.class).build().parse();
            beans.forEach(inserter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getLatestPrice(String itemId) {
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

//    public List<CarPartDTO> getCartObjects(List<String> ids) {
//        List<CarPartDTO> list = null;
////        try (Session session = dbUtil.getSessionFactory().openSession()) {
////            list = new ArrayList<>(ids.size());
////            for (Integer id : ids)
////                list.add(session.load(CarPartDTO.class, id, LockOptions.NONE));
////            return list;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        return Collections.emptyList();
//    }

    public BigDecimal getLatestCount(String itemId) {
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
        return BigDecimal.ZERO;
    }
}
