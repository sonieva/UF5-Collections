import java.time.Duration;
import java.time.LocalDate;

public class Alimentacio extends Producte {
    private LocalDate dataCaducitat;

    public Alimentacio(float preu, String nom, String codiBarres, LocalDate dataCaducitat) {
        super(preu, nom, codiBarres);
        this.dataCaducitat = dataCaducitat;
    }

    public LocalDate getDataCaducitat() {
        return dataCaducitat;
    }

    @Override
    public float getPreu() {
        return (float) (super.getPreu() - super.getPreu() * (1 / (Duration.between(this.getDataCaducitat(), LocalDate.now()).toDays() + 1)) + (super.getPreu() * 0.1));
    }
}
