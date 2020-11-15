package lt.lietpastas.partstore;

import lt.lietpastas.partstore.businessrules.AudiProfitMargin;
import lt.lietpastas.partstore.businessrules.BusinessService;
import lt.lietpastas.partstore.repository.CarPartDTO;
import lt.lietpastas.partstore.repository.StoreDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StoreController {

    @Autowired
    private StoreDatabaseService storeDatabaseService;

    @Autowired
    private BusinessService businessService;

//    public StoreController(StoreDatabaseService storeDatabaseService) {
//        this.storeDatabaseService = storeDatabaseService;
//    }

    @GetMapping("/list")
    public List<CarPartDTO> getPartList() {
        List<CarPartDTO> results  = storeDatabaseService.getInventoryList()
                .stream()
                .filter(x -> x.getAmount() > 0)
                .collect(Collectors.toList());
        return results;
    }

    @PostMapping(path = "cart/updatecart", consumes = "application/json", produces = "application/json")
    public String addToCart(@PathVariable String id) {

        return "Success";
    }


}
