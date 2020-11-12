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
    @Column(name = "kaina")
    private String kaina;

    // Kiekis
    @Column(name = "kiekis")
    private String kiekis;

    public void setKiekis(String kiekis) {
        this.kiekis = kiekis;
    }

    // Pavadinimas
    @Column(name = "pavadinimas")
    private String pavadinimas;

    // Markė
    @Column(name = "marke")
    private String marke;

    // Metai
    // Specialiai naudoju String, nes kol kas nedarom jokių manipuliacijų.
    @Column(name = "metai")
    private String metai;

    // Galingumas
    @Column(name = "galingumas")
    private String galingumas;

    // Variklio tūris
    @Column(name = "turis")
    private String turis;

    // Degalų rūšis
    @Column(name = "degalai")
    private String degalai;

    // Pavarų dėžė
    @Column(name = "deze")
    private String deze;

    // Kodas
    @Column(name = "prekesKodas")
    private String prekesKodas;

    // Tiekėjas
    @Column(name = "tiekejas")
    private String tiekejas;

    // Tiekėjo adresas
    @Column(name = "adresas")
    private String adresas;

    public CarPartDTO() { }

    public CarPartDTO(String dbEntry) {
        String[] values = dbEntry.split(",(?=([^\"]|\"[^\"]*\")*$)");

        this.pavadinimas = values[0];
        this.marke = values[1];
        this.metai = values[2];
        this.galingumas = values[3];
        this.turis = values[4];
        this.degalai = values[5];
        this.deze = values[6];
        this.prekesKodas = values[7];
        this.tiekejas = values[8];
        this.adresas = values[9];
    }

    public CarPartDTO(int id,
                      String pavadinimas,
                      String marke,
                      String metai,
                      String galingumas,
                      String turis,
                      String degalai,
                      String deze,
                      String prekesKodas,
                      String tiekejas,
                      String adresas) {
        this.id = id;
        this.pavadinimas = pavadinimas;
        this.marke = marke;
        this.metai = metai;
        this.galingumas = galingumas;
        this.turis = turis;
        this.degalai = degalai;
        this.deze = deze;
        this.prekesKodas = prekesKodas;
        this.tiekejas = tiekejas;
        this.adresas = adresas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getMetai() {
        return metai;
    }

    public void setMetai(String metai) {
        this.metai = metai;
    }

    public String getGalingumas() {
        return galingumas;
    }

    public void setGalingumas(String galingumas) {
        this.galingumas = galingumas;
    }

    public String getTuris() {
        return turis;
    }

    public void setTuris(String turis) {
        this.turis = turis;
    }

    public String getDegalai() {
        return degalai;
    }

    public void setDegalai(String degalai) {
        this.degalai = degalai;
    }

    public String getDeze() {
        return deze;
    }

    public void setDeze(String deze) {
        this.deze = deze;
    }

    public String getPrekesKodas() {
        return prekesKodas;
    }

    public void setPrekesKodas(String prekesKodas) {
        this.prekesKodas = prekesKodas;
    }

    public String getTiekejas() {
        return tiekejas;
    }

    public void setTiekejas(String tiekejas) {
        this.tiekejas = tiekejas;
    }

    public String getAdresas() {
        return adresas;
    }

    public void setAdresas(String adresas) {
        this.adresas = adresas;
    }

    public String getKaina() {
        return kaina;
    }

    public void setKaina(String kaina) {
        this.kaina = kaina;
    }

    public String getKiekis() {
        return kiekis;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(kaina);
        builder.append(", ");
        builder.append(kiekis);
        builder.append(", ");
        builder.append(pavadinimas);
        builder.append(", ");
        builder.append(marke);
        builder.append(", ");
        builder.append(metai);
        builder.append(", ");
        builder.append(galingumas);
        builder.append(", ");
        builder.append(turis);
        builder.append(", ");
        builder.append(deze);
        builder.append(", ");
        builder.append(degalai);
        builder.append(", ");
        builder.append(prekesKodas);
        builder.append(", ");
        builder.append(tiekejas);
        builder.append(", ");
        builder.append(adresas);

        return builder.toString();
    }
}
