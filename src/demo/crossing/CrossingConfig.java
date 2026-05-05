package demo.crossing;

import core.Configuration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A configuration representing a river crossing puzzle where a group of pups and wolves
 * are to cross the river in the least number of boat trips. Either one pup, two pups, or
 * one wolf can make it across, and the boat must have at least one passenger in order to
 * be able to make the trip from both sides of the river.
 *
 * @author Tereza Lang (@TORITZA)
 */
public class CrossingConfig implements Configuration {
    /** is the boat on the left side of the river? */
    private boolean boatLeft;
    /** the number of pups & wolves on the LEFT side of the river */
    private int pupsLeft;
    private int wolvesLeft;
    /** number of pups & wolves on the RIGHT side of the river */
    private int pupsRight;
    private int wolvesRight;


    /**
     * The constructor for an initial CrossingConfig. It requires the number of pups &
     * wolves initially on the left side of the river.
     *
     * @param pups number of pups that start on the left side
     * @param wolves number of wolves that start on the left side
     */
    public CrossingConfig(int pups, int wolves) {
        this.boatLeft = true;
        this.pupsLeft = pups;
        this.wolvesLeft = wolves;

        // at the puzzle's start, no pups & wolves will be on the right side
        // of the river
        this.pupsRight = 0;
        this.wolvesRight = 0;
    }

    /**
     * This constructor creates a deep copy of the current crossing configuration. The
     * boat is set to have crossed to the other side of the river.
     * Used in the generation of successors.
     *
     * @param orig the original CrossingConfig that's to be deep-copied
     */
    public CrossingConfig(CrossingConfig orig) {

        this.boatLeft = !orig.boatLeft;
        this.pupsLeft = orig.pupsLeft;
        this.wolvesLeft = orig.wolvesLeft;

        this.pupsRight = orig.pupsRight;
        this.wolvesRight = orig.wolvesRight;

    }

    /**
     * A setter used to move the corresponding amount of pups from the left
     * side to the right side of the river and vice versa.
     */
    public void movePup(int amt, boolean left) {
        if (left) {
            this.pupsLeft -= amt;
            this.pupsRight += amt;
        } else {
            this.pupsRight -= amt;
            this.pupsLeft += amt;
        }
    }

    /**
     * A setter used to move one wolf from the left
     * side to the right side of the river and vice versa.
     */
    public void moveWolf(boolean left) {
        if (left) {
            this.wolvesLeft -= 1;
            this.wolvesRight += 1;
        } else {
            this.wolvesRight -= 1;
            this.wolvesLeft += 1;
        }
    }

    /**
     * Is this CrossingConfig the solution we're looking for?
     * Returns true if the boat is on the right side of the river, and
     * there are no more pups or wolves on the left side.
     *
     * @return true or false depending on if this config is the
     * solution or not
     */
    @Override
    public boolean isSolution() {
        if (!boatLeft && pupsLeft == 0 && wolvesLeft == 0) {
            return true;
        }
        return false;
    }

    /**
     * Generates a list of successors based on all the possible ways both pups
     * and wolves can move across the river, taking into account the boat's current
     * position.
     *
     * @return a list of this config's successors/neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        List<Configuration> neighbors = new LinkedList<>();

        if (boatLeft) {
            // boat is currently on the left side of the river
            if (this.pupsLeft - 1 >= 0) { // first validate the following operation!
                CrossingConfig onePupLeft = new CrossingConfig(this);
                onePupLeft.movePup(1, true);
                neighbors.add(onePupLeft);
            }
            if (this.pupsLeft - 2 >= 0) {
                CrossingConfig twoPupLeft = new CrossingConfig(this);
                twoPupLeft.movePup(2, true);
                neighbors.add(twoPupLeft);
            }
            if (this.wolvesLeft - 1 >= 0) {
                CrossingConfig oneWolfLeft = new CrossingConfig(this);
                oneWolfLeft.moveWolf(true);
                neighbors.add(oneWolfLeft);
            }
        } else {
            // boat is currently on the other, RIGHT side of the river
            if (this.pupsRight - 1 >= 0) {
                CrossingConfig onePupRight = new CrossingConfig(this);
                onePupRight.movePup(1, false);
                neighbors.add(onePupRight);
            }
            if (this.pupsRight - 2 >= 0) {
                CrossingConfig twoPupRight = new CrossingConfig(this);
                twoPupRight.movePup(2, false);
                neighbors.add(twoPupRight);
            }
            if (this.wolvesRight - 1 >= 0) {
                CrossingConfig oneWolfRight = new CrossingConfig(this);
                oneWolfRight.moveWolf(false);
                neighbors.add(oneWolfRight);
            }
        }

        return neighbors;
    }

    /**
     * Two CrossingConfigs are considered equal to one another if they share same
     * number of pups & wolves on both sides of the river, and the boat is located
     * in the same position.
     *
     * @param other the reference object with which to compare
     * @return a boolean value indicating if these two objects are equal or not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof CrossingConfig otherConfig) {
            return this.boatLeft == otherConfig.boatLeft && this.pupsLeft == otherConfig.pupsLeft &&
                    this.wolvesLeft == otherConfig.wolvesLeft && this.pupsRight == otherConfig.pupsRight &&
                    this.wolvesRight == otherConfig.wolvesRight;
        }
        return false;
    }

    /**
     * The hash code of a crossing configuration is the sum of its total number of
     * wolves and pups plus its hashed boatLeft value.
     *
     * @return CrossingConfig hash code
     */
    @Override
    public int hashCode() {
        return Boolean.hashCode(boatLeft) + pupsLeft + wolvesLeft +
                pupsRight + wolvesRight;
    }

    /**
     * Converts the crossing's current values into their string representation:
     *      left=[pupsLeft, wolvesLeft], right=[pupsRight, wolvesRight]
     * (BOAT) is included either on the left or right side of the string above,
     * depending on its current position.
     *
     * @return a string in the format just displayed
     */
    @Override
    public String toString() {
        if (boatLeft) {
            return "(BOAT) left=[" + pupsLeft + ", " + wolvesLeft + "], right=[" +
                    pupsRight + ", " + wolvesRight + "]";
        }
        return "       left=[" + pupsLeft + ", " + wolvesLeft + "], right=[" + pupsRight +
                ", " + wolvesRight + "] (BOAT)";
    }
}

