public class Electronica extends Producte {
    private final int DIESGARANTIA;

    public Electronica(float preu, String nom, String codiBarres, int diesGarantia) {
        super(preu, nom, codiBarres);
        this.DIESGARANTIA = diesGarantia;
    }

    public int getDiesGarantia() {
        return DIESGARANTIA;
    }

    @Override
    public float getPreu() {
        return (float) (super.getPreu() + super.getPreu() * (this.getDiesGarantia() / 365) * 0.1);
    }
}
