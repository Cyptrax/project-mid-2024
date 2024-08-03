package presentatie.Multiform;

import data.Datalaag;
import logica.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComponentenViewForm extends JFrame {

    private JButton resetButton;
    private JButton quitButton;
    private JPanel mainPanel;
    private JButton addComponentenButton;
    private JTable tableWeerstanden;
    private JTable tableIcs;
    private JTable tableSpecialeComponenten;
    private JPanel weergavePanel;
    private JList docentenList;
    private JList personenList;
    private JTextField aantalTextField;
    private JButton ontleningToevoegenButton;
    private JButton notitieToevoegenButton;
    private JTextArea berichtTextArea;
    private JRadioButton icsRadioButton;
    private JRadioButton weerstandenRadioButton;
    private JRadioButton specialeComponentenRadioButton;
    private JTextField terugkeerTextField;
    private JButton voegComponentToeButton;
    private JLabel weergaveLabel;
    private JButton kleurButton1;
    private JButton kleurButton2;
    private JButton kleurButton3;
    private JButton kleurButton4;
    private JFrame frame;
    private PersonenForm personenForm;
    private int uitleningId;

    private ArrayList<String> colorCode = new ArrayList<>();
    private HashMap<String, Color> colorMap = new HashMap<>();

    Datalaag datalaag;
    List<Weerstanden> weerstandenLijst;
    List<Ic> icLijst;
    List<SpecialeComponenten> specialLijst;
    List<Docent> docentenLijst;
    List<Personen> personenLijst;
    ArrayList<Integer> componentenLijst = new ArrayList<>();
    ArrayList<Integer> aantalLijst = new ArrayList<>();
    Integer uitleenId;

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

    public ComponentenViewForm(JFrame surroundingFrame) {

        updateTable1();
        updateTable2();
        updateTable3();
        updateList1();
        updateList2();

        notitieToevoegenButton.setEnabled(false);
        resetButton.addActionListener(e -> {
            surroundingFrame.setContentPane(new Mainform(surroundingFrame).getMainPanel());
            surroundingFrame.setLocation(100,100);
            surroundingFrame.setSize(400,400);
            surroundingFrame.setVisible(true);

        });

        quitButton.addActionListener(e -> System.exit(0));
        addComponentenButton.addActionListener(e -> {
            try {
                surroundingFrame.setContentPane(new ComponentForm(surroundingFrame).getMainPanel());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Fout", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
            surroundingFrame.setLocation(200,300);
            surroundingFrame.setSize(400, 400);
            surroundingFrame.setVisible(true);

        });

        icsRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    weerstandenRadioButton.setSelected(false);
                    specialeComponentenRadioButton.setSelected(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Fout", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        weerstandenRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    icsRadioButton.setSelected(false);
                    specialeComponentenRadioButton.setSelected(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Fout", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        specialeComponentenRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    weerstandenRadioButton.setSelected(false);
                    icsRadioButton.setSelected(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Fout", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        ontleningToevoegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int ontlenerId;
                    int lenerId;

                    if (docentenList.isSelectionEmpty() || personenList.isSelectionEmpty()) {
                        JOptionPane.showMessageDialog(null, "Je moet een ontlener en een uitlener selecteren", "Fout bij personen", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        ontlenerId = docentenLijst.get(docentenList.getSelectedIndex()).getId();
                        lenerId = personenLijst.get(personenList.getSelectedIndex()).getId();
//                        System.out.println("ontlener");
//                        System.out.println(ontlenerId);
//                        System.out.println("lener");
//                        System.out.println(lenerId);
                    }

                    if (componentenLijst.size() <= 0 || aantalLijst.size() <= 0) {
                        JOptionPane.showMessageDialog(null, "Je moet componenten toevoegen", "Geen componenten", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    LocalDateTime now = LocalDateTime.now();
                    Timestamp datum = Timestamp.valueOf(now);
                    Date terugdatum = null;
                    if (terugkeerTextField.getText().length() <= 0) {
                        terugdatum = null;
                    } else {
                        try {
                            terugdatum = Date.valueOf(terugkeerTextField.getText());
                        } catch (IllegalArgumentException ecs) {
                            JOptionPane.showMessageDialog(null, "Foute datum. geef AUB een datum zoals dit: yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }

                    if (terugdatum == null) {
                        JOptionPane.showMessageDialog(null, "Je moet datum opgeven", "Geen datum", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Uitlening uit = new Uitlening(datum, terugdatum, ontlenerId, lenerId, componentenLijst, aantalLijst);
                    uitleenId = datalaag.uitlenenComponenten(uit);
                    componentenLijst.clear();
                    aantalLijst.clear();
                    updateTable1();
                    updateTable2();
                    updateTable3();
                    updateList1();
                    updateList2();
                    notitieToevoegenButton.setEnabled(true);

                    JOptionPane.showMessageDialog(null, "Ontlening toegevoegd", "ontlening", JOptionPane.INFORMATION_MESSAGE);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    notitieToevoegenButton.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Fout", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        voegComponentToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idcomponent;
                int aantal = 0;
                try {
                    aantal = Integer.parseInt(aantalTextField.getText());
                } catch (NumberFormatException num) {
                    JOptionPane.showMessageDialog(null, "Je aantal moet groter dan 0 zijn", "Fout bij aantal", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (aantal <= 0) {
                    JOptionPane.showMessageDialog(null, "Je aantal moet groter dan 0 zijn", "Fout bij aantal", JOptionPane.ERROR_MESSAGE);
                    return;
                }

//                System.out.println(componentenLijst);
//                System.out.println(aantalLijst);

                if (icsRadioButton.isSelected()) {
//                    System.out.println(tableIcs.getSelectedRow());

                    if (tableIcs.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(null, "Select een rij", "Fout bij componenten", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Ic i = icLijst.get(tableIcs.getSelectedRow());
                    idcomponent = i.getId();




                } else if (weerstandenRadioButton.isSelected()) {
//                    System.out.println(tableWeerstanden.getSelectedRow());
                    if (tableIcs.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(null, "Select een rij", "Fout bij componenten", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Weerstanden w = weerstandenLijst.get(tableWeerstanden.getSelectedRow());
                    idcomponent = w.getId();


                } else if (specialeComponentenRadioButton.isSelected()) {
//                    System.out.println(tableSpecialeComponenten.getSelectedRow());
                    if (tableSpecialeComponenten.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(null, "Select een rij", "Fout bij componenten", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    SpecialeComponenten s = specialLijst.get(tableSpecialeComponenten.getSelectedRow());
                    idcomponent = s.getId();


                } else {
                    JOptionPane.showMessageDialog(null, "je hebt geen colom aangeduid!", "Fout bij colom", JOptionPane.ERROR_MESSAGE);
                    return;
                }





                try {
                    if (datalaag.geefAantal(idcomponent, aantal) >= 0) {
                        try {
                            aantal = Integer.parseInt(aantalTextField.getText());
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Aantal moet een geheel nummer zijn", "Nummer", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Er zijn niet genoeg in voorraad", "Niet genoeg Voorraad", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Fout", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }


                componentenLijst.add(idcomponent);
                aantalLijst.add(aantal);

                JOptionPane.showMessageDialog(null, "Component toegevoegt", "component", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        notitieToevoegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String bericht = berichtTextArea.getText();
                    LocalDateTime now = LocalDateTime.now();
                    Timestamp datum = Timestamp.valueOf(now);
                    Notities n = new Notities(bericht, datum, uitleenId);
                    datalaag.addNotitie(n);
                    notitieToevoegenButton.setEnabled(false);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Er is een fout gebeurd", "Fout", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });

        tableIcs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                System.out.println("test");

                int icpinnen = icLijst.get(tableIcs.getSelectedRow()).getAantalpinnen();
                String fotoUrl = "./src/main/resources/ic_128px/" + icpinnen + "pin.png";
//                System.out.println(fotoUrl);
                ImageIcon icon = new ImageIcon(fotoUrl);
                weergaveLabel.setIcon(icon);
                resetColors();
            }
        });
        tableSpecialeComponenten.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String fotoUrl = "./src/main/resources/componenten/procesor-icon.png";
                ImageIcon icon = new ImageIcon(fotoUrl);
                weergaveLabel.setIcon(icon);
                resetColors();
            }
        });
        tableWeerstanden.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Double weerstandwaarde = weerstandenLijst.get(tableWeerstanden.getSelectedRow()).getWeerstandwaarde();
                double tolerantie = weerstandenLijst.get(tableWeerstanden.getSelectedRow()).getTolerantie();
//                System.out.println(weerstandwaarde);
                ArrayList<String> kleurenlijst = datalaag.wwToKleur(weerstandwaarde, tolerantie);
//                System.out.println(kleurenlijst);
//                System.out.println(datalaag.getColorByName(kleurenlijst.get(0)));
                kleurButton1.setVisible(true);
                kleurButton2.setVisible(true);
                kleurButton3.setVisible(true);
                kleurButton4.setVisible(true);
                kleurButton1.setBackground(datalaag.getColorByName(kleurenlijst.get(0)));
                kleurButton2.setBackground(datalaag.getColorByName(kleurenlijst.get(1)));
                kleurButton3.setBackground(datalaag.getColorByName(kleurenlijst.get(2)));
                kleurButton4.setBackground(datalaag.getColorByName(kleurenlijst.get(3)));
                weergaveLabel.setIcon(null);
            }
        });
    }

    public void resetColors() {
        kleurButton1.setBackground(Color.black);
        kleurButton2.setBackground(Color.black);
        kleurButton3.setBackground(Color.black);
        kleurButton4.setBackground(Color.black);
        kleurButton1.setVisible(false);
        kleurButton2.setVisible(false);
        kleurButton3.setVisible(false);
        kleurButton4.setVisible(false);
    }

//    public ArrayList<String> wwToKleur(double ww, double tol) {
//        long integerPart = (long) ww;
//        int count = 0;
//        ArrayList<String> kleuren = new ArrayList<>();
//        double decimalPart = ww - integerPart;
//        String wwstring = String.valueOf((integerPart));
//        for (int i = 0; i < wwstring.length(); i++) {
//            int cijfer = wwstring.charAt(i) - 48;
//            System.out.println(cijfer);
//            if (cijfer == 0) {
//                break;
//            }
//            kleuren.add(getKeyByValue(wwstring.charAt(i) - 48));
//
//        }
//
//        if (ww > 0 && ww < 10 && decimalPart == 0) {
//            kleuren.add(0, "zwart");
//            kleuren.add("zwart");
//        }
//
//        if (wwstring.length() == 2) {
//            kleuren.add("zwart");
//        }
//
//        if (decimalPart * 10 > 0) {
//            kleuren.add(getKeyByValue((int) (decimalPart*10)));
//        }
//
//        if (integerPart > 0 && integerPart < 10 && decimalPart * 10 > 0 && decimalPart * 10 < 10) {
//            kleuren.add("goud");
//        }
//
//        for(int i = 2; i < wwstring.length(); i++) {
//            int cijfer = wwstring.charAt(i) - 48;
//            System.out.println(cijfer);
//            if(cijfer == 0)
//                count++;
//        }
//
//        if (count > 0) {
//            kleuren.add(getKeyByValue(count));
//        }
//
//        if (kleuren.size() == 2) {
//            kleuren.add(1, "zwart");
//        }
//
////        System.out.println(count);
//
//        int toldec = (int) (tol * 100);
//
//        switch (toldec) {
//            case 100:
//                kleuren.add("bruin");
//                break;
//            case 200:
//                kleuren.add("rood");
//                break;
//            case 50:
//                kleuren.add("groen");
//                break;
//            case 25:
//                kleuren.add("blue");
//                break;
//            case 10:
//                kleuren.add("violet");
//                break;
//            case 5:
//                kleuren.add("grijs");
//                break;
//            case 500:
//                kleuren.add("goud");
//                break;
//            case 1000:
//                kleuren.add("silver");
//                break;
//
//        }
//
//        for (String s: kleuren) {
//            System.out.println(s);
//        }
//
//        return kleuren;
//    }


//    public String getKeyByValue(int value) {
//        return colorCode.get(value);
//    }
//
//
//    public void setuparraylist() {
//        colorCode.add("zwart");
//        colorCode.add("bruin");
//        colorCode.add("rood");
//        colorCode.add("oranje");
//        colorCode.add("geel");
//        colorCode.add("groen");
//        colorCode.add("blauw");
//        colorCode.add("paars");
//        colorCode.add("grijs");
//        colorCode.add("wit");
//    }

    public void updateTable1() {
//        System.out.println("update1");
        try {
//            System.out.println("update2");
            weerstandenLijst = datalaag.geefWeerstanden();

            String[] columnNames = {"weerstandwaarde", "tolerantie", "banden", "maximaalvermogen", "producent", "eenheidsprijs", "voorraad_aantal"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Weerstanden weer : weerstandenLijst) {
                Object[] row = {weer.getWeerstandwaarde(), weer.getTolerantie(), weer.getBanden(), weer.getMaximaalvermogen(), weer.getProducent(), weer.getEenheidsprijs(), weer.getVoorraad_aantal()};
                model.addRow(row);
//                System.out.println(Arrays.toString(row));
            }

            tableWeerstanden.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching data from database", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void updateTable2() {
        try {
            icLijst = datalaag.geefIcs();

            String[] columnNames = {"voedingsspanning", "aantalpinnen", "serieaanduiding", "typeaanduiding", "producent", "eenheidsprijs", "voorraad_aantal"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Ic ic : icLijst) {
                Object[] row = {ic.getVoedingsspanning(), ic.getAantalpinnen(), ic.getSerieaanduiding(), ic.getTypeaanduiding(), ic.getProducent(), ic.getEenheidsprijs(), ic.getVoorraad_aantal()};
                model.addRow(row);
//                System.out.println(Arrays.toString(row));
            }

            tableIcs.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching data from database", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void updateTable3() {
        try {
            specialLijst = datalaag.geefSpecialeComponenten();

            String[] columnNames = {"naam", "beschrijving", "producent", "eenheidsprijs", "voorraad_aantal"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (SpecialeComponenten sc : specialLijst) {
                Object[] row = {sc.getNaam(), sc.getBeschrijving(), sc.getProducent(), sc.getEenheidsprijs(), sc.getVoorraad_aantal()};
                model.addRow(row);
//                System.out.println(Arrays.toString(row));
            }

            tableSpecialeComponenten.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching data from database", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
}
