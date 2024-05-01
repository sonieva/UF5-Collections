# UF5-Collections
Practica final de la UF5 del M03 de 1r de DAW

## Classes

### Producte
Classe abstracte pare de les subclasses Alimentacio, Textil i Electronica
#### Constructor
````java
public Producte(float preu, String nom, String codiBarres) {  
    this.preu = preu;  
    this.nom = nom;  
    this.CODIBARRES = codiBarres;  
}  
````  
- preu - Preu del producte
- nom - Nom del producte
- CODIBARRES - Codi de barres del producte

#### Setters i Getters
- getPreu - Retorna el preu del producte
- setPreu - Canvia el preu del producte
- getNom - Retorna el nom del producte
- setNom - Canvia el nom del producte (Implementat però no utilitzat)
- getCodiBarres - Retorna el codi de barres del producte
- setCodiBarres - **No implementat**

#### Mètodes
- ``equals(Object o)`` - Utilitzat per comprovar si dos productes son iguals pel codi de barres i el preu
- ``hashCode()`` - Retorna el hash code del producte que en aquest cas es el codi de barres

#### Extras
- ``ComparadorNom`` - Classe estàtica anidada que implementa la interfície ``Comparator`` que actua com un comparador per ordenar els productes pel seu nom

### Alimentacio
Subclasse de Producte

#### Constructor
````java  
public Alimentacio(float preu, String nom, String codiBarres, LocalDate dataCaducitat) {  
    super(preu, nom, codiBarres);  
    this.DATACADUCITAT = dataCaducitat;  
}  
````  
- DATACADUCITAT - Data de caducitat del producte alimentari

#### Setters i Getters
- getDataCaducitat - Retorna la data de caducitat del producte alimentari
- setDataCaducitat - **No implementat**
- getPreu - Sobreescriu al getter de la classe Producte per ser calcular amb la següent fórmula ``preu - preu * ( 1 / (dataCaducitat - dataActual + 1)) + (preu * 0.1)``

#### Mètodes
No en té

### Textil
Subclasse de Producte que implementa la interfície ``Comparable``

#### Constructor
````java  
public Textil(float preu, String nom, String codiBarres, String composicio) {  
    super(preu, nom, codiBarres);  
    this.COMPOSICIO = composicio;  
}  
````  
- COMPOSICIO- Composició del producte tèxtil
#### Setters i Getters
- getComposicio - Retorna la composició del producte tèxtil
- setComposicio - **No implementat**

#### Mètodes
- ``compareTo(Textil t1)`` - Utilitzat per comparar productes tèxtils per la seva composició
- ``equals(Object o)`` - Utilitzat per comprovar si dos productes textils son iguals pel codi de barres

### Classe Electronica
Subclasse de Producte

#### Constructor
````java  
public Alimentacio(float preu, String nom, String codiBarres, LocalDate dataCaducitat) {  
    super(preu, nom, codiBarres);  
    this.DATACADUCITAT = dataCaducitat;  
}  
````  
- DIESGARANTIA - Dies de la garantia del producte electrònic

#### Setters i Getters
- getDiesGarantia - Retorna els dies de garantia del producte electrònic
- setDiesGarantia - **No implementat**
- getPreu - Sobreescriu al getter de la classe producte per ser calcular amb la següent fórmula ``preu + preu * (diesGarantia / 365) * 0.1``

#### Mètodes
No en té

### Compra
Classe que utilitza la classe Producte i les seves subclasses per afegir productes al carret, passar per caixa, mostrar el carret i buscar productes pel codi de barres.

#### Constructor
````java  
public Compra() {  
  this.llistaProductes = new ArrayList<Producte>(100);  
  this.llistaAliments = new ArrayList<Alimentacio>();  
  this.llistaTextils = new ArrayList<Textil>();  
  this.llistaElectronics = new ArrayList<Electronica>();  
}
````  
- llistaProductes - Llista per guardar tota mena de productes
- llistaAliments - Llista per guardar productes de tipus ``Alimentacio``
- llistaTextils - Llista per guardar productes de tipus ``Textil``
- llistaElectronics - Llista per guardar productes de tipus ``Electronica``

#### Setters i Getters
No en té

