import java.util.ArrayList;

public class SelectionArchive extends Selection
{	
	@Override
	public Population selectParents(Population population)
    {
        return tournament(population);
    }

    @Override
	protected Member returnWinnerCompetitor(ArrayList<Member> membersByTour)
    {
        Member bestMember, opponentMember;
        bestMember = membersByTour.get(0);
        for (int i=1;i<membersByTour.size() ;i++ ) 
        {
            opponentMember = membersByTour.get(i);
            if (opponentMember.fitness < bestMember.fitness)
                bestMember = opponentMember;
        }
        return bestMember;
    }

    @Override
    protected Population tournament(Population archive)
    {
        Population selected = new Population();
        ArrayList<Member> membersByTour;
        while(selected.population.size() < Constants.POPULATION_SIZE) 
        {
            membersByTour = super.makeCompetitors(archive);
            selected.population.add(returnWinnerCompetitor(membersByTour) );
        }
        return selected;
        //todo usei a fitness como criterio no spea2
    }
}