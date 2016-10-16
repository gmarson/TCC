import java.util.ArrayList;

/**
 * Created by gmarson on 9/14/2016.
 * TCC UFU
 */
public abstract class Selection {

    private static int TOUR = 3;


    public Selection()
    {

    }


    //varro de 0 até a taxa de crossover que são a qtd de filhos gerados
    // seleciono aleatoriamente 1 pra competir com o outro em uma quantidade tour de vezes
    // primeiro criterio fronteira, segundo critério, crowding distance
    public static ArrayList<Integer> binaryTournament()
    {
        int crossoverRate = (int)Population.CROSS_RATE /100;
        ArrayList<Member> p = Population.getInstance();
        ArrayList<Integer> parents = new ArrayList<Integer>();
        ArrayList<Front> f = Fronts.getInstance();
        Front currentFront;
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
                    if(!(bestParentForTour.equals(opponentParent)))
                    {
                        currentFront = f.get(bestParentForTour.getNdi());
                        currentFront.crowdingDistanceOfFront();

                        if(bestParentForTour.getCrowdingDistanceValue() < opponentParent.getCrowdingDistanceValue())
                        {
                            bestParentForTour = opponentParent;
                        }
                    }

                }
            }
            parents.add(p.indexOf(bestParentForTour));
            bestParentForTour = null;
            opponentParent = null;
        }

        return parents;
    }

    //Debugging ...
    public static void membersGoingToCrossover(ArrayList<Integer> parents)
    {
        //TODO tem que sempre colocar um numero par de pais. No caso da populaçao = 5 isso nao vai funcionar

        ArrayList<Member> p = Population.getInstance();
        Integer indexOfParents, nextIndexOfParents;
        System.out.println(""+parents);

        for(int i=0;i<parents.size()-1;i+=2)
        {
            System.out.println("I= "+i);
            indexOfParents = parents.get(i);
            nextIndexOfParents = parents.get(i+1);
            System.out.println("PARENT 1: "+p.get(indexOfParents)+"\nPARENT 2: "+p.get(nextIndexOfParents)+"\n");
        }

    }

}
