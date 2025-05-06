import java.util.ArrayList;

public class GameLogic {

    public String runGame(String input) {
        String out = "";
             if (input != null && input.equals("flipCoin")) {
                double flip = Math.random();
                if (flip >= 0.5) {
                    out = "heads";
                } else {
                    out = "tails";
                }
                input = null;
                return out;
            } else if (input != null && input.equals("rollDice")) {
                System.out.println("ROLL DICE GAMELOGIC");
                int roll = (int) (Math.random() * 6) + 1;
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
                input = null;
                return (out);

            }
            return null;
    }
}