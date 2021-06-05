
public class BuffElement
{
    private String name,kind;
    private int cost;
    private double value;
    private boolean available;
    
    public BuffElement(String n, String k, double v, int c)
    {
        this.name = n;
        this.kind = k;
        this.value = v;
        this.cost = c;
        this.available = true;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getKind(){
        return this.kind;
    }
    
    public double getValue(){
        return this.value;
    }
    
    public int getCost(){
        return this.cost;
    }
    
    public boolean getAvailable(){
        return this.available;
    }


}
