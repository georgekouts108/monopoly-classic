import java.util.Random;
import java.util.Scanner;

public class Player {
    private  static Scanner userInput = new Scanner(System.in);
    public static Random random = new Random();
    private String name;
    private Token token;
    private int locationID;
    private int doublesCount;
    private int doublesAttemptJail;
    private double bank;
    private boolean isArrested;

    public Player(String name, Token token) {
        this.name = name.toUpperCase();
        this.token = token;
        this.locationID = 0; // all players start on GO
        this.doublesCount = 0;
        this.bank = 1500;
        this.isArrested = false;
        this.doublesAttemptJail = 0;
    }

    public String getName() {
        return name;
    }
    public Token getToken() {
        return token;
    }
    public int getLocationID() {
        return locationID;
    }
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    /** JAIL METHODS
     * **/
    public boolean isArrested() {
        return this.isArrested;
    }
    public void setArrested(boolean arrested) {
        this.isArrested = arrested;
    }
    public void getArrested() {
        this.locationID = 10;
        this.isArrested = true;
        this.doublesCount = 0;
        System.out.println(this.name + " ("+this.token+") got arrested!");
    }
    public int getDoublesAttemptJail() {
        return doublesAttemptJail;
    }
    public void incrementDoublesAttemptJail() {
        this.doublesAttemptJail++;
    }
    public int attemptBail() {
        System.out.print("You're arrested! Enter \'1\' to pay $50 bail, " +
                "or \'2\' to try rolling doubles ("+(3 - this.doublesAttemptJail)+" attempts remaining): ");
        int choice = 0;
        boolean valid = false;
        while (!valid) {
            choice = userInput.nextInt();
            switch (choice) {
                case 1:
                case 2:
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid choice. Enter \'1\' to pay $50 bail, " +
                            "or \'2\' to try rolling doubles ("+(3 - this.doublesAttemptJail)+" attempts remaining): ");
                    break;
            }
        }

