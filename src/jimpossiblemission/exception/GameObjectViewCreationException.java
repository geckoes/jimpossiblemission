package jimpossiblemission.exception;

/**
 * Class Exception used during the creation of the GameObject view
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("serial")
public class GameObjectViewCreationException extends Exception
{
    public GameObjectViewCreationException(String errorMessage)
    {
        super(errorMessage);
    }
}
