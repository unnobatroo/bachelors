# A birodalom térképészei // Mapmakers of the realm

Pityóka császárnő elrendelte a nyugati földterületek birodalmához csatolását. Mivel a Burgonyák Birodalom igény tart e területre, a császárnő szolgálatában álló térképészként a te feladatod lesz a táj feltérképezése. A császárnő küldetésekkel határozza meg, hogy milyen tájakat szeretne látni a birodalmában, így ha sikerül teljesítened a kívánságait, komoly hírnévre tehetsz szert.
!['Illusztráció'](pics/theme.png) 

## A játék leírása

### Elemek lehelyezése
Ebben az egyszemélyes játékban egy 11x11-es négyzetrácsos térképet kell megtölteni különböző alakzatú, különböző tereptípusú térképelemekkel négy évszak alatt. A letehető térképelemek tereptípusai a következők lehetnek: erdő, falu, farm és víz. Minden térképelemet tudunk forgatni és tükrözni, és a térképelem nem fedhet le egy már teli mezőt, illetve nem lóghat le egy része sem a térképről. A térképen 5 fix cellában, minden játékban hegymezők találhatóak, amik szintén teli mezőknek számítanak 

![](pics/jo.png)
![](pics/rossz.png)

### A játék időtartama
Mindegyik évszak 7 időgységig tart. Minden térképelemhez tartozik egy időegység, ami meghatározza, hogy mennyi ideig tart őket felfedezni. Egy évszakban addig tudunk új térképelemeket húzni, amíg el nem érjük a 7 időegységet. Ha az összesített időérték eléri, vagy meghaladja a 7 időegységet, az évszak véget ér. Például, ha 1 időegységünk maradt hátra, és egy két időegységgel rendelkező térképelemet kapunk, az évszak véget ér, de a térképelemet még lehelyezhetjük.

!!illusztráció: időtartam

### Pontszámítás
Minden játék során 4 küldetéskártya (A,B,C,D) van érvényben, amik alapján pontot lehet kapni. Ilyen küldetéskártya lehet például ez:

> *'A hegymezőiddel szomszédos vízmezőidért három-három pontot kapsz.'*

![](pics/küldetés.png)


Minden évszak végén 2 küldetéskártyáért tudunk pontszámot kapni. A tavasz végén az A-B küldetésért, a nyár végén a B-C küldetésért, az ősz végén a C-D küldetésért, a tél végén pedig a D-A küldetésért tudunk pontokat szerezni.

A játék végén a négy évszak alatt szerzett pontszámaink összeadódnak, és ezek fogják adni a végleges pontszámunkat. 

![](pics/játékoldal.png)

### Hegyek
A hegyeink a térkép alábbi mezőiben találhatóak, ahogy a fentebbi képen is látható:
(sor, oszlop) => (2,2), (4,9), (6,4), (9,10), (10,6)

Ha a hegyeket 4 oldalról körbevesszük, **minden évszak végén** kapunk körbevett hegyenként 1-1 pontot.

![](pics/hegybekerites.png)

### Küldetések
Itt találod a játékban kiértékelendő küldetéseket és a hozzájuk tartozó ábrákat.

#### Minimálisan teljesítendő küldetések

![](pics/minimal.png)
1. Fasor: A leghosszabb, függőlegesen megszakítás nélkül egybefüggő erdőmezők mindegyikéért kettő-kettő pontot kapsz. Két azonos hosszúságú esetén csak az egyikért.
2. Krumpliöntözés: A farmmezőiddel szomszédos vízmezőidért két-két pontot kapsz.
3. Gazdag város: A legalább három különböző tereptípussal szomszédos falurégióidért három-három pontot kapsz.
4. Határvidék: Minden teli sorért vagy oszlopért 6-6 pontot kapsz. 

