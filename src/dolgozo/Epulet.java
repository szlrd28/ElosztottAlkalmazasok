package dolgozo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Epulet
{
    private List<Dolgozo> dolgozok;
    private final String FILE_NAME = "dolgozok.dat";

    public Epulet()
    {
        this.dolgozok = new ArrayList<>();
   File file = new File(FILE_NAME);
        if(file.exists()) { //ha fajl létezik betölteni
            loadDolgozok(); // meghivás
        }
    }

    public void addDolgozo(Dolgozo dolgozo) {   // dolgozo hozzá
        this.dolgozok.add(dolgozo);
    }

    public void removeDolgozo(String nev) throws DolgozoNotFoundException {    //dolgozok törlés
        boolean removed = this.dolgozok.removeIf(dolgozo -> dolgozo.getNev().equals(nev));
        if(!removed) {
            throw new DolgozoNotFoundException(nev);
        }
    }

    public List<Dolgozo> getDolgozokByNev(boolean reversed)// letudjuk kérdezni a dolgozok listajat név szerint
    {
        List<Dolgozo> tempDolgozok = new ArrayList<>(dolgozok);
        if (reversed)
        {
            tempDolgozok.sort(Comparator.comparing(Dolgozo::getNev).reversed()); //mi alapján csinalja a rendezést
        }
        else
        {
            Collections.sort(tempDolgozok); // lista rendezes
        }

        return tempDolgozok;
    }

    public List<Dolgozo> getDolgozokByFizetes(boolean reversed) // letudjuk kérdezni a dolgozok listajat fizetés szerint
    {
        List<Dolgozo> tempDolgozok = new ArrayList<>(dolgozok);
        if (reversed)
        {
            tempDolgozok.sort(Comparator.comparing(Dolgozo::getFizetes).reversed()); //mi alapján csinalja a rendezést
        }
        else
        {
            tempDolgozok.sort(Comparator.comparing(Dolgozo::getFizetes)); //lista rendezes
        }

        return tempDolgozok;
    }

    public void archive() {  // archiválás  ,kivételkezelés
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this.dolgozok);
        } catch (IOException e) {
            System.err.println("Hiba történt a(z) " + FILE_NAME + " fájlba írás közben!");
        }
    }

    public void loadDolgozok() { //betöltés , kivételkezelés
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            this.dolgozok = (List<Dolgozo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Hiba történt a(z) " + FILE_NAME + " fájl beolvasása közben!");
        }
    }
}
