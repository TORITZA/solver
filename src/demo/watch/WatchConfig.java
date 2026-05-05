package demo.watch;

import core.Configuration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A configuration of a watch containing a total number of hours with a defined start and finish
 * hour. Internally, it splits the initial time between an hour & minute hand and uses those
 * values in its calculations.
 *
 * @author Tereza Lang (@TORITZA)
 */
public class WatchConfig implements Configuration {
    /** scope of the clock, in hours */
    private final int clockSpan;
    /** the current time */
    private int currHour;
    private int currMin;
    /** the solution */
    private final int endHour;
    private final int endMin;

    /**
     * The constructor for an initial WatchConfig. It requires the total number of hours on the
     * clock, as well as a defined start & finish time.
     * Stores the provided to use in its calculations.
     *
     * @param clockSpan how many hours the clock contains
     * @param startHour the starting hour
     * @param startMin the starting minute
     * @param endHour the ending hour
     * @param endMin the ending minute
     */
    public WatchConfig(int clockSpan, int startHour, int startMin, int endHour,  int endMin) {
        this.clockSpan = clockSpan;
        this.currHour = startHour;
        this.currMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    /**
     * This constructor creates a deep copy of the current watch configuration. Used in the
     * generation of successors.
     *
     * @param orig the original WatchConfig that's to be deep-copied
     */
    public WatchConfig(WatchConfig orig) {
        this.clockSpan = orig.clockSpan;
        this.currHour = orig.currHour;
        this.currMin = orig.currMin;
        this.endHour = orig.endHour;
        this.endMin = orig.endMin;
    }

    /**
     * A setter that moves the hour hand of the clock, adjusting for wrap-around and negative
     * values if needed.
     *
     * @param time the amount of time to move the hour hand forwards/backwards by
     */
    public void moveHour(int time) {
        // wrap around if it exceeds the hours on the clock
        currHour = (currHour + time) % clockSpan;
        // if the clock hand moves backward, ensure the time doesn't dip
        // into the negatives
        if (currHour < 0) {
            currHour += clockSpan;
        }
    }

    /**
     * A setter that moves the minute hand of the clock, adjusting for wrap-around and
     * negative values if needed.
     *
     * @param time the amount of time to move the minute hand forwards/backwards by
     */
    public void moveMinute(int time) {
        // handle watch logic, control wrap for minute component
        currMin = (currMin + time) % 60;

        // account for minutes adjustment & potential neg. values
        if (currMin < 0) {
            currMin += 60;
        }
    }

    /**
     * Is this WatchConfig the solution we're looking for?
     * Returns true if the current time is the same as the clock's
     * end time.
     *
     * @return true or false depending on if this config is the
     * solution or not
     */
    @Override
    public boolean isSolution() {
        if (currHour == endHour && currMin == endMin) {
            return true;
        }
        return false;
    }

    /**
     * Generates a list of successors based on all the possible directions the
     * clock can move.
     *
     * @return a list of this config's successors/neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        // list of empty successors
        List<Configuration> neighbors = new LinkedList<>();

        if (currMin == endMin) {
            // hours backward/forward
            WatchConfig hrBackward = new WatchConfig(this);
            hrBackward.moveHour(-1);
            neighbors.add(hrBackward);

            WatchConfig hrForward = new WatchConfig(this);
            hrForward.moveHour(1);
            neighbors.add(hrForward);
        } else if (currHour == endHour) {
            // min backward/forward
            WatchConfig minBackward = new WatchConfig(this);
            minBackward.moveMinute(-1);
            neighbors.add(minBackward);

            WatchConfig minForward = new WatchConfig(this);
            minForward.moveMinute(1);
            neighbors.add(minForward);
        } else {
            // do BOTH
            WatchConfig hrBackward = new WatchConfig(this);
            hrBackward.moveHour(-1);
            neighbors.add(hrBackward);

            WatchConfig hrForward = new WatchConfig(this);
            hrForward.moveHour(1);
            neighbors.add(hrForward);

            WatchConfig minBackward = new WatchConfig(this);
            minBackward.moveMinute(-1);
            neighbors.add(minBackward);

            WatchConfig minForward = new WatchConfig(this);
            minForward.moveMinute(1);
            neighbors.add(minForward);
        }

        return neighbors;
    }

    /**
     * Two WatchConfigs are equal to one another if they share the same current time.
     *
     * @param other the reference object with which to compare
     * @return a boolean value indicating if these two objects are equal or not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof WatchConfig o) {
            return this.currHour == o.currHour && this.currMin == o.currMin;
        }
        return false;
    }

    /**
     * The hash code of a WatchConfig is simply its current time.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return this.currHour + this.currMin;
    }

    /**
     * Converts the watch's hour and minute hands into a string representation
     * of themselves, displayed in standard clock time:
     *             [hour]:[minute]
     *
     * @return a string in the format shown above
     */
    @Override
    public String toString() {
        String currHours = String.valueOf(currHour);
        String currMins = String.valueOf(currMin);

        if (Integer.parseInt(currHours) == 0) {
            currHours = String.valueOf(clockSpan);
        }

        if (Integer.parseInt(currMins) < 10) {
            currMins = "0" + currMins;
        }

        return currHours + ":" + currMins;
    }
}

