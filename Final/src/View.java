import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame jFrame;
    private JTabbedPane jTabs;
    private JPanel loginPanel;
    private JPanel gamePanel;

    JTextField username;
    JTextField password;
    private JLabel login;
    public JButton loginButton;

    private JButton coin;
    private JLabel coinState;
    JTextField betAmount;
    private JLabel bet;
    private JLabel balance;

    public View(){
        jFrame = new JFrame();
        jTabs = new JTabbedPane();
        loginPanel = new JPanel();
        gamePanel  = new JPanel();

        username = new JTextField(10);
        password = new JTextField(10);
        login = new JLabel("Enter username and password");
        loginButton = new JButton("Login!");


        coin = new JButton("Flip Coin!");
        coinState = new JLabel("Coin Hasn't Been Flipped");
        bet = new JLabel("Enter bet amount:");
        betAmount = new JTextField(10);
        balance = new JLabel("Current Balance: ");
    }

    public void initializeGUI(){
        loginPanel.add(username);
        loginPanel.add(password);
        loginPanel.add(loginButton);
        loginPanel.add(login);

        gamePanel.add(coin);
        gamePanel.add(coinState);
        gamePanel.add(bet);
        gamePanel.add(betAmount);
        gamePanel.add(balance);

        jTabs.add("Login", loginPanel);
        jTabs.add("Game", gamePanel);

        jFrame.add(jTabs);
        jFrame.setSize(500,500);
        jFrame.setVisible(true);
    }

    public String getUsernameText(){
        return username.getText();
    }
    public String getPasswordText(){
        return password.getText();
    }

    //why are these not doing anything?????
    public void loginButtonListener(ActionListener actionListener){
        System.out.println("Login action listener added");
        loginButton.addActionListener(actionListener);
        System.out.println("Added listener to button: " + loginButton.getText());

    }
    public void coinFlipButtonListener(ActionListener actionListener){coin.addActionListener(actionListener);}

}
