import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

public class Config {
    public static LongOpt[] longOptions = {
            new LongOpt("verbose", LongOpt.NO_ARGUMENT, null, 'v'),
            new LongOpt("median", LongOpt.NO_ARGUMENT, null, 'm'),
            new LongOpt("general-eval", LongOpt.NO_ARGUMENT, null, 'g'),
    };
    // Configuration settings for the program
    boolean verbose;
    boolean median;
    boolean general;


    public Config(String[] args) {
        Getopt g = new Getopt("Project2", args, "vmg", longOptions);
        g.setOpterr(true);
        int choice;
        while ((choice = g.getopt()) != -1) {
            switch (choice) {
                case 'v':
                    verbose = true;
                    break;
                case 'm':
                    median = true;
                    break;
                case 'g':
                    general = true;
                    break;
                default:
                    System.err.println("Unknown command line options" + (char) choice);
                    System.exit(1);

            }
        }

    }

    public boolean isMedian() {
        return median;
    }

    public boolean isGeneral() {
        return general;
    }

    public boolean isVerbose() {
        return verbose;
    }

}
