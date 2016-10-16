/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;

public abstract class Crossover {

    // TODO para real usar crossover aritméico
    private static double alfa=0.0, betha;

    private static ArrayList<Member> newMembers = new ArrayList<Member>();

    private Crossover(){}

    public static ArrayList<Member> getInstance()
    {
        return newMembers;
    }


    public static ArrayList<Member> arithmeticCrossover(Member parent1, Member parent2)
    {
        //TODO perguntar para o tiago pq o método que eu vi na internet eh diferente do dele
        betha = Utils.getRandomDouble((double)alfa+1,(double) alfa);
        double data1 = parent1.getData() + betha * (parent2.getData() - parent1.getData());
        double data2 = parent2.getData() + betha * (parent1.getData() - parent2.getData());
        ArrayList<Member> children = new ArrayList<Member>();

        children.add(new Member(data1));
        children.add(new Member(data2));

        return children;
    }

    /**
     * Crossover para funções reais
     * @param parent1
     * @param parent2
     * @return
     */
    public static void binaryCrossover(Member parent1,Member parent2 )
    {
        String parent1Binary = parent1.getBinaryDataDecimal();
        String parent2Binary = parent2.getBinaryDataDecimal();
        int diff = parent1Binary.length() - parent2Binary.length();

        if(diff > 0) //parent 1 is bigger
        {
            for(int i=0;i<diff;i++)
            {
                parent2Binary = "0" +parent2Binary;
            }
        }
        else if (diff <0) // parent 2 is bigger
        {
            for(int i=0;i<diff;i++)
            {
                parent1Binary = "0" +parent1Binary;
            }
        }

        int cutoff =  Utils.getRandom(parent1Binary.length(),0);
        String parent1FirstPart = parent1Binary.substring(0,cutoff);
        String parent1SecondPart = parent1Binary.substring(cutoff,parent1Binary.length());
        String parent2FirstPart =  parent2Binary.substring(0,cutoff);
        String parent2SecondPart= parent2Binary.substring(cutoff,parent2Binary.length());

        String child1Binary = parent1FirstPart + parent2SecondPart;
        String child2Binary = parent2FirstPart + parent1SecondPart;

        boolean p1negative = parent1.isNegative();
        boolean p2negative = parent2.isNegative();

        newMembers.add(new Member(child1Binary, parent1.getDecimal(),p1negative));
        newMembers.add(new Member(child2Binary, parent2.getDecimal(),p2negative));

        //TODO debuga aki pq ta errado
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

        }
        else
        {
                System.out.println("Invalid Crossover Option!");
        }


    }
}