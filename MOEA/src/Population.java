import java.util.ArrayList;
import java.util.Random;

/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

public abstract class Population {
    public static int POP_SIZE =5;
    public static int NUM_GER=50;
    public static double CROSS_RATE=20;
    public static double MUT_RATE=1;


    private static ArrayList<Member> population = new ArrayList<Member>();

    private Population()
    {
        this.CROSS_RATE = this.CROSS_RATE/100.0;
        this.MUT_RATE = this.MUT_RATE/100.0;
    }

    public static ArrayList<Member> getInstance(){
        return population;
    }

    public static void buildPopulation()
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
            population.add(new Member(minValue + (maxValue - minValue) * r.nextDouble()));
        }
    }

    public ArrayList<Member> getPopulation() {
        return population;
    }

    public static void replaceElement(ArrayList<Object> appliedFunctions, int indexOf)
    {
        population.get(indexOf).setResultOfFunctions(appliedFunctions);
    }


    public void removePartialNdi()
    {
        if(population == null)
        {
            System.out.println("Empty population on removePartialNdi!");
            return;
        }
        population.forEach(Member::removeFromPartialNdi); //TODO se bugar o erro pode estar aki
    }


    //Debugging ...
    public static void printPopulation()
    {
        System.out.println("Population");
        for(int i = 0; i<population.size(); i++)
        {
            System.out.println("I = "+i+" DATA = "+ population.get(i).getData());
        }
    }

    public static void printPopulationDetailed()
    {
        for(int i=0; i<population.size(); i++)
        {
            System.out.println("I"+i);
            population.get(i).printMember();
        }
    }

    public static void dominanceRelations()
    {
        Population.printPopulationDetailed();
        ArrayList<Member> p = Population.getInstance();
        if(p.isEmpty()) return;
        int i,j,k,l;
        ArrayList<Member> dominates;
        ArrayList<Integer> functions;
        Member currentMember, dominatedMember;
        int isDominatedBy;

        for(i=0;i<POP_SIZE;i++){
            currentMember = p.get(i);
            functions = currentMember.getResultOfFunctions();
            dominates = currentMember.getUi();
            isDominatedBy = currentMember.getNdi();

            System.out.print("Indivíduo \n"+currentMember.getData()+"\t");
            System.out.println(currentMember.getResultOfFunctions());
            System.out.println("\nDomina: ");


            if(dominates.isEmpty()) {System.out.print("Ninguem");}
            else
            {
                for(j = 0;j<dominates.size();j++)
                {
                    dominatedMember = dominates.get(j);
                    System.out.print(dominatedMember.getData()+"\t");
                    System.out.println(dominatedMember.getResultOfFunctions());

                }

            }
            System.out.println("\n");



        }

    }
}
