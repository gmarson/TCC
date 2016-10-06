import javax.rmi.CORBA.Util;
import java.util.ArrayList;

/**
 * Created by gmarson on 9/14/2016.
 * TCC UFU
 */
public abstract class Selection {

    private int TOUR = 3;


    public Selection()
    {

    }


    //varro de 0 até a taxa de crossover que são a qtd de filhos gerados
    // seleciono aleatoriamente 1 pra competir com o outro em uma quantidade tour de vezes
    // primeiro criterio fronteira, segundo critério, crowding distance
    public void torneioBinario()
    {// TODO refatorar o nome
        int crossoverRate = (int)Population.CROSS_RATE /100;
        ArrayList<Member> p = Population.getInstance();
        ArrayList<Integer> parents = new ArrayList<Integer>();

        Member bestParentForTour=null, opponentParent;
        int randomNumber;

        for(int i =0; i<Population.POP_SIZE; i++)
        {
            for(int j=0;j<TOUR;j++)
            {
                randomNumber = Utils.getRandom(Population.POP_SIZE,0);
                opponentParent = p.get(randomNumber);
                if(bestParentForTour == null)
                {
                    bestParentForTour = opponentParent;
                }
                else if(bestParentForTour.getNdi() > opponentParent.getNdi())
                {
                    bestParentForTour = opponentParent;
                }
                else if(bestParentForTour.getNdi() == opponentParent.getNdi())
                {

                    Utils.crowdingDistance(bestParentForTour , opponentParent);
                    //crowding Distance deve devolver a fronteira que engloba os dois individuos
                    // eu devo mandar o id da fronteira
                    //
                    //faz crowding distance dos dois e pega o que tem maior diversidade

                }
            }
            parents.add(p.indexOf(bestParentForTour));
        }

    }


}
