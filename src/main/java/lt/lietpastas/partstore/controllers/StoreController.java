package lt.lietpastas.partstore.controllers;

import lt.lietpastas.partstore.persistence.HibernateUtil;
import lt.lietpastas.partstore.entities.CarPartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StoreController {

    @Autowired
    private HibernateUtil dbUtil;

    @GetMapping("/list")
    public List<CarPartDTO> getPartList() {
        List<CarPartDTO> results  = dbUtil.getInventoryList()
                .stream()
                .filter(x -> x.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        return results;
    }

    @PostMapping(path = "cart/updatecart", consumes = "application/json", produces = "application/json")
    public String addToCart() {
        return "Success";
    }
}