#### Az alap küldetések
![](pics/alap.png)
1. Az erdő széle: A térképed szélével szomszédos erdőmezőidért egy-egy pontot kapsz.
2. Álmos-völgy: Minden olyan sorért, amelyben három erdőmező van, négy-négy pontot kapsz.
3. Öntözőcsatorna: Minden olyan oszlopodért, amelyben a farm illetve a vízmezők száma megegyezik, négy-négy pontot kapsz. Mindkét tereptípusból legalább egy-egy mezőnek lennie kell az oszlopban ahhoz, hogy pontot kaphass érte. 
4. Mágusok völgye: A hegymezőiddel szomszédos vízmezőidért három-három pontot kapsz.
5. Üres telek: A városmezőiddel szomszédos üres mezőkért 2-2 pontot kapsz.
6. Sorház: A leghosszabb, vízszintesen megszakítás nélkül egybefüggő falumezők mindegyikéért kettő-kettő pontot kapsz.
7. Páratlan silók: Minden páratlan sorszámú teli oszlopodért 10-10 pontot kapsz.
8. Gazdag vidék: Minden legalább öt különböző tereptípust tartalmazó sorért négy-négy pontot kapsz. 

#### Extra küldetések plusz pontért

![](pics/extra.png)
Az alábbi küldetéseket plusz pontért tudod megcsinálni. Ezeknek a küldetéseknek a kiértékeléséhez esetleg gráfbejáró algoritmusok használatára lesz szükséged. A homogén tereptípusból álló, egybefüggő, oldaluknál érintkező térképrészeket régióknak nevezzük.

1. Nagyváros: A legalább hat falumezőből álló régióidért 8-8 pontot kapsz.
2. Pontfényű rúna: Minden olyan, pontosan három üres mezőből álló régióért, amit minden oldalról teli mezők, vagy a térkép széle határol, négy-négy pontot kapsz.
3. Kockakrumpli: A legnagyobb kitöltött négyzeted egyik oldalának minden mezőjéért három-három pontot kapsz.

A küldetésekhez tartozó objektum:
```js
quests = 
{
  "minimum": [
    {
      "title": "Fasor",
      "description": "A leghosszabb, függőlegesen megszakítás nélkül egybefüggő erdőmezők mindegyikéért kettő-kettő pontot kapsz. Két azonos hosszúságú esetén csak az egyikért."
    },
    {
      "title": "Krumpliöntözés",
      "description": "A farmmezőiddel szomszédos vízmezőidért két-két pontot kapsz."
    },
    {
      "title": "Gazdag város",
      "description": "A legalább három különböző tereptípussal szomszédos falurégióidért három-három pontot kapsz."
    },
    {
      "title": "Határvidék",
      "description": "Minden teli sorért vagy oszlopért 6-6 pontot kapsz."
    }
  ],
  "basic": [
    {
      "title": "Az erdő széle",
      "description": "A térképed szélével szomszédos erdőmezőidért egy-egy pontot kapsz."
    },
    {
      "title": "Álmos-völgy",
      "description": "Minden olyan sorért, amelyben három erdőmező van, négy-négy pontot kapsz."
    },
    {
      "title": "Öntözőcsatorna",
      "description": "Minden olyan oszlopodért, amelyben a farm illetve a vízmezők száma megegyezik, négy-négy pontot kapsz. Mindkét tereptípusból legalább egy-egy mezőnek lennie kell az oszlopban ahhoz, hogy pontot kaphass érte."
    },
    {
      "title": "Mágusok völgye",
      "description": "A hegymezőiddel szomszédos vízmezőidért három-három pontot kapsz."
    },
    {
      "title": "Üres telek",
      "description": "A városmezőiddel szomszédos üres mezőkért 2-2 pontot kapsz."
    },
    {
      "title": "Sorház",
      "description": "A leghosszabb, vízszintesen megszakítás nélkül egybefüggő falumezők mindegyikéért kettő-kettő pontot kapsz."
    },
    {
      "title": "Páratlan silók",
      "description": "Minden páratlan sorszámú teli oszlopodért 10-10 pontot kapsz."
    },
    {
      "title": "Gazdag vidék",
      "description": "Minden legalább öt különböző tereptípust tartalmazó sorért négy-négy pontot kapsz."
    }
  ],
  "extra": [
    {
      "title": "Nagyváros",
      "description": "A legalább hat falumezőből álló régióidért 8-8 pontot kapsz."
    },
    {
      "title": "Pontfényű rúna",
      "description": "Minden olyan, pontosan három üres mezőből álló régióért, amit minden oldalról teli mezők, vagy a térkép széle határol, négy-négy pontot kapsz."
    },
    {
      "title": "Kockakrumpli",
      "description": "A legnagyobb kitöltött négyzeted egyik oldalának minden mezőjéért három-három pontot kapsz."
    }
  ]
}
```

