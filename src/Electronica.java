public class Electronica extends Producte {
    private int diesGarantia;

    public Electronica(double preu, String nom, String codiBarres, int diesGarantia) {
        super(preu, nom, codiBarres);
        this.diesGarantia = diesGarantia;
    }

    public int getDiesGarantia() {
        return diesGarantia;
    }

    @Override
    public double getPreu() {
        return super.getPreu() + super.getPreu() * ((double) this.getDiesGarantia() / 365) * 0.1;
    }
}
