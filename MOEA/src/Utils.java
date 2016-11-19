import java.util.ArrayList;
import java.util.Random;


/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */
public abstract class Utils {

    public static void dominatesPopulation()
    {
        ArrayList<Member> p = Population.getInstance();
        int populationSize = p.size();
        int i , j;
        Member mi, mj;
        if(!p.isEmpty())
        {
            for(i =0; i< populationSize;i++)
            {
                mi = p.get(i);
                for(j=i+1; j< populationSize; j++)
                {
                    mj = p.get(j);

                    if(dominates(mi,mj))
                    {

                        p.get(j).addNdi();
                        p.get(i).addMemberToUi(mj);
                    }
                    else if(dominates(mj,mi))
                    {

                        p.get(i).addNdi();
                        p.get(j).addMemberToUi(mi);
                    }
                }
            }
        }


    }

    public static boolean dominates(Member i, Member j)
    {
        boolean better=false, worse=false;
        ArrayList<Integer> arrayI = i.getResultOfFunctions();
        ArrayList<Integer> arrayJ = j.getResultOfFunctions();

        for(int k=0; k <arrayI.size();k++)
        {
            if(arrayI.get(k) <= arrayJ.get(k))
            {
                if(arrayI.get(k) < arrayJ.get(k))
                {
                    better = true;
                }
            }
            else
            {
                return false;
            }
        }

        if(better)
        {
            return true;
        }

        return false;
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
