/**
 * Created by gabrielm on 16/01/17.
 */
public abstract class EnvironmentalSelection {

    static Matrix distanceMatrix;

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

    public static void removeMostSimilar(Population environment)
    {

        int positionOfSigma = (int) Math.floor(Math.sqrt((double)environment.population.size()));
        int indexOfMatrix;
        
        while(environment.population.size() != Constants.ARCHIVE_SIZE)
        {
            Fitness.buildMatrixFromEnvironment(environment);
            indexOfMatrix =0;
            for(Member member: environment.population)
            {
                Fitness.calculateDensity(member,environment,indexOfMatrix);
                indexOfMatrix++;
            }

            environment.population.remove(environment.population.size()-1);
            //todo to na duvida se remove o primeiro ou o ultimo 
        }
        
    }


    public static Population environmentalSelection(Population population, Population archive)
    {
        Population union = new Population();
        Population environment = new Population();
        union.mergeTwoPopulations(population,archive); 
        environment = union.getNonDominated();
        System.out.println("No environmentalSelection imprimindo a uniao");///todo
        ProblemSCH problemSCH = new ProblemSCH();
        problemSCH.checkBestAnswerAppearances(union);

        if(environment.population.size() < Constants.ARCHIVE_SIZE)
        {   
            populateWithRemainingBest(environment, union);
        }
        else if(environment.population.size() > Constants.ARCHIVE_SIZE)
        {  
            removeMostSimilar(environment);  
        }
        
        return environment;

    }
    
    public static void setFirstMatrix()
    {
        distanceMatrix = null;
    }

}
