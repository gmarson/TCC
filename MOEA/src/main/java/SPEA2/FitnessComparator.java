package SPEA2;

import Population.*;

import java.util.Comparator;

/**
 * Created by gabrielm on 10/16/17.
 * Project : TCC.
 */
public class FitnessComparator implements Comparator<Member> {

    @Override
    public int compare(Member member1, Member member2) {
        return  Double.compare(
                member1.fitness,
                member2.fitness);
    }

}
