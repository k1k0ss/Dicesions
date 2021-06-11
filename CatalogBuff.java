import java.util.*;
import javax.swing.*;
import java.awt.*;

public class CatalogBuff
{
    private static ArrayList<BuffElement> buffs = new ArrayList<BuffElement>(); //List of all buff elements

    public CatalogBuff()
    {}
    
    //add element to the list
    public static void addBuff(BuffElement b){
        buffs.add(b);
    }
    
    //get random element of the list
    public static String getElementName(){
        Random r = new Random();
        int result;
        
        result = r.nextInt(9);
        return buffs.get(result).getName();
    }
    
    //get certain element by its position on the list
    public static BuffElement getElementByNumber(int x){
        if(x >= 9)
        {
            return null;
        }
        else
        {
            return buffs.get(x);
        }
    }
    
    //get element by its name
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
    
    //get the position of an element in the list
    public static int getPosition(String name){
        int position = -1; //No element yet
        for(int i=0; i<buffs.size(); i++)
        {
            if(buffs.get(i).getName().equals(name))
            {
                position = i;
            }
        }
        
        return position;
    }
    
    //get element by its kind (HP,Damage etc)
     public static BuffElement getElementB(String kind){
        int position = -1; //No element yet
        for(int i=0; i<buffs.size(); i++)
        {
            if(buffs.get(i).getKind().equals(kind) && buffs.get(i).getAvailable() == true)
            {
                position = i;
            }
        }
        if(position != -1)
        {
            return buffs.get(position);
        }
        else
        {
            return null;
        }
    }
    
    //get the kind of an element by its name
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
    
    //get the value of an element by its name
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
