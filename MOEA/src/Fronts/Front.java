package Fronts;

import Constants.Constants;
import Population.Member;
import Utilities.Sorts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Front implements Serializable{
    public ArrayList<Member> membersAtThisFront = new ArrayList<>();
    
    public void crowdingDistanceAssignment(){

        for (int i = 0; i < Constants.PROBLEM_SIZE; i++) {

            Sorts.insertionSort(membersAtThisFront,i);

            membersAtThisFront.get(0).crowdingDistanceValue                            = Double.MAX_VALUE;
            membersAtThisFront.get(membersAtThisFront.size()-1).crowdingDistanceValue  = Double.MAX_VALUE;


            double minFrontFunctionValue = membersAtThisFront.get(0).resultOfFunctions.get(i);
            double maxFrontFunctionValue = membersAtThisFront.get(membersAtThisFront.size() -1).resultOfFunctions.get(i);

            maxFrontFunctionValue = avoidDivisionByZeroInFmax(maxFrontFunctionValue,minFrontFunctionValue);
            applyCrowdingDistanceValue(i,maxFrontFunctionValue,minFrontFunctionValue);

        }

        Sorts.insertionSortCrowding(membersAtThisFront);

    }

    private void applyCrowdingDistanceValue(int i, double maxFrontFunctionValue, double minFrontFunctionValue )
    {
        for(int j=1;j<membersAtThisFront.size() -1;j++)
        {
            double previousMemberValue  = this.membersAtThisFront.get(j+1).resultOfFunctions.get(i);
            double nextMemberValue = this.membersAtThisFront.get(j-1).resultOfFunctions.get(i);
            this.membersAtThisFront.get(j).crowdingDistanceValue +=  ((previousMemberValue-nextMemberValue)/(maxFrontFunctionValue-minFrontFunctionValue));
        }
    }

    private double avoidDivisionByZeroInFmax(double fmax, double fmin)
    {
        if(fmax - fmin == 0)
            fmax += 0.1;

        return fmax;
    }

    public void addMemberToFront(Member member)
    {
        this.membersAtThisFront.add(member);
    }

    public void printFront()
    {
        int i=0;
        for(Member member:membersAtThisFront)
        {
            System.out.println("Population.Member "+i);
            System.out.println("Value: "+member.value);
            System.out.println("Vector Value: "+member.binaryValue);
            System.out.println("Rank: "+member.rank);
            System.out.println("Crowding: "+member.crowdingDistanceValue);
            int j =0;
            for (Double d: member.resultOfFunctions){
                System.out.println("F"+j+" = "+d);
                j++;
            }

            i++;
            System.out.println();

        }
        System.out.println();
    }



}
