import java.time.Duration;
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
        return super.getPreu() - super.getPreu() * ((double) 1 / (Duration.between(this.getDataCaducitat(), LocalDateTime.now()).toDays() + 1)) + (super.getPreu() * 0.1);
    }
}
