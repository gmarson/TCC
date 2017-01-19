/**
 * Created by gabrielm on 16/01/17.
 */
public abstract class EnvironmentalSelection {

    static Matrix distanceMatrix;

    public static void populateWithRemainingBest(Population environment, Population union)
    {   
        Sorts.quickSortMembersByKey(union,"fitness");   
        int i = 0;
        while(environment.population.size() < Constants.ARCHIVE_SIZE)
        {
            Member member = union.population.get(i);
            if (member.fitness >= 1.0) 
                environment.population.add(member);
                 
            i++;
        }

        /*
        * Junta os dois pq a population eh o resultado do crossover do arquivo, portanto, ela mudou
        * Entao a gnt junta os dois  e ordena por fitnnes pq ja vamos ter calculado tudo //todo no crossover instanciar todos os indivíduos para a pop
        * Aqui nao vai importar as fronteiras, aliás acho que as fronteiras nao importam no spea2, TENHO QUASE CTZ
        * Depois de juntar os dois vamos colocar os elementos deles na pop nova até que o tamnho do arquivo seja preenchido
        * */

    }

    public static void removeMostSimilar(Population environment)
    {
        int positionOfSigma = (int) Math.floor(Math.sqrt((double)environment.population.size()));
        int MatrixSize = environment.population.size();
        distanceMatrix = new Matrix(MatrixSize, MatrixSize);
        int indexOfMatrix = 0;
        while(environment.population.size() > Constants.ARCHIVE_SIZE)
        {
            for(Member member: environment.population)
            {
                Fitness.calculateDensity(member,environment,indexOfMatrix);
                indexOfMatrix++;
            }
            indexOfMatrix =0;
            Sorts.quickSortMembersByKey(environment,"density");

            environment.population.remove(0);
        }
        //todo fica esperto pra ver se nao tem que limpar a matriz
        
    }


    public static void environmentalSelection(Population population, Population archive)
    {
        Population union = new Population();
        Population environment = new Population();
        union.mergeTwoPopulations(population,archive); 
        environment = union.getNonDominated();

        if(environment.population.size() < Constants.ARCHIVE_SIZE)
        {
            populateWithRemainingBest(environment, union);
        }
        else if(environment.population.size() >Constants.ARCHIVE_SIZE)
        {
            removeMostSimilar(environment);
        }

    }
    


}
