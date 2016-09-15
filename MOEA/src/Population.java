import java.util.ArrayList;
import java.util.Random;

/**
 * Created by gmarson on 9/8/2016.
 */
public class Population {
    public static int POP_SIZE =100;
    public static int NUM_GER=50;
    public static double CROSS_RATE=20;
    public static double MUT_RATE=1;

    private ArrayList<Member> pop = new ArrayList<Member>();

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
        ArrayList<Function> f = Functions.getInstance();
        if(f == null) return;
        Function firstFunction = f.get(0);
        double maxValue = firstFunction.getMaxValue(), minValue = firstFunction.getMinValue();
        Member m;
        Random r = new Random();
        for(int i=0 ; i<POP_SIZE; i++)
        {
            this.pop.add(new Member(minValue + (maxValue - minValue) * r.nextDouble()));
        }
    }

    public void printPopulation()
    {
        System.out.println("Population");
        for(int i=0;i<this.pop.size();i++)
        {
            System.out.println("I = "+i+" DATA = "+pop.get(i).getData());
        }
    }
}
