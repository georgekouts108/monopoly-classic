public class CommunityChest {



    public static void printMessage(String message) {
        System.out.println("\n========================COMMUNITY CHEST====================================================");
        System.out.println(message);
        System.out.println("========================COMMUNITY CHEST====================================================\n");
    }

    public static void actOnCommunityChestCard(int cardID, Player player, GameBoard board, int diceRoll) {
        System.out.println("COMMUNITY CHEST CARD #"+cardID+" was drawn");
        switch (cardID) {
            case 201:
                printMessage("Advance to GO.\nCollect $200");
                player.setLocationID(0);
                player.encounterSpace(0,false, board, diceRoll, true);
                break;
            case 202:
                printMessage("Bank error in your favor.\nCollect $200");
                player.earnMoney(200);
                break;
            case 203:
                printMessage("Doctor's fee.\nPay $50");
                player.payMoney(50,true,board);
                break;
            case 204:
                printMessage("From sale of stock you get $50");
                player.earnMoney(50);
                break;
            case 205:
                // TODO: Implement the logic for GET OUT OF JAIL FREE cards
            case 206:
                printMessage("Go to Jail. Go directly to Jail.\nDo not pass Go, do not collect $200");
                player.getArrested();
                break;
            case 207:
                printMessage("Holiday fund matures.\nReceive $100");
                player.earnMoney(100);
                break;
            case 208:
                printMessage("Income tax refund.\nCollect $20");
                player.earnMoney(20);
                break;
            case 209:
                printMessage("It is your birthday.\nCollect $10 from every player.");
                for (int p = 0; p < board.players.length; p++) {
                    // TODO: a player who gives $10 cannot be bankrupt.
                    if (board.players[p].getToken() != player.getToken()) {
                        board.players[p].payRent(10, player);
                    }
                }
                break;
            case 210:
                printMessage("Life insurance matures.\nCollect $100");
                player.earnMoney(100);
                break;
            case 211:
                printMessage("Pay hospital fees of $100");
                player.payMoney(100,true,board);
                break;
            case 212:
                printMessage("Pay school fees of $50");
                player.payMoney(50,true,board);
                break;
            case 213:
                printMessage("Receive $25 consultancy fee.");
                player.earnMoney(25);
                break;
            case 214:
                printMessage("You are assessed for street repairs.\nFor each house pay $40, for each hotel pay $115");
                int houses = 0, hotels = 0;
                for (int p = 0; p < board.propertyDeck.properties.length; p++) {
                    Property nextProp = board.propertyDeck.properties[p];
                    if (nextProp.getProprietor() != null) {
                        if (nextProp.getProprietor().getToken() == player.getToken()) {
                            switch (nextProp.getHouseCount()) {
                                case 5:
                                    hotels++;
                                    break;
                                default:
                                    houses += nextProp.getHouseCount();
                                    break;
                            }
                        }
                    }
                }
                player.payMoney((houses*40)+(hotels*115), true, board);
                break;
            case 215:
                printMessage("You have won second prize in a beauty contest.\nCollect $10");
                player.earnMoney(10);
                break;
            case 216:
                printMessage("You inherit $100");
                player.earnMoney(100);
                break;
        }
    }
}
