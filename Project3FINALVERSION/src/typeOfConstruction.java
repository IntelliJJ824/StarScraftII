import java.util.*;

public class typeOfConstruction {
    protected List<Integer> changeList = new ArrayList<>();
    protected HashMap<Integer, Integer> totalMap = new HashMap<>();
    protected int newId, initialId, constructTime, currTime, totalMinerals, totalGas, available, constructing,
            numberOfAction, spendingCost, spendingGas;

    /**
     * This method is to pass the value from the situation.
     * @param totalmap the whole map for current game.
     * @param newId the newest Id for the user's selection to control.
     * @param initialId the setting id for the user's selection to control.
     * @param constructTime the required constructing time for the user's selection.
     * @param currTime the entire time for this game.
     * @param totalGas the current total gas.
     * @param totalMinerals the current total minerals.
     */
    public typeOfConstruction (HashMap<Integer, Integer> totalmap, int newId, int initialId, int constructTime,
                               int currTime,
                               int totalMinerals, int totalGas) {
        totalMap.putAll(totalmap);
        this.newId = newId;
        this.initialId = initialId;
        this.constructTime = constructTime;
        this.currTime = currTime;
        this.totalMinerals = totalMinerals;
        this.totalGas = totalGas;
    }

    public typeOfConstruction() {
        System.out.println("Welcome to StarCraftII model and optimizer!");
    }

    /**
     * This method is to print out the situation of user's selection.
     */
    public void printIndivadualSituation() {
        available = 0;
        constructing = 0;
        Set set = totalMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            int id = (int) pair.getKey();
            if (id > initialId && id < initialId + 1000) {  //find out the type of u/b user select.

                int secondBegin = (int) pair.getValue();
                if (currTime - secondBegin >= constructTime || takeoutBasicFacility(id)) {
                    //if the u/b finish constructing, including basic facility at the beginning
                    available++;
                } else {
                    constructing++;
                }
            }
        }
        System.out.print(available + " Available to be used, " + constructing + " Under constructing.\n");
    }


    /**
     * This method is to judge whether it is based facility, because time is different.
     * @param id the buildings or units
     * @return true presents it is a basic building or unit.
     */
    public boolean takeoutBasicFacility(int id) {
        return ((id > 2000 && id <= 2006) || (id == 1001));
    }


    /**
     * This method is used for testing whether the map is passed to the user.
     */
    public void printMap() {
        System.out.println(newId - initialId);
        Set set = totalMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            System.out.println("key is: " + pair.getKey());
        }
    }

    /**
     * The selection whether build or exit.
     */
    public void printGeneralSelection() {
        System.out.println("> Select one of the options you want to do: \na.Build b.Nothing/Esc");
    }

    /**
     * This method is to set the building and input to map.
     */
    public void setBuilding() {
        for (int i = 0; i < numberOfAction; i++) {
            newId ++; //this is to update the id.
            totalMap.put(newId, currTime);
        }
    }

    /**
     * This method is to judge whether it exists pylon, and reach the building time.
     * @return true presents exist
     */
    public boolean judgementPylonExist() {
        return (totalMap.containsKey(3001) && (currTime - totalMap.get(3001) >= 25));
    }

    public boolean judgeCoreExist() {
        return (totalMap.containsKey(6001) && (currTime - totalMap.get(6001) >= 50));
    }

    /**
     * This method is to make a judgement that whether total minerals and gas are enough to build.
     * @return true presents it is enough.
     */
    public boolean constructionCurrencyJudgement() {
        return ( (numberOfAction * spendingCost <= totalMinerals) && (numberOfAction * spendingGas <= totalGas));
    }

    /**
     * This method is to deduce the spending in the currency of minerals and gas.
     */
    public void deduceCurrency() {
        totalMinerals = totalMinerals - spendingCost * numberOfAction;
        totalGas = totalGas - spendingGas * numberOfAction;
    }

    /**
     * This method is to check whether there is enough requirement facility to build the unit.
     * @return true present enough.
     */
    public boolean checkFacilityAvailable(HashMap<Integer, Boolean> facilityMap) {
        int theNumberOfTrue = 0;
        Set set = facilityMap.entrySet();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            boolean available = (boolean) pair.getValue();
            if (available) {
                theNumberOfTrue++;
                changeList.add((int) pair.getKey()); //store the id to the available list.
            }
        }

        //Make a Judgement whether there is available gate Way.
        if (theNumberOfTrue < numberOfAction) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * This method is to build the unit.
     * At the same time, set the gate way situation.
     * complex building is the building that can build units, and there is situation for it.
     */
    public void setComplexBuilding(HashMap<Integer, Boolean> facilityMap) {
        for (int i = 0; i < numberOfAction; i++) {
            newId ++; //this is to update the id.
            totalMap.put(newId, currTime);

            //remove the value and insert the new one.
            System.out.println(changeList.get(i));
            int position = changeList.get(i);
            facilityMap.remove(position);
            facilityMap.put(newId, false);
        }
    }



    //The following code is for getter.

    /**
     * This method is to return the current hash map.
     * @return total hash map.
     */
    public HashMap<Integer, Integer> getTotalMap() {
        return totalMap;
    }

    /**
     * This method is to return the current minerals after controlling.
     */
    public int getTotalMinerals() {
        return totalMinerals;
    }

    /**
     * This method is to get the newest Id forall.
     * @return the current newest Id after the building order.
     */
    public int getNewId() {
        return newId;
    }

    /**
     * This method is to get the total gas.
     * @return the total gas.
     */
    public int getTotalGas() {
        return totalGas;
    }
}