#### Mètodes
- ``afegirProducte(String tipusProducte)`` - Demana les dades adients a cada tipus de producte (nom, preu, codi de barres, data de caducitat en el cas d'alimentació, composició en el cas de tèxtil i dies de garantia en cas d'electrònic) depenent del tipus passat i afegeix el producte tant a la llista que li pertoca com a la general.
- ``passarPerCaixa()`` - Crea una llista de productes únics i a continuació crea un HashSet per llista especifica de cada tipus de producte, per així obtenir tots els productes sense repetits de cada llista i introduir-los a la llista abans creada. Seguidament, comprova que la llista de productes únics no estigui buida, si ho esta, utilitza el mètode ``registrarLog()`` passant per paràmetre el missatge ``"El carret no pot estar buit"``, si no ho esta, mostra la capçalera del tiquet, recorre la llista obtenint per cada producte les unitats (comprovant la seva freqüència a la llista de productes general), el seu preu total (el qual es sumat als altres totals per calcular el subtotal) i si el producte a obtenir les dades es de la classe Textil comprova que el preu del producte sigui correcte utilitzant el mètode ``comprovarPreuTextil(producte)``, per ultim, neteja les llistes de productes creades al constructor de la classe.
- ``mostrarCarret()`` - Crea un HashMap anomenat carret amb el codi de barres del producte com a clau i la quantitat de valor, seguidament recorre la llista general i comprova per cada un si ja existeix el codi de barres al carret, si existeix, suma 1 a la quantitat, si no, afegeix el codi de barres i posa la quantitat a 1. A continuació, per cada conjunt clau valor del carret mostra per pantalla el nom del producte que es obtingut utilitzant el mètode ``buscarProducte()`` passant per paràmetre el codi de barres i la seva quantitat, per últim neteja el carret.
- ``buscarProducte(String codiBarres)`` - Crea un Optional i convertint la llista general a un stream fa un filtre per trobar el producte que el seu codi de barres coincideixi amb el codi de barres passat per paràmetre i agafa la primera coincidència, si ho troba, retorna el nom del producte, si no, retorna "No s'ha trobat el producte".
- ``comprovarPreuTextil(Producte producte)`` - Crea un Scanner per llegir l'arxiu ``UpdateTextilPrices.dat`` que s'ubica dins del directori ``updates``, si no existeix l'arxiu, utilitza el mètode ``registrarLog()`` passant per paràmetre el missatge ``"L'arxiu de preus dels productes textils no s'ha trobat"``, si existeix, recorre cada línia de l'arxiu composta per ``codiBarres;preuCorrecte``, la separa per obtenir el codi de barres i el preu correcte i comprova que el codi de barres de la línia sigui el mateix que el codi de barres del producte passat per paràmetre, si ho és, utilitza el mètode ``setPreu()`` del producte passant per paràmetre el preu correcte per així corregir el preu, tanca l'arxiu i deixa de recoure línies, si no ho es no fa res.
- ``registrarLog(String missatge)`` - Crea dos File, un amb el directori ``logs`` i altre amb l'arxiu ``logs/Exceptions.log``, primer comprova que existeixi el directori per crear-lo si no existeix, fa la mateixa comprovació amb l'arxiu (si salta qualsevol error en la creació del fitxer llança una Exception amb el missatge `"Ha ocurregut un error a l'hora de crear el fitxer de logs"`) i seguidament crea un FileWriter amb l'arxiu ``Exceptions.log`` en mode append (si salta qualsevol error a l'hora de escriure en el fitxer llança una Exception amb el missatge `"Ha ocurregut un error a l'hora de escriure en el fitxer de logs"`), escriu una línia amb la data i hora que s'ha produït l'error i el missatge passat per paràmetre, tanca l'arxiu i llança una Exception amb el missatge `"S'ha produit un error. Revisa el fitxer de logs"`.
- ``omplirCarret()`` - Crea tres llistes amb 100 possibles noms de producte cadascuna per als tres tipus de producte, una llista amb diferents composicions per als productes tèxtils i una String amb lletres majúscules i números. A continuació el que fa `100 - midaCarret` vegades (si la llista de productes general ha arribat als 100 productes, utilitza el mètode `registrarLog()` passant per paràmetre el missatge `"No es poden afegir mes de 100 productes"`) és: crear un StringBuilder buit, emplenar-lo amb 6 caràcters que s'escullen aleatòriament utilitzant el Random i la String i obtenir un numero aleatori entre 1 i 3 (o entre 1 i 4 si hi ha almenys un producte en la llista general) per determinar quin tipus de producte crearà (o si duplicarà algun que ja existeixi). Depenent del numero sortit s'inventa una data de caducitat en el cas de Alimentacio, agafa una composició aleatòria de la llista de composicions possibles en el cas de Textil o s'inventa un numero entre 0 i 853 per als dies de garantia en el cas d'Electronica, agafa un nom aleatori de la llista de possibles noms adient al tipus de producte que esta creant, s'inventa un numero (entre 0 i 7 en el cas d'Alimentació, entre 0 i 110 en el cas de Textil o entre 0 i 3500 en el cas d'Electronica) per al preu i crea un objecte de la classe corresponent per després introduir-lo tant a la llista que li pertoca com a la general. En el cas que el numero aleatori per determinar el tipus de producte a crear sigui 4, en comptes de crear un, agafa un producte aleatori de la llista general i l'afegeix tant a la llista que li pertoca com a la general.

### Main
Classe principal que mostra el menú del programa i utilitza el mètode adient de la classe Compra depenent de quina opció seleccionis.

## Justificacions

### Producte
- És una classe abstracta perquè així s'obliga a crear un producte d'un tipus en específic.
- La variable ``CODIBARRES`` és constant perquè mai canviarà.
- El mètode ``equals(Object o)`` comprova si dos productes son iguals si tenen el mateix codi de barres i preu.
- He implementat la interfície ``Comparator`` per poder ordenar alfabèticament tots els tipus de producte.

### Alimentacio
- La variable ``DATACADUCITAT`` és constant perquè no està pensat que canviï.

### Textil
- La variable ``COMPOSICIO`` és constant perquè no està pensat que canviï.
- He implementat la interfície ``Comparable`` ja que es demana que els productes textils s'ordenin per la seva composició.
- Torno a crear el mètode ``equals(Object o)`` perquè es demana que dos productes tèxtils són iguals si tenen el mateix codi de barres.

### Electronica
- La variable ``DIESGARANTIA`` és constant perquè no està pensat que canviï.

### Compra
- En el mètode ``buscarProducte(String codiBarres)`` he utilitzat la classe Optional perquè pot ser que no trobi el producte i aquest objecte està pensat per poder contenir tant valors null com no null.
- He creat el mètode ``omplirCarret`` per a l'hora de realitzar proves tenir més facilitat i també per provar l'error de límit de productes.
