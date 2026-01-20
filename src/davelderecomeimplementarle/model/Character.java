package impossiblemission.model;

public abstract class Character extends Entity
{

    public enum Direzione
    {
        DESTRA,
        SINISTRA,
        ALTO,
        BASSO
    }
    
    private String nome;
    private int velocita;
    
    public Character(int x, int y, String nome, int velocita)
    {
        super(x, y);
        this.nome = nome;
        this.velocita = velocita;
    }
    
    public String getNome() { return nome; }
    public int getVelocita() { return velocita; }
    
    public void muoviti(Direzione d)
    {
        switch (d)
        {
            case DESTRA: x += velocita; break;
            case SINISTRA: x -= velocita; break;
            // in futuro emetti eccezione!
            default: System.out.println("Direzione non ammessa"); break;
        }
    }
}
