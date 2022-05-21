package dolgozo;

import java.io.Serializable;

// létrehoztuk az abstract Dolgozo-t, get/setter generálás, felülirások
// Cloneable interface hasznalata, klonozás miatt
// Comparable<Dolgozo> interfész impl., dolgozok termeszetes rendezettsége
// Serializable int., beirjuk a fajlba
public abstract class Dolgozo implements Cloneable, Comparable<Dolgozo>, Serializable
{
    private static final long serialVersionUID = 1L; // megtudnánk tobb dolgozo osztályt kulonboztetni

    private String nev;
    private int fizetes;

    // konstruktor létrehozása, beallitjuk mind2 adattagot
    
    public Dolgozo(String nev, int fizetes) {
        this.nev = nev;
        this.fizetes = fizetes;
    }

    // felul irjuk a toString metodust, az ősosztályból
    @Override
    public String toString() {
        return "Név: " + nev + ", Fizetés: " + fizetes + " Ft";
    }
    // megadjuk hogy mi alapján hasonlitsa össze a dolgozokat
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        // megnezzuk hogy hogy Dolgozo-e
        if(!(o instanceof Dolgozo)) {
            return false;
        }
        // ha dolgozo tipusu akkor atkasztoljuk
        Dolgozo dolgozo = (Dolgozo) o;
        return this.nev.equals(dolgozo.getNev());
    }

    @Override
    public int compareTo(Dolgozo dolgozo)
    {
        return this.nev.compareTo(dolgozo.getNev());
    // terjen vissza int, de a nev összehasonlitva egy masik nevvel, 
    //ha 0-val tér vissza akkor megegyezik 2 név
    // ha A és B -1el tér vissza, B és A 1-el fog visszatérni
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public String getNev()
    {
        return nev;
    }

    public void setNev(String nev)
    {
        this.nev = nev;
    }

    public int getFizetes()
    {
        return fizetes;
    }

    public void setFizetes(int fizetes)
    {
        this.fizetes = fizetes;
    }
}
