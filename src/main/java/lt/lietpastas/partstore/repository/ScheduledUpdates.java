package lt.lietpastas.partstore.repository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdates {

    @Scheduled(cron = "0 0 1/4 ? * *")
    public void updateProductDatabase() {
        
    }
}
