package jimpossiblemission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Impossible mission model.
 * It contains list of users and database name.
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class ImpossibleMission implements Observer
{
    private static final String DATABASE = "games.db";
    private List<User> users;

    /**
     * Loads user stats from file and notifies observers.
     */
    @SuppressWarnings("unchecked")
    public List<User> loadUsers()
    {
        try
        {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(DATABASE));
            users = (List<User>) stream.readObject();
            stream.close();
        } catch (IOException | ClassNotFoundException e)
        {
            users = new ArrayList<>();
            File file = new File(DATABASE);
            try
            {
                file.createNewFile();
            } catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return users;
    }

    /**
     * Adds a new user to list
     * 
     * @param newUser
     */
    public void addUser(User newUser)
    {
        users.add(newUser);
        saveUsers(users);
    }

    /**
     * Save the users in database
     * 
     * @param users
     */
    private void saveUsers(List<User> users)
    {
        try
        {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(DATABASE));
            stream.writeObject(users);
            stream.close();

        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof User)
            updateUser((User) o);
    }

    private void updateUser(User user)
    {
        if (users.contains(user))
        {
            users.set(users.indexOf(user), user);
        } else
            users.add(user);
        saveUsers(users);
    }

}
