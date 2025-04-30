import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View {
    private JFrame jFrame;
    private JTabbedPane jTabs;
    private JPanel loginPanel;
    private JPanel gamePanel;

    private JTextField username;
    private JTextField password;
    private JLabel login;
    private JButton loginButton;
    public JButton createAccountButton;

    private JList leaderboardList;
    private DefaultListModel<String> leaderboard;
    private JButton coin;
    private JLabel coinState;
    private JTextField betAmount;
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
        createAccountButton = new JButton("Sign up");


        leaderboard = new DefaultListModel<>();
        leaderboardList = new JList(leaderboard);
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
        loginPanel.add(createAccountButton);

        gamePanel.add(coin);
        gamePanel.add(coinState);
        gamePanel.add(bet);
        gamePanel.add(betAmount);
        gamePanel.add(balance);
        gamePanel.add(leaderboardList);

        jTabs.add("Login", loginPanel);
        jTabs.add("Game", gamePanel);

        jFrame.add(jTabs);
        jFrame.setSize(500,500);
        jFrame.setVisible(true);
    }

    public void updateLeaderboardList(ArrayList<String> top3){
        leaderboard.removeAllElements();
        leaderboard.addAll(top3);

    }

    public String getUsernameText(){
        return username.getText();
    }

    public String getPasswordText(){
        return password.getText();
    }

    public String getBetAmountText(){
        return betAmount.getText();
    }

    public void loginButtonListener(ActionListener actionListener){loginButton.addActionListener(actionListener);}
    public void coinFlipButtonListener(ActionListener actionListener){coin.addActionListener(actionListener);}
    public void signUpButtonListener(ActionListener actionListener){createAccountButton.addActionListener(actionListener);}

}