        return choice;
    }
    public void resetDoublesAttemptJail() {
        this.doublesAttemptJail = 0;
    }


    /** ROLLING DOUBLES
     * **/
    public void incrementDoublesCount() {
        this.doublesCount++;
        if (this.doublesCount == 3) {
            this.getArrested();
            this.doublesCount = 0;
        }
    }
    public void resetDoublesCount(){
        this.doublesCount = 0;
    }
    public int getDoublesCount() {
        return doublesCount;
    }

    /** MOVING AROUND THE BOARD / LANDING ON SPACES
     * **/
    public void move(int amount, GameBoard gameBoard){

        int destination = (this.locationID + amount) % 40;

        for (int a = amount; a >= 1; a--) {
            this.locationID = (this.locationID + 1) % 40;
            this.encounterSpace(this.locationID, this.locationID != destination, gameBoard, amount, false);
        }
    }
    public void encounterSpace(int spaceID, boolean isPassing, GameBoard board, int diceRoll, boolean sentByCard) {
        if (spaceID == 0) {
            System.out.println(this.name + " ("+this.token+") got $200 for "+(isPassing ? "PASSING" : "LANDING ON") +" GO!");
            this.earnMoney(200);
        }
        else {
            if (!isPassing){
                switch (spaceID) {
                    case 2:
                    case 4:
                    case 7:
                    case 10:
                    case 17:
                    case 20:
                    case 22:
                    case 30:
                    case 33:
                    case 36:
                    case 38:
                        System.out.println(this.name + " ("+this.token+") landed on "+board.gameboard[spaceID].getSpaceName());
                        break;
                    default:
                        System.out.println(this.name + " ("+this.token+") landed on "+
                                board.propertyDeck.getProperty(spaceID).getName() +
                                (board.propertyDeck.getProperty(spaceID).getColor() != null ? " ("+board.propertyDeck.getProperty(spaceID).getColor()+")":"") );
                        break;
                }

                // TODO: the player must deal with the space...
                // TODO: in cases where a player must pay money, they should be given the option to manage their
                //  properties (for now, only utilities)
                switch (spaceID) {
                    case 5:
                    case 15:
                    case 25:
                    case 35:
                        Property railroad = board.propertyDeck.getProperty(spaceID);
                        // if the railroad is unowned, they may buy it from the bank
                        if (railroad.getProprietor() == null) {
                            int decision = unownedPropertyDecision(railroad);
                            if (decision == 1) {
                                this.payMoney(railroad.getPrice(), false, board);
                                board.propertyDeck.sellPropertyToPlayer(railroad.getName(), this);
                            }
                        }
                        else {
                            System.out.println(railroad.getName() + " is owned "+(railroad.isMortgaged() ? "and MORTGAGED " : "") +
                                    " by " + (railroad.getProprietor()==this ? "YOU" : railroad.getProprietor().getName()) );

                            if (railroad.getProprietor().getToken() != this.token && !railroad.isMortgaged()) {
                                if (!railroad.getProprietor().isArrested()) {

                                    double rent = board.propertyDeck.calculateRailroadRent(spaceID) * (sentByCard ? 2 : 1);
                                    if (sentByCard) {
                                        System.out.println(this.name+" was sent by a CHANCE CARD and must pay TWICE the rent.");
                                    }
                                    System.out.println(this.name+" owes them $"+rent+" in rent.");

                                    boolean playerWentBankrupt = this.payRent(rent, railroad.getProprietor());

//                                // TODO: if the player's rent payment bankrupts them, they will be presented the option to manage properties
                                    if (playerWentBankrupt){
                                        System.out.println(this.name + " went BANKRUPT!");
                                    }

//                                int managePropDecision = this.managePropertyDecision();
//                                if (managePropDecision == 1) {
//                                    this.manageProperties(board);
//                                }
                                }
                                else {
                                    System.out.println(railroad.getProprietor().getName() + " is IN JAIL, so they collect no rent.");
                                }
                            }

                        }
                        break;
                    case 12:
                    case 28:
                        Property utility = board.propertyDeck.getProperty(spaceID );
                        // if the utility is unowned, they may buy it from the bank
                        if (utility.getProprietor() == null) {
                            int decision = unownedPropertyDecision(utility);
                            if (decision == 1) {
                                this.payMoney(utility.getPrice(), false, board);
                                board.propertyDeck.sellPropertyToPlayer(utility.getName(), this);
                            }
                        }
                        else {
                            System.out.println(utility.getName() + " is owned "+(utility.isMortgaged() ? "and MORTGAGED " : "") +
                                    " by " + (utility.getProprietor()==this ? "YOU" : utility.getProprietor().getName()) );

                            if (utility.getProprietor().getToken() != this.token && !utility.isMortgaged()) {
                                if (!utility.getProprietor().isArrested()) {
                                    double rent;
                                    if (sentByCard) {
                                        System.out.println(this.name+" was sent by a CHANCE CARD and must pay 10 times a dice roll.");
                                        rent = 10 * ( (random.nextInt(6)+1) + (random.nextInt(6)+1) );
                                    }
                                    else {
                                        rent = board.propertyDeck.calculateUtilityRent(spaceID, diceRoll);
                                    }

                                    System.out.println("You owe them $"+rent+" in rent.");
                                    boolean playerWentBankrupt = this.payRent(rent, utility.getProprietor());

//                                // TODO: if the player's rent payment bankrupts them, they will be presented the option to manage properties
                                    if (playerWentBankrupt){
                                        System.out.println(this.name + " went BANKRUPT!");
                                    }

//                                int managePropDecision = this.managePropertyDecision();
//                                if (managePropDecision == 1) {
//                                    this.manageProperties(board);
//                                }
                                }
                                else {
                                    System.out.println(utility.getProprietor().getName() + " is IN JAIL, so they collect no rent.");
                                }
                            }

                        }
                        break;
                    case 30:
                        this.getArrested();
                        break;
                    case 4:
                        this.payMoney(200, true, board);
                        System.out.println(this.name + " ("+this.token+") paid $200 in Income Tax.");
                        break;
                    case 38:
                        this.payMoney(100, true, board);
                        System.out.println(this.name + " ("+this.token+") paid $100 in Luxury Tax.");
                        break;
                    case 20:
                        this.earnMoney(board.getTaxPot());
                        System.out.println(this.name + " ("+this.token+") earned $"+board.getTaxPot()+" from Free Parking!");
                        board.resetTaxPot();
                        break;
                    case 10:
                        break;
                    case 7:
                    case 22:
                    case 36:
                        int drawnChanceCardID = board.chanceCards.drawCard();

                        Chance.actOnChanceCard(drawnChanceCardID, this, board, diceRoll);
                        break;
                    case 2:
                    case 17:
                    case 33:
                        int drawnCommChestCardID = board.communityChestCards.drawCard();
                        CommunityChest.actOnCommunityChestCard(drawnCommChestCardID, this, board, diceRoll);
                        break;
                    default:
                        Property colorprop = board.propertyDeck.getProperty(spaceID);
                        // if the color property is unowned, they may buy it from the bank
                        if (colorprop.getProprietor() == null) {
                            int decision = unownedPropertyDecision(colorprop);
                            if (decision == 1) {
                                this.payMoney(colorprop.getPrice(), false, board);
                                board.propertyDeck.sellPropertyToPlayer(colorprop.getName(), this);
                            }
                        }
                        else {
                            System.out.println(colorprop.getName() + " is owned"+
                                    (colorprop.isMonopolized() ? ", MONOPOLIZED "+(colorprop.getHouseCount()>0 ? "with "+
                                            (colorprop.getHouseCount()==5?"a HOTEL" : colorprop.getHouseCount()+" HOUSE(S)") : "")+",": "")  +
                                    (colorprop.isMortgaged() ? " and MORTGAGED " : "") +
                                    " by " + (colorprop.getProprietor()==this ? "YOU" : colorprop.getProprietor().getName()) );

                            if (colorprop.getProprietor().getToken() != this.token && !colorprop.isMortgaged()) {
                                if (!colorprop.getProprietor().isArrested()) {

                                    double rent = board.propertyDeck.calculateColorPropertyRent(spaceID);

                                    System.out.println("You owe them $"+rent+" in rent.");

                                    boolean playerWentBankrupt = this.payRent(rent, colorprop.getProprietor());

//                                // TODO: if the player's rent payment bankrupts them, they will be presented the option to manage properties
                                    if (playerWentBankrupt){
                                        System.out.println(this.name + " went BANKRUPT!");
                                    }

//                                int managePropDecision = this.managePropertyDecision();
//                                if (managePropDecision == 1) {
//                                    this.manageProperties(board);
//                                }
                                }
                                else {
                                    System.out.println(colorprop.getProprietor().getName() + " is IN JAIL, so they collect no rent.");
                                }
                            }

                        }
                        break;
                }
            }
        }
    }


    /** BUYING AND MANAGING PROPERTIES
     * **/
    public int unownedPropertyDecision(Property property) {
        // TODO: In case a player cannot afford a property, give them the option to manage existing properties of theirs beforehand.
        System.out.print("Enter \'1\' to buy " + property.getName() + " for $"+property.getPrice() +" or \'0\' to pass: ");
        int choice = -1;
        boolean valid = false;
        while (!valid) {
            choice = userInput.nextInt();
            switch (choice) {
                case 1:
                case 0:
                    valid = true;
                    break;
                default:
                    System.out.print("Invalid choice. " +
                            "Enter \'1\' to buy " + property.getName() + "($"+property.getPrice() +") or \'0\' to pass: ");
                    break;
            }
        }

        return choice;
    }
    public int managePropertyDecision(boolean beforeRolling) {
        System.out.print("Enter \'1\' if you want to manage properties "+(beforeRolling ? "before rolling" : "")+" or \'0\' not to: ");
        int choice = -1;
        boolean valid = false;
        while (!valid) {
            choice = userInput.nextInt();
            switch (choice) {
                case 1:
                case 0:
                    valid = true;
                    break;
                default:
                    System.out.print("Invalid choice. Enter \'1\' if you want to manage properties or \'0\' not to: ");
                    break;
            }
        }

        return choice;
    }

    // TODO: This method must later be modified to accommodate selling, unmortgaging, and improving properties
    public void manageProperties(GameBoard board) {
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(this.name+", YOU ARE NOW IN A PROPERTY MANAGEMENT SESSION...");

        boolean finished = false;
        while (!finished) {

            int action = -1;
            boolean okay = false;
            while (!okay) {
                System.out.println("Here are the actions you may perform, if applicable: ");
                System.out.println("   {0} END SESSION");
                System.out.println("   {1} MORTGAGE A PROPERTY"); // todo: soon, other options will be added...
                System.out.println("   {2} UNMORTGAGE A PROPERTY");
                System.out.println("   {3} SELL A PROPERTY");
                System.out.println("   {4} BUILD A HOUSE/HOTEL");
                System.out.println("   {5} SELL A HOUSE/HOTEL");
                System.out.println();
                System.out.print("Enter the number corresponding to the desired action: ");
                action = userInput.nextInt();
                switch (action) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        okay = true;
                        break;
                }
            }
            switch (action) {
                case 0:
                    finished = true;
                    break;
                case 1:
                     if (board.propertyDeck.getUnmortgagedPropertyCount(this) > 0){
                         System.out.println("Here are your mortgageable properties:\n");
                         board.propertyDeck.listMortgagablePropertiesOfPlayer(this);
                         System.out.println();

                         boolean choiceGood = false;
                         int choice;
                         while (!choiceGood) {
                             System.out.print("Enter the number matching the property you'd like to mortgage, or \'0\' to stop: ");
                             choice = userInput.nextInt();
                             switch (choice) {
                                 case 0:
                                     choiceGood = true;
                                     break;
                                 default:
                                     if (board.propertyDeck.getProprietor(choice) != null) {
                                         if (board.propertyDeck.getProprietor(choice).getToken() == this.token) {
                                             if (board.propertyDeck.getProperty(choice).isMortgageable()) {
                                                 String proprietorName = board.propertyDeck.getProprietor(choice).getName();
                                                 String propertyName = board.propertyDeck.getProperty(choice).getName();
                                                 double mortgageVal = board.propertyDeck.getProperty(choice).getMortgageValue();
                                                 boolean approved = board.propertyDeck.mortgageOrUnmortgageProperty(choice, true);

                                                 if (approved) {
                                                     this.earnMoney(mortgageVal);
                                                     System.out.println(proprietorName+" mortgaged "+propertyName);
                                                 }
                                                 else {
                                                     System.out.println(proprietorName+" cannot mortgage "+propertyName+" b/c its color set has houses");
                                                 }

                                                 choiceGood = true;
                                             }
                                         }
                                         else {
                                             System.out.println("Sorry, that property is not yours.");
                                         }
                                     }
                                     else {
                                         System.out.println("Sorry, that is invalid.");
                                     }
                                     break;
                             }
                         }
                     }
                     else {
                         System.out.println("You do not have any mortgageable properties.");
                     }
                     break;
                case 2:
                    if (board.propertyDeck.getMortgagedPropertyCount(this) > 0){
                        System.out.println("Here are your mortgaged properties:\n");
                        board.propertyDeck.listMortgagedPropertiesOfPlayer(this);
                        System.out.println();

                        boolean choiceGood = false;
                        int choice;
                        while (!choiceGood) {
                            System.out.print("Enter the number matching the property you'd like to unmortgage, or \'0\' to stop: ");
                            choice = userInput.nextInt();
                            switch (choice) {
                                case 0:
                                    choiceGood = true;
                                    break;
                                default:
                                    if (board.propertyDeck.getProprietor(choice) != null) {
                                        if (board.propertyDeck.getProprietor(choice).getToken() == this.token) {
                                            if (!board.propertyDeck.getProperty(choice).isMortgageable()) {
                                                String proprietorName = board.propertyDeck.getProprietor(choice).getName();
                                                String propertyName = board.propertyDeck.getProperty(choice).getName();
                                                double mortgageVal = board.propertyDeck.getProperty(choice).getMortgageValue();
                                                board.propertyDeck.mortgageOrUnmortgageProperty(choice, false);
                                                this.payMoney(mortgageVal, false, board);
                                                System.out.println(proprietorName+" unmortgaged "+propertyName);
                                                choiceGood = true;
                                            }
                                        }
                                        else {
                                            System.out.println("Sorry, that property is not yours.");
                                        }
                                    }
                                    else {
                                        System.out.println("Sorry, that is invalid.");
                                    }
                                    break;
                            }
                        }
                    }
                    else {
                        System.out.println("You do not have any mortgaged properties.");
                    }
                    break;
                case 3:
                    if (board.propertyDeck.getUnmortgagedPropertyCount(this) > 0){
                        System.out.println("Here are properties that you could sell:\n");
                        board.propertyDeck.listMortgagablePropertiesOfPlayer(this);
                        System.out.println();

                        boolean choiceGood = false;
                        int choice;
                        while (!choiceGood) {
                            System.out.print("Enter the number matching the property you'd like to sell, or \'0\' to stop: ");
                            choice = userInput.nextInt();
                            switch (choice) {
                                case 0:
                                    choiceGood = true;
                                    break;
                                default:
                                    if (board.propertyDeck.getProprietor(choice) != null) {
                                        if (board.propertyDeck.getProprietor(choice).getToken() == this.token) {
                                            if (board.propertyDeck.getProperty(choice).canBeSold()) {
                                                String proprietorName = board.propertyDeck.getProprietor(choice).getName();
                                                String propertyName = board.propertyDeck.getProperty(choice).getName();
                                                double fullPrice = board.propertyDeck.getProperty(choice).getPrice();

                                                boolean approved = board.propertyDeck.repossesProperty(choice);
                                                if (approved){
                                                    this.earnMoney(fullPrice);
                                                    System.out.println("APPROVED: "+proprietorName+" sold "+propertyName);
                                                }
                                                else {
                                                    System.out.println("DECLINED: "+proprietorName+" could NOT sell "+propertyName+" because it's in a monopoly with house(s)/hotel(s).");
                                                }


                                                choiceGood = true;
                                            }
                                        }
                                        else {
                                            System.out.println("Sorry, that property is not yours.");
                                        }
                                    }
                                    else {
                                        System.out.println("Sorry, that is invalid.");
                                    }
                                    break;
                            }
                        }
                    }
                    else {
                        System.out.println("You do not have any properties that you could sell.");
                    }
                    break;
                case 4:
                    if (board.propertyDeck.getPropertyCountForBuildingHouses(this) > 0 && board.propertyDeck.listPropertiesThatAllowMoreHouses(this,false) != null){
                        System.out.println("Here are properties that you could buy a house/hotel on:\n");
                        int[] validPropIDs = board.propertyDeck.listPropertiesThatAllowMoreHouses(this,true);
                        System.out.println();

                        boolean choiceGood = false;
                        int choice;
                        while (!choiceGood) {
                            System.out.print("Enter the number matching the property you'd like to build a house/hotel on, or \'0\' to stop: ");
                            choice = userInput.nextInt();
                            switch (choice) {
                                case 0:
                                    choiceGood = true;
                                    break;
                                default:
                                    if (board.propertyDeck.getProprietor(choice) != null) {
                                        if (board.propertyDeck.getProprietor(choice).getToken() == this.token) {
                                            boolean propValid = false;
                                            for (int v = 0; v < validPropIDs.length; v++) {
                                                if(validPropIDs[v] == choice){
                                                    propValid = true;
                                                }
                                            }
                                            if (propValid) {
                                                String proprietorName = board.propertyDeck.getProprietor(choice).getName();
                                                String propertyName = board.propertyDeck.getProperty(choice).getName();
                                                double housePrice = board.propertyDeck.getProperty(choice).getHousePrice();

                                                boolean approved = board.propertyDeck.buildHouseOnProperty(choice);
                                                if (approved) {
                                                    this.payMoney(housePrice,false, board);
                                                    System.out.println("APPROVED: "+proprietorName+" bought a "+(board.propertyDeck.getProperty(choice).getHouseCount()==5?"HOTEL":"HOUSE")+" on "+propertyName);
                                                }
                                                else {
                                                    System.out.println("DECLINED: "+proprietorName+" cannot further improve "+propertyName);
                                                }

                                                choiceGood = true;
                                            }
                                        }
                                        else {
                                            System.out.println("Sorry, that property is not yours.");
                                        }
                                    }
                                    else {
                                        System.out.println("Sorry, that is invalid.");
                                    }
                                    break;
                            }
                        }
                    }
                    else {
                        System.out.println("You do not have any properties that you could buy a house on.");
                    }
                    break;
                case 5:
                    if (board.propertyDeck.getPropertyCountForSellingHouses(this) > 0 && board.propertyDeck.listPropertiesThatAllowLessHouses(this,false) != null) {
                        System.out.println("Here are properties that you could sell a house/hotel on:\n");
                        int[] validPropIDs = board.propertyDeck.listPropertiesThatAllowLessHouses(this,true);
                        System.out.println();

                        boolean choiceGood = false;
                        int choice;
                        while (!choiceGood) {
                            System.out.print("Enter the number matching the property you'd like to sell a house/hotel on, or \'0\' to stop: ");
                            choice = userInput.nextInt();
                            switch (choice) {
                                case 0:
                                    choiceGood = true;
                                    break;
                                default:
                                    if (board.propertyDeck.getProprietor(choice) != null) {
                                        if (board.propertyDeck.getProprietor(choice).getToken() == this.token) {
                                            boolean propValid = false;
                                            for (int v = 0; v < validPropIDs.length; v++) {
                                                if(validPropIDs[v] == choice){
                                                    propValid = true;
                                                }
                                            }
                                            if (propValid) {
                                                String proprietorName = board.propertyDeck.getProprietor(choice).getName();
                                                String propertyName = board.propertyDeck.getProperty(choice).getName();
                                                double housePrice = board.propertyDeck.getProperty(choice).getHousePrice();

                                                boolean approved = board.propertyDeck.sellHouseOnProperty(choice);
                                                if (approved) {
                                                    this.earnMoney(housePrice);
                                                    System.out.println("APPROVED: "+proprietorName+" sold a "+(board.propertyDeck.getProperty(choice).getHouseCount()+1==5?"HOTEL":"HOUSE")+" on "+propertyName);
                                                }
                                                else {
                                                    System.out.println("DECLINED: "+proprietorName+" has no houses nor hotels on "+propertyName);
                                                }

                                                choiceGood = true;
                                            }
                                        }
                                        else {
                                            System.out.println("Sorry, that property is not yours.");
                                        }
                                    }
                                    else {
                                        System.out.println("Sorry, that is invalid.");
                                    }
                                    break;
                            }

                        }
                    }
                    else {
                        System.out.println("You do not have any properties that you could sell a house on.");
                    }
                    break;
            }


        }
        System.out.println("END OF PROPERTY MANAGEMENT SESSION FOR "+this.name+".");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }


    /** PAYING AND EARNING MONEY
     * **/
    public double getBank() {
        return bank;
    }
    public boolean payRent(double rent, Player recipient) {
        this.bank -= rent;
        recipient.bank += rent;

        if (this.bank <= 0) {
            return true;
        }
        return false;
    }
    public void payMoney(double amount, boolean isTax, GameBoard board) {
        this.bank -= amount;
        if (isTax){
            board.addToPot(amount);
        }
    }
    public void earnMoney(double amount){
        this.bank += amount;
    }


}
