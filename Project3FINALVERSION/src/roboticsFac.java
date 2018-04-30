import java.util.HashMap;

/**
 * This class is to process robotics Facility and StarGate.
 */
public class roboticsFac extends typeOfConstruction {
    public roboticsFac(HashMap<Integer, Integer> totalmap, int newId, int initialId,
                       int constructTime, int currTime,
                       int totalMinerals, int totalGas,
                       int spendingCost, int spendingGas) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas);
        this.spendingCost = spendingCost;
        this.spendingGas = spendingGas;
    }

    /**
     * This method is to conduct the process, and it is main method in this class.
     * @param amount the number need to be added.
     */
    public void processActionInput(String amount) {
        numberOfAction = Integer.parseInt(amount);

        if (judgementDependentBuildingExist()) { //if the dependent buildings exit.
            if (constructionCurrencyJudgement()) { //if the minerals and gas are enough

                totalMinerals = totalMinerals - spendingCost * numberOfAction;
                totalGas = totalGas - spendingGas * numberOfAction;
                setBuilding();
                System.out.println("+++ Constructing the number of new building(s) " + numberOfAction + "...");

            } else {
                System.out.println("--Invalid: not enough minerals or gas!!!");
            }
        } else {
            System.out.println("--Invalid: firstly construct the DEPENDENT building.");
        }
    }

    /**
     * checking whether there exist cybernetics core and pylon.
     * @return true presents there exists.
     */
    public boolean judgementDependentBuildingExist() {
        return (judgementPylonExist() && totalMap.containsKey(6001) && (currTime - totalMap.get(6001) >= 50));
    }

}