### Lehetséges elemtípusok

![](pics/alakzatok.png)
A lehetséges elemtípusokat ebben a tömbben találod meg, ezt előkészítettük neked. A feladat kivitelezése rád van bízva, de egy megoldás lehet az, hogy a lerakásnál például a bal felső cellától kezdve fogja beilleszteni az alakzatot a megadott helyre. Az objektumoknak van ˛`rotation` és `mirrored` adattagja is, hogy ezeket is tudd tárolni a kisorsolt alakzatokban.

```js
shapes = [
    {
        time: 2,
        type: 'water',
        shape: [[1,1,1],
                [0,0,0],
                [0,0,0]],
        rotation: 0,
        mirrored: false
    },
    {
        time: 2,
        type: 'town',
        shape: [[1,1,1],
                [0,0,0],
                [0,0,0]],
        rotation: 0,
        mirrored: false        
    },
    {
        time: 1,
        type: 'forest',
        shape: [[1,1,0],
                [0,1,1],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'farm',
        shape: [[1,1,1],
                [0,0,1],
                [0,0,0]],
            rotation: 0,
            mirrored: false  
        },
    {
        time: 2,
        type: 'forest',
        shape: [[1,1,1],
                [0,0,1],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'town',
        shape: [[1,1,1],
                [0,1,0],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'farm',
        shape: [[1,1,1],
                [0,1,0],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 1,
        type: 'town',
        shape: [[1,1,0],
                [1,0,0],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 1,
        type: 'town',
        shape: [[1,1,1],
                [1,1,0],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 1,
        type: 'farm',
        shape: [[1,1,0],
                [0,1,1],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 1,
        type: 'farm',
        shape: [[0,1,0],
                [1,1,1],
                [0,1,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'water',
        shape: [[1,1,1],
                [1,0,0],
                [1,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'water',
        shape: [[1,0,0],
                [1,1,1],
                [1,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'forest',
        shape: [[1,1,0],
                [0,1,1],
                [0,0,1]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'forest',
        shape: [[1,1,0],
                [0,1,1],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
    {
        time: 2,
        type: 'water',
        shape: [[1,1,0],
                [1,1,0],
                [0,0,0]],
        rotation: 0,
        mirrored: false  
    },
]
```


## Játéktér

![](pics/játékoldal.png)

A játéktéren az alábbi dolgok jelennek meg:

- Térképész neve
- 11x11-es mátrix a térképpel, amin a hegyek és a letett alakzatok látszanak
- A véletlenszerűen kiválasztott küldetések nevei és leírása
- Melyik évszakban vagyunk éppen, és jelzi a játék, hogy ezekhez melyik küldetés tartozik
- Az évszakból hátralévő idő
- Az évszakok alatt gyűjtött pontszámaink
- A pontszámaink összesen
- A lehelyezendő elem és a hozzátartozó időtartam
- Forgatás és tükrözés gombok

## Megjelenés

Fontos az **igényes megjelenés**. Ez nem feltétlenül jelenti egy agyon csicsázott oldal elkészítését, de azt igen, hogy 1024x768 felbontásban és afölött az elrendezés jól jelenjen meg, a játéktábla négyzetes cellákat tartalmazzon. Ehhez lehet minimalista designt is alkalmazni, lehet különböző háttérképekkel és grafikus elemekkel felturbózott saját CSS-t készíteni, de lehet bármilyen CSS keretrendszer segítségét is igénybe venni.

