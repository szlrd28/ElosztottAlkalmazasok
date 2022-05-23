package dolgozo.model;

import dolgozo.exception.DolgozoNotFoundException;

import javax.sound.sampled.Port;
import javax.swing.table.AbstractTableModel;
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

public class Epulet extends AbstractTableModel
{
    private List<Dolgozo> dolgozok;
    private final String FILE_NAME = "dolgozok.dat";

    public Epulet()
    {
        this.dolgozok = new ArrayList<>();

        File file = new File(FILE_NAME);
        if(file.exists()) {  //ha fajl létezik betölteni
            loadDolgozok();  // meghivás
        }
    }

    public void addDolgozo(Dolgozo dolgozo) { // dolgozo hozzá
        this.dolgozok.add(dolgozo);
    }

    public void removeDolgozo(Dolgozo dolgozo) throws DolgozoNotFoundException //dolgozok törlés
    {
        boolean removed = this.dolgozok.remove(dolgozo);
        if(!removed) {
            throw new DolgozoNotFoundException(dolgozo.getNev());
        }
    }

    public List<Dolgozo> getDolgozokByNev(boolean reversed) // letudjuk kérdezni a dolgozok listajat név szerint
    {
        List<Dolgozo> tempDolgozok = new ArrayList<>(dolgozok);
        if (reversed)
        {
            tempDolgozok.sort(Collections.reverseOrder());  //mi alapján csinalja a rendezést
        }
        else
        {
            Collections.sort(tempDolgozok);  // lista rendezes
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
            tempDolgozok.sort(Comparator.comparing(Dolgozo::getFizetes));  //lista rendezes
        }

        return tempDolgozok;
    }

    public void archive() { // archiválás  ,kivételkezelés
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

    @Override
    public int getRowCount()
    {
        return dolgozok.size();// dolgozok lista sor index
    }

    @Override
    public int getColumnCount()
    {
        return 3; // 3 oszlop
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Dolgozo dolgozo = dolgozok.get(rowIndex);
        if(dolgozo.isPortas()) {  
            Portas portas = (Portas) dolgozo;
            switch (columnIndex) { 
                case 0:
                    return portas.getNev();
                case 1:
                    return portas.getFizetes();
                default:
                    return portas.getMuszak();
            }
        } else{
            Termelo termelo = (Termelo) dolgozo; // ha Portas nem dolgozo volt akkor Termelo
            switch (columnIndex) {
                case 0:
                    return termelo.getNev();
                case 1:
                    return termelo.getFizetes();
                default:
                    return termelo.getSzakma();
            }
        }
    }

    @Override
    public String getColumnName(int columnIndex) // oszlopok nevei
    {
        switch (columnIndex) {
            case 0:
                return "Név";
            case 1:
                return "Fizetés (Ft)";
            default:
                return "Szakma / Műszak";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if(columnIndex == 1) { // fizetés visszatérése
            return Integer.class;
        }

        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)// lehet modositani
    {
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) // elmenti az uj erteket
    {
        Dolgozo dolgozo = dolgozok.get(rowIndex);
        if(dolgozo.isPortas()) {
            Portas portas = (Portas) dolgozo;
            switch (columnIndex) {
                case 0:
                    portas.setNev((String) value);
                    break;
                case 1:
                    portas.setFizetes((int) value);
                    break;
                default:
                    portas.setMuszak((String) value);
            }
        } else {
            Termelo termelo = (Termelo) dolgozo;
            switch (columnIndex) {
                case 0:
                    termelo.setNev((String) value);
                    break;
                case 1:
                    termelo.setFizetes((int) value);
                    break;
                default:
                    termelo.setSzakma((String) value);
            }
        }
    }

    @Override
    public void fireTableDataChanged() // fognak latszani a valtozasok
    {
        super.fireTableDataChanged();
    }

    public void deleteRow(int rowNumber) { // ha kitorlodik egy sor
        Dolgozo dolgozo = dolgozok.get(rowNumber);
        try
        {
            removeDolgozo(dolgozo);
            fireTableRowsDeleted(rowNumber, rowNumber);
        }
        catch (DolgozoNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
