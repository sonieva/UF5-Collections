public class Electronica extends Producte {
    private int diesGarantia;

    public Electronica(float preu, String nom, String codiBarres, int diesGarantia) {
        super(preu, nom, codiBarres);
        this.diesGarantia = diesGarantia;
    }

    public int getDiesGarantia() {
        return diesGarantia;
    }

    @Override
    public float getPreu() {
        return (float) (super.getPreu() + super.getPreu() * (this.getDiesGarantia() / 365) * 0.1);
    }
}
