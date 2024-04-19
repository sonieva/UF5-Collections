# UF5-Collections
## Classe Producte
Classe abstracte pare de les subclasses Alimentacio, Textil i Electronica
### Constructor
````java 
public Producte(float preu, String nom, String codiBarres) {
    this.preu = preu;
    this.nom = nom;
    this.codiBarres = codiBarres;
} 
````
- preu - Preu del producte
- nom - Nom del producte
- codiBarres - Codi de barres del producte

### Setters i Getters
- getPreu - Retorna el preu del producte
- setPreu - Canvia el preu del producte (Implementat però no utilitzat)
- getNom - Retorna el nom del producte
- setNom - Canvia el nom del producte (Implementat però no utilitzat)
- getCodiBarres - Retorna el codi de barres del producte
- setCodiBarres - **No implementat**

### Mètodes
- equals(Object o) - Utilitzat per comparar dos productes pel codi de barres i el preu
- hashCode() - Retorna el hash code del producte que en aquest cas es el codi de barres

## Classe Alimentacio
Subclasse de Producte

### Constructor
````java
public Alimentacio(float preu, String nom, String codiBarres, LocalDate dataCaducitat) {
    super(preu, nom, codiBarres);
    this.dataCaducitat = dataCaducitat;
}
````
- dataCaducitat - Data de caducitat del producte

### Setters i Getters
- getDataCaducitat - Retorna la data de caducitat del producte
- setDataCaducitat - **No implementat**
- getPreu - Sobreescriu al getter de la classe pare per ser calcular amb la següent fórmula. ``preu - preu * ( 1 / (dataCaducitat - dataActual + 1)) + (preu * 0.1)``

### Mètodes
No hi ha