public class Archer extends Player
{

    public Archer()
    {

    }
     public void setStatsOfArcher(String name) {
        this.hp = 30;
        this.critical_chance = 30;
        this.damage = 30;
        this.player_name = name;
        
        //return this;
    }
}
