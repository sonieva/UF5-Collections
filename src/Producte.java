import java.util.Objects;

public abstract class Producte {
    private float preu;
    private String nom;
    private String codiBarres;

    public Producte(float preu, String nom, String codiBarres) {
        this.preu = preu;
        this.nom = nom;
        this.codiBarres = codiBarres;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodiBarres() {
        return codiBarres;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producte producte)) return false;

        return Objects.equals(getCodiBarres(), producte.getCodiBarres()) && Objects.equals(getPreu(), producte.getPreu());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodiBarres());
    }
}
