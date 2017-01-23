public abstract class Printer
{

    public static void checkEmpty(Population p)
    {
        if(p.population.isEmpty())
        {
            System.out.println("Vazio");
        }
    }
	public static void printMembersWithAppliedFunctions(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Member "+i+ " = "+m.value);
            System.out.println("F1 = "+m.resultOfFunctions.get(0));
            System.out.println("F2 = "+m.resultOfFunctions.get(1));
            System.out.println("Rank = "+m.rank + "\n");
            i++;
        }
    }

    public static void printMembersWithValue(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Member "+i+ " = "+m.value);
            i++;
        }
    }

    public static void printMembersWithFitness(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Member "+i+ ", fitness = "+m.fitness);
            i++;
        }
    }
}