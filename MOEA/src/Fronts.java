import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;
/**
 * Created by gmarson on 12/21/2016.
 * TCC UFU
 */
public class Fronts {
    protected ArrayList<Front> allFronts = new ArrayList<>();


    public void buildOrderedFronts(Population p)
    {
        int currentRankOfMember = -1;
        for(Member member:p.population)
        {
            if(currentRankOfMember != member.rank){
                currentRankOfMember = member.rank;
                addNewFront(currentRankOfMember);
            }

            this.allFronts.get(currentRankOfMember).addMemberToFront(member);
        }
    }

    public void addNewFront(int currentRankOfMember)
    {
        this.allFronts.add(currentRankOfMember,new Front());
    }

    public void printAllFronts()
    {
        int i=0;
        for(Front front: allFronts)
        {
            int currentRank = front.membersAtThisFront.get(0).rank;
            int memberCounter = 0;
            System.out.println("Fronteira "+currentRank);
            for(Member member:front.membersAtThisFront)
            {
                System.out.println("Member "+memberCounter+" = "+member.value);
                memberCounter++;
                i++;
            }
            System.out.println();
        }
        System.out.println("Populacao: "+i);
    }

    public void printFirstFrontOccurances() {
        ArrayList<Double> occurrances = new ArrayList<Double>();
        for (Member member:this.allFronts.get(0).membersAtThisFront)
        {
            if (!occurrances.contains(member.value)){
                occurrances.add(member.value);
                System.out.println(member.value);
            }
        }

    }

}
