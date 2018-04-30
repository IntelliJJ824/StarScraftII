import java.util.HashMap;

/**
 * This class is abandoned.
 */
public class roboticsBay extends roboticsFac {
    public roboticsBay(HashMap<Integer, Integer> totalmap, int newId, int initialId,
                       int constructTime, int currTime,
                       int totalMinerals, int totalGas,
                       int spendingCost, int spendingGas) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas, spendingCost, spendingGas);
    }

    @Override
    public boolean judgementDependentBuildingExist() {
        return (judgementPylonExist() && totalMap.containsKey(7001) && (currTime - totalMap.get(7001) >= 65));
    }
}
