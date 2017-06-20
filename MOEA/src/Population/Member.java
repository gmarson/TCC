package Population;

import Constants.Constants;
import Utilities.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public double fitness = -1;
    public double strength = -1;
    public double sigma = -1;
    public ArrayList<Double> distances = new ArrayList<>();

    //Both Variables
    public double value;
    public double key;
    public ArrayList<Integer> binaryValue;
    public ArrayList<Double> resultOfFunctions = new ArrayList<>();

    //AEMMT Variables
    public double weightedAverage=-1;
    public int[] parentTableMask1;
    public int[] parentTableMask2;


    //MOEA-D Variables
    public WeightVector weightVector ;
    public double solution = -1.0;
    public double distanceFromParentMember = -1.0;
    public ArrayList<Member> closestMembers;


    public Member(ArrayList<Integer> binaryValue) {this.binaryValue = binaryValue;}

    public Member(double value)
    {
        this.value = value;
    }

    public Member deepCopy()
    {
        Member newMember = new Member(this.value);
        newMember.rawFitness = this.rawFitness;
        newMember.density = this.density;
        newMember.fitness = this.fitness;
        newMember.strength = this.strength;
        newMember.rawFitness = this.rawFitness;
        newMember.weightedAverage = this.weightedAverage;
        newMember.solution = this.solution;
        newMember.distanceFromParentMember = this.distanceFromParentMember;


        for (Double d :this.resultOfFunctions)
        {
            newMember.resultOfFunctions.add(new Double(d));
        }

        for (Double d: this.distances)
        {
            newMember.distances.add(new Double(d));
        }

        newMember.binaryValue = new ArrayList<>();
        for (Integer i: this.binaryValue)
        {
            newMember.binaryValue.add(new Integer(i));
        }

        if (this.weightVector != null)
            newMember.weightVector = new WeightVector(this.weightVector);

        if (closestMembers != null && !closestMembers.isEmpty()) {
            newMember.closestMembers = new ArrayList<>();
            for (Member m : this.closestMembers) {

                newMember.closestMembers.add(m.deepCopy());
            }
        }

        return newMember;
    }

    public void printMember(){
        System.out.println("Value: "+value);
        System.out.println("Binary value: "+binaryValue );
        for (int i = 0; i < resultOfFunctions.size(); i++) {
            System.out.println("F"+i+" = "+resultOfFunctions.get(i));
        }
    }

}
