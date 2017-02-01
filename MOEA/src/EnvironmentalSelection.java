import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gabrielm on 16/01/17.
 */
public abstract class EnvironmentalSelection {



    public static void populateWithRemainingBest(Population environment, Population union)
    {   
        Sorts.quickSortMembersByKey(union,"fitness");   
        int i = 0;

        while(environment.population.size() < Constants.ARCHIVE_SIZE )
        {
            Member member = union.population.get(i);

            if (member.fitness >= 1.0) 
                environment.population.add(member);
                 
            i++;
        }

        /*
        * Junta os dois pq a population eh o resultado do crossover do arquivo, portanto, ela mudou
        * Entao a gnt junta os dois  e ordena por fitnnes pq ja vamos ter calculado tudo
        * Aqui nao vai importar as fronteiras, aliás acho que as fronteiras nao importam no spea2, TENHO QUASE CTZ
        * Depois de juntar os dois vamos colocar os elementos deles na pop nova até que o tamnho do arquivo seja preenchido
        * */

    }




    public static Population environmentalSelection(Population population, Population archive)
    {


        Population union = new Population();
        Population environment = new Population();
        union.mergeTwoPopulations(population,archive); 
        environment = union.getNonDominated();


        if(environment.population.size() < Constants.ARCHIVE_SIZE)
        {   
            populateWithRemainingBest(environment, union);

        }
        else if(environment.population.size() > Constants.ARCHIVE_SIZE)
        {   
            System.out.println("RemoveMostSimilar"); //todo
            //System.out.println("Environment"); // todo
            //Printer.printMembersWithValueFitnessAndDensity(environment); //todo
            removeMostSimilar(environment);
        }
        
        return environment;

    }

    public static void removeMostSimilar(Population environment)
    {

        //System.out.println("Tamanho do environment antes de remover: "+environment.population.size());//todo

        while(environment.population.size() > Constants.ARCHIVE_SIZE)
        {
            Fitness.buildMatrixFromEnvironment(environment);
            int i =0;
            for (Member member: environment.population)
            {
                member.distances = new ArrayList<>();
                Fitness.calculateDistanceBetweenMembers(environment,i);
                i++;
            }
            Member memberToBeExcluded = findMostCrowdedMember(new Population(environment));
            environment.population.remove(memberToBeExcluded);

            //System.out.println("Tamanho do environment: "+environment.population.size());


        }





        //System.out.println("Tamanho do environment depois de remover: "+environment.population.size()); //todo



    }

    public static Member findMostCrowdedMember(Population archive){
        Population mostCrowdedMembers = new Population();
        double minimumDistance;
        int sizeOfDistanceArray = archive.population.size()-1;
        int distanceToProcess = 0;
        Fitness.copyDistancesFromMatrixToMembers(archive);


        while(archive.population.size() > 1 && distanceToProcess < sizeOfDistanceArray)
        {

            //System.out.println("NOVO LACO");//todo
            //System.out.println("Tamanho do environment laco: "+archive.population.size()); //todo

            minimumDistance = archive.population.get(0).distances.get(distanceToProcess);
            //System.out.println("Distance to Process: "+distanceToProcess);//todo
            //System.out.println("Array de distancia do indivíduo "+archive.population.get(distanceToProcess).value+"(value): "+archive.population.get(0).distances);//todo
            //System.out.println("Distancia minima: "+minimumDistance);//todo
            mostCrowdedMembers.population = Utils.newArrayWithMember(archive.population.get(0));

            for(int i =1; i< archive.population.size(); i++){
                double distI = archive.population.get(i).distances.get(distanceToProcess);
                
                if (distI < minimumDistance) {
                    mostCrowdedMembers.population = Utils.newArrayWithMember(archive.population.get(i));
                }
                else if(distI == minimumDistance)
                {   
                    mostCrowdedMembers.population.add(archive.population.get(i));
                }
            }

            //System.out.println("Most Crowded Members: ");//todo
            //Printer.printMembersWithValueAndDistance(mostCrowdedMembers);//todo



            archive.population = mostCrowdedMembers.population;
            sizeOfDistanceArray = archive.population.size()-1;
            distanceToProcess++;
            //System.out.println("Tamanho do environment final laco: "+archive.population.size()); //todo
        }





        //System.out.println("Populacao do arquivo ");//todo
        //Printer.printMembersWithValueAndFitness(archive);//todo


        //System.out.println("Será eleminado "+mostCrowdedMembers.population.get(0).value);//todo
        return mostCrowdedMembers.population.get(0);


    }



}
