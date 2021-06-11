public class Sorcerer extends Player
{

    public Sorcerer()
    {
     
    }
    //set stats of sorcerer player
     public void setStatsOfSorcerer(String name) {
        this.hp = 250;
        this.critical_chance = 10;
        this.damage = 50;
        this.player_name = name;
        
        
    }

}
