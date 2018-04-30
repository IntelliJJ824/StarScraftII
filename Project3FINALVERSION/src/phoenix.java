import java.util.HashMap;

/**
 * This class is to process phoenix.
 * void ray share the similar situation.
 */
public class phoenix  extends typeOfConstruction {

    int type;
    private HashMap<Integer, Boolean> starGateMap = new HashMap<>();
    public phoenix(HashMap<Integer, Integer> totalmap, int newId, int initialId,
                   int constructTime, int currTime,
                   int totalMinerals, int totalGas,
                   int spendingCost, int spendingGas,
                   HashMap<Integer, Boolean> starGateMap,
                   int type) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas);
        this.spendingCost = spendingCost;
        this.spendingGas = spendingGas;
        this.starGateMap.putAll(starGateMap);
        this.type = type;
    }

    /**
     * This method is to process the user's input.
     * @param amount the number of units need to be constructed.
     */
    public void processActionInput(String amount) {
        numberOfAction = Integer.parseInt(amount);
        if (judgementDependentBuilding()) {
            if (checkFacilityAvailable(starGateMap)) {
                if (constructionCurrencyJudgement()) {
                    deduceCurrency();
                    setComplexBuilding(starGateMap);
                    System.out.println("");
                    System.out.println("+++ Constructing the number of new unit(s)" + numberOfAction + "...");
                } else {
                    System.out.println("--Invalid: The minerals or gas is not enough.");
                }
            } else {
                System.out.println("--Invalid: The Stargate has been used or not enough to build other unit.");
            }
        } else {
            System.out.println("--Invalid: firstly construct the DEPENDENT building.");
        }
    }

    /**
     * make a judgement whether there exist star gate and other facility.
     * @return yes presents exist.
     */
    public boolean judgementDependentBuilding() {
        if (totalMap.containsKey(8001) && (currTime - totalMap.get(8001) >= 60)) {
            if (type == 2) { //if it is Carrier.

                if (totalMap.containsKey(20001) && (currTime - totalMap.get(20001) >= 60)) {
                    return true;//if the robotics bay exist.
                } else { return false; }

            } else { return true; } //pass for phoenix and void ray.
        } else { //if there is not stargate.
            return false;
        }
    }

    /**
     * Process the situation of the star gate
     * @return the sta gate situation.
     */
    public HashMap<Integer, Boolean> getStarGateMap() {
        return starGateMap;
    }
}
