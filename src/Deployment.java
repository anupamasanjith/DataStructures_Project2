public abstract class Deployment implements Comparable<Deployment> {
    // base abstract class for Deployments
    int id;

    long timestamp;
    int general;
    int planet;

    int force;
    int quantity;

    public Deployment(int id, long ts, int g, int p, int f, int q) {
        timestamp = ts;
        general = g;
        planet = p;
        this.id = id;

        force = f;
        quantity = q;
    }

    public abstract boolean isSith();


    public abstract boolean isJedi();

    public abstract boolean checkForce();

    public abstract boolean checkNumTroops();

    @Override
    public abstract int compareTo(Deployment d);

}
