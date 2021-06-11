
public class Enemy
{
    private double hp;
    private double damage;
    private String enemy_name;
    
    public Enemy()
    {
        this.hp = 300;
        this.damage = 30;
        this.enemy_name = "Monster";
    }
    //decrease enemy's hp after player's hit
    public void decreaseHp(double dmg) {
        if(dmg>this.hp)
        {
            this.hp = 0;
        }
        else
        {
            this.hp = this.hp - dmg;
        }
    }
    //calculate the whole damage of the monster
    public double getPowerOfHit(int diceresult){
        return (damage + (damage*(((double) diceresult)/10)));
    }
    
    public double getHp(){
        return this.hp;
    }
    
    public double getDamage(){
        return this.damage;
    }
    
    public String getName(){
        return this.enemy_name;
    }
}
