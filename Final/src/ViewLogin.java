import javax.swing.*;
import java.awt.event.ActionListener;

public class ViewLogin extends JPanel{
    private JTextField username;
    private JTextField password;
    private JLabel login;
    private JButton loginButton;
    public JButton createAccountButton;
    public ViewLogin(){
        username = new JTextField(10);
        password = new JTextField(10);
        login = new JLabel("Enter username and password");
        loginButton = new JButton("Login!");
        createAccountButton = new JButton("Sign up");
    }
    public void initializeLogin(){
        add(username);
        add(password);
        add(loginButton);
        add(login);
        add(createAccountButton);
    }
    public String getUsernameText(){
        return username.getText();
    }

    public String getPasswordText(){
        return password.getText();
    }
    public void loginButtonListener(ActionListener actionListener){loginButton.addActionListener(actionListener);}
    public void signUpButtonListener(ActionListener actionListener){createAccountButton.addActionListener(actionListener);}
}
