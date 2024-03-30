public class PropertyDeck {

    protected Property[] properties;
    public PropertyDeck(){
        // there will eventually be 28 properties

        Utility u1 = new Utility("ELECTRIC COMPANY", 150, 12);
        Utility u2 = new Utility("WATER WORKS", 150, 28);

        Railroad r1 = new Railroad("READING RAILROAD", 200, 5);
        Railroad r2 = new Railroad("PENNSYLVANIA RAILROAD", 200, 15);
        Railroad r3 = new Railroad("B.& O. RAILROAD", 200, 25);
        Railroad r4 = new Railroad("SHORT LINE", 200, 35);

        String[] colorPropertyNames = new String[]{
                "MEDITERRANEAN AVENUE", "BALTIC AVENUE", "ORIENTAL AVENUE", "VERMONT AVENUE",
                "CONNECTICUT AVENUE", "ST. CHARLES PLACE", "STATES AVENUE", "VIRGINIA AVENUE",
                "ST. JAMES PLACE", "TENNESSEE AVENUE", "NEW YORK AVENUE", "KENTUCKY AVENUE",
                "INDIANA AVENUE", "ILLINOIS AVENUE", "ATLANTIC AVENUE", "VENTNOR AVENUE",
                "MARVIN GARDENS", "PACIFIC AVENUE", "NORTH CAROLINA AVENUE", "PENNSYLVANIA AVENUE",
                "PARK PLACE", "BOARDWALK"
        };
        double[] colorPropertyPrices = new double[]{
                60,60,100,100,120,140,140,160,180,180,200,220,220,240,260,260,280,300,300,320,350,400
        };
        int[] colorPropertyIDs = new int[]{1,3,6,8,9,11,13,14,16,18,19,21,23,24,26,27,29,31,32,34,37,39};

        double[] colorPropertyRents = new double[]{2,4,6,6,8,10,10,12,14,14,16,18,18,20,22,22,24,26,26,28,35,50};

        double[] oneHouseRents = new double[]{10,20,30,30,40,50,50,60,70,70,80,90,90,100,110,110,120,130,130,150,175,200};
        double[] twoHouseRents = new double[]{30,60,90,90,100,150,150,180,200,200,220,250,250,300,330,330,360,390,390,450,500,600};
        double[] threeHouseRents = new double[]{90,180,270,270,300,450,450,500,550,550,600,700,700,750,800,800,850,900,900,1000,1100,1400};
        double[] fourHouseRents = new double[]{160,320,400,400,450,625,625,700,750,750,800,875,875,925,975,975,1025,1100,1100,1200,1300,1700};
        double[] hotelRents = new double[]{250,450,550,550,600,750,750,900,950,950,1000,1050,1050,1100,1150,1150,1200,1275,1275,1400,1500,2000};
        double[] housePrices = new double[]{50,50,50,50,50,100,100,100,100,100,100,150,150,150,150,150,150,200,200,200,200,200};

        Color[] colorPropertyColors = new Color[]{
                Color.BROWN,Color.BROWN,Color.TURQUOISE,Color.TURQUOISE,Color.TURQUOISE,
                Color.PINK,Color.PINK,Color.PINK,Color.ORANGE,Color.ORANGE,Color.ORANGE,
                Color.RED,Color.RED,Color.RED,Color.YELLOW,Color.YELLOW,Color.YELLOW,
                Color.GREEN,Color.GREEN,Color.GREEN,Color.BLUE,Color.BLUE
        };
        // TODO: Later, add the remaining attributes of color properties

        this.properties = new Property[28];
        for (int p = 0; p < 28; p++) {
            switch (p) {
                case 0: this.properties[p] = u1; break;
                case 1: this.properties[p] = u2; break;
                case 2: this.properties[p] = r1; break;
                case 3: this.properties[p] = r2; break;
                case 4: this.properties[p] = r3; break;
                case 5: this.properties[p] = r4; break;
                default:
                    String cpName = colorPropertyNames[p - 6];
                    double cpPrice = colorPropertyPrices[p - 6];
                    int cpID = colorPropertyIDs[p - 6];
                    double cpRents = colorPropertyRents[p - 6];
                    Color cpColor = colorPropertyColors[p - 6];
                    double onehr = oneHouseRents[p - 6];
                    double twohr = twoHouseRents[p - 6];
                    double threehr = threeHouseRents[p - 6];
                    double fourhr = fourHouseRents[p - 6];
                    double housePrice = housePrices[p - 6];
                    double hotelRent = hotelRents[p - 6];
                    this.properties[p] = new ColorProperty(cpName, cpPrice, cpID, cpRents, cpColor, onehr, twohr, threehr, fourhr, hotelRent, housePrice);
                    break;
            }
        }
    }

    /** RETRIEVING PROPERTY INFORMATION
     * **/
    public Property[] getProperties() {
        return properties;
    }
    public Property getProperty(int propID) {
        int propIndex = 0;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID()==propID){
                propIndex = p;
                break;
            }
        }

        return this.properties[propIndex];
    }
    public Player getProprietor(int propID) {
        Player proprietor = null;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID() == propID){
                proprietor = this.properties[p].getProprietor();
                break;
            }
        }

        return proprietor;
    }


    /** RENT CALCULATIONS
     * **/
    public double calculateColorPropertyRent(int propID) {
        double rent = 0;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID() == propID) {
                switch (this.properties[p].getHouseCount()){
                    case 0:
                        rent = this.properties[p].getRent() * (this.properties[p].isMonopolized() ? 2 : 1);
                        break;
                    case 1:
                        rent = this.properties[p].getOneHouseRent();
                        break;
                    case 2:
                        rent = this.properties[p].getTwoHouseRent();
                        break;
                    case 3:
                        rent = this.properties[p].getThreeHouseRent();
                        break;
                    case 4:
                        rent = this.properties[p].getFourHouseRent();
                        break;
                    case 5:
                        rent = this.properties[p].getHotelRent();
                        break;
                }

                break;
            }
        }
        return rent;
    }
    public double calculateRailroadRent(int propID) {

        Player readingProprietor = null;
        Player pennsylvaniaProprietor = null;
        Player bandoProprietor = null;
        Player shortlineProprietor = null;

        for (int p = 0; p < this.properties.length; p++){
            switch (this.properties[p].getSpaceID()) {
                case 5:
                    readingProprietor = this.properties[p].getProprietor();
                    break;
                case 15:
                    pennsylvaniaProprietor = this.properties[p].getProprietor();
                    break;
                case 25:
                    bandoProprietor = this.properties[p].getProprietor();
                    break;
                case 35:
                    shortlineProprietor = this.properties[p].getProprietor();
                    break;
            }
        }
        int numberOfRailsOwned = 1;
        switch (propID) {
            case 5:
                if (pennsylvaniaProprietor != null){
                    if (pennsylvaniaProprietor.getToken() == readingProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (bandoProprietor != null){
                    if (bandoProprietor.getToken() == readingProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (shortlineProprietor != null){
                    if (shortlineProprietor.getToken() == readingProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                break;
            case 15:
                if (readingProprietor != null){
                    if (readingProprietor.getToken() == pennsylvaniaProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (bandoProprietor != null){
                    if (bandoProprietor.getToken() == pennsylvaniaProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (shortlineProprietor != null){
                    if (shortlineProprietor.getToken() == pennsylvaniaProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                break;
            case 25:
                if (readingProprietor != null){
                    if (readingProprietor.getToken() == bandoProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (pennsylvaniaProprietor != null){
                    if (pennsylvaniaProprietor.getToken() == bandoProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (shortlineProprietor != null){
                    if (shortlineProprietor.getToken() == bandoProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                break;
            case 35:
                if (readingProprietor != null){
                    if (readingProprietor.getToken() == shortlineProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (pennsylvaniaProprietor != null){
                    if (pennsylvaniaProprietor.getToken() == shortlineProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                if (bandoProprietor != null){
                    if (bandoProprietor.getToken() == shortlineProprietor.getToken()) {
                        numberOfRailsOwned++;
                    }
                }
                break;
        }

        if (numberOfRailsOwned == 2){
            return 50;
        }
        if (numberOfRailsOwned == 3){
            return 100;
        }
        if (numberOfRailsOwned == 4){
            return 200;
        }
        return 25;
    }
    public double calculateUtilityRent(int propID, int diceRoll) {

        Player electricCompanyProprietor = null;
        Player waterWorksProprietor = null;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID() == 12) {
                electricCompanyProprietor = this.properties[p].getProprietor();
            }
            else if (this.properties[p].getSpaceID() == 28) {
                waterWorksProprietor = this.properties[p].getProprietor();
            }
        }
        int multiplicand = 4;
        if (propID == 12) {
            if (waterWorksProprietor != null) {
                if (waterWorksProprietor.getToken() == electricCompanyProprietor.getToken()){
                    multiplicand = 10;
                }
            }
        }
        else if (propID == 28){
            if (electricCompanyProprietor != null) {
                if (electricCompanyProprietor.getToken() == waterWorksProprietor.getToken()){
                    multiplicand = 10;
                }
            }
        }

        return diceRoll * multiplicand;
    }


    /** COLOR PROPERTY MONOPOLIES
     * **/
    public void setMonopolizedStatus(Color propColor, boolean monopolized) {
        for (int p = 0; p < this.properties.length; p++) {

            if (this.properties[p].getColor()==propColor) {
                this.properties[p].setMonopolized(monopolized);
            }
        }
    }
    public boolean isColorGroupMonopolized(Color propColor) {
        Player[] proprietors = new Player[3];
        int propCount = 0;

        for (int p = 0; p < this.properties.length; p++) {
            if (this.properties[p].getColor() == propColor) {
                proprietors[propCount++] = this.properties[p].getProprietor();
            }
        }

        if (propCount == 2) {
            if (proprietors[0] != null){
                if (proprietors[1] != null) {
                    if (proprietors[0].getToken() == proprietors[1].getToken()) {
                        return true;
                    }
                }
            }
        }
        else {
            if (proprietors[0] != null){
                if (proprietors[1] != null) {
                    if (proprietors[2] != null) {
                        if (proprietors[0].getToken() == proprietors[1].getToken() && proprietors[1].getToken() == proprietors[2].getToken()) {
                            return true;
                        }
                    }

                }
            }
        }

        return false;
    }


    /** PROPERTY SALES AND RETURNS
     * **/
    public void sellPropertyToPlayer(String propName, Player newOwner) {
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getName().equalsIgnoreCase(propName)){
                this.properties[p].setProprietor(newOwner);

                // did the player acquire a new monopoly?
                if (this.properties[p].getColor() != null) {
                    Color propColor = this.properties[p].getColor();
                    boolean monopolized = this.isColorGroupMonopolized(propColor);
                    this.setMonopolizedStatus(propColor, monopolized);
                }
                break;
            }
        }
    }
    public boolean repossesProperty(int propID) {
        boolean approved = true;

        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID() == propID){

                if (this.properties[p].isMonopolized()) {
                    boolean okay = true;
                    for (int q = 0; q < this.properties.length; q++){
                        if (this.properties[q].getColor() == this.properties[p].getColor() && this.properties[q].getHouseCount()>0){
                            okay = false;
                            approved = false;
                            break;
                        }
                    }

                    if (okay) {
                        this.properties[p].setProprietor(null);

                        if (this.properties[p].getColor() != null) {
                            this.setMonopolizedStatus(this.properties[p].getColor(), false);
                        }
                    }
                }
                else {
                    this.properties[p].setProprietor(null);
                }

                break;
            }
        }
        return approved;
    }


    /** (UN)MORTGAGING PROPERTIES
     * **/
    public boolean mortgageOrUnmortgageProperty(int propID, boolean mortgage) {
        boolean approved = true;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID()==propID){
                if (!mortgage) {
                    this.properties[p].setMortgaged(false);
                }
                else {
                    if (this.properties[p].isMonopolized()) {
                        boolean okay = true;
                        for (int q = 0; q < this.properties.length; q++){
                            if (this.properties[q].getColor() == this.properties[p].getColor() && this.properties[q].getHouseCount()>0){
                                okay = false;
                                approved = false;
                                break;
                            }
                        }

                        if (okay) {
                            this.properties[p].setMortgaged(true);
                        }
                    }
                    else {
                        this.properties[p].setMortgaged(true);
                    }
                }


                break;
            }
        }
        return approved;
    }
    public int getUnmortgagedPropertyCount(Player player) {
        int count = 0;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getProprietor() != null) {
                if (this.properties[p].getProprietor().getToken() == player.getToken() && !this.properties[p].isMortgaged() && this.properties[p].getHouseCount()==0){
                    count++;
                }
            }
        }
        return count;
    }
    public int getMortgagedPropertyCount(Player player) {
        int count = 0;
        for (int p = 0; p < this.properties.length; p++){
            // TODO: Later, more conditions will be needed to see if a prop can be unmortgaged (ex. house count, hotel count, etc.)
            if (this.properties[p].getProprietor() != null) {
                if (this.properties[p].getProprietor().getToken() == player.getToken() && this.properties[p].isMortgaged()){
                    count++;
                }
            }
        }
        return count;
    }
    public void listMortgagablePropertiesOfPlayer(Player player) {
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getProprietor() != null) {
                if (this.properties[p].getProprietor().getToken() == player.getToken() && !this.properties[p].isMortgaged() && this.properties[p].getHouseCount()==0) {
                    int propID = this.properties[p].getSpaceID();
                    String name = this.properties[p].getName();
                    Color color = this.properties[p].getColor();
                    double price = this.properties[p].getPrice();
                    boolean monopolized = this.properties[p].isMonopolized();
                    double mortgageVal = this.properties[p].getMortgageValue();
                    System.out.println("[" + propID + "] " + name +
                            (color != null ? " ("+color+ (monopolized ? " - MONOPOLIZED" : "")+  ")" : "")+
                            " -- price $" + price + ", mortgage value $" + mortgageVal);
                }
            }
        }
    }
    public void listMortgagedPropertiesOfPlayer(Player player) {
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getProprietor() != null) {
                if (this.properties[p].getProprietor().getToken() == player.getToken() && this.properties[p].isMortgaged()) {
                    int propID = this.properties[p].getSpaceID();
                    String name = this.properties[p].getName();
                    Color color = this.properties[p].getColor();
                    double price = this.properties[p].getPrice();
                    boolean monopolized = this.properties[p].isMonopolized();
                    double mortgageVal = this.properties[p].getMortgageValue();
                    System.out.println("[" + propID + "] " + name +
                            (color != null ? " ("+color+ (monopolized ? " - MONOPOLIZED" : "")+  ")" : "")+
                            " -- price $" + price + ", mortgage value $" + mortgageVal);
                }
            }
        }
    }


    /** HOUSES AND HOTELS FOR MONOPOLIES
     * **/
    public int[] getMonopolyHouseDistribution(Color propColor){
        int[] propHousesCounts = new int[]{0,0,0};
        int nextIndex = 0;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getColor() == propColor) {
                propHousesCounts[nextIndex++] = this.properties[p].getHouseCount();
            }
        }
        if (nextIndex == 2){
            return new int[]{propHousesCounts[0],propHousesCounts[1]};
        }
        return propHousesCounts;
    }
    public boolean[] getPropIndexesThatCanReceiveHouses(int[] houseCounts) {
        boolean unanimous = true;
        int count = houseCounts[0];
        for (int c = 1; c < houseCounts.length; c++) {
            if (houseCounts[c] != count) {
                unanimous = false;
                break;
            }
        }
        if (unanimous){
            return (houseCounts.length == 2 ? new boolean[]{true,true} : new boolean[]{true,true,true} );
        }
        else {
            if (houseCounts.length == 2) {
                return ( houseCounts[0] < houseCounts[1] ? new boolean[]{true,false} : new boolean[]{false,true} ) ;
            }
            else {
                boolean[] allowances = new boolean[]{true,true,true};
                for (int c = 0; c < 3; c++) {
                    boolean okay = true;
                    switch (c) {
                        case 0:
                            okay = ( Math.abs(houseCounts[c]+1 - houseCounts[1])<2 && Math.abs(houseCounts[c]+1 - houseCounts[2])<2 );
                            break;
                        case 1:
                            okay = ( Math.abs(houseCounts[c]+1 - houseCounts[0])<2 && Math.abs(houseCounts[c]+1 - houseCounts[2])<2 );
                            break;
                        case 2:
                            okay = ( Math.abs(houseCounts[c]+1 - houseCounts[0])<2 && Math.abs(houseCounts[c]+1 - houseCounts[1])<2 );
                            break;
                    }
                    allowances[c] = okay;
                }
                return allowances;
            }
        }
    }

    public boolean[] getPropIndexesThatCanSellHouses(int[] houseCounts) {
        boolean unanimous = true;
        int count = houseCounts[0];
        for (int c = 1; c < houseCounts.length; c++) {
            if (houseCounts[c] != count) {
                unanimous = false;
                break;
            }
        }
        if (unanimous){
            if (count != 0) {
                return (houseCounts.length == 2 ? new boolean[]{true,true} : new boolean[]{true,true,true} );
            }
            else {
                return (houseCounts.length == 2 ? new boolean[]{false,false} : new boolean[]{false,false,false} );
            }
        }
        else {
            if (houseCounts.length == 2) {
                return ( houseCounts[0] > houseCounts[1] ? new boolean[]{true,false} : new boolean[]{false,true} ) ;
            }
            else {
                boolean[] allowances = new boolean[]{true,true,true};
                for (int c = 0; c < 3; c++) {
                    boolean okay = true;
                    switch (c) {
                        case 0:
                            okay = ( Math.abs(houseCounts[c]-1 - houseCounts[1])<2 && Math.abs(houseCounts[c]-1 - houseCounts[2])<2 );
                            break;
                        case 1:
                            okay = ( Math.abs(houseCounts[c]-1 - houseCounts[0])<2 && Math.abs(houseCounts[c]-1 - houseCounts[2])<2 );
                            break;
                        case 2:
                            okay = ( Math.abs(houseCounts[c]-1 - houseCounts[0])<2 && Math.abs(houseCounts[c]-1 - houseCounts[1])<2 );
                            break;
                    }
                    allowances[c] = okay;
                }
                return allowances;
            }
        }
    }
    public Property[] getPropertiesThatCanReceiveHouses(Color propColor) {
        Property[] propertiesInMonopoly = new Property[3];
        int nextPropIndex = 0;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getColor() == propColor) {
                propertiesInMonopoly[nextPropIndex++] = this.properties[p];
            }
        }
        propertiesInMonopoly = (nextPropIndex == 2 ? new Property[]{propertiesInMonopoly[0],propertiesInMonopoly[1]} : propertiesInMonopoly);
        int[] houseDistribution = this.getMonopolyHouseDistribution(propColor);
        boolean[] allowances = this.getPropIndexesThatCanReceiveHouses(houseDistribution);

        Property[] allowedProps = new Property[nextPropIndex];

        for (int a = 0; a < allowances.length; a++) {
            if (allowances[a] && !propertiesInMonopoly[a].isMortgaged()) {
                allowedProps[a] = propertiesInMonopoly[a];
            }
            else {
                allowedProps[a]=null;
            }
        }
        return allowedProps;
    }

    public Property[] getPropertiesThatCanSellHouses(Color propColor) {
        Property[] propertiesInMonopoly = new Property[3];
        int nextPropIndex = 0;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getColor() == propColor) {
                propertiesInMonopoly[nextPropIndex++] = this.properties[p];
            }
        }
        propertiesInMonopoly = (nextPropIndex == 2 ? new Property[]{propertiesInMonopoly[0],propertiesInMonopoly[1]} : propertiesInMonopoly);
        int[] houseDistribution = this.getMonopolyHouseDistribution(propColor);
        boolean[] allowances = this.getPropIndexesThatCanSellHouses(houseDistribution);

        Property[] allowedProps = new Property[nextPropIndex];

        for (int a = 0; a < allowances.length; a++) {
            if (allowances[a] && !propertiesInMonopoly[a].isMortgaged()) {
                allowedProps[a] = propertiesInMonopoly[a];
            }
            else {
                allowedProps[a] = null;
            }
        }
        return allowedProps;
    }
    public int getPropertyCountForBuildingHouses(Player player){

        Color[] colors = new Color[]{Color.BROWN, Color.TURQUOISE, Color.PINK, Color.ORANGE,
                Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

        int count = 0;
        for (int col = 0; col < colors.length; col++) {
            if (this.isColorGroupMonopolized(colors[col])) {
                boolean playerIsOwner = false;
                for (int p = 0; p < this.properties.length; p++){
                    if (this.properties[p].getColor()==colors[col]) {
                        playerIsOwner = (this.properties[p].getProprietor().getToken()==player.getToken());
                        break;
                    }
                }
                if (playerIsOwner) {
                    Property[] allowedProps = getPropertiesThatCanReceiveHouses(colors[col]);
                    for (int ap = 0; ap < allowedProps.length; ap++) {
                        if (allowedProps[ap] != null) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    public int getPropertyCountForSellingHouses(Player player){

        Color[] colors = new Color[]{Color.BROWN, Color.TURQUOISE, Color.PINK, Color.ORANGE,
                Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

        int count = 0;
        for (int col = 0; col < colors.length; col++) {
            if (this.isColorGroupMonopolized(colors[col])) {
                boolean playerIsOwner = false;
                for (int p = 0; p < this.properties.length; p++){
                    if (this.properties[p].getColor()==colors[col]) {
                        playerIsOwner = (this.properties[p].getProprietor().getToken()==player.getToken());
                        break;
                    }
                }
                if (playerIsOwner) {
                    Property[] allowedProps = getPropertiesThatCanSellHouses(colors[col]);
                    for (int ap = 0; ap < allowedProps.length; ap++) {
                        if (allowedProps[ap] != null) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    public int[] listPropertiesThatAllowLessHouses(Player player, boolean print) {
        String propIDs = "";

        Color[] colors = new Color[]{Color.BROWN, Color.TURQUOISE, Color.PINK, Color.ORANGE,
                Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

        for (int col = 0; col < colors.length; col++) {
            if (this.isColorGroupMonopolized(colors[col])) {
                boolean playerIsOwner = false;
                for (int p = 0; p < this.properties.length; p++){
                    if (this.properties[p].getColor()==colors[col]) {
                        playerIsOwner = (this.properties[p].getProprietor().getToken()==player.getToken());
                        break;
                    }
                }
                if (playerIsOwner) {
                    Property[] allowedProps = getPropertiesThatCanSellHouses(colors[col]);
                    for (int ap = 0; ap < allowedProps.length; ap++) {
                        if (allowedProps[ap] != null) {
                            if (allowedProps[ap].getHouseCount() > 0 && !allowedProps[ap].isMortgaged()) {
                                int propID = allowedProps[ap].getSpaceID();
                                String name = allowedProps[ap].getName();
                                Color color = allowedProps[ap].getColor();
                                double price = allowedProps[ap].getPrice();
                                boolean monopolized = allowedProps[ap].isMonopolized();
                                double mortgageVal = allowedProps[ap].getMortgageValue();
                                double housePrice = allowedProps[ap].getHousePrice();
                                int houseCount = allowedProps[ap].getHouseCount();

                                if (print) {
                                    System.out.println("[" + propID + "] " + name +
                                            (color != null ? " (" + color +
                                                    (monopolized ? " - MONOPOLIZED with "+houseCount+" house(s)" : "")
                                                    + ")" : "") +
                                            " -- price $" + price + ", mortgage value $" + mortgageVal + ", houses cost $"+housePrice+"/house (max 5 == hotel)");
                                }

                                propIDs += propID + " ";
                            }
                        }
                    }
                }
            }
        }
        if (propIDs.equals("")) {
            return null;
        }

        String[] propIDsSplit = propIDs.split("\\s+");
        int[] prop_ids = new int[propIDsSplit.length];
        for (int p = 0; p < prop_ids.length; p++) {
            prop_ids[p] = Integer.parseInt(propIDsSplit[p]);
        }

        return prop_ids;
    }

    public int[] listPropertiesThatAllowMoreHouses(Player player, boolean print) {
        String propIDs = "";

        Color[] colors = new Color[]{Color.BROWN, Color.TURQUOISE, Color.PINK, Color.ORANGE,
                Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

        for (int col = 0; col < colors.length; col++) {
            if (this.isColorGroupMonopolized(colors[col])) {
                boolean playerIsOwner = false;
                for (int p = 0; p < this.properties.length; p++){
                    if (this.properties[p].getColor()==colors[col]) {
                        playerIsOwner = (this.properties[p].getProprietor().getToken()==player.getToken());
                        break;
                    }
                }
                if (playerIsOwner) {
                    Property[] allowedProps = getPropertiesThatCanReceiveHouses(colors[col]);
                    for (int ap = 0; ap < allowedProps.length; ap++) {
                        if (allowedProps[ap] != null) {
                            if (allowedProps[ap].getHouseCount() < 5 && !allowedProps[ap].isMortgaged()) {
                                int propID = allowedProps[ap].getSpaceID();
                                String name = allowedProps[ap].getName();
                                Color color = allowedProps[ap].getColor();
                                double price = allowedProps[ap].getPrice();
                                boolean monopolized = allowedProps[ap].isMonopolized();
                                double mortgageVal = allowedProps[ap].getMortgageValue();
                                double housePrice = allowedProps[ap].getHousePrice();
                                int houseCount = allowedProps[ap].getHouseCount();

                                if (print) {
                                    System.out.println("[" + propID + "] " + name +
                                            (color != null ? " (" + color +
                                                    (monopolized ? " - MONOPOLIZED with "+houseCount+" house(s)" : "")
                                                    + ")" : "") +
                                            " -- price $" + price + ", mortgage value $" + mortgageVal + ", houses cost $"+housePrice+"/house (max 5 == hotel)");
                                }


                                propIDs += propID + " ";
                            }
                        }
                    }
                }
            }
        }
        if (propIDs.equals("")) {
            return null;
        }

        String[] propIDsSplit = propIDs.split("\\s+");
        int[] prop_ids = new int[propIDsSplit.length];
        for (int p = 0; p < prop_ids.length; p++) {
            prop_ids[p] = Integer.parseInt(propIDsSplit[p]);
        }

        return prop_ids;
    }
    public boolean buildHouseOnProperty(int propID) {
        boolean maxHousesAlreadyReached = false;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID() == propID){
                maxHousesAlreadyReached = this.properties[p].incrementHouseCount();
                break;
            }
        }
        return maxHousesAlreadyReached;
    }
    public boolean sellHouseOnProperty(int propID) {
        boolean noBuildings = false;
        for (int p = 0; p < this.properties.length; p++){
            if (this.properties[p].getSpaceID() == propID){
                noBuildings = this.properties[p].decrementHouseCount();
                break;
            }
        }
        return noBuildings;
    }
}
