package SPEA2;

import org.apache.commons.math3.util.Pair;
import java.util.*;

/**
 * Created by gabrielm on 10/16/17.
 * Project : TCC.
 */
public class MutableDistanceMap {

    private List<List<Pair<Integer, Double>>> distanceMatrix;

    public MutableDistanceMap(double[][] rawDistanceMatrix) {
        super();
        initialize(rawDistanceMatrix);
    }

    protected void initialize(double[][] rawDistanceMatrix) {
        distanceMatrix = new LinkedList<List<Pair<Integer, Double>>>();

        for (int i = 0; i < rawDistanceMatrix.length; i++) {
            List<Pair<Integer, Double>> distances = new LinkedList<Pair<Integer, Double>>();

            for (int j = 0; j < rawDistanceMatrix[i].length; j++) {
                if (i != j) {
                    distances.add(new Pair<Integer, Double>(j, rawDistanceMatrix[i][j]));
                }
            }

            Collections.sort(distances, new Comparator<Pair<Integer, Double>>() {

                @Override
                public int compare(Pair<Integer, Double> o1,
                                   Pair<Integer, Double> o2) {
                    return Double.compare(o1.getSecond(), o2.getSecond());
                }

            });

            distanceMatrix.add(distances);
        }
    }


    public int findMostCrowdedPoint() {
        double minimumDistance = Double.POSITIVE_INFINITY;
        int minimumIndex = -1;

        for (int i = 0; i < distanceMatrix.size(); i++) {
            List<Pair<Integer, Double>> distances = distanceMatrix.get(i);
            Pair<Integer, Double> point = distances.get(0);


            if (point.getSecond() < minimumDistance) {
                minimumDistance = point.getSecond();
                minimumIndex = i;
            } else if (point.getSecond() == minimumDistance) {
                for (int k = 0; k < distances.size(); k++) {
                    double kdist1 = distances.get(k).getSecond();
                    double kdist2 = distanceMatrix.get(minimumIndex).get(k).getSecond();

                    if (kdist1 < kdist2) {
                        minimumIndex = i;
                        break;
                    } else if (kdist2 < kdist1) {
                        break;
                    }
                }
            }
        }

        return minimumIndex;
    }


    public void removePoint(int index) {
        distanceMatrix.remove(index);

        for (List<Pair<Integer, Double>> distances : distanceMatrix) {
            ListIterator<Pair<Integer, Double>> iterator = distances.listIterator();

            while (iterator.hasNext()) {
                Pair<Integer, Double> point = iterator.next();

                if (point.getFirst() == index) {
                    iterator.remove();
                } else if (point.getFirst() > index) {
                    // decrement the index so it stays aligned with the
                    // index in distanceMatrix
                    iterator.set(new Pair<Integer, Double>(
                            point.getFirst()-1, point.getSecond()));
                }
            }
        }
    }

}



