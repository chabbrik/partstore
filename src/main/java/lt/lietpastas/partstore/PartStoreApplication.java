package lt.lietpastas.partstore;

import lt.lietpastas.partstore.repository.StoreDatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PartStoreApplication {
	public PartStoreApplication(StoreDatabaseService storeDatabaseService) {
		storeDatabaseService.loadDBData();
	}
	public static void main(String[] args) {
		SpringApplication.run(PartStoreApplication.class, args);
	}
}
