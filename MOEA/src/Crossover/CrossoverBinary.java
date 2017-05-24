package Crossover;

import Constants.Constants;
import Utilities.*;
import Population.*;

import java.util.ArrayList;

/**
 * Created by gabrielm on 05/01/17.
 * MADE ONLY FOR INTEGER FUNCTIONS
 */


public class CrossoverBinary implements Crossover {

    @Override
    public Population crossoverAndMutation(Population selected){

        Population children = new Population();
        ArrayList<Integer> pairOfChildren;

    
        for (int i = 0, j =0; j < selected.population.size()/2; i+=2, j++) {
            Member m1 = selected.population.get(i);
            Member m2 = selected.population.get(i+1);

            ArrayList<Integer> m1BinaryValue = Utils.toBinary((int)m1.value);
            ArrayList<Integer> m2BinaryValue = Utils.toBinary((int)m2.value);

            makeBinariesSameSize(m1BinaryValue,m2BinaryValue);

            pairOfChildren = makeChildren(m1BinaryValue,m2BinaryValue);

            children.addMember(new Member(pairOfChildren.get(0)));
            children.addMember(new Member(pairOfChildren.get(1)));
        }

        return children;
    }


    private void mutation(ArrayList<Integer> binaryNumber)
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

    private void makeBinariesSameSize(ArrayList<Integer> m1BinaryValue, ArrayList<Integer> m2BinaryValue){
        int m1Size = m1BinaryValue.size(), m2Size = m2BinaryValue.size();

        if (m1Size > m2Size)
            increaseToSameSize(m2BinaryValue,m1Size - m2Size);
        else if (m2Size > m1Size)
            increaseToSameSize(m1BinaryValue,m2Size - m1Size);
    }

    private void increaseToSameSize(ArrayList<Integer> binary, int differenceBetweenSizes)
    {
        boolean haveToCorrectSignal = false;
        if(binary.get(0) == 1)
        {
            binary.set(0,0);
            haveToCorrectSignal = true;
        }

        for (int i = 0; i < differenceBetweenSizes; i++) {
            binary.add(0,0);
        }
        if (haveToCorrectSignal) binary.set(0,1);
    }


    private ArrayList<Integer> makeChildren(ArrayList<Integer> binary1, ArrayList<Integer> binary2)
    {
        int cutoff =  Utils.getRandom(0, binary1.size());

        ArrayList<Integer> pairOfChildren   = new ArrayList<>();
        ArrayList<Integer> child1First      = new ArrayList<>(binary1.subList(0, cutoff));
        ArrayList<Integer> child1Second     = new ArrayList<>(binary2.subList(cutoff, binary2.size() ) );
        ArrayList<Integer> child2First      = new ArrayList<>(binary2.subList(0, cutoff));
        ArrayList<Integer> child2Second     = new ArrayList<>(binary1.subList(cutoff, binary1.size() ) );

        child1First.addAll(child2Second);
        child2First.addAll(child1Second);

        mutation(child1First);
        mutation(child2First);

        pairOfChildren.add(Utils.binaryToInteger(child1First));
        pairOfChildren.add(Utils.binaryToInteger(child2First));

        return pairOfChildren;
    }
}
