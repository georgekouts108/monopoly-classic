public class GameBoard {

    protected Space[] gameboard;
    protected PropertyDeck propertyDeck;
    protected double taxPot;

    protected CardPile chanceCards;
    protected CardPile communityChestCards;
    protected Player[] players;

    public GameBoard() {
        this.propertyDeck = new PropertyDeck();
        this.taxPot = 500;
        this.gameboard = new Space[40];
        this.chanceCards = new CardPile(CardType.CHANCE);
        this.communityChestCards = new CardPile(CardType.COMMUNITY_CHEST);
        this.players = null;
        for (int g = 0; g < 40; g++) {
            SpaceType stype;
            switch (g) {
                case 0:
                    stype=SpaceType.GO; break;
                case 38:
                case 4:
                    stype=SpaceType.TAX; break;
                case 5:
                case 15:
                case 25:
                case 35:
                    stype=SpaceType.RAILROAD; break;
                case 2:
                case 17:
                case 33:
                    stype=SpaceType.COMMUNITY_CHEST; break;
                case 7:
                case 22:
                case 36:
                    stype=SpaceType.CHANCE; break;
                case 12:
                case 28:
                    stype=SpaceType.UTILITY; break;
                case 20:
                    stype=SpaceType.FREE_PARKING; break;
                case 30:
                    stype=SpaceType.GO_TO_JAIL; break;
                case 10:
                    stype=SpaceType.IN_JAIL_JUST_VISITING; break;
                default:
                    stype=SpaceType.PROPERTY; break;
            }

            gameboard[g] = new Space(g, stype);
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public double getTaxPot() {
        return taxPot;
    }

    public void addToPot(double amount) {
        this.taxPot += amount;
    }
    public void resetTaxPot(){
        this.taxPot = 500;
    }
}
