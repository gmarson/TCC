import java.util.ArrayList;

/**
 * Created by gabrielm on 05/01/17.
 */

public class BinaryCrossover implements Crossover {

    @Override
    public Population crossoverAndMutation(Population selected){

        Population children = new Population();
        ArrayList<Integer> pairOfChildren;
        for (int i = 0, j =0; j < selected.population.size()/2; i+=2, j++) {
            Member m1 = selected.population.get(i);
            Member m2 = selected.population.get(i+1);

            ArrayList<Integer> m1BinaryValue = Utils.toBinary((int)m1.value);
            ArrayList<Integer> m2BinaryValue = Utils.toBinary((int)m2.value);

            increaseToMaxBinaryLenSize(m1BinaryValue);
            increaseToMaxBinaryLenSize(m2BinaryValue);

            pairOfChildren = makeChildren(m1BinaryValue,m2BinaryValue);

            children.addMember(new Member(pairOfChildren.get(0)));
            children.addMember(new Member(pairOfChildren.get(1)));
        }
        return children;
    }

    protected void binaryMutation(ArrayList<Integer> binaryNumber)
    {
        if(Utils.getRandom(1,100) <= Constants.MUTATION_RATE)
        {
            int sectionToBeMutated = Utils.getRandom(0,binaryNumber.size());
            if(binaryNumber.get(sectionToBeMutated) == 0)
                binaryNumber.set(sectionToBeMutated,1);
            else
                binaryNumber.set(sectionToBeMutated,0);
        }
    }

    protected void increaseToMaxBinaryLenSize(ArrayList<Integer> binary)
    {
        boolean haveToCorrectSignal = false;
        if(binary.get(0) == 1)
        {
            binary.set(0,0);
            haveToCorrectSignal = true;
        }

        for (int i = 0; i < Constants.MAX_BINARY_LEN; i++) {
            binary.add(0,0);
        }
        if (haveToCorrectSignal) binary.set(0,1);
    }

    protected ArrayList<Integer> makeChildren(ArrayList<Integer> binary1, ArrayList<Integer> binary2)
    {
        int cutoff =  Utils.getRandom(0, Constants.MAX_BINARY_LEN);

        ArrayList<Integer> pairOfChildren   = new ArrayList<>();
        ArrayList<Integer> child1First      = new ArrayList<>(binary1.subList(0, cutoff));
        ArrayList<Integer> child1Second     = new ArrayList<>(binary2.subList(cutoff, binary2.size() ) );
        ArrayList<Integer> child2First      = new ArrayList<>(binary2.subList(0, cutoff));
        ArrayList<Integer> child2Second     = new ArrayList<>(binary1.subList(cutoff, binary1.size() ) );

        child1First.addAll(child2Second);
        child2First.addAll(child1Second);

        binaryMutation(child1First);
        binaryMutation(child2First);

        pairOfChildren.add(Utils.binaryToInteger(child1First));
        pairOfChildren.add(Utils.binaryToInteger(child2First));

        return pairOfChildren;

    }
}
