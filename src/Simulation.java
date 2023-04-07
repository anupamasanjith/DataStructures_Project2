import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {
    private ArrayList<General> generals;
    private ArrayList<Planet> planets;
    private Config config;
    private Scanner in;
    private int numGenerals;

    private int numPlanets;

    private int currId = 0;

    // reads in the input and stores it in appropriate variables
    public Simulation(Config c) {
        config = c;
        in = new Scanner(System.in);
        // skip the comment
        in.nextLine();

        in.next();
        String mode = in.next();

        in.next();
        numGenerals = in.nextInt();
        in.next();
        numPlanets = in.nextInt();


        generals = new ArrayList<>(numGenerals);
        for (int i = 0; i < numGenerals; i++) {
            generals.add(new General(i));

        }

        planets = new ArrayList<>(numPlanets);
        for (int i = 0; i < numPlanets; i++) {
            planets.add(new Planet(i));
        }
        if (mode.equals("PR")) {
            in.next();
            int seed = in.nextInt();
            in.next();
            int numDeployments = in.nextInt();

            in.next();
            int arrivalRate = in.nextInt();

            in = P2Random.PRInit(seed, numGenerals, numPlanets, numDeployments, arrivalRate);
        }


    }

    // perform the war based on config specification
    public void performWarfare() {

        long currentTime = 0;

        System.out.println("Deploying troops...");


        Deployment d = getnextDeployment();
        while (d != null) {
            if (d.timestamp < 0) {
                System.err.print("Invalid Timestamp");
                System.exit(1);
            }
            if (currentTime > d.timestamp) {
                System.err.print("Invalid Timestamp");
                System.exit(1);
            }
            if (d.general > numGenerals - 1 | d.planet > numPlanets - 1) {
                System.err.print("Invalid General|Planet");
                System.exit(1);
            }

            if (currentTime < d.timestamp) {
                if (config.median) {
                    for (Planet p : planets) {
                        p.printMedian(currentTime);

                    }
                }
                currentTime = d.timestamp;

            }
            Planet p = planets.get(d.planet);
            General g = generals.get(d.general);

            g.CountTroops(d);
            p.addDeployment(d);
            p.performBattle(config);


            d = getnextDeployment();

        }

        if (config.median) {
            for (Planet g : planets) {
                g.printMedian(currentTime);

            }
        }

        System.out.print("---End of Day---\n");

        int totalbattles = 0;
        for (Planet p : planets) {
            totalbattles += p.getNumBattles();

        }
        System.out.print("Battles: " + totalbattles + "\n");
        if (config.general) {
            System.out.print("---General Evaluation---\n");
            getEvaluation(config);
        }


    }

    // get the next deployment
    private Deployment getnextDeployment() {


        if (in.hasNextLong()) {
            long timestamp = in.nextLong();
            String type = in.next();

            int generalId = Integer.parseInt(in.next().substring(1));
            int planetId = Integer.parseInt(in.next().substring(1));
            int force = Integer.parseInt(in.next().substring(1));
            int numTroops = Integer.parseInt(in.next().substring(1));


            if (type.equals("SITH")) {
                return new sithDeployment(currId++, timestamp, generalId, planetId, force, numTroops);
            } else {
                return new jediDeployment(currId++, timestamp, generalId, planetId, force, numTroops);
            }
        }
        return null;


    }

    // get the general evaluation at the end of the day.
    private void getEvaluation(Config c) {
        ArrayList<Deployment> jedi = new ArrayList<>();
        ArrayList<Deployment> sith = new ArrayList<>();

        for (Planet p : planets) {
            jedi.addAll(p.jediDeployments);
            sith.addAll(p.sithDeployments);
        }
        for (Deployment de : jedi) {
            General g = generals.get(de.general);
            g.Count(de);
        }
        for (Deployment de : sith) {
            General g = generals.get(de.general);
            g.Count(de);
        }

        for (General g : generals) {
            g.geneval(c);
        }

    }

}
