public class GameLogic {
    public String runGame(String input) {
        System.out.println("HELLO FROM runGame in GameLogic; my I recieved: "+ input); //where the fuck is balance coming from??
        String out = "";
            //input = bufferedReader.readLine();
            if (input != null && !input.equals("flipCoin") && !input.equals("rollDice")) {
                int bal = Integer.parseInt(input);
                //System.out.println(bal);
//                leaderboard.updateLeaderboard(id,bal); //dont forget about this!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                top3 = leaderboard.getTopThree();
//                for(String s : top3){
//                    printWriter.println(s);
//                }
            } else if (input != null && input.equals("flipCoin")) {
                //String out;
                //System.out.println("COIN FLIP GAMELOGIC");
                double flip = Math.random();
                if (flip >= 0.5) {
                    out = "heads";
                } else {
                    out = "tails";
                }
                //printWriter.println(out);
                input = null;
                return out;
            } else if (input != null && input.equals("rollDice")) {
                System.out.println("ROLL DICE GAMELOGIC");
                //String out;
                int roll = (int) (Math.random() * 6) + 1;
                //System.out.println(roll);
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
                //printWriter.println(out);
                input = null;
                return (out);

            }
            if (input != null) {

                double flip = Math.random();
                if (flip >= 0.5) {
                    out = "heads";
                } else {
                    out = "tails";
                }
                //printWriter.println(out);
                input = null;
            }
            return out;
    }
}
//what about dice??