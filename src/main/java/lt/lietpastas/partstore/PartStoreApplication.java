package lt.lietpastas.partstore;

import lt.lietpastas.partstore.dataloaders.InitialDataLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PartStoreApplication {
	public PartStoreApplication(InitialDataLoader initialDataLoader) {
		initialDataLoader.loadDBData();
	}
	public static void main(String[] args) {
		SpringApplication.run(PartStoreApplication.class, args);
	}
}
