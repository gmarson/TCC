/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;

public abstract class Crossover {


    private static double alfa=0.0, betha;

    private static ArrayList<Member> newMembers = new ArrayList<Member>();

    private Crossover(){}

    public static ArrayList<Member> getInstance()
    {
        return newMembers;
    }


    public static ArrayList<Member> arithmeticCrossover(Member parent1, Member parent2)
    {

        betha = Utils.getRandomDouble((double)alfa+1,(double) alfa);

        double data1 = parent1.getData() * betha + (1-betha ) * parent2.getData();
        double data2 = parent2.getData() * betha + (1-betha ) * parent1.getData();
        ArrayList<Member> children = new ArrayList<Member>();

        children.add(new Member(data1));
        children.add(new Member(data2));

        return children;
    }

    public static ArrayList<Member> binaryCrossover(Member parent1,Member parent2 )
    {
        // o sinal é considerado no crossover e compoe o numero no cruzamento
        String parent1Binary = parent1.getBinaryData();
        String parent2Binary = parent2.getBinaryData();
        int diff = parent1Binary.length() - parent2Binary.length();

        if(diff > 0) //parent 1 is bigger
        {
            parent2Binary = setParentToCorrectSize(parent2,diff);

        }
        else if (diff <0) // parent 2 is bigger
        {
            parent1Binary = setParentToCorrectSize(parent1,diff);
        }

        int cutoff =  Utils.getRandom(parent1Binary.length(),0);
        String parent1FirstPart = parent1Binary.substring(0,cutoff);
        String parent1SecondPart = parent1Binary.substring(cutoff,parent1Binary.length());
        String parent2FirstPart =  parent2Binary.substring(0,cutoff);
        String parent2SecondPart= parent2Binary.substring(cutoff,parent2Binary.length());

        String child1Binary = parent1FirstPart + parent2SecondPart;
        String child2Binary = parent2FirstPart + parent1SecondPart;

        ArrayList<Member> children = new ArrayList<Member>();

        //System.out.println("oia os fi");


        children.add(new Member(child1Binary));
        children.add(new Member(child2Binary));

        return children;

    }

    public static String setParentToCorrectSize(Member parent, int diff)
    {
        String parentBinary = parent.getBinaryData();
        if (parent.isNegative())
        {
            for(int i=0;i<diff;i++)
            {
                parentBinary = "0" +parentBinary;
            }
        }
        else
        {
            parentBinary.substring(1);
            for(int i=0;i<diff-1;i++)
            {
                parentBinary = "0" +parentBinary;
            }
            parentBinary = "1" +parentBinary;
        }
        return parentBinary;

    }

    public static void doCrossover(int option,ArrayList<Integer> indexOfParents)
    {

        double amountOfParents = indexOfParents.size() / 2.0;
        ArrayList<Member> p = Population.getInstance();
        ArrayList<Member> children = new ArrayList<Member>();
        Member m1,m2;
        if(Math.floor(amountOfParents) - amountOfParents != 0)
        {
            System.out.println("Amount of parents is not an even number");
            return;
        }

        if(option == 1)
        { // arithmetic crossover
            int parentIndex = 0;
            for(int i=0; i<amountOfParents; i++)
            {
                m1 = p.get(indexOfParents.get(parentIndex));
                m2 = p.get(indexOfParents.get(parentIndex+1));

                children = arithmeticCrossover(m1,m2);
                for(int j = 0; j <2;j++)
                {
                    if(Mutation.checkForMutation())
                    {
                        Mutation.doMutation(children.get(j));
                    }
                }

                parentIndex +=2;
                p.add(new Member(children.get(0)));
                p.add(new Member(children.get(1)));
            }
        }
        else if(option == 2)
        { //binary crossover
            int parentIndex = 0;
            for(int i=0; i<amountOfParents; i++)
            {
                m1 = p.get(indexOfParents.get(parentIndex));
                m2 = p.get(indexOfParents.get(parentIndex+1));

                children = binaryCrossover(m1,m2);
                for(int j = 0; j <2;j++)
                {
                    if(Mutation.checkForMutation())
                    {
                        Mutation.doMutation(children.get(j));
                    }
                }

                parentIndex += 2;
                p.add(new Member(children.get(0)));
                p.add(new Member(children.get(1)));
            }
        }
        else
        {
                System.out.println("Invalid Crossover Option!");
        }


    }

    public static void resetNewMebers()
    {
        newMembers.clear();
    }

}