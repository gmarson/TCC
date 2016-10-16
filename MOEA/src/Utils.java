import java.util.ArrayList;
import java.util.Random;


/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */
public abstract class Utils {



    public static void dominates()
    {
        int dominates;
        ArrayList<Member> p = Population.getInstance();
        int i, j;
        ArrayList<Integer> resultingFunctionsI, resultingFunctionsJ;
        if (!p.isEmpty()) {
            for (i = 0; i < Population.POP_SIZE; i++) {
                for (j = i + 1; j < Population.POP_SIZE; j++) {

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


    /**
     * return 1 se i domina j
     * return -1 se j domina i
     * return 0 se ngm domina ngm
     */
    public static int compare(ArrayList<Integer> resultingFunctionsI, ArrayList<Integer> resultingFunctionsJ )
    {
        boolean IgreaterThanJ =false;
        boolean JgreaterThanI = false;
        int numberOfFunctions = resultingFunctionsI.size(), k, k1;
        ArrayList<Boolean> IDominatesJ = new ArrayList<Boolean>();
        ArrayList<Boolean> JDominatesI = new ArrayList<Boolean>();
        for(k=0;k<numberOfFunctions;k++)
        {
            for(k1=0;k1<numberOfFunctions;k1++)
            {
                if(resultingFunctionsI.get(k) >= resultingFunctionsJ.get(k1))
                {
                    IDominatesJ.add(true);
                    JDominatesI.add(false);
                    if(resultingFunctionsI.get(k) > resultingFunctionsJ.get(k1) && IgreaterThanJ ==false){
                        IgreaterThanJ =true;
                    }
                }
                else
                {
                    JDominatesI.add(true);
                    IDominatesJ.add(false);

                    if(resultingFunctionsJ.get(k1) > resultingFunctionsI.get(k) && JgreaterThanI ==false){
                        JgreaterThanI =true;
                    }
                }
            }
        }

        if(!(IDominatesJ.contains(false)) && IgreaterThanJ ) return 1;
        else if(!(JDominatesI.contains(false)) && JgreaterThanI ) return -1;
        else return 0;

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
