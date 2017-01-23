
import java.util.ArrayList;

/**
 * Created by gmarson on 12/23/2016.
 * TCC UFU
 */
public abstract class Selection {

    public abstract Population selectParents(Population population);
    protected abstract Member returnWinnerCompetitor(ArrayList<Member> membersByTour);
    protected abstract Population tournament(Population population);

    protected ArrayList<Member> makeCompetitors(Population p)
    {
        ArrayList<Member> membersByTour = new ArrayList<>();
        for (int j = 0; j < Constants.TOUR_SIZE; j++) 
        {
            int randomNumberForTournament = Utils.getRandom(0,p.population.size());
            membersByTour.add(p.population.get(randomNumberForTournament));
        }
        return membersByTour;
    }


}
