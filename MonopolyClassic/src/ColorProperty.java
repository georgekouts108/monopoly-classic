public class ColorProperty extends Property {

    private double rent;
    private Color color;

    private int houseCount;
    private double housePrice;
    private double oneHouseRent;
    private double twoHouseRent;
    private double threeHouseRent;
    private double fourHouseRent;
    private double hotelRent;

    private boolean isMonopolized;
    public ColorProperty(String name, double price, int spaceID, double rent, Color color,
                         double onehr, double twohr, double threehr, double fourhr, double hotelRent, double housePrice){
        super(name, price, spaceID);
        this.rent = rent;
        this.color = color;
        this.isMonopolized = false;
        this.houseCount = 0;
        this.oneHouseRent = onehr;
        this.twoHouseRent = twohr;
        this.threeHouseRent = threehr;
        this.fourHouseRent = fourhr;
        this.hotelRent = hotelRent;
        this.housePrice = housePrice;
    }

    @Override
    public boolean isMonopolized() {
        return isMonopolized;
    }

    @Override
    public int setMonopolized(boolean monopolized) {
        isMonopolized = monopolized;
        return 0;
    }
    @Override
    public double getRent() {
        return rent;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isMortgageable(){
        // TODO: there will later be more conditions to judge this result...
        return !this.isMortgaged();
    }

    @Override
    public boolean canBeSold() {
        // TODO: there will later be more conditions to judge this result...
        return !this.isMortgaged();
    }

    public int getHouseCount() {
        return houseCount;
    }

    public boolean decrementHouseCount() {
        if (this.houseCount >= 1) {
            this.houseCount -= 1;
            return true;
        }
        return false;
    }
    public boolean incrementHouseCount() {
        if (this.houseCount < 5) {
            this.houseCount += 1;
            return true;
        }
        return false;
    }

    public double getOneHouseRent() {
        return oneHouseRent;
    }

    public double getTwoHouseRent() {
        return twoHouseRent;
    }

    public double getThreeHouseRent() {
        return threeHouseRent;
    }

    public double getFourHouseRent() {
        return fourHouseRent;
    }

    public double getHousePrice() {
        return housePrice;
    }

    public double getHotelRent() {
        return hotelRent;
    }
}
