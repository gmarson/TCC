/**
 * Created by gabrielm on 16/01/17.
 */
public abstract class EnvironmentalSelection {

    public static void populateWithRemainingBest(Population population, Population archive)
    {
        Population environment = new Population();
        environment.mergeTwoPopulations(population, archive);
        Sorts.quickSortMembersByFitness(environment);   

        archive  = new Population();

        int i = 0;
        while(archive.population.size() < Constants.ARCHIVE_SIZE)
        {
            archive.population.add(environment.population.get(i)); 
            i++;
        }

        /*
        * Junta os dois pq a population eh o resultado do crossover do arquivo, portanto, ela mudou
        * Entao a gnt junta os dois  e ordena por fitnnes pq ja vamos ter calculado tudo //todo no crossover instanciar todos os indivíduos para a pop
        * Aqui nao vai importar as fronteiras, aliás acho que as fronteiras nao importam no spea2, TENHO QUASE CTZ
        * Depois de juntar os dois vamos colocar os elementos deles na pop nova até que o tamnho do arquivo seja preenchido
        * */

    }

    public static void removeMostSimilar(Population archive)
    {

    }

    


}
