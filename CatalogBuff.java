import java.util.*;

public class CatalogBuff
{
    private static ArrayList<BuffElement> buffs = new ArrayList<BuffElement>();

    public CatalogBuff()
    {}
    
    public static void addBuff(BuffElement b){
        buffs.add(b);
    }
    
    public static void showBuffs(){
        for(int i=0; i<buffs.size(); i++)
        {
            System.out.println(buffs.get(i).getName());
        }
    }
    
    public static String getElementName(){
        Random r = new Random();
        int result;
        
        result = r.nextInt(9);
        return buffs.get(result).getName();
    }
    
    public static BuffElement getElement(String name){
        int position = -1; //No element yet
        for(int i=0; i<buffs.size(); i++)
        {
            if(buffs.get(i).getName().equals(name))
            {
                position = i;
            }
        }
        
        return buffs.get(position);
    }
    
    public static String getElementKind(String name){
        int position = -1; //No element yet
        for(int i=0; i<buffs.size(); i++)
        {
            if(buffs.get(i).getName().equals(name))
            {
                position = i;
            }
        }
        
        return buffs.get(position).getKind();
        
        }
    
    public static double getElementValue(String name){
        int position = -1; //No element yet
        for(int i=0; i<buffs.size(); i++)
        {
            if(buffs.get(i).getName().equals(name))
            {
                position = i;
            }
        }
        
        return buffs.get(position).getValue();
        
    }
}
