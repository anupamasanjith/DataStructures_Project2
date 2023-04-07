
public class General {
    int id;

    int Totalcountjedi;
    int Totalcountsith;

    int remainingjedi;
    int remainingsith;


    public General(int id) {
        this.id = id;


    }


    // counts the total troops deployed on each side
    public void CountTroops(Deployment d) {
        if (d.isSith()) {
            Totalcountsith += d.quantity;
        } else {

            Totalcountjedi += d.quantity;
        }


    }

    // counts the number of remaining deployments
    public void Count(Deployment d) {
        if (d.isSith()) {
            remainingsith += d.quantity;

        } else {
            remainingjedi += d.quantity;
        }

    }

    // performs and the prints out calculations from the gen evaluation
    public void geneval(Config c) {
        int num_survivors = remainingjedi + remainingsith;
        int num_deployed = Totalcountjedi + Totalcountsith;
        if (c.isGeneral()) {

            System.out.print("General " + id + " deployed " + Totalcountjedi + " Jedi troops and "
                    + Totalcountsith + " Sith troops, and " + num_survivors + "/" + num_deployed +
                    " troops survived.\n");
        }

    }


}
