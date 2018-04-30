import java.util.HashMap;

/**
 * This class is to process templar Architecture.
 */
public class templarArch extends roboticsFac {
    int type;
    public templarArch(HashMap<Integer, Integer> totalmap, int newId, int initialId,
                       int constructTime, int currTime,
                       int totalMinerals, int totalGas,
                       int spendingCost, int spendingGas,
                       int type) {
        super(totalmap, newId, initialId, constructTime, currTime, totalMinerals, totalGas, spendingCost, spendingGas);
        this.type = type;
        // type: 1.Templar Archives and Dark Shrine 2. Robotics Bay 3. Fleet Beacon
    }

    /**
     * checking whether there exist pylon and twilight Council.
     * @return true presents both exist.
     */
    @Override
    public boolean judgementDependentBuildingExist() {
        if (type == 1) {
            return (judgementPylonExist() && totalMap.containsKey(16001) && (currTime - totalMap.get(16001) >= 50));
        } else if (type == 2) {
            return (judgementPylonExist() && totalMap.containsKey(7001) && (currTime - totalMap.get(7001) >= 65));
        } else {
            return (judgementPylonExist() && totalMap.containsKey(8001) && (currTime - totalMap.get(8001) >= 60));
        }
    }
}
