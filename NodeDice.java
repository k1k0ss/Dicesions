import java.util.*;

public class NodeDice
{
    private int dice_result;
    Random r = new Random();
    public NodeDice()
    {}
    
    //roll the dice and return result
    public int roll(){
        dice_result = r.nextInt(6) + 1;
        return dice_result;
    }

}
