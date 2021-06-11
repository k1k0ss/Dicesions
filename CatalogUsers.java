import java.util.*;

public class CatalogUsers
{
    private static ArrayList<User> users = new ArrayList<User>(); //list of registered users
    
    public CatalogUsers()
    {}
    //add new user
    public static void addUser(User u){
        users.add(u);
    }
    //remove user
    public static void removeUser(User u){
        users.remove(u);
    }
    //check if there is a player with this username and email during registration. if there is, then we consider this user as already registered
    public static boolean foundPlayer(String un, String mail) {
        boolean flag = false;
        for(int i = 0; i < users.size(); i++)
        {
            if(users.get(i).getUsername().equals(un) && users.get(i).getEmail().equals(mail))
            {
                flag = true;
            }
        }
        
        return flag;
    }
    //find user with this username and password during log in.
    public static User getUser(String un, String pass){
        for(User user : users)
        {
            if((user.getUsername().equals(un) || user.getEmail().equals(un)) && user.getPassword().equals(pass))
            {
                return user;
            }
        }
        return null;
    }
    //find available registered players to fight with the player who just logged in
    public static ArrayList<User> findOpponents(int x){
        ArrayList<User> opponents = new ArrayList<User>();
        for(int i = 1; i <= x; i++)
        {
            for(int j = 0; j < users.size(); j++)
            {
                if(users.get(j).getAvailable())
                {
                    opponents.add(users.get(j));
                    users.get(j).setAvailable();
                    break;
                }
            }
        }
        return opponents;
    }
}
