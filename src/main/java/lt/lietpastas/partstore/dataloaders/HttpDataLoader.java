package lt.lietpastas.partstore.dataloaders;

import lt.lietpastas.partstore.entities.CountDTO;
import lt.lietpastas.partstore.entities.PriceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class HttpDataLoader {
    Logger logger = LoggerFactory.getLogger(HttpDataLoader.class);

    String PRICE_URL = "/item/price/";
    String ITEM_URL = "/item/count/";

    private final WebClient client;

    public HttpDataLoader(Environment environment) {
        String url = environment.getProperty("provider.url");

        if (url == null) {
            throw new IllegalArgumentException("No Provider URL loaded");
        }

        client = WebClient.create(url);
    }

    public BigDecimal getLatestPrice(String itemId) {

        try {
            PriceDTO result = client.get()
                    .uri(PRICE_URL + itemId)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(PriceDTO.class)
                    .block();
            logger.debug(PRICE_URL + itemId + " = " + result.getValue());

            return result.getMonetaryValue();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new BigDecimal(0);
    }

    public BigDecimal getLatestCount(String itemId) {
        try {
            CountDTO result = client.get()
                    .uri(ITEM_URL + itemId)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(CountDTO.class)
                    .block();
            logger.debug(ITEM_URL + itemId + " = " + result.getValue());
            return result.getValue();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
