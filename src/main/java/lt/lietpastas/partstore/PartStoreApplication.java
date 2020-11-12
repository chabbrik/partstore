package lt.lietpastas.partstore;

import lt.lietpastas.partstore.repository.StoreDatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PartStoreApplication {
	public PartStoreApplication(StoreDatabaseService storeDatabaseService) {
		storeDatabaseService.loadDBData();
	}
	public static void main(String[] args) {
		SpringApplication.run(PartStoreApplication.class, args);
	}
}