Nincs elvárás arra vonatkozóan, hogy milyen **technológiá**val (táblázat, div-ek vagy canvas) oldod meg a feladatot, továbbá a megjelenést és működést illetően sincsenek kőbe vésett elvárások. A lényeg, hogy a fenti feladatok felismerhetők legyenek, és **a játék jól játszható legyen.**

## Pontozás

A feladat megoldásával 20 pont szerezhető. Vannak minimum elvárások, melyek teljesítése nélkül a beadandó nem elfogadható. A plusz feladatokért további 5 pont szerezhető. Ha valaki mindent megold, a beadandóra akár 25 pontot is kaphat.

**A gyakorlati jegyszerzés JavaScript beadandóhoz kapcsolódó feltételei: minden minimumkövetelmény teljesítése ÉS a késésre járó pontlevonás után is legalább 8 pont (40%) elérése.** (Halogatás előtt tehát érdemes lehet megfontolni, hogy mi az egyszerűbb: határidőre elkészíteni a minimális követelményeknek megfelelő verziót; vagy két héttel később maxpont fölötti verzióra ugyanúgy 8 pontot kapni a levonás miatt.)

!! Ne feledd, hogy a beadandó határideje fix, a későket nem fogjuk tudni elfogadni. !!

### Minimálisan teljesítendő (enélkül nem fogadjuk el, 8 pont)
- Főoldal: A játék főoldala megjelenik, ahol meg tudjuk adni a játékosnevünket, illetve a játék leírását is megtekinthetjük (1 pont)
- Játéktér: A játék elindítása után kirajzolódik a 11x11 térkép kirajzolása a hegyekkel a megfelelő helyen. (1 pont)
- Játéktér: A térképelemek közül egy véletlenszerűen megjelenik a hozzájuk tartozó időegységekkel. (1 pont)
- Játéktér: A térképelemeket az üres helyekre le tudjuk helyezni. (2 pont)
- Játéktér: A játék 28 időegységig tart, és a térképelemek lehelyezésével kivonja a térképelemhez tartozó időegységet belőle. (1 pont)
- Játéktér: A játék végén, a 28 időegység eltelte után a 4 alapküldetéshez tartozó pontszámot kiszámolja, és kiírja hány pontot értünk el. (2 pont)

### Az alap feladatok (12 pont)
- Játéktér: A megjelenített térképelem forgatható és tükrözhető, és azt így tudjuk lehelyezni. (1 pont)
- Játéktér: A 12 küldetéskártya mindegyikét le tudja ellenőrizni a játékunk. (4 pont)
- Játéktér: A játék 4 évszakon keresztül tart, minden évszak 7 időegységig tart, az évszakokhoz tartozó küldetéskártyák kiemelődnek. (2 pont)
- Játéktér: Minden évszak végén kiszámolódik a hozzájuk tartozó küldetésekből az évszak végi pontszám, és a játék folytatódik a következő évszakra. (1 pont)
- Játéktér: A hegyek teljes bekerítésével 1 plusz pont szerezhető, amelyek minden évszak végén hozzáadódnak a pontszámunkhoz (1 pont)
- Játék vége: A játék végén megjelenik a négy évszak alatt szerzett összpontszám és a nevünk (1 pont)
- Igényes megjelenés (2 pont)

# Extrák
- A 3 extra gráfbejárásos küldetéskártyák mindegyikéért 1-1 pont szerezhető. (3 pont)
- Játék vége: A játékok során elért pontszámaink a nevünkkel eltárolódnak localStorage-ban, és a Főoldalon a Toplista első 5 helyezettje megjelenik (2 pont)

## További elvárások

