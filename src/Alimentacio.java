import java.time.LocalDateTime;

public class Alimentacio extends Producte {
    private LocalDateTime dataCaducitat;

    public Alimentacio(double preu, String nom, String codiBarres, LocalDateTime dataCaducitat) {
        super(preu, nom, codiBarres);
        this.dataCaducitat = dataCaducitat;
    }

    public LocalDateTime getDataCaducitat() {
        return dataCaducitat;
    }

    @Override
    public double getPreu() {
    }
}
