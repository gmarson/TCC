import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gabrielm on 16/01/17.
 */
public abstract class EnvironmentalSelection {


    private static void populateWithRemainingBest(Population environment, Population union)
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

    }


    public static Population environmentalSelection(Population population, Population archive)
    {


        Population union = new Population();
        Population environment;
        union.mergeTwoPopulations(population,archive);
        environment = union.getNonDominated();

        if(environment.population.size() < Constants.ARCHIVE_SIZE){
            populateWithRemainingBest(environment, union);
        }


        else if(environment.population.size() > Constants.ARCHIVE_SIZE) {

            removeMostSimilar(environment);

        }
        
        return environment;

    }

    private static void removeMostSimilar(Population environment)
    {


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

        }


    }

    public static Member findMostCrowdedMember(Population archive){
        Population mostCrowdedMembers = new Population();
        double minimumDistance;
        int sizeOfDistanceArray = archive.population.size()-1;
        int distanceToProcess = 0;
        Fitness.copyDistancesFromMatrixToMembers(archive);


        while(archive.population.size() > 1 && distanceToProcess < sizeOfDistanceArray)
        {
            minimumDistance = archive.population.get(0).distances.get(distanceToProcess);
            mostCrowdedMembers.population = Utils.newArrayWithMember(archive.population.get(0));

            for(int i =1; i< archive.population.size(); i++){
                double distI = archive.population.get(i).distances.get(distanceToProcess);

                if (distI < minimumDistance)
                    mostCrowdedMembers.population = Utils.newArrayWithMember(archive.population.get(i));
                else if(distI == minimumDistance)
                    mostCrowdedMembers.population.add(archive.population.get(i));

            }

            archive.population = mostCrowdedMembers.population;
            sizeOfDistanceArray = archive.population.size()-1;
            distanceToProcess++;

        }
        return mostCrowdedMembers.population.get(0);

    }




}
