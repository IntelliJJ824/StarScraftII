import java.util.*;

/**
 * This class is to process observe, immortal.
 */
public class observe extends typeOfConstruction {
    private HashMap<Integer, Boolean> roboticsMap = new HashMap<>();
    int type;
    public observe(HashMap<Integer, Integer> totalmap, int newId, int initialId, int constructTime,
                   int currTime,
                   int totalMinerals, int totalGas,
                   int spendingCost, int spendingGas,
                   HashMap<Integer, Boolean> roboticsMap, int type) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas);
        this.spendingCost = spendingCost;
        this.spendingGas = spendingGas;
        this.roboticsMap.putAll(roboticsMap);
        this.type = type;
    }

    /**
     * This is to process user input for immortal and observer.
     * @param amount the number of units need to be added.
     */
    public void processActionInput(String amount) {
        numberOfAction = Integer.parseInt(amount);
        if (judgementDependentBuilding()) {                 //dependent buildings exist.
            if (checkFacilityAvailable(roboticsMap)) {      //the robotics are available.
                if (constructionCurrencyJudgement()) {      //currency is enough.
                    deduceCurrency();
                    setComplexBuilding(roboticsMap);
                    System.out.println("++Constructing the number of new unit(s)" + numberOfAction + "...");
                } else {
                    System.out.println("Invalid: The minerals or gas is not enough.");
                }
            } else {
                System.out.println("Invalid: The Robotics Facility has been used or not enough to build other unit.");
            }
        } else {
            System.out.println("Invalid: firstly construct the DEPENDENT building.");
        }
    }

    /**
     * Judge whether there is dependent building.
     * @return true presents exist.
     */
    public boolean judgementDependentBuilding() {
        if (totalMap.containsKey(7001) && (currTime - totalMap.get(7001) >= 65)) {
            if (type == 2) {//judge whether it is colossi.

                if (totalMap.containsKey(19001) && (currTime - totalMap.get(19001) >= 65)) {
                    return true;
                } else {
                    return false;
                }

            } else { //observe and immortal will be pass
                return true;
            }
        } else { //do not have robotics facility.
            return false;
        }
    }

    //getter method.
    public HashMap<Integer, Boolean> getRoboticsMap() {
        return roboticsMap;
    }
}
