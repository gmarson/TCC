
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gmarson on 9/14/2016.
 * TCC UFU
 */
public abstract class Selection {

    private static int TOUR = 5;


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
        ArrayList<Front> fronts = Fronts.getInstance();
        Front currentFront;
        Member bestParentForTour=null, opponentParent;
        int randomNumber,randomNumber2;

        for(int i =0; i<Population.POP_SIZE; i++)
        {
            for(int j=0;j<TOUR;j++)
            {
                //System.out.println("----------------NOVA RODADA------------------");
                randomNumber = Utils.getRandom(Population.POP_SIZE,0);
                opponentParent = p.get(randomNumber);


                //opponentParent.printMember();
                //System.out.println("\ncontra");
                if(bestParentForTour == null)
                {
                    bestParentForTour = opponentParent;
                }
                //bestParentForTour.printMember();

                if(bestParentForTour.getNdi() > opponentParent.getNdi())
                {
                    bestParentForTour = opponentParent;
                }
                else if(bestParentForTour.getNdi() == opponentParent.getNdi())
                {
                    if(bestParentForTour.getData() != opponentParent.getData())
                    {

                        //bestParentForTour.printMember();
                        //Fronts.printFronts();
                        currentFront = fronts.get(bestParentForTour.getFrontId());
                        currentFront.crowdingDistanceOfFront();

                        if (bestParentForTour.getCrowdingDistanceValue() == opponentParent.getCrowdingDistanceValue() || bestParentForTour.isInfinity() && opponentParent.isInfinity())
                        {
                            //System.out.println("Crowding Distance igual");
                            //System.out.println(Utils.getRandom(2,0));
                            if(Utils.getRandom(2,0) == 1)
                            {
                                bestParentForTour = opponentParent;
                            }
                            //s.nextLine();

                        }
                        else if(bestParentForTour.getCrowdingDistanceValue() < opponentParent.getCrowdingDistanceValue())
                        {
                            //System.out.println("Crowding distance de bestparent"+ bestParentForTour.getCrowdingDistanceValue());
                            //System.out.println("Crowding distance de opponentparent"+ opponentParent.getCrowdingDistanceValue());
                            bestParentForTour = opponentParent;
                        }

                    }

                }

                //System.out.println("Vencedor");
                //bestParentForTour.printMember();
                //s.nextLine();
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
