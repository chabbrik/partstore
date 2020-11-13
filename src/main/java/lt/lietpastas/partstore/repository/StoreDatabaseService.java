package lt.lietpastas.partstore.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
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
        Consumer<String> inserter = s -> {
            CarPartDTO carPart = new CarPartDTO(s);
//            carPart.setKaina(getLatestPrice(carPart.getPrekesKodas()));
//            carPart.setKiekis(getLatestCount(carPart.getPrekesKodas()));
            save(carPart);
        };

        try {
            List<String> lines = Files.readAllLines(Paths.get(DATASOURCE), StandardCharsets.UTF_8);
            lines
                    .stream()
                    .filter(x -> !x.contains(FIRST_ROW_MARKER))
                    .forEach(inserter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLatestPrice(String itemId) {
        String ITEM_URL = "/item/count/" + itemId;
        return client.get()
                .uri(ITEM_URL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String getLatestCount(String itemId) {
        String ITEM_URL = "/item/price/" + itemId;
        return client.get()
                .uri(ITEM_URL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
