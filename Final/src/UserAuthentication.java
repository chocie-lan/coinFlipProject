import java.util.ArrayList;

public class UserAuthentication {
    public String login(String checkUser, String checkPass, ArrayList<String> userList){
        int userFound = searchForUser(checkUser,checkPass, userList);
        if(userFound == 1){
            return "validUser";
        }
        return "accountDNE";
    }
    public String signup(String checkUser, String checkPass, ArrayList<String> userList){
        int userFound = searchForUser(checkUser, checkPass, userList);
        if(userFound == 0){
            return "addNewUser";
        }
        System.out.println("SELECT A NEW USERNAME");
        return "userExists";
    }
    public int searchForUser(String checkUser, String checkPass, ArrayList<String> userList) {
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
                return 1;
            }else if(myUsername.equals(checkUser)){
                return 2;
            }
        }
        return 0;
    }
}