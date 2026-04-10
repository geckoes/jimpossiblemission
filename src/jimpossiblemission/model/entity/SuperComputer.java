package jimpossiblemission.model.entity;

/**
 * Supercomputer
 * 
 * @author Filippo Taiuti
 *
 */
public class SuperComputer extends Computer
{
    /**
     * Constructor of supercomputer
     * @param x initial x
     * @param y initial y
     */
    public SuperComputer(int x, int y)
    {
        super(x, y);
        typeOfComputer = TypeOfComputer.SuperComputer;
    }

}
