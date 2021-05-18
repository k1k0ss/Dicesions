public class Warrior extends Player
{
    public Warrior()
    {}

    public void setStatsOfWarrior(String name) {
        this.hp = 50;
        this.critical_chance = 50;
        this.damage = 50;
        this.player_name = name;
        
        //return this;
    }
}
