import java.util.Random;

public class Dice {

    private static Random roller = new Random();

    private int die1;
    private int die2;

    public Dice(){
        this.die1 = 0;
        this.die2 = 0;
    }

    public void roll(){
        this.die1 = roller.nextInt(6) + 1;
        this.die2 = roller.nextInt(6) + 1;
    }

    public int getTotal() {
        return this.die1 + this.die2;
    }

    public boolean isDoubles() {
        return this.die1 == this.die2;
    }

    public void report(){
        if (this.isDoubles()) {
            System.out.println("### Double "+this.die1+"'s ("+this.getTotal()+") were rolled! ###");
        }
        else {
            System.out.println("A "+this.getTotal()+" ("+this.die1+" + "+this.die2+") was rolled!");
        }
    }
}
