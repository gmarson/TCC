import java.util.ArrayList;

/**
 * Created by gabrielm on 09/01/17.
 */
public class CrossoverSBX implements Crossover{

    @Override
    public Population crossoverAndMutation(Population selected) {
        for (int i = 0, j = 0; j < selected.population.size()/2; i+=2, j++) {
            Member m1 = selected.population.get(i);
            Member m2 = selected.population.get(i+1);


        }
        return null;
    }




}
