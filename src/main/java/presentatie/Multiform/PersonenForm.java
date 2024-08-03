package presentatie.Multiform;

import data.Datalaag;
import logica.Docent;
import logica.Personen;
import logica.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PersonenForm {
    private JButton resetButton;
    private JButton quitButton;
    private JPanel mainPanel;
    private JTextArea displayTextArea;
    private JTextField voornaamTextField;
    private JTextField familienaamTextField;
    private JTextField vakgebiedTextField;
    private JTextField beginjaarTextField;
    private JLabel voornaamLabel;
    private JLabel familienaamLabel;
    private JLabel vakgebiedLabel;
    private JLabel beginjaarLabel;
    private JButton voegDocentToeButton;
    private JButton voegStudentToeButton;
    private JList displayList;
    Datalaag datalaag;

    {
        try {
            datalaag = new Datalaag("mydb");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public PersonenForm(JFrame surroundingFrame) {
//        updateTextfield();
        updateList();
        resetButton.addActionListener(e -> {
            surroundingFrame.setContentPane(new Mainform(surroundingFrame).getMainPanel());
            surroundingFrame.setLocation(100,100);
            surroundingFrame.setSize(400,400);
            surroundingFrame.setVisible(true);

        });

        quitButton.addActionListener(e -> System.exit(0));
        voegStudentToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long nummer = datalaag.getNummmers();
                    if (voornaamTextField.getText().length() <= 0 || familienaamTextField.getText().length() <= 0) {
                        JOptionPane.showMessageDialog(null, "Geen voornaam of familienaam ingevuld", "Foute ingave", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String naam = voornaamTextField.getText();
                    String familienaam = familienaamTextField.getText();

                    Student stud = new Student(naam, familienaam, nummer);
                    datalaag.maakStudentAan(stud);
//                    updateTextfield();
                    updateList();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        voegDocentToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long nummer = datalaag.getNummmers();
                    if (voornaamTextField.getText().length() <= 0 || familienaamTextField.getText().length() <= 0 || vakgebiedTextField.getText().length() <= 0) {
                        JOptionPane.showMessageDialog(null, "Je hebt een vakje niet ingevult", "Foute ingave", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String naam = voornaamTextField.getText();
                    String familienaam = familienaamTextField.getText();
                    String vak;
                    if (!(vakgebiedTextField.getText().equals("ELO") || vakgebiedTextField.getText().equals("ICT"))) {
                        System.out.println(vakgebiedTextField.getText());
                        JOptionPane.showMessageDialog(null, "Je hebt geen juist vakgebied ingegeven: de vakgebieden zijn ELO of ICT", "Foute ingave", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        vak = vakgebiedTextField.getText();
                    }

                    Date beginjaar = null;
                    if (beginjaarTextField.getText().length() <= 0) {
                        beginjaar = null;
                    } else {
                        beginjaar = Date.valueOf(beginjaarTextField.getText());
                    }

                    Docent doc = new Docent(naam, familienaam, nummer, vak, beginjaar);
                    datalaag.maakDocentAan(doc);
//                    updateTextfield();
                    updateList();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Foute datum. geef AUB een datum zoals dit: yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
    }

//    public void updateTextfield() {
//        displayTextArea.setText("");
//        List<Personen> personenlijst;
//        try {
//            personenlijst = datalaag.geefPersonen();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        for (Personen p : personenlijst) {
//            displayTextArea.append(p.getVoornaam() + " " + p.getfamilienaam() + " " + p.getNummer() + "\n");
//        }
//    }


    public void updateList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<Personen> personenlijst;
        try {
            personenlijst = datalaag.geefPersonen();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Personen p : personenlijst) {
            listModel.addElement(p.getVoornaam() + " " + p.getfamilienaam() + " " + p.getNummer());
        }
        displayList.setModel(listModel);
    }
}
