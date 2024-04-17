import java.util.Objects;

public class Textil extends Producte implements Comparable<Textil> {
    private String composicio;

    public Textil(float preu, String nom, String codiBarres, String composicio) {
        super(preu, nom, codiBarres);
        this.composicio = composicio;
    }

    public String getComposicio() {
        return composicio;
    }

    @Override
    public int compareTo(Textil t1) {
        return this.getComposicio().compareTo(t1.getComposicio());
    }
}
