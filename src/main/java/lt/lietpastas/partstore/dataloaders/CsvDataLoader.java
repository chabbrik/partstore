package lt.lietpastas.partstore.dataloaders;

import com.opencsv.bean.CsvToBeanBuilder;
import lt.lietpastas.partstore.entities.CarPartDTO;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
public class CsvDataLoader {
    String datasource;
    public CsvDataLoader(Environment environment) {
        datasource = environment.getProperty("csv.datasource");

        if (datasource == null) {
            throw new IllegalArgumentException("No CSV datasource provided");
        }
    }

    public List<CarPartDTO> parseCsv() {
        try {
            Reader reader = null;
            reader = new InputStreamReader(new FileInputStream(datasource), StandardCharsets.UTF_8);
            List<CarPartDTO> beans = new CsvToBeanBuilder(reader)
                    .withType(CarPartDTO.class).build().parse();
            return beans;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
}
