package lt.lietpastas.partstore;

import lt.lietpastas.partstore.dbhelper.DbLoader;
import lt.lietpastas.partstore.dbhelper.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class PartStoreApplication {

	public static void main(String[] args) {
//		SpringApplication.run(PartStoreApplication.class, args);
		DbLoader dbLoader = new DbLoader();
		dbLoader.cleanTxtData();

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<PartDTO> parts = session.createQuery("from CarPart", PartDTO.class).list();
			parts.forEach(part -> System.out.println(part.getPavadinimas()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
