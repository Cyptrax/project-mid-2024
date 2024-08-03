package presentatie.Multiform;

import data.Datalaag;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ComponentForm extends JPanel implements ActionListener {

    static JLabel l;
    static Datalaag datalaag;
    private JButton resetButton;
    private JFrame surroundingFrame;
    private JPanel mainPanel;

    public ComponentForm(JFrame surroundingFrame) throws SQLException {
        this.surroundingFrame = surroundingFrame;
        datalaag = new Datalaag("mydb");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton button1 = new JButton("save");
        JButton button2 = new JButton("open");
        l = new JLabel("no file selected");
        resetButton = new JButton("Terug");

        mainPanel.add(button1);
        mainPanel.add(button2);
        mainPanel.add(l);
        mainPanel.add(resetButton);

        button1.addActionListener(this);
        button2.addActionListener(this);
        resetButton.addActionListener(e -> {
            surroundingFrame.setContentPane(new ComponentenViewForm(surroundingFrame).getMainPanel());
            surroundingFrame.setLocation(0, 0);
            surroundingFrame.setSize(1400, 800);
            surroundingFrame.setVisible(true);
        });

        this.add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String com = evt.getActionCommand();

        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int r = (com.equals("save")) ? j.showSaveDialog(null) : j.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            String filePath = j.getSelectedFile().getAbsolutePath();
            l.setText(filePath);
            datalaag.loadComponentsFromCSV(filePath);
            JOptionPane.showMessageDialog(this, "Components loaded successfully!");
        } else {
            l.setText("the user cancelled the operation");
        }
    }
}