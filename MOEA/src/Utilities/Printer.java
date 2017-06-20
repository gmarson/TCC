package Utilities;

import ManyObjective.*;
import ManyObjective.TableFunctions.TableAEMMT;
import ManyObjective.TableFunctions.TableFunctions;
import Population.*;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class Printer
{

    public static void checkEmpty(Population p)
    {
        if(p.population.isEmpty())
        {
            System.out.println("Vazio");
        }
    }


    public static void printOneNeighboring(Member m){
        System.out.print("Membro de vetor de distancia: ");
        for (int i = 0; i <m.weightVector.vector.length ; i++) {
            System.out.print(m.weightVector.vector[i]+" ");
        }

        System.out.println("\nValor dos membros na vizinhanca");
        for (Member childMember : m.closestMembers)
        {
            System.out.println(childMember.value);
        }
    }

    public static void printNeighboring(Population p){


        for(Member m : p.population){
            System.out.print("Membro de vetor de distancia: ");
            for (int i = 0; i <m.weightVector.vector.length ; i++) {
                System.out.print(m.weightVector.vector[i]+" ");
            }

            System.out.println("\nValor dos membros na vizinhanca");
            for (Member childMember : m.closestMembers)
            {
                System.out.println(childMember.value);
            }

        }
    }

    public static void printNeighboringBinary(Population p){
        for(Member m : p.population){
            System.out.print("Membro de vetor de distancia: ");
            for (int i = 0; i <m.weightVector.vector.length ; i++) {
                System.out.print(m.weightVector.vector[i]+" ");
            }

            System.out.println("\nValor dos membros na vizinhanca");
            for (Member childMember : m.closestMembers)
            {
                System.out.println(childMember.binaryValue);
            }

        }
    }

    public static void printTables(TableFunctions tableFunctions){
        for (Table table: tableFunctions.getTables()){
            System.out.println("Tabela:"+ Arrays.toString(table.mask));
            int i =1;
            for(Member member: table.tablePopulation.population)
            {
                int j=1;
                System.out.println("Member: "+i+" VALUE = "+ member.value);
                System.out.println("Member: "+i+" BINARYVALUE = "+ member.binaryValue);

                for (Double d: member.resultOfFunctions)
                {
                    System.out.println("Function = "+ j+" = "+d);
                    j++;
                }
                i++;
            }
            System.out.println();
        }

    }

    public static void printBinaryValuesNonDominatedTable(TableFunctions tableFunctions){
        for (Table table: tableFunctions.getTables()){
            if (table.isNonDominatedTable){
                System.out.println("Tabela:"+ Arrays.toString(table.mask));
                System.out.println("Quantidade de elementos: "+table.tablePopulation.population.size());
                int i =1;
                for(Member member: table.tablePopulation.population)
                {
                    System.out.println("Member: "+i+" BINARYVALUE = "+ member.binaryValue);

                    i++;
                }
                System.out.println();
            }
        }
    }

    public static void printNonDominatedTable(TableFunctions tableFunctions){
        for (Table table: tableFunctions.getTables()){
            if (table.isNonDominatedTable){
                System.out.println("Tabela:"+ Arrays.toString(table.mask));
                int i =1;
                for(Member member: table.tablePopulation.population)
                {
                    int j=1;
                    System.out.println("Member: "+i+" VALUE = "+ member.value);
                    System.out.println("Member: "+i+" BINARYVALUE = "+ member.binaryValue);

                    for (Double d: member.resultOfFunctions)
                    {
                        System.out.println("Function = "+ j+" = "+d);
                        j++;
                    }
                    i++;
                }
                System.out.println();
            }
        }
    }

    public static void printMembersValue(Population p)
    {
        ArrayList<Double> values = new ArrayList<>();
        for(Member m : p.population)
        {
            values.add(m.value);
        }
        System.out.println(values);
    }

    public static void printBinaryMembersWithAppliedFunctions(Population p){
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Member "+i+ " = "+m.binaryValue);
            int j=1;
            for(Double d: m.resultOfFunctions)
            {

                System.out.println("F"+j+" = "+d);
                j++;

            }
            System.out.println("Rank = "+m.rank + "\n");
            i++;
        }
    }

	public static void printMembersWithAppliedFunctions(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Member "+i+ " = "+m.value);
            int j=1;
            for(Double d: m.resultOfFunctions)
            {

                System.out.println("F"+j+" = "+d);
                j++;

            }
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
            System.out.println("Population.Member "+i+ " = "+m.value);
            i++;
        }
    }

    public static void printBinaryMembers(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.binaryValue);
            i++;
        }
    }

    public static void printMembersWithValueAndDomination(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.value+" Solutions that this member dominates = "+m.solutionsThatThisMemberDominates.size());
            i++;
        }
    }

    public static void printMembersWithWeightedAverage(Population p){
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.value+"   WeightedAverage = "+m.weightedAverage);
            i++;
        }
    }

    public static void printMembersWithWeightedAverageAndFunctions(Population p){
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.value+"   WeightedAverage = "+m.weightedAverage);
            int j=1;
            for(Double d: m.resultOfFunctions)
            {

                System.out.println("F"+j+" = "+d);
                j++;

            }
            System.out.println("Rank = "+m.rank + "\n");
            i++;
        }
    }

    public static void printMembersWithValues(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.value+"   BinaryValue = "+m.binaryValue);
            i++;
        }
    }

    public static void printMembersWithValueAndFitness(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.value+"   SPEA2.Fitness = "+m.fitness);
            i++;
        }
    }

    public static void printMembersWithValueFitnessAndDensity(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.value+"   SPEA2.Fitness = "+m.fitness+"   Density = "+m.density);
            i++;
        }
    }

    public static void printMembersWithBinaryValueFitnessAndDensity(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ " = "+m.binaryValue+"   SPEA2.Fitness = "+m.fitness+"   Density = "+m.density);
            i++;
        }
    }

    public static void printMembersWithFitness(Population p)
    {
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ ", fitness = "+m.fitness);
            i++;
        }
    }


    public static void printMembersWithValueAndDistance(Population p){
        checkEmpty(p);
        int i =0;
        for(Member m : p.population)
        {
            System.out.println("Population.Member "+i+ ", value = "+m.value+"  distance: "+m.distances);
            i++;
        }
    }


    public static void printPopulationArchiveAndUnion(Population p, Population archive, Population union)
    {
        System.out.println("");
        System.out.println("POPULACAO");
        printMembersWithValueAndFitness(p);
        System.out.println("");
        System.out.println("ARQUIVO");
        printMembersWithValueAndFitness(archive);
        System.out.println("");
        System.out.println("UNIAO");
        printMembersWithValueAndFitness(union);
        System.out.println("");
    }


    public static void printFirstFront(Population p)
    {
        p.fronts.allFronts.get(0).printFront();
    }

    public static void printMemberWithClosestMembers(Member m ){
        System.out.println("Membro de valor: "+m.value);
        System.out.println("Solution: "+m.solution);
        System.out.print("Vetor de Pesos: ");
        for(Double d:m.weightVector.vector)
            System.out.print(d+" ");
        System.out.println("\nFuncões objetivo: ");

        int j=0;
        for(Double d: m.resultOfFunctions)
        {

            System.out.println("F"+j+" = "+d);
            j++;

        }

        for(Member me: m.closestMembers){
            System.out.println("Valor do Membro: "+ me.value);
            System.out.println("Distancia para o pai: "+me.distanceFromParentMember);
            System.out.println("Solution: "+me.solution);
            System.out.print("Vetor de Pesos: ");
            for(Double d:me.weightVector.vector)
                System.out.print(d+" ");

            System.out.println("\nFuncões objetivo: ");

            j=0;
            for(Double d: me.resultOfFunctions)
            {

                System.out.println("F"+j+" = "+d);
                j++;

            }


            System.out.println("OS SEGUINTE ATRIBUTOS DEVEM SER NULOS OU VAZIOS");
            System.out.println("Closest Members: "+me.closestMembers);



            System.out.println();
        }
    }

    private static void printInfoTable(Table t){
        System.out.println("Tabela: "+t.mask);
        System.out.println("Is Non Dominated Table? "+t.isNonDominatedTable);
        System.out.println("Size: "+t.tablePopulation.population.size());
        System.out.println("Contribution: "+t.contribution);
        System.out.println("Convergence: "+t.convergence);
        System.out.println();
    }

    public static void printTablesInfos(ArrayList<Table> tables) {
        for (Table t : tables)
        {
            printInfoTable(t);
        }

    }

    public static void printOnlyScoredTables(ArrayList<Table> tables) {
        for (Table t : tables)
        {
            if (t.convergence != 0 || t.contribution != 0)
                printInfoTable(t);
        }

    }

    public static void printMasks(TableFunctions tableFunctions) {
        for (Table table : tableFunctions.getTables())
        {
            System.out.println(Arrays.toString(table.mask));
        }
    }
}