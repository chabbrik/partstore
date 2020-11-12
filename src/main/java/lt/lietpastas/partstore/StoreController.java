package lt.lietpastas.partstore;

import lt.lietpastas.partstore.repository.CarPartDTO;
import lt.lietpastas.partstore.repository.StoreDatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {
    private StoreDatabaseService storeDatabaseService;

    public StoreController(StoreDatabaseService storeDatabaseService) {
        this.storeDatabaseService = storeDatabaseService;
    }

    @GetMapping("/list")
    public List<CarPartDTO> getPartList() {
        return storeDatabaseService.getInventoryList();
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
