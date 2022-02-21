
public class CommandLine {

    private int numThreads;
    private int numSkiers;
    private int numLifts = 40;
    private int numRuns = 10;
    private int port = 8080;
    private final int MAX_NUM_THREADS = 1024;
    private final int MAX_NUM_SKIERS = 100000;
    private final int MAX_NUM_LIFTS = 60;
    private final int MIN_NUM_LIFTS = 5;
    private final int MAX_NUM_RUNS = 20 * MAX_NUM_SKIERS;

    public CommandLine(String[] args) {
        this.numThreads = Integer.parseInt(args[0]);
        this.numSkiers = Integer.parseInt(args[1]);
        this.numLifts = Integer.parseInt(args[2]);
        this.numRuns = Integer.parseInt(args[3]);
        this.port = Integer.parseInt(args[4]);
    }

    public boolean validCommand(String[] args) {
        if (args.length != 5) {
            System.out.println("Missing command parameter.");
            return false;
        } else if (Integer.parseInt(args[0]) > MAX_NUM_THREADS) {
            System.out.println("Number of threads should be smaller than 1024.");
            return false;
        } else if (Integer.parseInt(args[1]) > MAX_NUM_SKIERS) {
            System.out.println("Number of skiers should be smaller than 100000.");
            return false;
        } else if (Integer.parseInt(args[2]) > MAX_NUM_LIFTS || Integer.parseInt(args[2]) < MIN_NUM_LIFTS) {
            System.out.println("Number of lifts should be between 5 to 60.");
            return false;
        } else if (Integer.parseInt(args[3]) > MAX_NUM_RUNS) {
            System.out.println("Number of run the resort capacity");
            return false;
        }
        return true;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    public int getNumSkiers() {
        return numSkiers;
    }

    public void setNumSkiers(int numSkiers) {
        this.numSkiers = numSkiers;
    }

    public int getNumLifts() {
        return numLifts;
    }

    public void setNumLifts(int numLifts) {
        this.numLifts = numLifts;
    }

    public int getNumRuns() {
        return numRuns;
    }

    public void setNumRuns(int numRuns) {
        this.numRuns = numRuns;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
