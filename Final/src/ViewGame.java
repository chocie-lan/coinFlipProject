import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewGame extends JPanel {
    private JButton dice;
    private DefaultListModel<String> diceSelections;
    private JList diceSelector;
    private DefaultListModel<String> selections;
    private JList selector;
    private JLabel diceState;
    private JList leaderboardList;
    private DefaultListModel<String> leaderboard;

    private JButton coin;
    private JLabel coinState;
    private JTextField betAmount;
    private JLabel bet;
    private JLabel balance;
    private JLabel balanceLabel;
    private JLabel leaderboardLabel;

    public ViewGame(){
        setLayout(null);
        //dice
        dice = new JButton("Dice");
        diceSelections = new DefaultListModel<>();
        diceSelector = new JList(diceSelections);
        diceState = new JLabel("Dice Hasn't Been Rolled");

        //coin
        selections = new DefaultListModel<>();
        selector = new JList(selections);
        coin = new JButton("Flip Coin!");
        coinState = new JLabel("Coin Hasn't Been Flipped");

        //leaderboard
        leaderboard = new DefaultListModel<>();
        leaderboardList = new JList(leaderboard);
        leaderboardLabel = new JLabel("Leaderboard:");

        //betting
        bet = new JLabel("Enter bet amount:");
        betAmount = new JTextField(10);
        balance = new JLabel("Current Balance: ");
        balanceLabel = new JLabel("Current Balance: ");

    }
    public void initializeGameView(){
        selector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        diceSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selections.addElement("heads");
        selections.addElement("tails");

        diceSelections.addElement("1");
        diceSelections.addElement("2");
        diceSelections.addElement("3");
        diceSelections.addElement("4");
        diceSelections.addElement("5");
        diceSelections.addElement("6");

        selector.setBounds(50,80, 50,50);
        coin.setBounds(150, 80, 100, 30);
        coinState.setBounds(300, 80, 150, 20);
        diceSelector.setBounds(50, 180, 20,120);
        dice.setBounds(150,180, 70, 70);
        diceState.setBounds(300, 180, 150,20);


        bet.setBounds(25, 10, 150,20);
        betAmount.setBounds(150, 10, 100,20);
        balance.setBounds(400,10, 50, 20);
        leaderboardList.setBounds(300,300, 100,100);
        balanceLabel.setBounds(300,10,100,20);
        leaderboardLabel.setBounds(300, 270, 100, 20);

        add(selector);
        add(coin);
        add(diceSelector);
        add(dice);
        add(coinState);
        add(bet);
        add(betAmount);
        add(balance);
        add(leaderboardList);
        add(diceState);
        add(leaderboardLabel);
        add(balanceLabel);
    }

    public void updateLeaderboardList(ArrayList<String> top3){
        leaderboard.removeAllElements();
        leaderboard.addAll(top3);
    }
    public String getSelectedDice(){
        return diceSelections.getElementAt(diceSelector.getSelectedIndex());
    }
    public void setBalance(int value){
        balance.setText(Integer.toString(value));
    }

    public void setCoinState(String input){
        coinState.setText(input);
    }
    public void setDiceState(String input){
        diceState.setText(input);
    }

    public String getSelected(){
        return selections.getElementAt(selector.getSelectedIndex());
    }

    public String getBetAmountText(){
        return betAmount.getText();
    }

    public void coinFlipButtonListener(ActionListener actionListener){coin.addActionListener(actionListener);}
    public void diceButtonListener(ActionListener actionListener){dice.addActionListener(actionListener);}
}
