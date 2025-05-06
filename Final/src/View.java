import javax.swing.*;
import java.awt.*;

public class View extends JFrame{
    CardLayout cardLayout; //add getters??
    JPanel cardPanel;
    ViewGame viewGame;
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
