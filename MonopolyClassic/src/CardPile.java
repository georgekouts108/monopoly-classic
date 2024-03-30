import java.util.Arrays;
import java.util.Random;

public class CardPile {
    private static Random random = new Random();

    private CardType cardType;
    private int[] cardIDs;

    public CardPile(CardType cardType) {
        this.cardType = cardType;
        this.cardIDs = new int[16];
        for (int c = 1; c <= 16; c++) {
            this.cardIDs[c - 1] = ( this.cardType==CardType.CHANCE ? c+100 : c+200 );
        }
    }
    public CardType getCardType() {
        return cardType;
    }

    public void shuffle() {
        int[] shuffled = new int[16];
        Arrays.fill(shuffled, -1);

        for (int c = 0; c < this.cardIDs.length; c++) {
            int nextCardID = this.cardIDs[c];
            while (true) {
                int randomIndex = random.nextInt(16);
                if (shuffled[randomIndex] == -1) {
                    shuffled[randomIndex] = nextCardID;
                    break;
                }
            }
        }

        this.cardIDs = shuffled;
    }

    public int drawCard() {
        int drawnCardID = this.cardIDs[0];

        int[] updatedPile = new int[16];
        for (int u = 0; u < 15; u++) {
            updatedPile[u] = this.cardIDs[u+1];
        }

        updatedPile[15] = drawnCardID;
        this.cardIDs = updatedPile;

        return drawnCardID;
    }
}
