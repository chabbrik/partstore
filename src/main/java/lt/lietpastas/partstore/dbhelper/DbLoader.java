package lt.lietpastas.partstore.dbhelper;

import lt.lietpastas.partstore.PartDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

public class DbLoader {
    String DATASOURCE = "partsData.csv";
    String BMW = "BMW";
    String VW = "Volkswagen";
    String AUDI = "Audi";
    String FIRST_ROW_MARKER = "Pavadinimas";

    private void save(PartDTO part) {
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

    public DbLoader() {}

    public void cleanTxtData() {
        Consumer<String> inserter = s -> {
            save(new PartDTO(s));
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

}
