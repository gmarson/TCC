import java.util.ArrayList;

/**
 * Created by gabrielm on 18/02/17.
 */
public class CrossoverBinaryKnapsack implements Crossover{

    @Override
    public Population crossoverAndMutation(Population selected) {
        Population children = new Population();

        for (int i = 0, j =0; j < selected.population.size()/2; i+=2, j++)
        {
            Member m1 = selected.population.get(i);
            Member m2 = selected.population.get(i+1);

            ArrayList<Member> pairOfChildren = makeChildren(m1,m2);

            children.addMember(pairOfChildren.get(0));
            children.addMember(pairOfChildren.get(1));
        }

        return children;
    }


    public ArrayList<Member> makeChildren(Member m1, Member m2)
    {
        int cutoff =  Utils.getRandom(0, Constants.QTD_ITEMS);

        ArrayList<Member>  pairOfChildren       = new ArrayList<>();
        ArrayList<Integer> parent1FirstPart     = new ArrayList<>(m1.binaryValue.subList(0,cutoff));
        ArrayList<Integer> parent1SecondPart    = new ArrayList<>(m1.binaryValue.subList(cutoff,Constants.QTD_ITEMS));
        ArrayList<Integer> parent2FirstPart     = new ArrayList<>(m2.binaryValue.subList(0,cutoff));
        ArrayList<Integer> parent2SecondPart    = new ArrayList<>(m2.binaryValue.subList(cutoff,Constants.QTD_ITEMS));

        Member child1 = new Member(addAllElements(parent1FirstPart,parent2SecondPart));
        Member child2 = new Member(addAllElements(parent2FirstPart,parent1SecondPart));

        pairOfChildren.add(child1);
        pairOfChildren.add(child2);

        return pairOfChildren;
    }



    public ArrayList<Integer> addAllElements(ArrayList<Integer> a1, ArrayList<Integer> a2)
    {
        ArrayList<Integer> child = a1;

        for (Integer item: a2)
        {
            child.add(item);
        }

        return child;
    }

}
