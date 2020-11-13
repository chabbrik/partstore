package lt.lietpastas.partstore;

import lt.lietpastas.partstore.repository.CarPartDTO;
import lt.lietpastas.partstore.repository.StoreDatabaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StoreController {
    private StoreDatabaseService storeDatabaseService;

    public StoreController(StoreDatabaseService storeDatabaseService) {
        this.storeDatabaseService = storeDatabaseService;
    }

    @GetMapping("/list")
    public List<CarPartDTO> getPartList() {
        List<CarPartDTO> results = storeDatabaseService.getInventoryList();
        results.forEach(s-> {System.out.println(s.toString());});
        return results;
    }

    @PostMapping("cart/add/{id}")
    public String addToCart(@PathVariable String id) {

        return "Success";
    }

    @PostMapping("cart/remove/{id}")
    public String removeFromCart(@PathVariable String id) {

        return "Success";
    }
}
