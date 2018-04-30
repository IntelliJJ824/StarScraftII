import java.util.HashMap;

/**
 * This class is to process cybernetics core.
 */
public class cybernetics extends gateWay {
    public cybernetics(HashMap<Integer, Integer> totalmap, int newId, int initialId,
                       int constructTime, int currTime,
                       int totalMinerals, int totalGas,
                       int spendingCost) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas, spendingCost);
    }

    /**
     * This method add the Judgement whether there exit the gateWay.
     * @return true presents the dependent is satisfied.
     */
    @Override
    public boolean judgementPylonExist() {
        return (super.judgementPylonExist() && totalMap.containsKey(5001) && (currTime - totalMap.get(5001) >= 65));
    }
}
