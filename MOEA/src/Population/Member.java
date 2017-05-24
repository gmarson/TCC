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



public class Member implements Serializable{

    //NSGAII variables
    public ArrayList<Double> resultOfFunctions = new ArrayList<>();
    public ArrayList<Member> solutionsThatThisMemberDominates = new ArrayList<>();
    public int numberOfSolutionsThatDominatesThisMember =0;
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

    //AEMMT Variables
    public double weightedAverage=-1;
    public ArrayList<Integer> parentTableMask1;
    public ArrayList<Integer> parentTableMask2;


    //MOEA-D Variables
    public WeightVector weightVector ;
    public double solution = -1.0;
    public double distanceFromParentMember = -1.0;
    public ArrayList<Member> closestMembers = new ArrayList<>();


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
        newMember.resultOfFunctions = this.resultOfFunctions;
        newMember.distances = this.distances;
        newMember.binaryValue = this.binaryValue;
        newMember.weightedAverage = this.weightedAverage;
        newMember.weightVector = this.weightVector;
        newMember.solution = this.solution;
        newMember.distanceFromParentMember = this.distanceFromParentMember;
        newMember.closestMembers = this.closestMembers;
        return newMember;
    }



}
