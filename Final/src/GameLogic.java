public class GameLogic {
    public String runGame(String input){
            if(input != null && !input.equals("flipCoin")){
                int bal = Integer.parseInt(input);
                System.out.println(bal);
                //leaderboard.updateLeaderboard(id,bal);
            }
            String out = "";
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