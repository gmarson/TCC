package Population;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */

public class Member implements Serializable, Cloneable{

    //NSGAII variables
    public ArrayList<Member> solutionsThatThisMemberDominates = new ArrayList<>();
    public int numberOfSolutionsThatDominatesThisMember = 0;
    public int rank = -1;
    public double crowdingDistanceValue;

    //SPEA2 variables
    public double rawFitness = -1;
    public double density = -1;
    public double strength = -1;
    public double sigma = -1;
    public ArrayList<Double> distances = new ArrayList<>();

    //AEMMT Variables
    public double weightedAverage=-1;
    public int[] parentTableMask1;
    public int[] parentTableMask2;

    //MOEA-D Variables
    public WeightVector weightVector ;
    public ArrayList<Member> neighborhood;

    //Both Variables
    public double value;
    public double key;
    public ArrayList<Integer> binaryValue;
    public ArrayList<Double> resultOfFunctions = new ArrayList<>();
    public double fitness = -1;

    public Member(ArrayList<Integer> binaryValue) {this.binaryValue = binaryValue;}

    public Member(double value)
    {
        this.value = value;
    }

    public Member(String bValue){
        char[] binaryVector = bValue.toCharArray();
        for (int i = 0, n = binaryVector.length; i < n; i++) {
             binaryValue.add((int) binaryVector[i]);
        }
    }

    public Member deepCopy()
    {
        Member newMember = new Member(this.value);
        newMember.rawFitness = this.rawFitness;
        newMember.density = this.density;
        newMember.fitness = this.fitness;
        newMember.strength = this.strength;
        newMember.weightedAverage = this.weightedAverage;

        for (Double d :this.resultOfFunctions)
        {
            newMember.resultOfFunctions.add(new Double(d));
        }

        for (Double d: this.distances)
        {
            newMember.distances.add(new Double(d));
        }

        if (this.binaryValue != null){
            newMember.binaryValue = new ArrayList<>();
            for (Integer i: this.binaryValue)
            {
                newMember.binaryValue.add(new Integer(i));
            }
        }


        if (this.weightVector != null)
            newMember.weightVector = new WeightVector(this.weightVector);

        if (neighborhood != null && !neighborhood.isEmpty()) {
            newMember.neighborhood = new ArrayList<>();
            for (Member m : this.neighborhood) {

                newMember.neighborhood.add(m.deepCopy());
            }
        }

        return newMember;
    }

    public void printMember()
    {
        System.out.println("Instance: "+this);
        System.out.println("Value: "+value);
        System.out.println("Binary value: "+binaryValue );
        for (int i = 0; i < resultOfFunctions.size(); i++) {
            System.out.println("F"+i+" = "+resultOfFunctions.get(i));
        }
        if(fitness != -1){
            System.out.println("Fitness: "+fitness);
        }
    }

    public String functionsToString(){

        StringBuilder functions = new StringBuilder("[");
        int commasAdded = 0;
        for(Double d : resultOfFunctions){
            functions.append(d.toString());
            if(commasAdded != resultOfFunctions.size() - 1){
                functions.append(",");
                commasAdded++;
            }
        }

        functions.append("]");

        return functions.toString();
    }
}
