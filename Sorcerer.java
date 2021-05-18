public class Sorcerer extends Player
{

    public Sorcerer()
    {
     
    }
     public void setStatsOfSorcerer(String name) {
        this.hp = 10;
        this.critical_chance = 10;
        this.damage = 10;
        this.player_name = name;
        
        //return this;
    }

}
