package formPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    private JPanel loginPage;
    private JButton accediButton;
    private JButton registratiButton;
    private JTextField textField1;
    private JPasswordField passwordField1;

    public login() {

        Database db = new Database("127.0.0.1","3306", "calcolatricejava");
        db.Connect("root","");
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();

                if(db.Login(username,password))
                    openCalculatorForm();
            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();

                db.Registrazione(username, password);

                openCalculatorForm();
            }
        });
    }

    private void openCalculatorForm(){
        //nascondo il form
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(loginPage);
        if (frame != null) {
            frame.setVisible(false);
        }
        FrmCalculator.main();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("login");
        frame.setContentPane(new login().loginPage);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
