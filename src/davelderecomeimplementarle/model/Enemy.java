package impossiblemission.model;

public abstract class Enemy extends Character
{

    public Enemy(int x, int y, String nome, int velocita)
    {
        super(x, y, nome, velocita);
    }
    
    abstract public void attacca();

}
