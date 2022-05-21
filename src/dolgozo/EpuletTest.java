package dolgozo;
// teszt létrehozása dolgozo.dat fájlba ment, minden tesztnél hozzáadja
// dolgozo.dat törölni kell tesztek után
// ha nem adjuk minden tesztnél hozza, akkor a dat fajlbol tolti be
public class EpuletTest
{
    public static void main(String[] args)
    {
        Epulet epulet = new Epulet();
        epulet.addDolgozo(new Portas("Zsiga Józsi", 200000, "nappalos"));
        epulet.addDolgozo(new Termelo("Nagy Csaba", 300000, "termelő"));

        System.out.println("Dolgozók lekérdezése Név alapján nem fordított sorrendben");
        epulet.getDolgozokByNev(false).forEach(System.out::println);
        System.out.println();

        System.out.println("Dolgozók lekérdezése Név alapján fordított sorrendben");
        epulet.getDolgozokByNev(true).forEach(System.out::println);
        System.out.println();

        System.out.println("Dolgozók lekérdezése Fizetés alapján nem fordított sorrendben");
        epulet.getDolgozokByFizetes(false).forEach(System.out::println);
        System.out.println();

        System.out.println("Dolgozók lekérdezése Fizetés alapján fordított sorrendben");
        epulet.getDolgozokByFizetes(true).forEach(System.out::println);

        epulet.archive();
    }
}
