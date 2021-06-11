
public class Main
{

    public static void main(String[] args) {
        //Set buff elements
        
        BuffElement b = new BuffElement("Golden Sword", "Damage", 5, 5);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Silver Shield", "HP", 5, 5);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Sneaky Arrow", "Critical", 5, 5);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Hammer of Thor", "Damage", 10, 10);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Black Castle", "HP", 10, 10);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Master Of Target", "Critical", 10, 10);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Killer Mode", "Damage", 15, 15);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Tank Mode", "HP", 15, 15);
        CatalogBuff.addBuff(b);
        
        b = new BuffElement("Shooter Mode", "Critical", 15, 15);
        CatalogBuff.addBuff(b);
        
        //Start game
        new StartGame();
   }

   
}
