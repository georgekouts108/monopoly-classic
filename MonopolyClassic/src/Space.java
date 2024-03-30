public class Space {

    private SpaceType spaceType;
    private int spaceID;
    private String spaceName;

    public Space(int _id, SpaceType _type){
        this.spaceType=_type;
        this.spaceID = _id;

        switch (_id) {
            case 38:
                this.spaceName = "LUXURY TAX"; break;
            case 4:
                this.spaceName = "INCOME TAX"; break;
            case 2:
            case 17:
            case 33:
                this.spaceName = "COMMUNITY CHEST"; break;
            case 7:
            case 22:
            case 36:
                this.spaceName = "CHANCE"; break;
            case 20:
                this.spaceName = "FREE PARKING"; break;
            case 30:
                this.spaceName = "GO TO JAIL"; break;
            case 10:
                this.spaceName = "IN JAIL / JUST VISITING"; break;
        }

    }

    public SpaceType getSpaceType() {
        return spaceType;
    }

    public String getSpaceName() {
        return this.spaceName;
    }
    public int getSpaceID() {
        return spaceID;
    }
}
