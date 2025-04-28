import javax.swing.*;
import java.awt.*;

public class View {
    private ViewLogin viewLogin;
    private CardLayout cardLayout;
    private JPanel jPanel;

    public View(){
        viewLogin = new ViewLogin();
        cardLayout = new CardLayout();
        jPanel = new JPanel(cardLayout);
    }


}
