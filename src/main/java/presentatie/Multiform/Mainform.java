package presentatie.Multiform;

import javax.swing.*;

public class Mainform {
    private JPanel mainPanel;
    private JButton personenButton;
    private JButton showComponentsButton;
    private JButton bestaandeOntleningenButton;

    public Mainform(JFrame surroundingFrame) {

        showComponentsButton.addActionListener(e -> {
            surroundingFrame.setContentPane(new ComponentenViewForm(surroundingFrame).getMainPanel());
            surroundingFrame.setLocation(0, 0);
            surroundingFrame.setSize(1400, 800);
            surroundingFrame.revalidate();
            surroundingFrame.repaint();
        });

        personenButton.addActionListener(e -> {
            surroundingFrame.setContentPane(new PersonenForm(surroundingFrame).getMainPanel());
            surroundingFrame.setLocation(100, 100);
            surroundingFrame.setSize(500,500);
            surroundingFrame.revalidate();
            surroundingFrame.repaint();
        });

        bestaandeOntleningenButton.addActionListener(e -> {
            surroundingFrame.setContentPane(new terugOntlening(surroundingFrame).getMainPanel());
            surroundingFrame.setLocation(100, 100);
            surroundingFrame.setSize(1000,700);
            surroundingFrame.revalidate();
            surroundingFrame.repaint();
        });


    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Form1");
        frame.setContentPane(new Mainform(frame).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }
}

