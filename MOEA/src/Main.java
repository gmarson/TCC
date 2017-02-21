public class Main {

    public static void main(String[] args) {


        Problem sch = new ProblemSCH();
        Problem f2 = new ProblemF2();
        Problem knapsack = new ProblemKnapsack();

        //NSGAII nsgaii = new NSGAII();
        //nsgaii.runAlgorithm(knapsack);

        SPEA2 spea2 = new SPEA2();
        spea2.runAlgorithm(knapsack);



    }

    private void testFunction() {
        Population pop = new Population();
        Member m1, m2, m3, m4, m5, m6;

        m1 = new Member(0);
        m1.fitness = 0;
        m2 = new Member(1);
        m2.fitness = 1;
        m3 = new Member(2);
        m3.fitness = 2;
        m4 = new Member(-3);
        m4.fitness = -3;
        m5 = new Member(4);
        m5.fitness = 4;
        m6 = new Member(5);
        m6.fitness = 5;

        pop.population.add(m2);
        pop.population.add(m1);
        pop.population.add(m5);
        pop.population.add(m3);
        pop.population.add(m6);
        pop.population.add(m4);

        Sorts.quickSortMembersByKey(pop, "fitness");

        Printer.printMembersWithFitness(pop);
    }
}
