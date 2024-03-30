public class Property {

    private Player proprietor;
    private double price;
    private String name;
    private int spaceID;

    private boolean isMortgaged;
    private double mortgageValue;

    public Property(String name, double price, int spaceID){
        this.proprietor = null;
        this.price = price;
        this.name = name;
        this.spaceID = spaceID;
        this.mortgageValue = this.price * 0.5;
        this.isMortgaged = false;
    }

    public Player getProprietor() {
        return proprietor;
    }

    public void setProprietor(Player proprietor) {
        this.proprietor = proprietor;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getSpaceID(){
        return spaceID;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
    }

    public double getMortgageValue() {
        return mortgageValue;
    }

    public boolean isMortgageable() { // todo: override for color properties
        return !isMortgaged;
    }

    public boolean canBeSold(){ // todo: override for color properties
        return !isMortgaged;
    }

    /**
     * UNIQUE TO COLOR PROPERTIES
     * @return
     */
    public double getRent() {
        return 0;
    }

    /**
     * UNIQUE TO COLOR PROPERTIES
     * @return
     */
    public boolean isMonopolized() {
        return false;
    }

    public int setMonopolized(boolean monopolized) {
        return 0;
    }

    /**
     * UNIQUE TO COLOR PROPERTIES
     * @return
     */
    public Color getColor() {
        return null;
    }

    /**
     * UNIQUE TO COLOR PROPERTIES
     * @return
     */
    public int getHouseCount() {return 0;}

    /**
     * UNIQUE TO COLOR PROPERTIES
     */
    public boolean decrementHouseCount() {return false;}

    /**
     * UNIQUE TO COLOR PROPERTIES
     */
    public boolean incrementHouseCount() {return false;}

    /**
     * UNIQUE TO COLOR PROPERTIES
     * @return
     */
    public double getHousePrice() {
        return 0;
    }
    public double getOneHouseRent() {
        return 0;
    }

    public double getTwoHouseRent() {
        return 0;
    }

    public double getThreeHouseRent() {
        return 0;
    }

    public double getFourHouseRent() {
        return 0;
    }

    public double getHotelRent() {
        return 0;
    }
}
