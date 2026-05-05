package demo.watch;

import core.Configuration;
import core.Solver;

import java.util.Arrays;
import java.util.Collection;

/**
 * Main class for the watch puzzle.
 *
 * @author Tereza Lang (@TORITZA)
 */
public class Watch {
    /**
     * Run an instance of the watch puzzle.
     *
     * @param args [0]: the number of hours in the watch;
     *             [1]: the starting hour;
     *             [2]: the starting minute;
     *             [3]: the ending hour;
     *             [4]: the ending minute;
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java Watch hours start-hour start-minute end-hour end-minute");
        } else {
            // Java stream to convert all String args into ints
            int[] intArgs = Arrays.stream(args)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            WatchConfig initial = new WatchConfig(intArgs[0], intArgs[1], intArgs[2], intArgs[3], intArgs[4]);
            Solver sol = new Solver();
            Collection<Configuration> steps = sol.solve(initial);

            // format clock times correctly
            String startHour = String.valueOf(intArgs[1]);
            String startMin = String.valueOf(intArgs[2]);
            if (Integer.parseInt(startMin) < 10) {
                startMin = "0" + startMin;
            }

            String endHour = String.valueOf(intArgs[3]);
            String endMin = String.valueOf(intArgs[4]);
            if (Integer.parseInt(endMin) < 10) {
                endMin = "0" + endMin;
            }

            // beginning of main output display:
            System.out.println("Hours: " + intArgs[0] + ", Start: " + startHour + ":" + startMin +
                    ", End: " + endHour + ":" + endMin);
            System.out.println("Total configs: " + sol.getTotalConfigs());
            System.out.println("Unique configs: " + sol.getUniqueConfigs());

            if (steps == null) {
                System.out.println("No solution found");
            } else {
                int i = 0;
                for (Configuration config : steps) {
                    System.out.println("Step " + i + ": " + config);
                    i++;
                }
            }
        }
    }
}

