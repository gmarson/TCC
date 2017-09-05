package Problems;

import SupportingFiles.Constants;
import SupportingFiles.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 04/04/17.
 */
public class KnapsackItem {

    public double weight;
    public ArrayList<Double> attributes = new ArrayList<>(); //lucro e por ai vai

    private double MAX_ATTRIBUTE_VALUE_FOR_ITEM = 10;
    private double MIN_ATTRIBUTE_VALUE_FOR_ITEM = 1;
    private double MAX_WEIGHT = 100;
    private double MIN_WEIGHT = 1;

    KnapsackItem()
    {
        this.weight =  Utils.getRandom((int)MIN_WEIGHT,(int)MAX_WEIGHT);
        this.createAttributesOfItems();
    }

    KnapsackItem(ArrayList<Double> objectivesOfItem, Double weight) {
        this.weight = weight;
        this.attributes = new ArrayList<>(objectivesOfItem);
    }


    private void createAttributesOfItems()
    {
        for (int i = 0; i < Constants.PROBLEM_SIZE ; i++) {
            this.attributes.add(i,(double)Utils.getRandom((int)MIN_ATTRIBUTE_VALUE_FOR_ITEM,(int)MAX_ATTRIBUTE_VALUE_FOR_ITEM));
        }
    }

    void printItem()
    {
        System.out.println("Peso: "+this.weight);
        for (int i = 0; i < attributes.size(); i++) {
            System.out.println("Atributo "+i+" = "+attributes.get(i)+"\n");
        }
    }

}
