package Crossover;

import SupportingFiles.Parameters;
import Population.*;
import SupportingFiles.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 22/06/17.
 * Project : TCC.
 */
public class CrossoverUniformKnapsack implements Crossover{

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


    private ArrayList<Member> makeChildren(Member m1, Member m2)
    {
        ArrayList<Member> pairOfChildren = new ArrayList<>();
        ArrayList<Integer> child1Vector = new ArrayList<>();
        ArrayList<Integer> child2Vector = new ArrayList<>();

        for (int i = 0; i < m1.binaryValue.size(); i++) {
            int choice = Utils.getRandom(0,2);
            if (choice == 1){
                child1Vector.add(m1.binaryValue.get(i));
                child2Vector.add(m2.binaryValue.get(i));
            }
            else
            {
                child1Vector.add(m2.binaryValue.get(i));
                child2Vector.add(m1.binaryValue.get(i));
            }
        }

        mutation(child1Vector);
        mutation(child2Vector);

        Member child1 = new Member(child1Vector);
        Member child2 = new Member(child2Vector);

        pairOfChildren.add(child1);
        pairOfChildren.add(child2);

        return pairOfChildren;
    }

    private void mutation(ArrayList<Integer> binaryNumber)
    {
        if(Utils.getRandom(1,100) <= Parameters.MUTATION_RATE)
        {
            int sectionToBeMutated = Utils.getRandom(0,binaryNumber.size());
            if(binaryNumber.get(sectionToBeMutated) == 0)
                binaryNumber.set(sectionToBeMutated,1);
            else
                binaryNumber.set(sectionToBeMutated,0);
        }
    }
}
