import java.util.ArrayList;
import java.util.*;

public class Player
{
    protected double hp;
    protected double critical_chance;
    protected double damage;
    protected int coins;
    protected String player_name;
    protected int posX,posY;
    protected ArrayList <Player> players = new ArrayList<Player>();
    
    Player()
    {
        hp = 0;
        critical_chance = 0;
        damage = 0;
        coins = 0;
        player_name = null;
    }
    
    
    //set the position of each player (for example if x=1 and y=1 then player steps on the first node (up and right))
    public void setPlayerPosition(int x, int y){
        this.posX = x;
        this.posY = y;
    }
    
    //get the x-coordinate of player
    public int getPosX(){
        return this.posX;
    }
    
    //get the y-coordinate of player
    public int getPosY(){
        return this.posY;
    }
    
    //increase the coins of player by one, at the end of a node
    public void increaseCoins(){
        this.coins++;
    }
    
    //find the stat of player that should be increased, based on the buff
    public void increaseStat(BuffElement b){
        if(b.getKind().equals("HP"))
        {
            this.increaseHP(b.getValue());
        }
        else if(b.getKind().equals("Critical"))
        {
            this.increaseCritical(b.getValue());
        }
        else
        {
            this.increaseDamage(b.getValue());
        }
    }
    
    //increase hp of player by x
    public void increaseHP(double x){
        this.hp = this.hp + x;
    }
    
    //decrease hp of player by x, lower value of hp is 0
    public void decreaseHp(double x) {
        if(x>this.hp)
        {
            this.hp = 0;
        }
        else
        {
            this.hp = this.hp - x;
        }
        
    }
    
    //increase critical of player by x
    public void increaseCritical(double x){
        this.critical_chance = this.critical_chance + x;
        if(this.critical_chance > 50)
        {
            this.critical_chance = 50;
        }
    } 
    
    //increase damage of player by x
    public void increaseDamage(double x){
        this.damage = this.damage + x;
    }
    
    //calculate the whole damage the player will cause to its enemy
    public double getPowerOfHit(int diceresult){
        double wholeDamage = this.damage + (this.damage*(((double) diceresult/10))); //default damage
        Random r = new Random();
        int result = r.nextInt(5) + 1; //probabilities of critical chance
        if(this.critical_chance <= 10)
        {
            if(result == 5)
            {
                wholeDamage = wholeDamage + (this.damage*0.5);
            }
        }
        else if(this.critical_chance <= 20)
        {
            if(result >= 4)
            {
                wholeDamage = wholeDamage + (this.damage*0.5);
            }
        }
        else if(this.critical_chance <= 30)
        {
            if(result >= 3)
            {
                wholeDamage = wholeDamage + (this.damage*0.5);
            }
        }
        else if(this.critical_chance <= 40)
        {
            if(result >= 2)
            {
                wholeDamage = wholeDamage + (this.damage*0.5);
            }
        }
        else 
        {
            wholeDamage = wholeDamage + (this.damage*0.5);
        }
        return wholeDamage;
    }
   
}
