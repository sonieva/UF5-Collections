import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Alimentacio extends Producte {
    private final LocalDate DATACADUCITAT;

    public Alimentacio(float preu, String nom, String codiBarres, LocalDate dataCaducitat) {
        super(preu, nom, codiBarres);
        this.DATACADUCITAT = dataCaducitat;
    }

    public LocalDate getDataCaducitat() {
        return DATACADUCITAT;
    }

    @Override
    public float getPreu() {
        return (float) ((super.getPreu() - super.getPreu() * ((float) 1 / (getDataCaducitat().until(LocalDate.now(), ChronoUnit.DAYS)) + 1)) + (super.getPreu() * 0.1));
    }
}
