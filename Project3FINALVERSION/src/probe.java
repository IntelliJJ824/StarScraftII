import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class is to build the probe.
 * Function includes building, gathering minerals and gas.
 */
public class probe extends typeOfConstruction{
    private int numberOfPatchWorking, numberOfGasWorking;
    private final int PROBE = 1;
    private List<Integer> mineralPatchList = new ArrayList<>();
    private List<Integer> gasList = new ArrayList<>();
    private HashMap<Integer, Boolean> nexusMap = new HashMap<>();

    /**
     * @param totalmap the hash map for the total program.
     * @param newId the newest Id for the probes.
     * @param initialId the original id for probes catalog.
     * @param constructTime the time need to be used to built.
     * @param currTime current time.
     * @param totalMinerals all the minerals.
     * @param totalGas all the gas.
     * @param mineralPatchList mineral patch situation.
     * @param numberOfPatchWorking the number of patch working.
     * @param gasList gas situation.
     * @param numberOfGasWorking number of gas working.
     */
    public probe (HashMap<Integer, Integer>  totalmap, int newId, int initialId,
                  int constructTime, int currTime,
                  int totalMinerals, int totalGas,
                  List<Integer> mineralPatchList, int numberOfPatchWorking,
                  List<Integer> gasList, int numberOfGasWorking,
                  HashMap<Integer, Boolean> nexusMap) {

        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas);

        //given the information for current mineral patch using situation.
        this.mineralPatchList.addAll(mineralPatchList);
        this.numberOfPatchWorking = numberOfPatchWorking;

        //given the information for current gas assimilator using situation.
        this.gasList.addAll(gasList);
        this.numberOfGasWorking = numberOfGasWorking;

