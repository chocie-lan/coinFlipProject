import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private ViewLogin viewLogin;
    private CardLayout cardLayout;
    private JPanel jPanel;

    public View(){
        viewLogin = new ViewLogin();
        cardLayout = new CardLayout();
        jPanel = new JPanel(cardLayout);

        setSize(500,500);
        setVisible(true);
    }


}
