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
- dataCaducitat - Data de caducitat del producte alimentari

### Setters i Getters
- getDataCaducitat - Retorna la data de caducitat del producte alimentari
- setDataCaducitat - **No implementat**
- getPreu - Sobreescriu al getter de la classe pare per ser calcular amb la següent fórmula ``preu - preu * ( 1 / (dataCaducitat - dataActual + 1)) + (preu * 0.1)``

### Mètodes
No en té

## Classe Textil
Subclasse de Producte que implemente l'interfície Comparable
### Constructor
````java
public Textil(float preu, String nom, String codiBarres, String composicio) {
    super(preu, nom, codiBarres);
    this.composicio = composicio;
}
````
- composicio - Composicio del producte tèxtil
### Setters i Getters
- getComposicio - Retorna la composició del producte textil
- setComposicio - **No implementat**

### Mètodes
- compareTo(Textil t1) - Utilitzat per comparar productes tèxtils per la seva composició

## Classe Electronica
Subclasse de Producte

### Constructor
````java
public Electronica(float preu, String nom, String codiBarres, int diesGarantia) {
    super(preu, nom, codiBarres);
    this.diesGarantia = diesGarantia;
}
````
- diesGarantia - Dies de la garantia del producte electronic

### Setters i Getters
- getDiesGarantia - Retorna els dies de garantia del producte electronic
- setDiesGarantia - **No implementat**
- getPreu - Sobreescriu al getter de la classe pare per ser calcular amb la següent fórmula ``preu + preu * (diesGarantia / 365) * 0.1``

### Mètodes
No en té

## Classe Compra
Classe utilitzada per afegir productes, passar per caixa, mostrar el carret i buscar productes pel codi de barres

### Constructor
````java
public Compra() {
    this.llistaAliments = new ArrayList<>();
    this.llistaTextils = new ArrayList<>();
    this.llistaElectronics = new ArrayList<>();
}
````
- llistaAliments - Llista per guardar productes d'alimentació
- llistaTextils - Llista per guardar productes tèxtils
- llistaElectronics - Llista per guardar productes electrònics

### Setters i Getters
No en té

### Mètodes
- afegirProducte(String tipusProducte) - Demana les dades adients a cada tipus de producte (nom, preu, codi de barres [si o si ha de tenir 6 caràcters], data de caducitat en el cas d'alimentació, composició en el cas de tèxtil i dies de garantia en cas d'electronic) depenent del tipus passat i les afegeix a la llista que pertoca abans comprovant que no hi hagi ja ficats 100 productes entre les tres llistes, si ja a arribat al límit, llança una Exception
- passarPerCaixa - Crea una llista de productes general i a continuació crea un HashSet per cada una de les tres llistes, per així obtenir tots els productes sense repetits de cada llista i introduir-los a la llista general. Seguidament, mostra la capçalera del tiquet amb el nom del supermercat i la data actual, recorre la llista general obtenint per cada producte la seva freqüencia en la llista pertinent per així saber les unitats i mostra per pantalla el nom del producte, les unitats, preu unitari i preu total, per últim, neteja les tres llistes de productes
- mostrarCarret - Crea una llista de productes general on introdueix tots els productes de les tres llistes i un HashMap anomenat carret amb el codi de barres com a clau i la quantitat del producte de valor, seguidament recorre la llista general i comprova per cada un si ja existeix el codi de barres al carret, si existeix, suma 1 a la quantitat, si no, afegeix el codi de barres i posa la quantitat a 1. A continuacio, per cada conjunt clau valor del carret mostra per pantalla el nom del producte utilitzant el mètode ``buscarProducte`` i la seva quantitat, per últim neteja el carret
- buscarProducte(String codiBarres) - Crea un Stream on afageix les tres llistes de productes passades a stream per després crear un Optional (ja que pot ser que no trobi el producte) i amb el Stream creat anteriorment fa un filtre per trobar el producte que el seu codi de barres coincideixi amb el codi de barres passat per parametre i agafa la primera coincidencia, si ho troba, retorna el nom del producte, si no, retorna "No s'ha trobat el producte"
