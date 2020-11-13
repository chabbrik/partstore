package lt.lietpastas.partstore.repository;

import javax.persistence.*;

@Entity
@Table(name = "CarPart")
public class CarPartDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Kaina
    @Column(name = "price")
    private String price;

    // Kiekis
    @Column(name = "amount")
    private String amount;

    // Pavadinimas
    @Column(name = "name")
    private String name;

    // Markė
    @Column(name = "brand")
    private String brand;

    // Metai
    // Specialiai naudoju String, nes kol kas nedarom jokių manipuliacijų.
    @Column(name = "year")
    private String year;

    // Galingumas
    @Column(name = "power")
    private String power;

    // Variklio tūris
    @Column(name = "engineVolume")
    private String engineVolume;

    // Degalų rūšis
    @Column(name = "fuelType")
    private String fuelType;

    // Pavarų dėžė
    @Column(name = "gearbox")
    private String gearbox;

    // Kodas
    @Column(name = "itemCode")
    private String itemCode;

    // Tiekėjas
    @Column(name = "supplier")
    private String supplier;

    // Tiekėjo adresas
    @Column(name = "supplierAddress")
    private String supplierAddress;

    public CarPartDTO() { }

    public CarPartDTO(String dbEntry) {
        String[] values = dbEntry.split(",(?=([^\"]|\"[^\"]*\")*$)");

        this.name = values[0];
        this.brand = values[1];
        this.year = values[2];
        this.power = values[3];
        this.engineVolume = values[4];
        this.fuelType = values[5];
        this.gearbox = values[6];
        this.itemCode = values[7];
        this.supplier = values[8];
        this.supplierAddress = values[9];
    }

//    public CarPartDTO(int id,
//                      String pavadinimas,
//                      String marke,
//                      String metai,
//                      String galingumas,
//                      String turis,
//                      String degalai,
//                      String deze,
//                      String prekesKodas,
//                      String tiekejas,
//                      String adresas) {
//        this.id = id;
//        this.name = pavadinimas;
//        this.brand = marke;
//        this.year = metai;
//        this.power = galingumas;
//        this.engineVolume = turis;
//        this.degalai = degalai;
//        this.deze = deze;
//        this.prekesKodas = prekesKodas;
//        this.tiekejas = tiekejas;
//        this.adresas = adresas;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



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
        builder.append(this.supplierAddress);
        builder.append(", ");
        builder.append(this.supplierAddress);

        return builder.toString();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
        this.itemCode = itemCode;
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
}
