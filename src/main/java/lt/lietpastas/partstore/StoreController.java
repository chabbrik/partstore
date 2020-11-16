package lt.lietpastas.partstore;

import lt.lietpastas.partstore.businesslayer.MarginCalculator;
import lt.lietpastas.partstore.dblayer.CarPartDTO;
import lt.lietpastas.partstore.dblayer.StoreDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StoreController {

    @Autowired
    private StoreDatabaseService storeDatabaseService;

    @Autowired
    private MarginCalculator marginCalculator;

    @GetMapping("/list")
    public List<CarPartDTO> getPartList() {
        List<CarPartDTO> results  = storeDatabaseService.getInventoryList()
                .stream()
                .filter(x -> x.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "cart/updatecart", consumes = "application/json", produces = "application/json")
    public String addToCart(@RequestBody CartModel cartModel) {
        List<CarPartDTO> cartItems = storeDatabaseService.getCartObjects(cartModel);


        return "Success";
    }


}
