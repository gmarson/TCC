import java.util.ArrayList;

/**
 * Created by gmarson on 9/14/2016.
 */
public abstract class Functions {

    private static ArrayList<Function> functions = new ArrayList<Function>();

    public static ArrayList<Function> getInstance() {
        return functions;
    }

    private Functions() {

    }

    public ArrayList<Function> getArray(){return this.functions;}
}