        //update the nexus map.
        this.nexusMap.putAll(nexusMap);
    }

    /**
     * This method is to print out the action that user might select.
     */
    public void printActionSelection() {
        System.out.println(
                "> Select one of the options you want probe to do: \n"
                + "a. Build new probe, "
                + "b.Assign to gather minerals c. Assign to gather gas. d. Nothing/Esc");
    }

    /**
     * This method is to process the user specific action.
     * @param actionInput the task to be assigned.
     * @param amount the number of more workers to be assigned.
     */
    public void processActionInput(String actionInput, String amount) {
        numberOfAction = Integer.parseInt(amount);
        switch (actionInput.toLowerCase()) {
            case("a"):
                if (constructionJudgement()) {
                    if (checkFacilityAvailable(nexusMap)) {     //one nexus can build one probe each time.
                        totalMinerals = totalMinerals - 50 * numberOfAction;
                        setComplexBuilding(nexusMap);
                        System.out.println("+++ Constructing the number of new probe " + numberOfAction + "...");
                    } else {
                        System.out.println("--Invalid: Nexus has been already used or not enough to build such an amount of probes.");
                    }
                } else {
                    System.out.println("--Invalid: Minerals are not enough!!!");
                }
                break;

            case("b")://assign to gather minerals
                if (assignPatchJudgement()) { //not excess patches limitation.

                    // 1. if the number of free probes go to work 2. else if switching part of the probes 3. Invalid
                    if (constructingUnitJudgement()) {
                        setGatherMinerals();
                        System.out.println("-> Assign to gather minerals...");

                    } else if(switcherJudgement(numberOfGasWorking)) { //set part of the worker to patch from gas.
                        setGatherMinerals();
                        setPartProbesToPatch();
                        System.out.println("->> switching part of or the whole of probe(s) working "
                                + "from gas to minerals patch...");

                    } else { //not enough available.
                        System.out.println("--Invalid: Not enough available probes for you to this assignment. "
                                + "(building or waiting probes to be constructed firstly.)");
                    }
                } else {    //excess
                    System.out.println("--Invalid: Adding too much probes to gather minerals "
                            + "(excess limitation for all the patches).");
                }
                break;

            case("c"):
                //check: 1. assimilator exist 2. enough probes available 3. excess the limitation.

                if (checkFacility()) { //this is to check whether satisfy the requirement. yes!
                    if (assignGasJudgement()) {  //probes did not excess the limitation.

                        // 1. if the number of free probes go to work 2. else if switching part of the probes 3. Invalid
                        if (constructingUnitJudgement()) {
                            setGatherGas(numberOfAction);                //set all the free probes to gas as user require.
                            System.out.println("-> Assign to gather gas...");

                        } else if (switcherJudgement(numberOfPatchWorking)) { //assign from patch to minerals
                            setGatherGas(numberOfAction);
                            setPartProbesToGas();
                            System.out.println("->> switching part or the whole of probe(s) working "
                                    + "from minerals patch to gas...");
                        } else {
                            System.out.println("--Invalid: not enough available probes.");
                        }

                    } else {
                        System.out.println("--Invalid: the amount of working for assimilator excesses the limitation.");
                    }
                } else {
                    System.out.println("--Invalid: firstly constructed or waited the ASSIMILATOR.");
                }
                break;
            default:
                break;
        }
    }

    /**
     * This is the method to set part of the probes to patch.
     */
    public void setPartProbesToPatch() {
        int freeProbes = available - numberOfGasWorking - numberOfPatchWorking;
        int switchProbes = numberOfAction - freeProbes;
        for (int i = 0; i < switchProbes; i++) {
            if (gasList.get(0) > 0) {
                int workForce = gasList.get(0) - 1;
                gasList.set(0, workForce);
            } else {
                int workForce = gasList.get(1) + 1;
                gasList.set(1, workForce);
            }
        }
    }

    /**
     * This method is to set part of the probes to work from patch to gas.
     */
    public void setPartProbesToGas() {
        int freeProbes = available - numberOfPatchWorking - numberOfGasWorking;
        int switchProbes = numberOfAction - freeProbes;
        for (int i = 0; i < switchProbes; i++) {
            Object obj = Collections.max(mineralPatchList);
            int maximum = (int) obj;
            int position = mineralPatchList.indexOf(maximum);
            maximum = maximum - 1; //set part of probes to gather gas
            mineralPatchList.set(position, maximum);
        }
    }

    /**
     * This method is to switch a part of the other side probes, and use the whole free probes to finish the task.
     * available - number of Gas working - number of Patch Working = the number of free probes.
     * @param numberOfOtherSide the opposite palace of probes working.
     * @return true presents it works.
     */
    public boolean switcherJudgement(int numberOfOtherSide) {
            return (numberOfOtherSide + (available - numberOfGasWorking - numberOfPatchWorking) >= numberOfAction);
    }

    /**
     * This method is to check whether there is assimilator to begin gathering gas.
     * @return true present that there exists, and it is finished.
     */
    public boolean checkFacility() {
        return (totalMap.containsKey(4001) && (currTime - totalMap.get(4001) >= 30));
    }

    /**
     * This method  is to judge whether the total minerals are enough for constructing the probes
     * @return true presents it is valid construction.
     */
    public boolean constructionJudgement() {
        return (numberOfAction * 50 <= totalMinerals);
    }

    /**
     * This is to judge whether the probes assigned to work excess the limitation of the patches.
     * @return true presents not excess.
     */
    public boolean assignPatchJudgement() { //there are 8 mineral patches, and up to 3 probes for each patch.
        return (numberOfAction + numberOfPatchWorking <= 3 * mineralPatchList.size() );
    }

    /**
     * This method is to make a judgement whether it is overload.
     * @return true presents it works.
     */
    public boolean assignGasJudgement() {
            return (numberOfAction + numberOfGasWorking <= gasList.size() * 3);
    }

    /**
     * This method is to judge whether there are enough available probes to be assigned to gather minerals.
     * @return true represent it is enough available.
     */
    public boolean constructingUnitJudgement() {
        return ( numberOfGasWorking + numberOfPatchWorking + numberOfAction <= available);
    }


    /**
     * This method is to set the probes to gather the minerals.
     */
    public void setGatherMinerals() {
        //find the minimum value for the array list.
        for (int i = 0; i < numberOfAction; i++) {
            Object obj = Collections.min(mineralPatchList);
            int minimum = (int) obj;
            int position = mineralPatchList.indexOf(minimum);   //get the position of the minimum value.
            minimum ++;
            mineralPatchList.set(position, minimum);
        }
//        System.out.println(mineralPatchList);
    }

    /**
     * This method is to set the probes to gather the gas.
     */
    public void setGatherGas(int numberAction) {
        for (int i = 0; i < numberAction; i++) {
            if (gasList.get(0) >= 3) { //if the first gas assimilator is full, go to the next one.
                int workForce = gasList.get(1) + 1;
                gasList.set(1, workForce);
            } else {
                int workForce = gasList.get(0) + 1;
                gasList.set(0, workForce);
            }
        }
    }


    //The following is getter method.
    /**
     * This method is to get the Mineral Patch list.
     * @return Mineral Patch List.
     */
    public List<Integer> getMineralPatchList() {
        return mineralPatchList;
    }

    /**
     * This method is to get the newest gas working list.
     * @return the gas list.
     */
    public List<Integer> getGasList() {
        return gasList;
    }

    public HashMap<Integer, Boolean> getNexusMap() {
        return nexusMap;
    }
}
