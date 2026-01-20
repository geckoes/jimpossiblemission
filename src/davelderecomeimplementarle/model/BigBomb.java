package impossiblemission.model;

public class BigBomb extends Enemy
{

    public BigBomb(int x, int y, String nome, int velocita)
    {
        super(x, y, nome, velocita);
    }

    @Override
    public void attacca()
    {
        // TODO Auto-generated method stub
    }

    public void muoviti(Direzione d) 
    {
        switch(d)
        {
            case DESTRA: case SINISTRA: super.muoviti(d); break;
            case ALTO: y -= getVelocita(); break;
            case BASSO: y += getVelocita(); break;
        }
    }
    
    public void muoviti(Spy p) {
        muoviti(p.x > this.x ? Direzione.DESTRA : Direzione.SINISTRA);
        muoviti(p.y> this.y ? Direzione.ALTO : Direzione.BASSO);
    }
}
