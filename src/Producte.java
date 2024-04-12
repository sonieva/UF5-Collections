public abstract class Producte {
    private double preu;
    private String nom;
    private String codiBarres;

    public Producte(double preu, String nom, String codiBarres) {
        this.preu = preu;
        this.nom = nom;
        this.codiBarres = codiBarres;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
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
}
