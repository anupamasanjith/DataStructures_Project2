public class jediDeployment extends Deployment {
    // keeps track of sith deployments

    public jediDeployment(int id, long ts, int g, int p, int f, int q) {
        super(id, ts, g, p, f, q);
    }

    @Override
    public boolean isSith() {
        return false;
    }


    @Override
    public boolean isJedi() {
        return true;
    }

    @Override
    public boolean checkForce() {
        return this.force > 0;
    }

    @Override
    public boolean checkNumTroops() {
        return this.quantity > 0;
    }

    @Override
    public int compareTo(Deployment d) {
        // return a negative number if this is higher priority than d
        // return a positive number if this is lower priority than d
        if (this.force == d.force) {
            return this.id - d.id;
        }
        return this.force - d.force;
    }
}
