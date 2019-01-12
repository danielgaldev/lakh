# Lokáció alapú közösségi háló
#### _Gál Dániel_
#### _Témalaboratórium_
#### _2018.12.10._
A forráskód megtalálható a GitHub-on [ezen a linken](https://github.com/danielgaldev/lakh/tree/temalabor).

## A projekt célja
Témaként egy lokáció alapú közösségi hálót megvalósító webalkalmazás elkészítését választottam. Az alkalmazás feladata kezdetben az, hogy eseményeket hozhassunk létre benne, melyekhez tartozik időpont és földrajzi hely, emellett listázni tudja a felhasználóhoz legközelebb eső eseményeket.
Keretrendszerként a Java Spring-et választottam. A frontend-et terv szerint majd a React framework segítségével fogom elkészíteni. Kíváncsiságból egy olyan további kihívást is felállítottam, hogy az egész projektet Docker container-ek felhasználásával készítem el.

## Docker

### Motiváció
A 2016-os DockerCon-on egy fejlesztő bemutatott egy tiszta Docker-es munkamenetet, mely során semmi nem volt a gépére telepítve, csupán egy Docker és egy VS Code. Egy full stack alkalmazást húzott le egy GitHub repository-ból, majd egyetlen paranccsal elindította. Képes volt csatolni a konténerhez a VS Code debugger-ét, kijavítani a hibát, és a változatása a production server-en is megjelent, mindezt percek alatt. A demó megtekinthető [erre a linkre kattintva](https://youtu.be/vE1iDPx6-Ok?t=2080).

3 és fél év fejlesztési tapasztalattal magam mögött rengeteg programozási nyelvvel és framework-kel volt dolgom, és majdnem mindegyikhez egy külön sajátos környezet kellett. Egy idő után kezdett bosszantani, hogy egy projekt miatt több gigabájt helyet foglal el a gépemen egy szoftver. Ezen kívül sokkal tisztábbnak érzem az operációs rendszeremet, ha nincs rajta végtelen sok program. Ezen indokok mögé még felsorakoztatnám azt, hogy az új laptopomra még nem akartam Linux-ot telepíteni, de szerettem volna a Linux környezet előnyeit kiélvezni anélkül, hogy a pici 256GB-os SSD-met virtuális gépek tömkelegével pakoljam tele.

A Docker-ben megoldásra leltem minden fentebb felsorolt problémámra. A témával való foglalkozás előtt már egy ideje ismerkedtem a technológiával, azonban Java Spring-gel együtt még nem próbáltam ki.

### Mi az a Docker?
> A Docker jelenleg az egyik legelterjedtebb container framework, amelynek implementációja erősen támaszkodik a Linux kernel nyújtotta szeparációs lehetőségekre. A Docker fejlesztők és sysadminok számára egyaránt egy nagyon egyszerű lehetőséget biztosít hordozható alkalmazások létrehozására és menedzselésére. Segítségével bárki fejleszthet és becsomagolhat egy alkalmazást például a saját laptopján, és biztos lehet benne, hogy az egy teljesen másik környezetben, tipikusan egy cloud szerveren futtatva is ugyanúgy működni fog.

> A másik komoly előny a sebesség. A technológiából fakadóan a Docker containerek kicsik és és gyorsak, hiszen tulajdonképpen csak egy, a kernelen futó sandbox környezetről van szó, amely kevés erőforrást emészt fel.

> Első látásra egy container nem különbözik sokban egy VM-től, hiszen ugyanúgy egy teljesen elkülönített, virtualizált környezett biztosít, amelynek a gazdaszámítógép szolgáltat erőforrásokat. Azonban a VM-el ellentétben a containerek nem az egész stacket, csupán a felhasználói teret virtualizálják, a többi erőforrás pedig közös.

([ithub.hu](https://ithub.hu/blog/post/Docker_bevezetes_a_containerek_vilagaba/))

> Az új alkalmazások építése és telepítése sokkal gyorsabb konténerekkel. A Docker konténerek becsomagolják a szoftvert és a függőségeit egy szabványosított egységbe, ami mindent tartalmaz a futtatáshoz: kódot, futtatókörnyezetet, rendszereszközöket és függvénykönyvtárakat. Ez garantálja azt, hogy az alkalmazás mindig ugyanolyan körülmények között fusson. Az együttműködés egyszerű, csupán a konténer képét kell megosztani a csapattársainkkal.
([docker.com](https://www.docker.com/why-docker))

### Docker Compose
> A Compose egy eszköz a több konténeres Docker alkalmazások definiálásához és futtatásához. Egy YAML fájlban konfigurálható az alkalmazás szolgáltatásai, majd egyetlen paranccsal fel lehet építeni és el lehet indítani a szolgáltatásaidat.

([Overview of Docker Compose](https://docs.docker.com/compose/overview/))

A projektben a Docker Compose segítségével definiálok egy adatbázis konténert, valamint az ezt haszánló alkalmazás konténert. A tervek szerint a frontend is külön konténert fog kapni.

## Az alkalmazás használata
A lokáció alapú közösségi hálóból csupán egy két String-et listázó és paraméteres hozzáadó GET endpoint készült el, és jelenleg könyveket tárol (szerző, cím), a hangsúlyt ugyanis a Docker-es megvalósításra helyeztem.

### Futtatás
Mielőtt bármit is tudunk kezdeni az alkalmazásunkkal, néhány kezdő lépést meg kell tennünk. Adjuk ki parancssorból a
```
docker volume create --name mysql-data
```
parancsot. Ezzel a Docker létrehoz egy területet a host gépen, melyet a konténer is használhat.
Az alkalmazásunkat a
```
docker-compose build
```
paranccsal tudjuk felépíteni. Letöltődnek a `pom.xml`-ben definiált függőségek a felépítő image-be, majd végbemegy a `.jar` fájl felépítése.
Az alkalmazást ezek után a következő paranccsal tudjuk elindítani az alkamazásunkat:
```
docker-compose up
```
A paranccsorban megkapjuk a Spring framework-től megszokott alkalmazások indulásakor megjelenő output-ot, és az alkalmazás elkezd hallgatózni a 8080-as porton.

#### Használat
A http://localhost:8080/demo/all URL-en az adatbázis tartalma kerül listázásra (kezdetben egy üres listát kapunk: []).

Hozzáadni adatot a http://localhost:8080/demo/add?author=szerzo&title=cim URL-en lehet.

### Fejlesztés
A Docker nagy előnye, hogy nem kell semmilyen fejlesztőkörnyezet hozzá. Elég egy Docker és egy szövegszerkesztő, és full stack alkamazások is felhúzhatók gyakorlatilag a semmiből. Töltsük le a Dockert (Windows-on Docker for Windows, MAC-en Docker for Mac, Linuxon pedig bármelyik distro-nak a csomagkezelőjéből), és adjuk ki a Futtatás fejezetben leírt parancsokat.
#### Forráskód átírása
Ha átírjuk a programunkat, a `.jar` fájlt újra kell építeni, amihez a `docker-compose build` parancsot kell kiadni. Ha a `pom.xml`-t nem módosítottuk, a függőségek a Docker cache-ből kerülnek felhasználásra, nem kell őket újra letölteni. Ha először build-elünk, az `application.properties` fájlban a
```
spring.jpa.hibernate.ddl-auto=create
```
sort használjuk (újraépíti az adatbázis sémát), de ha azt szeretnénk, hogy a tárolt adataink megmaradjanak (és a sémákon sem tervezünk változtatni), írjuk át ezt a sort a következőre:
```
spring.jpa.hibernate.ddl-auto=update
```
#### `pom.xml` megváltozatása
A folyamat ugyanaz, mint az előző alfejezetben, a különbség csupán az, hogy a függőségek átírása miatt a Docker eldobja a Docker cache-t és újra le kell tölteni a függőségeket.

## A projekt felépítése
### docker-compose.yml
A [Compose fájl](https://docs.docker.com/compose/gettingstarted/) tartlmazza a konténerek futási konfigurációját.
#### Service-ek
A projekt két szolgáltatásból áll.
##### db - Az adatbázis
```
image: mysql:5.7
```
Az adatbázis szolgáltatás egy MySQL 5.7 image.
```
environment:
  - MYSQL_ROOT_PASSWORD=topsecret
  - MYSQL_DATABASE=devdb
  - MYSQL_USER=dbuser
  - MYSQL_PASSWORD=dbpassword
```
A konténer ezekkel a környezeti változókkal indul. A MySQL image ezen változók alapján létrehoz egy `devdb` nevű adatbázist, a root jelszót beállítja `topsecret`-re, és létrehoz egy `dbuser` nevű felhasználót `dbpassword` jelszóval. Ezen a felhasználón keresztül fog az alkalmazás kommunikálni az adatbázissal (lásd `applicaion.properties` fájl).
```
volumes:
  - mysql-data:/var/lib/mysql
```
A konténer a `mysql-data` nevű volume-ot (host gépen található, a konténer által használható lemezterületet) a konténer `/var/lib/mysql` mappájához csatolja, amelyben az adatbázisban tárolt adatok találhatók. Így az adatok megmaradnak a host gépen, ha leállítjuk a szolgáltatást, és a következő indításkor is a rendelkezésre állnak majd.
```
ports:
  - "3306:3306"
```
Az szolgáltatás a 3306-os porton hallgatózik (a host gép 3306-os portját a konténer 3306-os portjához csatolja).
##### backend - Az alkalmazás
```
build: .
```
A szolgáltatás a Compose fájllal egy mappában található Dockerfile-t (lásd később) használja a saját felépítéséhez.
```
command: bash -c "java -jar $$(ls *.jar)"
```
A szolgáltatás induláskor ezt a parancsot futtatja. Elindítja a `.jar` fájlt, aminek következtében elindul a Spring alkamazás.
```
ports:
  - "8080:8080"
```
A szolgáltatás a 8080-as porton hallgatózik.

#### Egyéb

```
volumes:
  mysql-data:
    external: true
```
Az adatokat tároló volume-ot külső volume-ként kell definiálni, hogy az adatok a konténer újraépítésekor is megmaradjanak.

### Dockerfile
A konténer felépítéséhez szükséges lépések a [Dockerfile](https://docs.docker.com/engine/reference/builder/)-ban találhatók.
A projektem Dockerfile-ja a `backend` szolgáltatás felépítésének lépéseit írja le. A szolgáltatás [Multi-stage build](https://docs.docker.com/develop/develop-images/multistage-build/) struktúrát használ, melynek előnye, hogy a kész konténer csupán a futtatási környezetet (jre) és a `.jar` fájlt fogja tartalmazni.
```
FROM maven:3.6-jdk-8-alpine as builder
```
A konténert egy Maven image-ből származtatom le. 3.6-os Maven-t és JDK8-at használok. Az _alpine_ tag azt a célt szolgálja, hogy a konténert futtató OS Alpine Linux legyen, ami méretben sokkal kisebb (kb. 200MB, 2-3x kisebb), mint az alapértelmezett (főként Ubuntu alapú) konténerek. Erre a container-re a Dockerfile további szakaszaiban a `builder` névvel tudunk hivatkozni.
```
RUN mkdir /code
WORKDIR /code
COPY pom.xml .
```
Létrehozza a `/code` mappát, beállítja munkamappának, majd bemásolja a `pom.xml`-t.
```
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline
```
A Maven letölti a függőségeket a `pom.xml` alapján.
```
COPY src/ ./src/
```
Bemásolja a forráskódot.
```
RUN mvn -B -e -o -T 1C clean install
```
A Maven összeállítja a `.jar` fájlt.

```
FROM openjdk:8-jre-alpine
```
A második `FROM` paranccsal elkezdődik annak a konténernek a felépítése, amely a Dockerfile-ban definiált parancsok lefutása után futni fog. Az első `FROM` paranccsal kezdődő konténer adatai még léteznek és elérhetők. Ez a konténer egy Apline Linux alapú Java 8 futtatókörnyezet.
```
RUN apk add --no-cache bash
```
Telepíti a _bash_-t. Ez a `.jar` fájl futtatásához kell.
```
RUN mkdir /app
```
Létrehozza a `/app` mappát, amibe a `.jar` fájl fog kerülni.
```
COPY --from=builder /code/target /app
```
A `builder` konténer `target/` mappáját másolja az új konéner `/app` mappájába. Ez a mappa tartalmazza a `.jar` fájlt.
```
WORKDIR /app
```
Munkamappának beállítja a `/app` mappát.

A Multi-stage build abban nyilvánul meg, hogy a `.jar` fájl felépítéséhez szükséges környezetet (`builder`) hátrahagyjuk, és csak a futtatásához kellő Java Runtime Environment marad meg.


## További tervek
A jelenleg megvalósított rendszer megfelelő kiindulóállapot bármilyen Docker alapú Java Spring alkalmazáshoz. A cél ezek után a tényleges lokáció alapú közösségi háló REST API-jának megtervezése, majd a frontend megtervezése és megvalósítása.

## Alternatív technológiák

### Virtuális gépek
Korábban már szó volt a Docker virtuális gépekkel szembeni előnyeiről. A virtuális gépek:
+ A host rendszertől elkülönített környezet, ahol büntetlenül megtehetünk bármit, amit csak szeretnénk.
- Az egész operációs rendszert tartalmazzák. Ha több projekten dolgozom, mindegyikhez kell egy virtuális gép, melyek külön-külön is több gigabájtnyi helyet foglalnak.
- Egy virtuális gép összerakása macerás.
- Futás közben nagyon sok erőforrást használ.

Mindezek alapján a Docker-es megoldás mind időben, mint erőforrásfelhasználásban kedvezőbb a tisztán virtuális gépeket haszánló módszernél. Elég csupán egy virtuális gép, mely a megosztott felhasználói terek alatt pörög, és mindez elrejtve a szemünk elől a Docker által, hogy képesek legyünk egyedül a fejlesztésre koncentrálni.

### Vagrant
Létezik egy technológia, amely megkímél minket a virtuális gépek elindításának és konfigurálásának feladataitól, és mégis egy tiszta virtuális gépet tudunk használni a segítségével. A Docker-hez képest lényegesen könnyebb elsajátítani.

#### Mi az a Vagrant?
> A Vagrant egy eszköz, mely virtuális gépeket épít és menedzsel egyetlen munkafolyamat során. Az automatizációra és a könnyen elsajátítható munkafolyamatra fókuszál, így csökkenti a fejlesztőkörnyezetek felépítéséhez szükséges időt, segíti a munkamegosztást és a segítségével az "én gépemen működik" kifogás a múlté lehet.

([vagrantup.com](https://www.vagrantup.com/intro/index.html))

#### Előnyök és hátrányok röviden
+ Host rendszertől elkülönített környezet
+ Könnyen tanulható, egyszerű
- Erőforrásigényes

#### A technológiáról bővebben
A Vagrant egy virtualizációs szoftver segítségével építi fel a virtuális gépeit, mint például a VirtualBox vagy a VMWare, ezért ezek valamelyike is szükséges a használatához.

A konfiguráláshoz egy ún. `Vagrantfile`-t használunk, ebbe írandók a virtuális gépünkkel szemben támasztott elvárásaink. Egy ilyen konfigurációs paraméter például az a shell script, amelyben a környezetünk felépítéséhez szükséges parancsokat tartalmazza.

A virtuális gépeket SSH-n keresztül tudjuk kezelni. A `Vagrantfile`-t tartalmazó mappa automatikusan megosztásra kerül a virtuális géppel, annak a `/vagrant` mappájában tudjuk elérni ugyanazokat a fájlokat. Bármilyen változtatás természetesen azonnal megjelenik a gépen belül is, tehát használhatjuk a host gépen a saját kedvenc szövegszerkesztőnket a fejlesztéshez.

A Vagrant biztosítja a _port forwarding_-ot, tehát ha egy webalkalmazást fejlesztünk, a virtuális gépünk egy tetszőleges portját a host gép egy portjához csatlakoztathatjuk, és így a böngészőnkből elérjük az alkalmazásunkat.

A virtuális gépünk a
```
vagrant up
```
paranccsal indítható.

A tárhely menedzselésére is vannak különböző módszerek. Ha csak egy kis szünetet szeretnénk tartani, de nem szeretnénk, hogy a környezetünk fusson, adjuk ki a
```
vagrant suspend
```
parancsot, és a virtuális gép RAM-ja rákerül a háttértárunkra, hogy később ugyanonnan tudjuk folytatni a munkát, ahol abbahagytuk.
Ha le akarjuk állítani a virtuális gépünket, erre a
```
vagrant halt
```
parancs szolgál. Ekkor leáll a virtuális operációs rendszer, de minden általa háttértárra írt adat megmarad. Az ezt követő indításkor a virtuális gép operációs rendszere bootol és folytathatjuk a munkát. Végül, de nem utolsósorban a
```
vagrant destroy
```
parancs elintézi nekünk, hogy a virtuális gépünknek semmi nyoma ne maradjon a host gépen, tehát kitörli a virtuális gépet magát, és minden fájlt a hozzá tartozó lemezterületen.
