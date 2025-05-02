import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewGame extends JPanel {
    private JButton dice;
    private DefaultListModel<String> diceSelections;
    private JList diceSelector;
    private DefaultListModel<String> selections;
    private JList selector;
    private JList leaderboardList;
    private DefaultListModel<String> leaderboard;

    private JButton coin;
    private JLabel coinState;
    private JTextField betAmount;
    private JLabel bet;
    private JLabel balance;
    public ViewGame(){
        dice = new JButton("Dice");
        diceSelections = new DefaultListModel<>();
        diceSelector = new JList(diceSelections);
        selections = new DefaultListModel<>();
        selector = new JList(selections);
        leaderboard = new DefaultListModel<>();
        leaderboardList = new JList(leaderboard);
        coin = new JButton("Flip Coin!");
        coinState = new JLabel("Coin Hasn't Been Flipped");
        bet = new JLabel("Enter bet amount:");
        betAmount = new JTextField(10);
        balance = new JLabel("Current Balance: ");
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

        add(selector);
        add(coin);
        add(diceSelector);
        add(dice);
        add(coinState);
        add(bet);
        add(betAmount);
        add(balance);
        add(leaderboardList);
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

    public String getSelected(){
        return selections.getElementAt(selector.getSelectedIndex());
    }

    public String getBetAmountText(){
        return betAmount.getText();
    }

    public void coinFlipButtonListener(ActionListener actionListener){coin.addActionListener(actionListener);}
    public void diceButtonListener(ActionListener actionListener){dice.addActionListener(actionListener);}
}
