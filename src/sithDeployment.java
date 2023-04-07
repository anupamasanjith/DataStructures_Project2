public class sithDeployment extends Deployment{

    // keeps track of sith deployments
    int count;
    public sithDeployment(int id, long ts, int g, int p, int f, int q) {
        super(id, ts, g, p, f, q);
    }

    @Override
    public boolean isSith() {
        return true;
    }




    @Override
    public boolean isJedi() {
        return false;
    }

    @Override
    public boolean checkForce() {
        return this.force>0;
    }

    @Override
    public boolean checkNumTroops() {
        return this.quantity>0;
    }

    @Override
    public int compareTo(Deployment d) {
        if (this.force == d.force){
            return this.id - d.id;
        }

        return d.force-this.force;
    }
}
