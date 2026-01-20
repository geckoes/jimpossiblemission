package impossiblemission.model;

public abstract class GameObject extends Entity
{
    private PuzzlePiece tessera;

    public GameObject(int x, int y)
    {
        this(x, y, null);
    }

    public GameObject(int x, int y, PuzzlePiece tessera)
    {
        super(x, y);
        this.tessera = tessera;
    }

    public PuzzlePiece search()
    {
        return tessera;
    }
}
