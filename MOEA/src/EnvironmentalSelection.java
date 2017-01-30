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
            
            int indexOfMemberToBeExcluded = findMostCrowdedMember(environment);
            environment.population.remove(indexOfMemberToBeExcluded);
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

    public static int findMostCrowdedMember(Population environment){
        double minimumDistance = Double.POSITIVE_INFINITY;
        int minimumIndex = -1;

        for (int i =0 ; i< Fitness.distanceMatrix.rows; i++)
        {
            System.out.println("Tamanho da matrix: "+ Fitness.distanceMatrix.size());//todo
            System.out.println("Linhas : "+ Fitness.distanceMatrix.rows);//todo
            System.out.println("Colunas: "+ Fitness.distanceMatrix.columns);//todo
            System.out.println("Index i: "+ i);//todo
            Fitness.distanceMatrix.printMatrix();//todo
            ArrayList<Double> distanceArray = Fitness.distanceMatrix.getDistanceFromMemberIndex(i);
            System.out.println("Distance array: "+distanceArray);//todo
            System.out.println(distanceArray.get(i));
            if (distanceArray.get(i) < minimumDistance)
            {
                minimumDistance = distanceArray.get(i);
                minimumIndex = i;
            }
            else if (distanceArray.get(i) == minimumDistance)
            {
                for (int k = 0; k < distanceArray.size(); k++) {
                    double KthDistance1 = distanceArray.get(k);
                    double KthDistance2 = Fitness.distanceMatrix.distance[minimumIndex][k];

                    if (KthDistance1 < KthDistance2)
                    {
                        minimumIndex = i;
                        break;
                    }
                    else if (KthDistance2 < KthDistance1)
                    {
                        break;
                    }

                }
            }

        }

        return minimumIndex;


    }



}
