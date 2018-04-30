import java.util.HashMap;

/**
 * This class is to process assimilator, pylon and nexus.
 * They are the same types.
 * And the function include the amount of assimilator cannot excess the amount of gas geysers.
 */
public class assimilator extends typeOfConstruction {
    //This is the spending cost for assimilator or pylon.
    int spendingCost;
    int nexusAmount;
    public assimilator(HashMap<Integer, Integer> totalmap, int newId, int initialId,
                       int constructTime, int currTime,
                       int totalMinerals, int totalGas,
                       int spendingCost,
                       int nexusId, int nexusOriginalId) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas);
        this.spendingCost = spendingCost;
        nexusAmount = nexusId - nexusOriginalId;
    }

    /**
     * This method is to set the assimilator for the program.
     * @param amount the number of assimilator or pylon need to be added.
     */
    public void processActionInput(String amount) { // only selection for building.
        numberOfAction = Integer.parseInt(amount);
        if (assimilatorConstructionJudgement()) {
            if ((initialId < 4000) || assimilatorLimitationJudge()) {
                totalMinerals = totalMinerals - spendingCost * numberOfAction;
                setBuilding();
                System.out.println("+++ Constructing the number of new building " + numberOfAction + "...");
            } else {
                System.out.println("--Invalid construction: not enough gas geysers to build assimilator.");
            }
        } else {
            System.out.println("--Invalid construction, Minerals are not enough!!!");
        }
    }

    /**
     * This is to judge whether the total minerals are enough for constructing the assimilator.
     * @return true presents it is valid.
     */
    public boolean assimilatorConstructionJudgement() {
        return (numberOfAction * spendingCost <= totalMinerals);
    }

    /**
     * This method is to judge whether the number of assimilator construction excess the gas geysers.
     * @return true presents not excess.
     */
    public boolean assimilatorLimitationJudge() {
        return (numberOfAction + newId - initialId <= nexusAmount * 2);
    }
}
