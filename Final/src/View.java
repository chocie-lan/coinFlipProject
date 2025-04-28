import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame jframe;
    private JPanel jPanel;

    private JTextField username;
    private JTextField password;
    private JLabel login;
    private JButton loginButton;

    private JButton coin;
    private JLabel coinState;
    private JTextField betAmount;
    private JLabel balance;

    public View(){
        jframe = new JFrame();
        jPanel = new JPanel();

        username = new JTextField(10);
        password = new JTextField(10);
        login = new JLabel("Enter username and password");
        loginButton = new JButton("Login!");

        coin = new JButton();
        coinState = new JLabel();
        betAmount = new JTextField();
        balance = new JLabel("Balance: ");
    }

    public void initializeGUI(){
        jPanel.add(username);
        jPanel.add(password);

        jframe.add(jPanel, BorderLayout.NORTH);
        jframe.add(login, BorderLayout.SOUTH);
        jframe.add(loginButton, BorderLayout.CENTER);

        jframe.setSize(500,500);
        jframe.setVisible(true);
    }

    public void initializeGame(){
        jframe.remove(jPanel);
        jframe.remove(login);
        jframe.remove(loginButton);

        jframe.add(coin, BorderLayout.CENTER);
        jframe.add(betAmount, BorderLayout.NORTH);
        jframe.add(balance, BorderLayout.SOUTH);
    }

    public String getUsernameText(){
        return username.getText();
    }

    public String getPasswordText(){
        return password.getText();
    }

}
