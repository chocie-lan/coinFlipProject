import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame{ //view is the FRAME
    //viewGame/View Login are PANELS -> tabs for game play?
    CardLayout cardLayout;
    JPanel cardPanel;
    ViewGame viewGame; //getter??
    ViewLogin viewLogin;

    public View(){
        setTitle("Coin Flip Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        this.viewGame = new ViewGame();
        this.viewLogin = new ViewLogin();

        cardPanel.add(this.viewGame, "Game");
        cardPanel.add(this.viewLogin, "Login");

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "Login");

        setVisible(true);
    }
}
