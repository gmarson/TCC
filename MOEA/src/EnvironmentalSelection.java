import java.util.ArrayList;

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

        System.out.println("Tamanho do environment: "+environment.population.size());//todo

        while(environment.population.size() > Constants.ARCHIVE_SIZE)
        {
            
            Member memberToBeExcluded = findMostCrowdedMember(environment);
            environment.population.remove(memberToBeExcluded);
            Fitness.buildMatrixFromEnvironment(environment);
            int i =0;
            for (Member member: environment.population)
            {
                Fitness.calculateDistanceBetweenMembers(member,environment,i);
                i++;
            }
        }

        System.out.println("Tamanho do environment: "+environment.population.size()); //todo

    }

    public static Member findMostCrowdedMember(Population archive){
        ArrayList<Member> mostCrowdedMembers = new ArrayList<>();        
        double minimumDistance;
        int firstSizeOfDistanceArray = archive.population.size();
        int distancia_a_analisar = 0;

        Fitness.copyDistancesFromMatrixToMembers(archive);

        while(archive.population.size() > 1 && distancia_a_analisar < firstSizeOfDistanceArray)
        {
            minimumDistance = archive.population(0).distances.get(distancia_a_analisar);
            mostCrowdedMembers = new ArrayList<>();
            mostCrowdedMembers.add(archive.population.get(0));

            for(int i =1; i< archive.size(); i++){
                int distI = archive.population.get(i).distances.get(distancia_a_analisar);
                
                if (distI < minimumDistance) {
                    mostCrowdedMembers = new ArrayList<>();
                    mostCrowdedMembers.add(archive.population.get(i));
                }
                else if(distI == minimumDistance)
                {   
                    mostCrowdedMembers.add(archive.population.get(i));
                }
            }

            archive.population = mostCrowdedMembers;
            distancia_a_analisar++;
        }

        return mostCrowdedMembers.get(0);


    }



}
