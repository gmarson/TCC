import javax.rmi.CORBA.Util;
import java.util.ArrayList;

/**
 * Created by gabrielm on 02/02/17.
 */
public class CrossoverBlend implements Crossover {

    double alpha = 0.5; // ou 0.25
    double beta = Utils.getRandomDouble(-alpha,alpha+1);


    @Override
    public Population crossoverAndMutation(Population selected) {
        Population children = new Population();
        ArrayList<Double> pairOfChildren;


        for (int i = 0, j =0; j < selected.population.size()/2; i+=2, j++) {
            Member m1 = selected.population.get(i);
            Member m2 = selected.population.get(i+1);

            pairOfChildren = makeChildren(m1,m2);

            children.addMember(new Member(pairOfChildren.get(0)));
            children.addMember(new Member(pairOfChildren.get(1)));
        }

        return children;
    }


    public ArrayList<Double> makeChildren(Member m1, Member m2)
    {
        ArrayList<Double> pairOfChildren = new ArrayList<>();

        return null;
    }


    public void mutation()
    {

    }
}
