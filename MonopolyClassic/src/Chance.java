public class Chance {

    public static void printMessage(String message) {
        System.out.println("\n========================CHANCE====================================================");
        System.out.println(message);
        System.out.println("========================CHANCE====================================================\n");
    }

    public static void actOnChanceCard(int cardID, Player player, GameBoard board, int diceRoll) {
        System.out.println("CHANCE CARD #"+cardID+" was drawn");
        switch (cardID) {
            case 101:
                printMessage("Advance to Boardwalk");
                player.setLocationID(39);
                player.encounterSpace(39,false, board, diceRoll, true);
                break;
            case 102:
                printMessage("Advance to GO.\nCollect $200");
                player.setLocationID(0);
                player.encounterSpace(0,false, board, diceRoll, true);
                break;
            case 103:
                printMessage("Advance to Illinois Avenue.\nIf you pass Go, collect $200");
                while (player.getLocationID() != 24) {
                    player.setLocationID( (player.getLocationID() + 1) % 40);
                    player.encounterSpace(player.getLocationID(), player.getLocationID() != 24, board, diceRoll ,true);
                }
                break;
            case 104:
                printMessage("Advance to St. Charles Place.\nIf you pass Go, collect $200");
                while (player.getLocationID() != 11) {
                    player.setLocationID( (player.getLocationID() + 1) % 40);
                    player.encounterSpace(player.getLocationID(), player.getLocationID() != 11, board, diceRoll,true );
                }
                break;
            case 105:
            case 106:
                printMessage("Advance to the nearest Railroad.\nIf unowned, you may buy it from the Bank.\nIf owned, pay the owner twice the rental to which they are otherwise entitled.");
                boolean arrived = player.getLocationID()==5 || player.getLocationID()==15 || player.getLocationID()==25 || player.getLocationID()==35;
                while (!arrived) {
                    player.setLocationID( (player.getLocationID() + 1) % 40);
                    arrived = player.getLocationID()==5 || player.getLocationID()==15 || player.getLocationID()==25 || player.getLocationID()==35;
                    player.encounterSpace(player.getLocationID(), !arrived, board, diceRoll, true );
                }
                break;
            case 107:
                printMessage("Advance token to nearest Utility.\nIf unowned, you may buy it from the Bank.\nIf owned, throw dice and pay owner a total ten times amount thrown.");
                boolean _arrived = player.getLocationID()==12 || player.getLocationID()==28;
                while (!_arrived) {
                    player.setLocationID( (player.getLocationID() + 1) % 40);
                    _arrived = player.getLocationID()==12 || player.getLocationID()==28;
                    player.encounterSpace(player.getLocationID(), !_arrived, board, diceRoll, true );
                }
                break;
            case 108:
                printMessage("Bank pays you dividend of $50");
                player.earnMoney(50);
                break;
            case 109:
                // TODO: Implement the logic for GET OUT OF JAIL FREE cards
                break;
            case 110:
                printMessage("Go back 3 spaces");
                int count = 3;
                while (count > 0) {
                    player.setLocationID( (player.getLocationID() - 1) % 40);
                    count--;
                    player.encounterSpace(player.getLocationID(), count != 0, board, diceRoll,true );
                }
                break;
            case 111:
                printMessage("Go to Jail. Go directly to Jail.\nDo not pass Go, do not collect $200");
                player.getArrested();
                break;
            case 112:
                printMessage("Make general repairs on all your properties.\nFor each house pay $25, for each hotel pay $100");
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
                player.payMoney((houses*25)+(hotels*100), true, board);
                break;
            case 113:
                printMessage("Speeding fine.\nPay $15");
                player.payMoney(15, true, board);
                break;
            case 114:
                printMessage("Take a trip to Reading Railroad.\nIf you pass Go, collect $200");
                while (player.getLocationID() != 5) {
                    player.setLocationID( (player.getLocationID() + 1) % 40);
                    player.encounterSpace(player.getLocationID(), player.getLocationID() != 5, board, diceRoll ,true);
                }
                break;
            case 115:
                printMessage("You have been elected Chairman of the Board.\nPay each player $50");
                for (int p = 0; p < board.players.length; p++) {
                    // TODO: a player who receives $50 cannot be bankrupt.
                    if (board.players[p].getToken() != player.getToken()) {
                        player.payRent(50, board.players[p]);
                    }
                }
                break;
            case 116:
                printMessage("Your building loan matures.\nCollect $150");
                player.earnMoney(150);
                break;
        }
    }
}
