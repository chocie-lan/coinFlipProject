public class GameLogic {
    public String runGame(String input) {
            if (input != null && !input.equals("flipCoin") && !input.equals("rollDice")) {
                int bal = Integer.parseInt(input);
            } else if (input != null && input.equals("flipCoin")) {
                return(flipCoin());
            } else if (input != null && input.equals("rollDice")) {
               return(rollDice());
            }
            return null;
    }

    public String flipCoin(){
        double flip = Math.random();
        String out = "";
        if (flip >= 0.5) {
            out = "heads";
        } else {
            out = "tails";
        }
        return out;
    }

    public String rollDice(){
        String out = "";
        int roll = (int) (Math.random() * 6) + 1;
        //UPDATE: Use String.valueOf(roll); //???
        if (roll == 1) {
            out = "1";
        } else if (roll == 2) {
            out = "2";
        } else if (roll == 3) {
            out = "3";
        } else if (roll == 4) {
            out = "4";
        } else if (roll == 5) {
            out = "5";
        } else {
            out = "6";
        }
        //input = null;
        return (out);
    }
}