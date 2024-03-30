import java.util.Random;

public class Main {

    public static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("WELCOME TO MONOPOLY!\n");
        play();
    }
    public static void play() {
        System.out.println("The game board is being set up...\n");
        GameBoard gameBoard = new GameBoard();

        gameBoard.chanceCards.shuffle();
        gameBoard.communityChestCards.shuffle();

        Player george = new Player("michael", Token.TOPHAT);
        Player ioanna = new Player("lauren", Token.PENGUIN);
//        Player catherine = new Player("catherine", Token.DUCK);
//        Player annie = new Player("annie", Token.CAT);
//        Player panayiotis = new Player("panayiotis", Token.RACECAR);

        System.out.println("Introducing our Players...\n");
        Player[] players = new Player[]{george,ioanna};
        for (int p = 0; p < players.length; p++) {
            System.out.println("Player "+(p+1)+" - "+players[p].getName() + " who is the "+players[p].getToken());
        }
        gameBoard.setPlayers(players);
        System.out.println();
        Dice dice = new Dice();

        // SETUP IS DONE BY NOW
        int activePlayer = random.nextInt(players.length);

        int roundsRemaining = 100;
        while (roundsRemaining > 0) {
            System.out.println("%%%%%%%%% " +roundsRemaining + " ROUNDS LEFT %%%%%%%%\n");

            int playersRemaining = players.length;
            while (playersRemaining > 0) {

                // TODO: If players[activePlayer] is BANKRUPT, then immediately do:
                //      playersRemaining--;
                //      activePlayer = (activePlayer + 1) % players.length;
                //  otherwise, you can proceed with lines 49 to 119

                System.out.println(gameBoard.players[activePlayer].getName() + " (" + gameBoard.players[activePlayer].getToken() + ") [$"+
                        gameBoard.players[activePlayer].getBank()+"] is now up!");
                boolean attemptedDoublesInJail = false;

                if (gameBoard.players[activePlayer].isArrested()) {
                    int choice = gameBoard.players[activePlayer].attemptBail();
                    if (choice == 1) {
                        gameBoard.players[activePlayer].payMoney(50, true, gameBoard);
                        gameBoard.players[activePlayer].setArrested(false);
                    }
                    else {
                        attemptedDoublesInJail = true;
                        dice.roll();
                        dice.report();

                        if (dice.isDoubles()) {
                            gameBoard.players[activePlayer].setArrested(false);
                            gameBoard.players[activePlayer].resetDoublesAttemptJail();
                            gameBoard.players[activePlayer].move(dice.getTotal(), gameBoard);
                            gameBoard.players[activePlayer].resetDoublesCount();
                            playersRemaining--;
                            activePlayer = (activePlayer + 1) % gameBoard.players.length;
                        }
                        else {
                            gameBoard.players[activePlayer].incrementDoublesAttemptJail();
                            if (gameBoard.players[activePlayer].getDoublesAttemptJail() == 3) {
                                gameBoard.players[activePlayer].setArrested(false);
                                gameBoard.players[activePlayer].payMoney(50, true, gameBoard);
                                gameBoard.players[activePlayer].resetDoublesAttemptJail();
                                gameBoard.players[activePlayer].move(dice.getTotal(), gameBoard);
                            }
                            playersRemaining--;
                            activePlayer = (activePlayer + 1) % gameBoard.players.length;
                        }
                    }
                }

                if (!gameBoard.players[activePlayer].isArrested() && !attemptedDoublesInJail) {

                    // before rolling, a player may choose to manage their properties
                    int managePropDecision = gameBoard.players[activePlayer].managePropertyDecision(true);
                    if (managePropDecision == 1) {
                        gameBoard.players[activePlayer].manageProperties(gameBoard);
                    }

                    dice.roll();
                    dice.report();
                    if (dice.isDoubles()) {
                        gameBoard.players[activePlayer].incrementDoublesCount();
                        if (gameBoard.players[activePlayer].getDoublesCount() == 0) {
                            activePlayer = (activePlayer + 1) % gameBoard.players.length;
                            playersRemaining--;
                        }
                        else {
                            gameBoard.players[activePlayer].move(dice.getTotal(), gameBoard);

                            if (gameBoard.players[activePlayer].isArrested()){
                                activePlayer = (activePlayer + 1) % gameBoard.players.length;
                                playersRemaining--;
                            }

                            System.out.println();
                        }
                    } else {
                        gameBoard.players[activePlayer].move(dice.getTotal(), gameBoard);
                        gameBoard.players[activePlayer].resetDoublesCount();
                        System.out.println();
                        playersRemaining--;
                        activePlayer = (activePlayer + 1) % gameBoard.players.length;
                    }
                }
            }

            roundsRemaining--;
        }
    }
}