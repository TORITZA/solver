package demo.crossing;

import core.Configuration;
import core.Solver;
import java.util.Arrays;
import java.util.Collection;

/**
 * Main class for the crossing puzzle.
 *
 * @author Tereza Lang (@TORITZA)
 */
public class Crossing {
    /**
     * Run an instance of the crossing puzzle.
     *
     * @param args [0]: the number of pups;
     *             [1]: the number of wolves;
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Crossing pups wolves");
        } else {
            // Java stream to convert all String args into ints
            int[] intArgs = Arrays.stream(args)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            CrossingConfig initial = new CrossingConfig(intArgs[0], intArgs[1]);
            Solver sol = new Solver();
            Collection<Configuration> steps = sol.solve(initial);

            // beginning of main output display:
            System.out.println("Pups: " + intArgs[0] + ", Wolves: " + intArgs[1]);
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

