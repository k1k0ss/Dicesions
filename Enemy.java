
public class Enemy
{
    private double hp;
    private double damage;
    private String enemy_name;
    
    public Enemy()
    {
        this.hp = 250;
        this.damage = 30;
        this.enemy_name = "Monster";
    }
    
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
    
    public double getPowerOfHit(int diceresult){
        //System.out.println((damage + (damage*(((double) diceresult)/10))));
        return (damage + (damage*(((double) diceresult)/10)));
    }
    
    public double getHp(){
        //System.out.println(this.hp);
        return this.hp;
    }
    
    public double getDamage(){
        return this.damage;
    }
    
    public String getName(){
        return this.enemy_name;
    }
}
