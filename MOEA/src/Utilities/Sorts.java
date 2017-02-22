package Utilities;

import Population.*;


import java.util.*;


public class Sorts
{
	public static void quickSortMembersByKey(Population pop, String keyForValue)
	{
		if(keyForValue.equals("density")){
			for(Member m: pop.population)
			{
				m.key = m.density;
			}
		}
		else if(keyForValue.equals("fitness"))
		{
			for(Member m: pop.population)
			{
				m.key = m.fitness;
			}
		}
		else if(keyForValue.equals("value"))
		{
			for(Member m: pop.population)
			{
				m.key = m.value;
			}
		}
		sort(pop.population, 0, pop.population.size()-1 );	
	}

	public static void sort(ArrayList<Member> list, int from, int to)
	{
	    if (from < to) {
	        int pivot = from;
	        int left = from + 1;
	        int right = to;
	        double pivotValue = list.get(pivot).key;
	        while (left <= right) {
	            
	            while (left <= to && pivotValue >= list.get(left).key) {
	                left++;
	            }
	            
	            while (right > from && pivotValue < list.get(right).key) {
	                right--;
	            }
	            if (left < right) {
	                Collections.swap(list, left, right);
	            }
	        }
	        Collections.swap(list, pivot, left - 1);
	        sort(list, from, right - 1); 
	        sort(list, right + 1, to);   
	    }
	}

	public static void insertionSort(ArrayList<Member> members, int idOfFunctionToBeEvaluated)
    {
        for (int i = 0; i < members.size(); i++)
        {
            Member memberBeingCompared = members.get(i);
            double valueOfMemberBeingCompared = memberBeingCompared.resultOfFunctions.get(idOfFunctionToBeEvaluated);
            for (int j = i -1; j >= 0 && members.get(j).resultOfFunctions.get(idOfFunctionToBeEvaluated) > valueOfMemberBeingCompared ; j--)
            {
                members.set(j+1, members.get(j));
                members.set(j,memberBeingCompared);
            }
        }
    }

    public static void insertionSortCrowding(ArrayList<Member> members)
    {
        for (int i = 0; i < members.size(); i++)
        {
            Member memberBeingCompared = members.get(i);
            double valueOfMemberBeingCompared = memberBeingCompared.crowdingDistanceValue;
            for (int j = i -1; j >= 0 && members.get(j).crowdingDistanceValue > valueOfMemberBeingCompared ; j--)
            {
                members.set(j+1, members.get(j));
                members.set(j,memberBeingCompared);
            }
        }

    }


}