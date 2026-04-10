package jimpossiblemission.model.entity;

/**
 * Class Computer, player can interact to it to send commands to game
 * 
 * @author Filippo Taiuti
 *
 */
public class Computer extends GameObject
{
    protected TypeOfComputer typeOfComputer;

    /**
     * Represents the type of computer in game session 
     * @author Filippo Taiuti
     *
     */
    public enum TypeOfComputer
    {
        Computer, SuperComputer
    }


    /**
     * Constructor of Computer
     * 
     * @param x initial x
     * @param y initial y
     */
    public Computer(int x, int y)
    {
        super(x, y);
        collider = new BoxCollider(this);
        typeOfComputer = TypeOfComputer.Computer;
    }

    /**
     * Return the typo of computer
     * @return typeOfComputer
     */
    public TypeOfComputer getTypeOfComputer()
    {
        return typeOfComputer;
    }

}
