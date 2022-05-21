package dolgozo;
//leszarmaztatjuk a dolgozoból a Portást + szakma
public class Termelo extends Dolgozo
{
    private String szakma;

    public Termelo(String nev, int fizetes, String szakma) {
        super(nev, fizetes);//átadjuk az ősosztály konstruktorának
        this.szakma = szakma;
    }
    //ősosztályból vissza adott toStringhez még + a szakma
    @Override
    public String toString() {
        return super.toString() + ", Szakma: " + szakma;
    }

    public String getSzakma()
    {
        return szakma;
    }

    public void setSzakma(String szakma)
    {
        this.szakma = szakma;
    }
}
