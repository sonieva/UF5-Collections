import java.util.Objects;

public class Textil extends Producte implements Comparable<Textil> {
    private final String COMPOSICIO;

    public Textil(float preu, String nom, String codiBarres, String composicio) {
        super(preu, nom, codiBarres);
        this.COMPOSICIO = composicio;
    }

    public String getComposicio() {
        return COMPOSICIO;
    }

    @Override
    public int compareTo(Textil t1) {
        return this.getComposicio().compareTo(t1.getComposicio());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Textil textil)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(getCodiBarres(), textil.getCodiBarres());
    }
}
