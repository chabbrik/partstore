package lt.lietpastas.partstore.repository;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CarPart")
public class CarPartDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @CsvBindByName(column = "Pavadinimas")
    @Column(name = "name")
    private String name;

    @CsvBindByName(column = "Markė")
    @Column(name = "brand")
    private String brand;

    @CsvBindByName(column = "Metai")
    @Column(name = "year")
    private String year;

    @CsvBindByName(column = "Galingumas")
    @Column(name = "power")
    private String power;

    @CsvBindByName(column = "Variklio tūris")
    @Column(name = "engineVolume")
    private String engineVolume;

    @CsvBindByName(column = "Degalų rūšis")
    @Column(name = "fuelType")
    private String fuelType;

    @CsvBindByName(column = "Pavarų dėžė")
    @Column(name = "gearbox")
    private String gearbox;

    @CsvBindByName(column = "Kodas")
    @Column(name = "itemCode")
    private String itemCode;

    @CsvBindByName(column = "Tiekėjas")
    @Column(name = "supplier")
    private String supplier;

    @CsvBindByName(column = "Tiekėjo adresas")
    @Column(name = "supplierAddress")
    private String supplierAddress;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private BigDecimal price;
    private BigDecimal finalPrice;
    private String amountItemCode;

    public CarPartDTO() { }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.price);
        builder.append(", ");
        builder.append(this.amount);
        builder.append(", ");
        builder.append(this.name);
        builder.append(", ");
        builder.append(this.brand);
        builder.append(", ");
        builder.append(this.year);
        builder.append(", ");
        builder.append(this.power);
        builder.append(", ");
        builder.append(this.engineVolume);
        builder.append(", ");
        builder.append(this.gearbox);
        builder.append(", ");
        builder.append(this.fuelType);
        builder.append(", ");
        builder.append(this.itemCode);
        builder.append(", ");
        builder.append(this.supplier);
        builder.append(", ");
        builder.append(this.supplierAddress);

        return builder.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(String engineVolume) {
        this.engineVolume = engineVolume;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public CarPartDTO clone() {
        /* For some reason super.clone was throwing exceptions.
        *  So I am making hand-made version.
        *  itemCode, price and amount will be set individually.
        */
        CarPartDTO clone = new CarPartDTO();
        clone.name = this.name;
        clone.brand = this.brand;
        clone.year = this.year;
        clone.power = this.power;
        clone.engineVolume = this.engineVolume;
        clone.fuelType = this.fuelType;
        clone.gearbox = this.gearbox;
        clone.supplier = this.supplier;
        clone.supplierAddress = this.supplierAddress;
        return clone;

    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }


    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    /* GOTCHD: I need a separate code to fetch number of parts */
    /* GOTCHD: For some weird reason, ids to fetch amount and price are different */
    public void setAmountItemCode(String amountItemCode) {
        this.amountItemCode = amountItemCode;
    }

    public String getAmountItemCode() {
        return amountItemCode;
    }
}
