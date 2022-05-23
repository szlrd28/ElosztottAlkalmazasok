package dolgozo.exception;
// forditás idejű hiba
public class DolgozoNotFoundException extends Exception
{
    public DolgozoNotFoundException(String nev)
    {
        super("Dolgozó nem található " + nev + " névvel");
    }
}
