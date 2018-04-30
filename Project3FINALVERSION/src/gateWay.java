import java.util.HashMap;

/**
 * This class is to process gateway.
 */
public class gateWay extends typeOfConstruction {

    public gateWay(HashMap<Integer, Integer> totalmap, int newId, int initialId,
                   int constructTime, int currTime,
                   int totalMinerals, int totalGas,
                   int spendingCost) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas);
        this.spendingCost = spendingCost;
    }

    /**
     * This method is to process user's input for establishing gateway.
     * @param amount the total amount that user want to add.
     */
    public void processActionInput(String amount) {
        numberOfAction = Integer.parseInt(amount);

        if (judgementPylonExist()) { //exist pylon, and reach the building time.
            if (gateWayConstructionJudgement()) { //enough minerals for it.
                totalMinerals = totalMinerals - spendingCost * numberOfAction;//deduce the minerals
                setBuilding();
                System.out.println("+++Constructing the number of new building(s) " + numberOfAction + "...");
            } else {
                System.out.println("--Invalid: Minerals are not enough!!!");
            }
        } else {
            System.out.println("--Invalid: firstly construct the DEPENDENT building.");
        }
    }


    /**
     * This method is to whether there is enough minerals to build this building.
     * @return true presents it is enough.
     */
    public boolean gateWayConstructionJudgement() {
        return (numberOfAction * spendingCost <= totalMinerals);
    }
}
