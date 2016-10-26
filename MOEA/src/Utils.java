import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */
public abstract class Utils {



    public static void dominates() //TODO o negocio é saber a causa das fronts estarem ordenadas por data no final, Sabendo isso ja era!!!!
    {
        int dominates;
        ArrayList<Member> p = Population.getInstance();
        int populationSize = p.size();
        int i, j;
        ArrayList<Integer> resultingFunctionsI, resultingFunctionsJ;
        if (!p.isEmpty()) {
            for (i = 0; i < populationSize; i++) {
                for (j = i + 1; j < populationSize; j++) {

                    //System.out.println("data i = "+p.get(i).getData()+"\ndata j = "+p.get(j).getData());
                    resultingFunctionsI = p.get(i).getResultOfFunctions();
                    resultingFunctionsJ = p.get(j).getResultOfFunctions();

                    dominates = compare(resultingFunctionsI, resultingFunctionsJ);

                    switch (dominates){
                        case 1:
                        {
                            p.get(j).addNdi(); // j eh dominada por i
                            p.get(i).addMemberToUi(p.get(j)); //j eh adicionado na
                                                             // lista dos que sao dominados por i
                            break;
                        }
                        case -1:
                        {
                            p.get(i).addNdi(); // i eh dominada por j
                            p.get(j).addMemberToUi(p.get(i)); //i eh adicionado na
                                                             // lista dos que sao dominados por j
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
            }
        }
    }


    public static int compare(ArrayList<Integer> resultingFunctionsI, ArrayList<Integer> resultingFunctionsJ )
    {

        boolean IgreaterThanJ =false;
        boolean JgreaterThanI = false;
        int numberOfFunctions = resultingFunctionsI.size(), k, k1;
        ArrayList<Boolean> IDominatesJ = new ArrayList<Boolean>();
        ArrayList<Boolean> JDominatesI = new ArrayList<Boolean>();
        Scanner s = new Scanner(System.in);
        for(k=0;k<numberOfFunctions;k++)
        {
            for(k1=0;k1<numberOfFunctions;k1++)
            {
                //System.out.println("funcao de i "+resultingFunctionsI.get(k)+"\nfuncao de j "+resultingFunctionsJ.get(k1));
                //s.nextLine();
                if(resultingFunctionsI.get(k) <= resultingFunctionsJ.get(k1))
                {
                    IDominatesJ.add(true);
                    JDominatesI.add(false);
                    if(resultingFunctionsI.get(k) < resultingFunctionsJ.get(k1) && IgreaterThanJ ==false){
                        IgreaterThanJ =true;
                    }
                }
                else
                {
                    JDominatesI.add(true);
                    IDominatesJ.add(false);

                    if(resultingFunctionsJ.get(k1) < resultingFunctionsI.get(k) && JgreaterThanI ==false){
                        JgreaterThanI =true;
                    }
                }
            }
        }

        if(!(IDominatesJ.contains(false)) && IgreaterThanJ )
        {
            //System.out.println("i domina j");
            return 1;
        }
        else if(!(JDominatesI.contains(false)) && JgreaterThanI )
        {
            //System.out.println("j domina i");
            return -1;
        }
        else
        {
            //System.out.println("nao se dominam");
            return 0;
        }

    }

    public static int getRandom(int max, int min)
    {
        max = max -1;
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static double getRandomDouble(double max, double min)
    {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

}
