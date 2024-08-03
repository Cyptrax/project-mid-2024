package presentatie.Multiform;

import data.Datalaag;
import logica.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static presentatie.Multiform.ComponentForm.datalaag;

public class terugOntlening {
    private JList docentenList;
    private JList personenList;
    private JButton filterenOpUitlenersButton;
    private JTable showTable;
    private JTextField aantalComponentenTextField;
    private JButton voegNotitieToeButton;
    private JTextArea notitieTextArea;
    private JButton quitButton;
    private JButton terugButton;
    private JButton terugbrengenButton;
    private JPanel mainPanel;
    private JButton filterenOpLenersButton;

    List<Docent> docentenLijst;
    List<Personen> personenLijst;
    int filternummer;
    int filterId;
    List<Filter> filterList;

    {
        try {
            datalaag = new Datalaag("mydb");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Er kan geen connectie met de database gemaakt worden", "Fout bij connecteren", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public terugOntlening(JFrame surroundingFrame) {
        updateList1();
        updateList2();

        terugButton.addActionListener(e -> {
            surroundingFrame.setContentPane(new Mainform(surroundingFrame).getMainPanel());
            surroundingFrame.setLocation(100,100);
            surroundingFrame.setSize(400,400);
            surroundingFrame.setVisible(true);

        });
        quitButton.addActionListener(e -> System.exit(0));

        filterenOpUitlenersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filternummer = 1;

                if (docentenList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Selecteer in rij", "Fout bij filteren", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                filterId = docentenLijst.get(docentenList.getSelectedIndex()).getId();
//                System.out.println(filterId);
                getFiltered();

            }
        });
        filterenOpLenersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filternummer = 2;

                if (personenList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Selecteer in rij", "Fout bij filteren", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                filterId = personenLijst.get(personenList.getSelectedIndex()).getId();
//                System.out.println(filterId);
                getFiltered();

            }
        });
        voegNotitieToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bericht = notitieTextArea.getText();
                LocalDateTime now = LocalDateTime.now();
                Timestamp datum = Timestamp.valueOf(now);

                if (showTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Selecteer een uitlening", "Fout bij notitie", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ontleenId = filterList.get(showTable.getSelectedRow()).getIdUitlenen();
//                System.out.println(ontleenId);

                if (bericht.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "Je moet een bericht meegeven", "Fout bij Bericht", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                Notities n = new Notities(bericht, datum, ontleenId);
                try {
                    datalaag.addNotitie(n);
                    JOptionPane.showMessageDialog(null, "Notitie aangemaakt", "Notitie", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurt", "Fout", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }

            }
        });
        terugbrengenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (showTable.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(null, "Je moet een rij aanduiden", "Fout bij rij", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                if (aantalComponentenTextField.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(null, "Je moet een aantal meegeven", "Fout bij aantal", JOptionPane.ERROR_MESSAGE);
                    return;
                }

//                System.out.println(verschil);
                Filter f = filterList.get(showTable.getSelectedRow());
                int verschil = (f.getAantalComponenten()) - (Integer.parseInt(aantalComponentenTextField.getText()));

                if (verschil < 0) {
                    JOptionPane.showMessageDialog(null, "Je kunt niet meer componenten teruggeven dan dat jer er hebt uitgeleent", "Fout bij aantal", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (Integer.parseInt(aantalComponentenTextField.getText()) < 0) {
                    JOptionPane.showMessageDialog(null, "Je kunt geen negatief aantal componenten teruggeven", "Fout bij aantal", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                int uitlenenId = f.getIdUitlenen();
                int componentenId = f.getComponentenId();
                int aantal = Integer.parseInt(aantalComponentenTextField.getText());
                LocalDateTime now = LocalDateTime.now();
                Timestamp datum = Timestamp.valueOf(now);
                getFiltered();
                try {
                    datalaag.brengComponentenTerug(uitlenenId, componentenId, aantal, datum);
                    getFiltered();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Fout gebeurd bij database", "Fout bij aantal", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public void getFiltered() {
        try {
            filterList = datalaag.filterenUitlenen(filternummer, filterId);
            updateTable(filterList);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void updateList1() {
        try {
            docentenLijst = datalaag.geefDocent();

            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Docent docent : docentenLijst) {
                listModel.addElement(docent.getVoornaam() + " " + docent.getfamilienaam());
            }
            docentenList.setModel(listModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateList2() {
        try {
            personenLijst = datalaag.geefPersonen();

            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Personen persoon : personenLijst) {
                listModel.addElement(persoon.getVoornaam() + " " + persoon.getfamilienaam());
            }
            personenList.setModel(listModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTable(List<Filter> filterList) {

        String[] columnNames = {"ontleendaan_id", "ontleenddoor_id", "uitlenen_id", "componenten_id", "aantal_teruggebracht", "aantal_uitgeleent", "datum_uitlening gedaan" ,"voornaam", "familienaam"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Filter f : filterList) {
            Object[] row = {f.getOntleendAanId(), f.getOntleendDoorId(), f.getIdUitlenen(), f.getComponentenId(), f.getAantal_terugbrengingen(), f.getAantalComponenten(), f.getTijdstip() , f.getVoornaam(), f.getFamilienaam()};
            model.addRow(row);
        }
        showTable.setModel(model);

    }



}
