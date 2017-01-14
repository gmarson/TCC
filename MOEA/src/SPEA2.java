/**
 * Created by gabrielm on 09/01/17.
 */
public class SPEA2 {



    Matrix distanceMatrix = new Matrix(Constants.DISTANCE_MATRIX_SIZE, Constants.DISTANCE_MATRIX_SIZE);

    public void runAlgorithm(){
        int genCounter = 0;
        Population p = new Population();
        Problem problem = new ProblemSCH();
        Front archive = new Front();
        Population union = new Population();

        p.population = problem.generateRandomMembers();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS)
        {
            problem.evaluateAgainstObjectiveFunctions(p);
            union.mergePopulationWithFront(p,archive);
            union.fastNonDominatedSort();

            archive = union.getNonDominatedFront();
            //todo verificar se os numeros estao em ordem de dominancia

            genCounter++;
        }
    }


    public void calculateStrength(Member member)
    {
        member.strength = member.solutionsThatThisMemberDominates.size();
    }

    public void calculateRawFitness(Member memberToBeEvaluated, Population union)
    {
        for (Member member: union.population)
        {
            if(member.solutionsThatThisMemberDominates.contains(memberToBeEvaluated))
                memberToBeEvaluated.rawFitness += member.strength;
        }
    }

    public void calculateDensity(Member member, Population union, int indexOfMatrix)
    {
        calculateDistanceBetweenMembers(member,union, indexOfMatrix);
    }

    public void calculateDistanceBetweenMembers(Member member,Population union,int indexOfMatrix)
    {
        //todo ver se o membro no indice da matriz eh o mesmo membro do que foi passado por paramentro
        Member mi = union.population.get(indexOfMatrix), mj;
        for (int j = 0; j < distanceMatrix.columns; j++)
        {
            mj = union.population.get(j);
            distanceMatrix.distance[indexOfMatrix][j] = Utils.euclidianDistance(mi,mj);
        }
    }

    public double calculateSigma(Member member)
    {
        double sigma = 0;
        int positionOfsigma = (int) Math.floor(Math.sqrt((double)distanceMatrix.columns));
        //todo ordenar a linha de posicao na matriz
        //todo o sigma é o valor na positionOfSigma

        return sigma;
    }

    public void calculateFitness(Population union)
    {
        int indexOfMatrix=0;
        for(Member member: union.population)
        {
            calculateStrength(member);
            calculateRawFitness(member, union);
            calculateDensity(member, union, indexOfMatrix);        //todo implementar
            member.fitness = member.rawFitness + member.density;
            indexOfMatrix++;
        }
    }


}
