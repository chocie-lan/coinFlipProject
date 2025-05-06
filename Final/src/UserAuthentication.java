import java.util.ArrayList;

//if you don't press the correct button originally, it will not let you try again....

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
        System.out.println("SIGN UP METHOD CALLED");
        boolean userFound = searchForUser(checkUser, checkPass, userList);
        if(!userFound){
            System.out.println("User not found, creating account");
            return "addNewUser"; //prompt login
        }
        return "userExists";
    }
    public boolean searchForUser(String checkUser, String checkPass, ArrayList<String> userList) {
        //loop for login
        System.out.println("PASSWORD IN USER AUTHENTICATION: " + checkPass);
        System.out.println("USERNAME FROM USER AUTHENTICATION: " + checkUser);
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
            } else {
                //System.out.println("NO MATCH");
            }
        }
        return false;
    }
}