package lt.lietpastas.partstore.repository;

import com.opencsv.bean.CsvToBeanBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class StoreDatabaseService {
    private final String DATASOURCE = "partsData.csv";
    private final String PROVIDER_URL = "http://localhost:8085";
    private final String FIRST_ROW_MARKER = "Pavadinimas";
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
            System.out.println(carPartEntry.toString());

            save(carPartEntry);

//            String[] ids = carPartEntry.getItemCode().split(",");
//
//            for (String id : ids) {
//                CarPartDTO carPart = new CarPartDTO(values);
//                carPart.setItemCode(id);
//                carPart.setPrice(getLatestPrice(carPart.getItemCode()));
//                carPart.setAmount(getLatestCount(carPart.getItemCode()));
//                save(carPart);
//            }
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

    private int getLatestPrice(String itemId) {
        String ITEM_URL = "/item/count/" + itemId;
        try {
            return client.get()
                    .uri(ITEM_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getLatestCount(String itemId) {
        String ITEM_URL = "/item/price/" + itemId;
        try {
            return client.get()
                    .uri(ITEM_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
