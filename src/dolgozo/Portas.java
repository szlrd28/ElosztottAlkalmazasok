package dolgozo;
//leszarmaztatjuk a dolgozoból a Portást + muszak
public class Portas extends Dolgozo
{
    private String muszak;

    public Portas(String nev, int fizetes, String muszak) {
        super(nev, fizetes);//átadjuk az ősosztály konstruktorának
        this.muszak = muszak;
    }
    //ősosztályból vissza adott toStringhez még + a muszak
    @Override
    public String toString() {
        return super.toString() + ", Műszak: " + muszak;
    }

    public String getMuszak()
    {
        return muszak;
    }

    public void setMuszak(String muszak)
    {
        this.muszak = muszak;
    }
}

