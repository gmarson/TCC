package Crossover;

import Utilities.*;
import Population.*;
import java.util.ArrayList;

/**
 * Created by gmarson on 2/2/2017.
 * TCC UFU
 */
public class CrossoverArithmetic implements Crossover {

    private double beta = Utils.getRandomDouble(0,1);

    @Override
    public Population crossoverAndMutation(Population selected){

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

    private ArrayList<Double> makeChildren(Member m1, Member m2)
    {
        ArrayList<Double> pairOfChildren = new ArrayList<>();
        double offspring1 = beta*m1.value + (1- beta) * m2.value;
        double offspring2 = (1-beta) * m1.value + beta*m2.value;

        pairOfChildren.add(offspring1);
        pairOfChildren.add(offspring2);

        return  pairOfChildren;
    }

}
