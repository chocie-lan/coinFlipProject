import javax.swing.*;
import java.awt.event.ActionListener;

public class ViewLogin extends JPanel{
    private JTextField username;
    private JTextField password;
    private JLabel login;
    private JButton loginButton;
    public JButton createAccountButton;
    public JLabel statusMessage;

    public ViewLogin(){
        setLayout(null);
        username = new JTextField(10);
        password = new JTextField(10);
        login = new JLabel("Enter username and password:");
        loginButton = new JButton("Login!");
        createAccountButton = new JButton("Sign up");
        statusMessage = new JLabel("");
    }

    public void initializeLogin(){

        login.setBounds(10,10, 200, 20);
        username.setBounds(20, 50, 100, 20);
        password.setBounds(150, 50, 100, 20);
        loginButton.setBounds(300, 30, 100, 20);
        createAccountButton.setBounds(300, 60, 100, 20);
        statusMessage.setBounds(20, 100, 400, 100);


        add(username);
        add(password);
        add(loginButton);
        add(login);
        add(createAccountButton);
        add(statusMessage);
    }
    public String getUsernameText(){
        return username.getText();
    }
    public String getPasswordText(){return password.getText();}
    public void loginButtonListener(ActionListener actionListener){loginButton.addActionListener(actionListener);}
    public void signUpButtonListener(ActionListener actionListener){createAccountButton.addActionListener(actionListener);}
}
