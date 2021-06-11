public class Archer extends Player
{

    public Archer()
    {

    }
    //set stats of archer player
     public void setStatsOfArcher(String name) {
        this.hp = 200;
        this.critical_chance = 40;
        this.damage = 40;
        this.player_name = name;
        
    }
}