- Az elkészült feladatot tömörítve, az összes szükséges állománnyal, illetve a program gyökérmappájában elhelyezett `README.md` fájllal együtt legkésőbb a határidőig (plusz 2 hét pontlevonással) kell feltölteni a Canvas rendszerbe.
- A játék megvalósításához NEM használható semmilyen keretrendszer, külső JavaScript könyvtár.
- A `README.md` fájlban szerepeljen a következő kijelentés (a <> jeleket nem kell beleírni):

  ```txt
  <Hallgató neve> 
  <Neptun kódja> 
  Webprogramozás - számonkérés
  Ezt a megoldást a fent írt hallgató küldte be és készítette a Webprogramozás kurzus számonkéréséhez.
  Kijelentem, hogy ez a megoldás a saját munkám. Nem másoltam vagy használtam harmadik féltől 
  származó megoldásokat. Nem továbbítottam megoldást hallgatótársaimnak, és nem is tettem közzé. 
  Az Eötvös Loránd Tudományegyetem Hallgatói Követelményrendszere 
  (ELTE szervezeti és működési szabályzata, II. Kötet, 74/C. §) kimondja, hogy mindaddig, 
  amíg egy hallgató egy másik hallgató munkáját - vagy legalábbis annak jelentős részét - 
  saját munkájaként mutatja be, az fegyelmi vétségnek számít. 
  A fegyelmi vétség legsúlyosabb következménye a hallgató elbocsátása az egyetemről.
  ```

- A `README.md` fájlban a kijelentés alatt egy üres sorral elválasztva szerepeljen az alábbi lista. Az egyes `[ ]` közötti szóközt cseréld le x-re azokra a részfeladatokra, amit sikerült (akár részben) megoldanod!

  ```txt
  Minimálisan teljesítendő (enélkül nem fogadjuk el, 8 pont)

  [ ] Főoldal: A játék főoldala megjelenik, ahol meg tudjuk adni a játékosnevünket, illetve a játék leírását is megtekinthetjük (1 pont)
  [ ] Játéktér: A játék elindítása után kirajzolódik a 11x11 térkép kirajzolása a hegyekkel a megfelelő helyen. (1 pont)
  [ ] Játéktér: A térképelemek közül egy véletlenszerűen megjelenik a hozzájuk tartozó időegységekkel. (1 pont)
  [ ] Játéktér: A térképelemeket az üres helyekre le tudjuk helyezni. (2 pont)
  [ ] Játéktér: A játék 28 időegységig tart, és a térképelemek lehelyezésével kivonja a térképelemhez tartozó időegységet belőle. (1 pont)
  [ ] Játéktér: A játék végén, a 28 időegység eltelte után a 4 alapküldetéshez tartozó pontszámot kiszámolja, és kiírja hány pontot értünk el. (2 pont)

  Az alap feladatok (12 pont)

  [ ] Játéktér: A megjelenített térképelem forgatható és tükrözhető, és azt így tudjuk lehelyezni. (1 pont)
  [ ] Játéktér: A 12 küldetéskártya mindegyikét le tudja ellenőrizni a játékunk. (4 pont)
  [ ] Játéktér: A játék 4 évszakon keresztül tart, minden évszak 7 időegységig tart, az évszakokhoz tartozó küldetéskártyák kiemelődnek. (2 pont)
  [ ] Játéktér: Minden évszak végén kiszámolódik a hozzájuk tartozó küldetésekből az évszak végi pontszám, és a játék folytatódik a következő évszakra. (1 pont)
  [ ] Játéktér: A hegyek teljes bekerítésével 1 plusz pont szerezhető, amelyek minden évszak végén hozzáadódnak a pontszámunkhoz (1 pont)
  [ ] Játék vége: A játék végén megjelenik a négy évszak alatt szerzett összpontszám és a nevünk (1 pont)
  [ ] Igényes megjelenés (2 pont)

  Extrák (5 pont)

  [ ] A 3 extra gráfbejárásos küldetéskártyák mindegyikéért 1-1 pont szerezhető. (3 pont)
  [ ] Játék vége: A játékok során elért pontszámaink a nevünkkel eltárolódnak localStorage-ban, és a Főoldalon a Toplista első 5 helyezettje megjelenik (2 pont)
  ```

**A megfelelően kitöltött `README.md` fájl nélkül a megoldást nem fogadjuk el!**
