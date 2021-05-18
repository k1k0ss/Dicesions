import java.util.ArrayList;

public class Player
{
    protected double hp;
    protected double critical_chance;
    protected double damage;
    protected int coins;
    protected String player_name;
    
    Player()
    {
        hp = 0;
        critical_chance = 0;
        damage = 0;
        coins = 0;
        player_name = null;
    }
    
    public void showStats(){
        System.out.println(this.hp);
        System.out.println(this.critical_chance);
        System.out.println(this.damage);
        System.out.println(this.coins);
        System.out.println(this.player_name);
    
    }
    
    /*public void setStatsOfWarrior(String name) {
        this.hp = 50;
        this.critical_chance = 50;
        this.damage = 50;
        this.player_name = name;
        
        //return this;
    } */
    
   
}
