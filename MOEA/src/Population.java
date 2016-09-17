import java.util.ArrayList;
import java.util.Random;

/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

public class Population {
    public static int POP_SIZE =5;
    public static int NUM_GER=50;
    public static double CROSS_RATE=20;
    public static double MUT_RATE=1;

    private static ArrayList<Member> population = new ArrayList<Member>();

    public Population(int size, int ger, double cross, double mut)
    {
        this.POP_SIZE = size;
        this.NUM_GER = ger;
        this.CROSS_RATE = cross/100.0;
        this.MUT_RATE = mut/100.0;

    }

    public Population()
    {
        this.CROSS_RATE = this.CROSS_RATE/100.0;
        this.MUT_RATE = this.MUT_RATE/100.0;
    }

    public void generatePopulation()
    {
        Function f = ProblemSCH.getInstance();
        if(f == null)
        {
            System.out.println("Null Function");
            return;
        }

        Member m;
        Random r = new Random();

        double maxValue = f.getMaxValue();
        double minValue = f.getMinValue();

        for(int i=0 ; i<POP_SIZE; i++)
        {
            this.population.add(new Member(minValue + (maxValue - minValue) * r.nextDouble()));
        }
    }

    public void printPopulation()
    {
        System.out.println("Population");
        for(int i = 0; i<this.population.size(); i++)
        {
            System.out.println("I = "+i+" DATA = "+ population.get(i).getData());
        }
    }

    public ArrayList<Member> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Member> population) {
        this.population = population;
    }

    public void replaceElement(ArrayList<Object> appliedFunctions, int indexOf)
    {
        this.population.get(indexOf).setResultOfFunctions(appliedFunctions);
    }

    public void printPopulationDetailed()
    {
        for(int i=0; i<this.population.size(); i++)
        {
            System.out.println("I"+i);
            this.population.get(i).printMember();
        }
    }
}
