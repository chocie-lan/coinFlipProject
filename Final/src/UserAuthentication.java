import java.util.ArrayList;

public class UserAuthentication {
    public String login(String checkUser, String checkPass, ArrayList<String> userList){
        boolean userFound = searchForUser(checkUser,checkPass, userList);
        if(userFound){
            System.out.println("Valid login, signing you in");
            return "validUser";
        }
        return "accountDNE"; //prompt sign up
    }
    public String signup(String checkUser, String checkPass, ArrayList<String> userList){
        boolean userFound = searchForUser(checkUser, checkPass, userList);
        if(!userFound){
            System.out.println("User not found, creating account");
            return "addNewUser"; //prompt login
        }
        return "userExists";
    }
    public boolean searchForUser(String checkUser, String checkPass, ArrayList<String> userList) {
        //loop for login
        for (String s : userList) {
            String myUsername = "";
            String myPassword = "";
            String[] existingUsers = s.trim().split("\\s+");
            try {
                myUsername = existingUsers[0];
                myPassword = existingUsers[1];
            } catch (NumberFormatException e) {
                System.out.println("error");
            }
            if (myUsername.equals(checkUser) && myPassword.equals(checkPass)) {
                    return true;
            }
        }
        return false;
    }
}