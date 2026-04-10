package jimpossiblemission.model.entity.strategy;

/**
 * Class for attack strategies
 * 
 * @author Filippo Taiuti
 *
 */
public class AttackStrategies
{
    /**
     * Transforms string in attackStrategy
     * 
     * @param attackStrategy String
     * @return attackStrategy AttackStrategy
     */
    public static AttackStrategy getStrategy(String attackStrategy)
    {
        switch (attackStrategy) {
            case "electrictimed":
                return new ElectricTimedAttackStrategy();
            case "electric":
                return new ElectricAttackStrategy();
            case "laser":
                return new LaserAttackStrategy();
            default:
                return new NoAttackStrategy();
        }
    }
}
