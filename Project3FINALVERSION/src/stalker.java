import java.util.HashMap;

/**
 * This class is to process the stalker, and it is abandoned.
 */
public class stalker extends zealot {
    public stalker(HashMap<Integer, Integer> totalmap, int newId, int initialId, int constructTime,
                   int currTime,
                   int totalMinerals, int totalGas,
                   int spendingCost, int spendingGas,
                   HashMap<Integer, Boolean> gateWayMap,
                   int type) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas,
                spendingCost, spendingGas, gateWayMap, type);
    }

    /**
     * This method is over written because the cybernetics core center need to be judged.
     * @return true presents both exist.
     */
    @Override
    public boolean judgementDependentBuilding() {
        return (super.judgementDependentBuilding() && judgeCoreExist());
    }
}
