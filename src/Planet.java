import java.util.Collections;
import java.util.PriorityQueue;

public class Planet {
    PriorityQueue<Deployment> jediDeployments;
    PriorityQueue<Deployment> sithDeployments;
    PriorityQueue<Integer> topHalf;
    PriorityQueue<Integer> bottomHalf;
    int median;
    int TroopsLost;


    public int id;
    private int numBattles;

    public Planet(int id) {
        this.id = id;
        numBattles = 0;
        jediDeployments = new PriorityQueue<>();
        sithDeployments = new PriorityQueue<>();
        topHalf = new PriorityQueue<>();
        bottomHalf = new PriorityQueue<>(Collections.reverseOrder());

    }

    // adds the deployment to the appropriate PQ's.
    public void addDeployment(Deployment d) {
        if (d.checkForce() && d.checkNumTroops()) {
            if (d.isSith()) {
                sithDeployments.add(d);
            } else {
                jediDeployments.add(d);
            }
        } else {
            System.err.print("Invalid Force/Quantity");
            System.exit(1);
        }
    }
    // error checking for battle.

    public boolean canBattle() {
        if (jediDeployments.isEmpty() | sithDeployments.isEmpty()) {
            return false;
        } else if (jediDeployments.peek().planet != sithDeployments.peek().planet)
            return false;
        else if (jediDeployments.peek().force > sithDeployments.peek().force) {
            return false;
        }
        return true;
    }

    // performs individual battles
    public void performBattle(Config c) {
        while (canBattle()) {
            TroopsLost = Math.min(jediDeployments.peek().quantity,
                    sithDeployments.peek().quantity);

            jediDeployments.peek().quantity -= TroopsLost;
            sithDeployments.peek().quantity -= TroopsLost;


            if (c.verbose) {
                System.out.print("General " + sithDeployments.peek().general +
                        "'s battalion attacked General " + jediDeployments.peek().general +
                        "'s battalion on planet " + id + ". " + (TroopsLost * 2) +
                        " troops were lost.\n");


            }

            if (jediDeployments.peek().quantity == 0) {
                jediDeployments.remove();

            }
            if (sithDeployments.peek().quantity == 0) {
                sithDeployments.remove();
            }

            numBattles++;
            if (c.median) {
                median(TroopsLost * 2);
            }
        }


    }

    // returns number of battles performed
    public int getNumBattles() {
        return numBattles;
    }

    // computes the median troops remaining
    // written with code from in class
    public int median(int i) {

        if (topHalf.isEmpty() && bottomHalf.isEmpty()) {
            // this is the first piece of data
            topHalf.add(i);
            median = i;
        } else {

            if (i < median) {
                bottomHalf.add(i);
            } else {
                topHalf.add(i);
            }
            // rebalance sizes of PQ
            if (bottomHalf.size() - topHalf.size() == 2) {
                // shift one element from bottom to top
                topHalf.add(bottomHalf.remove());
            } else if (topHalf.size() - bottomHalf.size() == 2) {
                bottomHalf.add(topHalf.remove());
            }

            // update median
            if (bottomHalf.size() > topHalf.size()) {
                median = bottomHalf.peek();
            } else if (bottomHalf.size() < topHalf.size()) {
                median = topHalf.peek();
            } else {
                median = (topHalf.peek() + bottomHalf.peek()) / 2;
            }

        }
        return median;

    }

    public void printMedian(long t) {
        if (numBattles != 0) {
            System.out.println("Median troops lost on planet " + id + " at time " +
                    t + " is " + median + ".");
        }
    }

}


