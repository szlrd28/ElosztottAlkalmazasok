package dolgozo.view;
//ablak létrehozása, gombok stb., funkciok beallitasa
import dolgozo.model.Epulet;
import dolgozo.model.Portas;
import dolgozo.model.Termelo;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EpuletFrame extends JFrame
{
    private Epulet epulet;

    private JTable table;
    private JScrollPane scrollPane; //gorgetes

    private JPanel mainPanel; 
    private JPanel buttonPanel;// ahol a gombok lesznek

    private JButton newPortasButton; // portás  gomb
    private JButton newTermeloButton; // termelo gomb   
    private JButton archiveButton; // archiv gomb
    private JButton removeDolgozoButton; // torles gomb

    public EpuletFrame() {
        frameConfig();

        epulet = new Epulet();

        initComponents();
    }

    private void frameConfig() { // beallitasok 
        setTitle("Épület");
        setDefaultCloseOperation(EXIT_ON_CLOSE); //bezaraskor mi tortenjen
        setResizable(false); // ne lehessen meretezni
        pack(); // kell a pack();
        setSize(new Dimension(600, 300));
        setLocationRelativeTo(null); // kepernyo kozepen
        setLayout(new BorderLayout()); // komponensek hogy helyeszkedjen el
    }

    private void initComponents() { // komponensek init.
        table = new JTable(epulet); // abstract Table átadása 
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setSize(new Dimension(600, 300));

        scrollPane = new JScrollPane(table);
        scrollPane.setSize(new Dimension(600, 300));

        mainPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout());

        newPortasButton = new JButton("Új portás");
        newTermeloButton = new JButton("Új termelő");
        archiveButton = new JButton("Archiválás");
        removeDolgozoButton = new JButton("Törlés");

        addComponents(); //hozza adja a komp. a framekhez
    }

    private void addComponents() {
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        buttonPanel.add(newPortasButton);
        buttonPanel.add(newTermeloButton);
        buttonPanel.add(archiveButton);
        buttonPanel.add(removeDolgozoButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addListeners();
    }

    private void addListeners() { //amikor bezarodik az alk. mindig archivalja!
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                epulet.archive();
            }
        });

        newPortasButton.addActionListener(actionEvent -> { // akciófigyelő a Portas gombra
            JPanel jPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            JLabel nevLabel = new JLabel("Név");
            JLabel fizetesLabel = new JLabel("Fizetés (Ft)");
            JLabel muszakLabel = new JLabel("Műszak");

            JTextField nevField = new JTextField();
            JTextField fizetesField = new JTextField();
            JTextField muszakField = new JTextField();

            jPanel.add(nevLabel);
            jPanel.add(nevField);
            jPanel.add(fizetesLabel);
            jPanel.add(fizetesField);
            jPanel.add(muszakLabel);
            jPanel.add(muszakField);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    jPanel,
                    "Új portás adatai",
                    JOptionPane.OK_CANCEL_OPTION
            );
            if(result == JOptionPane.OK_OPTION) {
                Portas portas = new Portas(
                        nevField.getText(),
                        Integer.parseInt(fizetesField.getText()),
                        muszakField.getText()
                );

                epulet.addDolgozo(portas);
                epulet.fireTableDataChanged();

                String message = String.format("%s portás sikeresen felvéve!", portas.getNev());
                showMessage("Sikeres portás felvétel", message);
            }
        });

        newTermeloButton.addActionListener(actionEvent -> { // akciófigyelő a Termelo gombra
            JPanel jPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            JLabel nevLabel = new JLabel("Név");
            JLabel fizetesLabel = new JLabel("Fizetés (Ft)");
            JLabel szakmaLabel = new JLabel("Szakma");

            JTextField nevField = new JTextField();
            JTextField fizetesField = new JTextField();
            JTextField szakmaField = new JTextField();

            jPanel.add(nevLabel);
            jPanel.add(nevField);
            jPanel.add(fizetesLabel);
            jPanel.add(fizetesField);
            jPanel.add(szakmaLabel);
            jPanel.add(szakmaField);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    jPanel,
                    "Új portás adatai",
                    JOptionPane.OK_CANCEL_OPTION
            );
            if(result == JOptionPane.OK_OPTION) {
                Termelo termelo = new Termelo(
                        nevField.getText(),
                        Integer.parseInt(fizetesField.getText()),
                        szakmaField.getText()
                );

                epulet.addDolgozo(termelo);
                epulet.fireTableDataChanged();

                String message = String.format("%s termelő sikeresen felvéve!", termelo.getNev());
                showMessage("Sikeres termelő felvétel", message);
            }
        });

        archiveButton.addActionListener(actionEvent -> {// gomb nyomásra archivaljuk + üzenet
            epulet.archive();
            String message = String.format( //összeallitjuk  a stringet
                    "Épület dolgozói sikeresen archiválva ekkor: %s",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd - HH:mm:ss"))
            );
            showMessage("Sikeres archiválás", message);
        });

        removeDolgozoButton.addActionListener(actionEvent -> { //akciófigyelő törlés
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1) {
                String dolgozoNev = (String) table.getValueAt(selectedRow, 0);

                epulet.deleteRow(selectedRow);

                String message = String.format("%s dolgozó sikeresen törölve!", dolgozoNev);
                showMessage("Sikeres törlés", message);
            }
        });
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
