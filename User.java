
public class User
{
    private String username; 
    private String password;
    private String email;
    private int age;
    private boolean available; //shows if the player is in game or he can join the next game
    
    public User()
    {
        available = true;
    }
    
    public void setUsername(String u){
        this.username = u;
    }
    
    public void setPassword(String p){
        this.password = p;
    }
    
    public void setEmail(String e){
        this.email = e;
    }

    public void setAge(int a){
        this.age = a;
    }
    
    public void setAvailable(){
        this.available = !this.available;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public int getAge(){
        return this.age;
    }
    
    public boolean getAvailable(){
        return this.available;
    }
    
    
}
