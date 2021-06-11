public class Warrior extends Player
{
    public Warrior()
    {}
    //set stats of warrior player
    public void setStatsOfWarrior(String name) {
        this.hp = 350;
        this.critical_chance = 20;
        this.damage = 30;
        this.player_name = name;
        
        
    }
}
