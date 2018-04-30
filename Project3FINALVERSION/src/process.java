import java.io.InputStreamReader;
import java.util.*;

/**
 * Design: instead of using the real name of every units or building,
 * in this program, an id which is began with 1000 is given.
 * The id is increased with every 1000 values.
 * The initial list is to compare with the idList in order to find out the number of units and buildings.
 * hash map is to store the key for id, and value for time, which is individual list.
 * The id0 list is the type list.
 */
public class process {
    private Timer totalTimer;
    protected static int secondsTotal = 0;
    private final int PERIOD = 1000;
    protected static int totalMinerals;
    protected static int totalGas;
    boolean gateWayUpdate = false;

    //research time.
    private int wrapGateResearchTime = 0;

    //The speed of gathering minerals.
    protected int numberMaxVelocity, numberMinVelocity, totalNumberPatchWorking;

    //The speed of gathering gas.
    protected int totalNumberGasWorking;


    //This is buildings.
    static final int NEXUS = 0;
    static final int PROBE = 1;
    static final int PYLON = 2;
    static final int ASSIMILATOR = 3;
    static final int GATEWAY = 4;
    static final int CYBERNETIC = 5;
    static final int ROBOTIC = 6;
    static final int STARGATE = 7;
    //This is unit.
    static final int ZEALOT = 8;
    static final int STALKER = 9;
    static final int SENTRY = 10;
    static final int OBSERVER = 11;
    static final int IMMORTAL = 12;
    static final int PHOENIX = 13;
    static final int VOID_RAY = 14;

    //extend specification buildings.
    static final int COUNCIL = 15;
    static final int TEMPLAR_ARCHIVES = 16;
    static final int DARK_SHRINE = 17;
    static final int ROBOTICS_BAY = 18;
    static final int FLEET_BEACON = 19;
    //extend specification units.
    static final int COLOSSI = 20;
    static final int HIGH_TEMPLAR = 21;
    static final int DARK_TEMPLAR = 22;
    static final int CARRIER = 23;
    //extension
    static final int WARP_GATE = 24;


    //This is for Mineral patch.
    static final int mineralPatch1 = 0;
    static final int mineralPatch2 = 1;
    static final int mineralPatch3 = 2;
    static final int mineralPatch4 = 3;
    static final int mineralpatch5 = 4;

    //this is to store the type of the unit, and its newest ID.
    protected ArrayList<Integer> idList = new ArrayList<>();
    protected ArrayList<Integer> initialList = new ArrayList<>();

    // this is to store all the units and building.
    protected HashMap<Integer, Integer> totalmap = new HashMap<>();

    //total gate way with the situation.
    protected HashMap<Integer, Boolean> gateWayMap = new HashMap<>();

    //total robotics facility with the situation.
    protected HashMap<Integer, Boolean> roboticsMap = new HashMap<>();

    //total stargate facility with the situation.
    protected HashMap<Integer, Boolean> starGateMap = new HashMap<>();

    //total nexus facility with the situation.
    protected HashMap<Integer, Boolean> nexusMap = new HashMap<>();


    //This is the list to convert from number to specific name.
    protected String[] convertList = {
            "NEXUS", "PROBE", "PYLON", "ASSIMILATOR", "GATEWAY", "CYBERNETICS CORE", "ROBOTICS FACILITY", "STARGATE",
            "ZEALOT", "STALKER", "SENTRY", "OBSERVER", "IMMORTAL", "PHOENIX", "VOID RAY",
            "TWILIGHT COUNCIL", "TEMPLAR ARCHIVES", "DARK SHRINE", "ROBOTICS BAY", "FLEET BEACON",
            "COLOSSI", "HIGH TEMPLAR", "DARK TEMPLAR", "CARRIER", "WARP GATE"};

    protected int[] timeList = {
            100, 17, 25, 30, 65, 50, 65, 60, //This is for buildings and probe.
            38, 42, 37, 40, 55, 35, 60, //This is for units
            50, 50, 100, 65, 60, //buildings after extending the spec
            75, 55, 55, 120, //units.
            10

    };

    protected int[] gasCostList = {
            0, 0, 0, 0, 0, 0, 100, 150, //This is for building and probe.
            0, 50, 100, 75, 100, 100, 150,
            100, 200, 250, 200, 200,//buildings after extending spec.
            200, 150, 125, 250, 0
    };

    protected int[] mineralsCostList = {
            400, 50, 100, 75, 150, 150, 200, 150,//This is for building and probes.
            100, 125, 50, 25, 250, 150, 250,    //This is for units.
            150, 150, 100, 200, 300,
            300, 50, 125, 350, 0 //units for extension.
    };

    //This is the list of mineral patch, 0 presents there is no probe working for it.
    private List<Integer> mineralPatchList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    //This is the list of gas, 0 presents there is no probe working for it.
    private List<Integer> gasList = new ArrayList<>();


    /**
     * This is to start the game.
     */
    public void runTheGame() {
        totalTimer = new Timer();
        start();
    }


    /**
     * This is the beginner of this game, and this method is to start the timer as well as the basic facilities.
     */
    public void start() {
        setIdList();
        buildBase();
        assignMineralBeginning();
        totalTimer.schedule(totalTimeCalculation, 1000, PERIOD);
        totalTimer.schedule(addMinerals, 2000, PERIOD); //The mineral calculation begins after 1 second.
        totalTimer.schedule(addGas, 2000, PERIOD);
    }

    /**
     * This method is to declare all units and buildings with their id.
     */
    public void setIdList() {
        int id = 1000;
        for (int i = 0; i < 25; i++) {
            idList.add(id);
            initialList.add(id);
            id += 1000;
        }
    }

    /**
     * This method is to build the basic army for requirement 6 probes and 1 nexus.
     */
    public void buildBase() {
        //This is to set the six probes at the beginning.
        for (int i = 0; i < 6; i++) {
            int probeNo = idList.get(PROBE) + 1;
            idList.set(PROBE, probeNo);
            totalmap.put(idList.get(PROBE), secondsTotal);
        }

        //This is to set the nexus.
        int nexusNo = idList.get(NEXUS) + 1;
        idList.set(NEXUS, nexusNo);
        nexusMap.put(nexusNo, true); //set the nexus list at the beginning.
        totalmap.put(idList.get(NEXUS), secondsTotal);

        //This is to set the minerals and gas.
        totalMinerals = 50;
        totalGas = 0;
    }

    //printer and timing calculation.
    /**
     * this method is to calculate the total time in seconds.
     */
    TimerTask totalTimeCalculation = new TimerTask() {
        @Override
        public void run() {
            secondsTotal++;
        }
    };

    /**
     * This method is to print out the current situation.
     */
    public void printSituation() {
        System.out.println("Minerals are: " + totalMinerals + " Vespene gas is: " + totalGas);
        String situation = "";
        for (int i = 0; i < idList.size(); i++) {
            int amount = idList.get(i) - initialList.get(i);
            if (amount > 0) {
                int character = initialList.get(i) / 1000 - 1;//This is to get the units/buildings in the array.
                situation = situation + amount + " " + convertList[character] + " ";
            }
        }
        System.out.println(situation);
    }

    /**
     * This is the method to assign probes working for minerals automatically.
     * The first mineral patch is assigned 2.
     * The rest of them are assigned 1 probe.
     */
    public void assignMineralBeginning() {
        mineralPatchList.set(mineralPatch1, 1);

        for (int i = 0; i < 5; i++) {       //set the probes to the current minerals patch.
            int numberWorking = mineralPatchList.get(i) + 1;
            mineralPatchList.set(i, numberWorking);
        }

        MineralCalculator();
    }

    /**
     * This is to print out the minerals and gas.
     */
    public void printCurrency() {
        System.out.println("**** Minerals are: " + totalMinerals + " Vespene gas is: " + totalGas +" ****");
    }

    /**
     * This is to calculate the number of maximum speed of gathering and also on the otherwise.
     */
    public void MineralCalculator() {
        //reset all the value and calculate again.
        numberMaxVelocity = 0;
        numberMinVelocity = 0;
        totalNumberPatchWorking = 0;

        for (int i = 0; i < mineralPatchList.size(); i++) {
            int numberOfProbes =  mineralPatchList.get(i);
            if (numberOfProbes > 0 && numberOfProbes < 3) {     //first two probes to a mineral patch
                numberMaxVelocity = numberMaxVelocity + numberOfProbes;
            }
            if (numberOfProbes == 3) {      //The third probe to one mineral patch.
                numberMinVelocity++;
            }
        }
        //work out the total patch situation.
        totalNumberPatchWorking = numberMinVelocity + numberMaxVelocity;
    }

    /**
     * This method is to calculate total minerals need to be added every time.
     */
    TimerTask addMinerals = new TimerTask() {
        @Override
        public void run() {
            double minerals = (41.0 / 60.0 * numberMaxVelocity) + (20.0 / 60.0 * numberMinVelocity);
            int addMinerals = (int) minerals;
            totalMinerals = totalMinerals + addMinerals;

        }
    };

    /**
     * This method is to calculate the number of probes working for gathering gas.
     */
    public void gasCalculator() {
        totalNumberGasWorking = 0;
        if (!gasList.isEmpty()) {
            for (int element : gasList) {
                totalNumberGasWorking += element;
            }
        }
    }

    /**
     * This method is to calculate total gas to be added every second.
     */
    TimerTask addGas = new TimerTask() {
        @Override
        public void run() {
            double gas = (38.0 / 60.0 * totalNumberGasWorking);
            int addGas = (int) Math.round(gas);
            totalGas = totalGas + addGas;
        }
    };

    /**
     * This method is to find the free probes.
     * @return the number of free probes.
     */
    public int findNothingTaskProbes() {
        int id = initialList.get(PROBE) + 1;
        int freeProbes = 6;
        for (int i = initialList.get(PROBE) + 7; i <= idList.get(PROBE); i++) {//total times = total number of probes
                int value = totalmap.get(id);
                if (secondsTotal - value >= timeList[PROBE]) {
                    freeProbes++;
                }
        }
        freeProbes = freeProbes - totalNumberGasWorking - totalNumberPatchWorking;
        return freeProbes;
    }

    /**
     * This method is the main method for this program.
     * @param input The selection from user about what type of unit or building he want to control.
     */
    public void setUserInput(String input) {
        Scanner reader = new Scanner(new InputStreamReader(System.in)); //This is to scan user's action input.
        printCurrency();
        switch (input.toLowerCase()) {
            case("a"):
                updateNexusMap();
                System.out.println(nexusMap);
                System.out.println(convertList[NEXUS] + ": ");
                assimilator nexusSelection = new assimilator(
                  totalmap, idList.get(NEXUS), initialList.get(NEXUS),
                  timeList[NEXUS], secondsTotal,
                        totalMinerals, totalGas,
                        400,
                        idList.get(NEXUS), initialList.get(NEXUS));
                nexusSelection.printIndivadualSituation();
                nexusSelection.printGeneralSelection();
                String actionNex = reader.next();

                if (actionNex.toLowerCase().equals("a")) {
                    System.out.println("How many Nexus do you want to add?");
                    String amount = reader.next();
                    nexusSelection.processActionInput(amount);
                    int times = nexusSelection.getNewId() - idList.get(NEXUS);

                    if (times > 0) { //get update if the new Nexus is added.
                        //update nexus map.
                        setTotalFacilityUnavailable(times, idList.get(NEXUS), nexusMap);
                        //update the hash map.
                        totalmap.putAll(nexusSelection.getTotalMap());
                        //get new id.
                        idList.set(NEXUS, nexusSelection.getNewId());
                        //get current minerals.
                        totalMinerals = nexusSelection.getTotalMinerals();
                        //add the patch list (extensions).
                        increasePatchList(times);
                        System.out.println(mineralPatchList);
                    }

                }
                break;

            case("b"):
                updateNexusMap();
                probe probeSelection = new probe(totalmap, idList.get(PROBE), initialList.get(PROBE),
                        timeList[PROBE], secondsTotal,
                        totalMinerals, totalGas,
                        mineralPatchList, totalNumberPatchWorking,
                        gasList, totalNumberGasWorking,
                        nexusMap);
                //print out the situation.
                System.out.print(convertList[PROBE] + ": ");
                probeSelection.printIndivadualSituation();
                //print out the minerals patch and gas working situation.
                System.out.println("--There are " + totalNumberPatchWorking + " probes gathering minerals, "
                        + totalNumberGasWorking + " gathering gas. "
                        + findNothingTaskProbes() + " nothing to do.");

                //try to prompt user input the action for his selection.
                probeSelection.printActionSelection();
                String actionInput = reader.next();

                //try to find out the amount of this action.
                if (!actionInput.equals("d")) {
                    System.out.println("How many probe(s) do you want to add to take this action?");
                    String amount = reader.next();
                    //process the user input.
                    probeSelection.processActionInput(actionInput, amount);
                }

                int timesPro = probeSelection.getNewId() - idList.get(PROBE);
                if (timesPro > 0) {
                    updateHashMapTotalAfterUnit(probeSelection.getNexusMap(), nexusMap);
                    //get new Id
                    idList.set(PROBE, probeSelection.getNewId());
                    //get new map
                    totalmap.putAll(probeSelection.getTotalMap());
                    //get new currentMinerals
                    totalMinerals = probeSelection.getTotalMinerals();
                }

                    //get new minerals patch list
                    renewPatchList(probeSelection.getMineralPatchList());
                    MineralCalculator();
                    //get new gas patch list
                    renewGasList(probeSelection.getGasList());
                    gasCalculator();

                break;

            //The following codes are for pylon.
            case("c"):
                System.out.print(convertList[PYLON] + ": ");
                assimilator pylonSelection = new assimilator(
                        totalmap, idList.get(PYLON), initialList.get(PYLON),
                        timeList[PYLON], secondsTotal,
                        totalMinerals, totalGas,
                        100,
                        idList.get(NEXUS), initialList.get(NEXUS));
                pylonSelection.printIndivadualSituation();
                pylonSelection.printGeneralSelection();
                String actionInputPy = reader.next();

                if(actionInputPy.toLowerCase().equals("a")) {
                    System.out.println("How many pylon(s) do you want to construct?");
                    String amount = reader.next();
                    pylonSelection.processActionInput(amount);
                    //update the hash map.
                    totalmap.putAll(pylonSelection.getTotalMap());
                    //get new id.
                    idList.set(PYLON, pylonSelection.getNewId());
                    //get current minerals.
                    totalMinerals = pylonSelection.getTotalMinerals();
                }
                break;

            //The following are for assimilator.
            case("d"):
                System.out.print(convertList[ASSIMILATOR] + ": ");
                assimilator assimilatorSelection = new assimilator(
                        totalmap, idList.get(ASSIMILATOR), initialList.get(ASSIMILATOR),
                        timeList[ASSIMILATOR], secondsTotal,
                        totalMinerals, totalGas,
                        75,
                        idList.get(NEXUS), initialList.get(NEXUS));
                assimilatorSelection.printIndivadualSituation();
                assimilatorSelection.printGeneralSelection();//print the option for it.
                String actionInputAs = reader.next();

                if (actionInputAs.toLowerCase().equals("a")) { //construct begins with here.
                    System.out.println("How many assimilator(s) do you want to be constructed?");
                    String amount = reader.next();
                    assimilatorSelection.processActionInput(amount);

                    //get new id.
                    int newestId = assimilatorSelection.getNewId();
                    int times = newestId - idList.get(ASSIMILATOR);

                    if (times > 0 ) {
                        //update the hash map.
                        totalmap.putAll(assimilatorSelection.getTotalMap());
                        //the new gas assimilator is created
                        for (int i = 0; i < times; i++) {
                            gasList.add(0);
                        }
                        //set the new Id.
                        idList.set(ASSIMILATOR, newestId);
                        //get current minerals.
                        totalMinerals = assimilatorSelection.getTotalMinerals();
                    }
                }
                break;

            //This is began with GATEWAY type.
            case("e"):
                updateGateWayMap(); //checking the available gateway.
                System.out.println(gateWayMap);     //printing method in here.
                System.out.println(convertList[GATEWAY] + ": ");
                gateWay gateWaySelection = new gateWay(
                        totalmap, idList.get(GATEWAY), initialList.get(GATEWAY),
                        timeList[GATEWAY], secondsTotal,
                        totalMinerals, totalGas,
                        150 //spending  cost is the minerals that need to be deduced for each.
                );
                gateWaySelection.printIndivadualSituation();
                int numebrOfWrapGate = idList.get(WARP_GATE) - initialList.get(WARP_GATE);
                System.out.println("There are: " + numebrOfWrapGate + " Wrap Gate(s).");
                gateWaySelection.printGeneralSelection();

                if (gateWayUpdate && totalmap.containsKey(6001)
                        && (secondsTotal-totalmap.get(6001) >= timeList[CYBERNETIC])) { //print out the option for updating the gateway.
                    System.out.println("c.Update Gateway to Warp Gate.");
                }

                String actionInputGAT = reader.next();

                if (actionInputGAT.toLowerCase().equals("a")) {
                    //process user input.
                    System.out.println("How many gateway(s) do you want to construct?");
                    String amount = reader.next();
                    gateWaySelection.processActionInput(amount);

                    //get the new map.
                    totalmap.putAll(gateWaySelection.getTotalMap());
                    // get the new id.
                    int newestId = gateWaySelection.getNewId();

                    //the map of the gate way will be added, if the new gate way is add.
                    int times = newestId - idList.get(GATEWAY);
                    if (times > 0) {
                        //set the situation for the gate way.
                        setTotalFacilityUnavailable(times, idList.get(GATEWAY), gateWayMap);

                        //set the newId for gate way.
                        idList.set(GATEWAY, newestId);

                        //This is to get the minerals.
                        totalMinerals = gateWaySelection.getTotalMinerals();
                    }

                } else if (actionInputGAT.toLowerCase().equals("c")) {
                        if ((wrapGateResearchTime != 0) && (secondsTotal - wrapGateResearchTime >= 160)) {
                            updateToWarpGate();
                            System.out.println("GateWay is updating...");
                        } else {//did not reach the researching time.
                            System.out.println("--Invalid: Upgrades to Warp Gate haven't or is still in researching.");
                        }
                }
                break;

            case("f"):      //This is for cybernetics core.
                System.out.println(convertList[CYBERNETIC] + ": ");
                cybernetics cyberneticsSelection = new cybernetics(
                  totalmap, idList.get(CYBERNETIC), initialList.get(CYBERNETIC),
                  timeList[CYBERNETIC], secondsTotal,
                  totalMinerals, totalGas,
                  150);
                cyberneticsSelection.printIndivadualSituation();
                cyberneticsSelection.printGeneralSelection();
                //research option
                if (totalmap.containsKey(6001)
                        && (secondsTotal - totalmap.get(6001) >= timeList[GATEWAY])
                        && !gateWayUpdate) {
                    System.out.println("c.Research Warp Gate.");
                }

                String actionInputCYB = reader.next();

                //process the user's input
                if (actionInputCYB.toLowerCase().equals("a")) {
                    System.out.println("How many Cybernetics Core do you want to construct?");
                    String amount = reader.next();
                    cyberneticsSelection.processActionInput(amount);

                    //get the new map.
                    totalmap.putAll(cyberneticsSelection.getTotalMap());

                    //get the new id.
                    int newestId = cyberneticsSelection.getNewId();
                    idList.set(CYBERNETIC, newestId);

                    //This is to get the total minerals (after deducing).
                    totalMinerals = cyberneticsSelection.getTotalMinerals();
                /**
                 * cyberneticsSelection.printMap(); before this part, testing is success,
                 * because deduce minerals, time, and building condition.
                 */
                } else if (actionInputCYB.toLowerCase().equals("c")) {
                    if (totalGas >= 50 && totalMinerals >= 50) {
                        gateWayUpdate = true;
                        totalGas = totalGas - 50;
                        totalMinerals = totalMinerals - 50;
                        wrapGateResearchTime = secondsTotal;    //the research begins.
                        System.out.println("++Warp Gate is searching...");
                    } else {
                        System.out.println("--Invalid: Minerals or gas are not enough.");
                    }
                }

                break;
            /**
             * The reason why ROBOTIC and STARGate share the same class.
             * because they have the same conditional except the spending of minerals and gas.
             */
            case("g"):
                System.out.println(convertList[ROBOTIC] + ": ");
                roboticsFac roboticsSelection = new roboticsFac(
                        totalmap, idList.get(ROBOTIC), initialList.get(ROBOTIC),
                        timeList[ROBOTIC], secondsTotal,
                        totalMinerals, totalGas,
                        200, gasCostList[ROBOTIC]);
                roboticsSelection.printIndivadualSituation();
                roboticsSelection.printGeneralSelection();
                String actionInputRob = reader.next();

                //process the user's input.
                if (actionInputRob.toLowerCase().equals("a")) {
                    System.out.println("How many Robotics Facility do you want to construct?");
                    String amount = reader.next();
                    roboticsSelection.processActionInput(amount);

                    //get the new map.
                    totalmap.putAll(roboticsSelection.getTotalMap());

                    //get the new id.
                    int newestId = roboticsSelection.getNewId();
                    if (newestId - idList.get(ROBOTIC) > 0) {
                        int times = newestId - idList.get(ROBOTIC);
                        //set the situation of the robotic facility.
                        setTotalFacilityUnavailable(times, idList.get(ROBOTIC), roboticsMap);
                        //set the new id.
                        idList.set(ROBOTIC, newestId);
                        //deduce the minerals.
                        totalMinerals = roboticsSelection.getTotalMinerals();
                        //deduce the gas.
                        totalGas = roboticsSelection.getTotalGas();
                    }
                }
                break;

            case("h"):
                System.out.println(convertList[STARGATE] + ": ");
                roboticsFac starGateSelection = new roboticsFac(
                        totalmap, idList.get(STARGATE), initialList.get(STARGATE),
                        timeList[STARGATE], secondsTotal,
                        totalMinerals, totalGas,
                        150, gasCostList[STARGATE]);
                starGateSelection.printIndivadualSituation();
                starGateSelection.printGeneralSelection();
                String actionInputSTA = reader.next();

                //process the user's input.
                if (actionInputSTA.toLowerCase().equals("a")) {
                    System.out.println("How many Stargate do you want to construct?");
                    String amount = reader.next();
                    starGateSelection.processActionInput(amount);

                    //get map
                    totalmap.putAll(starGateSelection.getTotalMap());
                    //get id
                    int newestId = starGateSelection.getNewId();
                    if (newestId - idList.get(STARGATE) > 0) {
                        int times = newestId - idList.get(STARGATE);
                        //set the situation of the star gate.
                        setTotalFacilityUnavailable(times, idList.get(STARGATE), starGateMap);
                        System.out.print("");
                        //set the new id.
                        idList.set(STARGATE, newestId);
                        //deduce the minerals.
                        totalMinerals = starGateSelection.getTotalMinerals();
                        //deduce the gas.
                        totalGas = starGateSelection.getTotalGas();
                    }
                }
                break;

            case("i"):
                updateGateWayMap();
                System.out.println(convertList[ZEALOT] + ": ");
                zealotOfUnit(ZEALOT, 100, 1);
                break;

            case ("j"):
                updateGateWayMap();
                System.out.println(convertList[STALKER] + ": ");
                zealotOfUnit(STALKER, 120, 2);
                break;

            case ("k"):
                updateGateWayMap();
                System.out.println(convertList[SENTRY] + ": ");
                zealotOfUnit(SENTRY, 50, 1);
                break;
            /**
             * zealot, stalker, and sentry can be regarded as a type of unit.
             * because they are produced by the same building.
             */

            case ("l"):
                updateRoboticsMap();
                System.out.println(convertList[OBSERVER] + ": ");
                observeOfUnit(OBSERVER, 25, 1);
                break;
            case ("m"):
                updateRoboticsMap();
                System.out.println(convertList[IMMORTAL] + ": ");
                observeOfUnit(IMMORTAL, 250, 1);
                break;
            /**
             * Immortal and Observer share similar situation, because they are built from the same building.
             */
            case ("n"):
                updateStarGateMap();
                System.out.println(convertList[PHOENIX] + ": ");
                phoenixOfUnit(PHOENIX, 150, 1);
                break;
            case ("o"):
                updateStarGateMap();
                System.out.println(convertList[VOID_RAY] + ": ");
                phoenixOfUnit(VOID_RAY, 250, 1);
                break;

            case ("p"): //This is for twilight council.
                System.out.println(convertList[COUNCIL] + ": ");
                roboticsFac councilSelection = new roboticsFac(
                        totalmap, idList.get(COUNCIL), initialList.get(COUNCIL),
                        timeList[COUNCIL], secondsTotal,
                        totalMinerals, totalGas,
                        150, gasCostList[COUNCIL]);
                councilSelection.printIndivadualSituation();
                councilSelection.printGeneralSelection();
                String actionInputCOUN = reader.next();

                //process the input.
                if (actionInputCOUN.toLowerCase().equals("a")) {
                    System.out.println("How many Twilight council do you want to construct?");
                    String amount = reader.next();
                    councilSelection.processActionInput(amount);

                    //get the new id.
                    int newestId = councilSelection.getNewId();
                    if (newestId - idList.get(COUNCIL) > 0) {
                        //get the new map.
                        totalmap.putAll(councilSelection.getTotalMap());
                        //get the new id.
                        idList.set(COUNCIL, newestId);
                        //update the currency.
                        totalMinerals = councilSelection.getTotalMinerals();
                        totalGas = councilSelection.getTotalGas();
                        System.out.println();
                    }
                }
                break;

            case ("q"):
                System.out.println(convertList[TEMPLAR_ARCHIVES] + ": ");
                TemplarOfBuilding(TEMPLAR_ARCHIVES, 150, 1);
                break;
            case ("r"):
                System.out.println(convertList[DARK_SHRINE] + ": ");
                TemplarOfBuilding(DARK_SHRINE, 100, 1);
                break;
            case ("s"):
                System.out.println(convertList[ROBOTICS_BAY] + ": ");
                TemplarOfBuilding(ROBOTICS_BAY, 200,2 );
                break;
            case ("t"):
                System.out.println(convertList[FLEET_BEACON] + ": ");
                TemplarOfBuilding(FLEET_BEACON, 300, 3);
                break;
            case ("u"):
                updateRoboticsMap();
                System.out.println(convertList[COLOSSI] + ": ");
                observeOfUnit(COLOSSI, 300, 2);
                break;
            case ("v"):
                updateGateWayMap();
                System.out.println(convertList[HIGH_TEMPLAR] + ": ");
                zealotOfUnit(HIGH_TEMPLAR, 50, 3);
                break;
            case ("w"):
                updateGateWayMap();
                System.out.println(convertList[DARK_TEMPLAR] + ": ");
                zealotOfUnit(DARK_TEMPLAR, 125, 4);
                break;
            case ("x"):
                updateStarGateMap();
                System.out.println(convertList[CARRIER] + ": ");
                phoenixOfUnit(CARRIER, 350, 2);

                break;
            case("quit"):
                System.out.println("Game Over!!!");
                break;
            default:
                System.out.println("Invalid Selection!");
                break;
        }
    }

    /**
     * This method is to add the minerals patch when new nexus is built.
     * @param times the number of new nexus is built.
     */
    public void increasePatchList(int times) {
        int addAmount = 8 * times;
        for (int i = 0; i < addAmount; i++) {
            mineralPatchList.add(0);
        }
    }

    /**
     * This method is to set phoenix, void ray, and carrier(2).
     * @param type the units type.
     * @param mineralsSpending the cost of the units.
     * @param code type of this units.
     */
    public void phoenixOfUnit(int type, int mineralsSpending, int code) {
        Scanner reader = new Scanner(new InputStreamReader(System.in));
        phoenix selection = new phoenix(
                totalmap, idList.get(type), initialList.get(type),
                timeList[type], secondsTotal,
                totalMinerals, totalGas,
                mineralsSpending, gasCostList[type],
                starGateMap,
                code);
        printIndividual(selection);
        String actionInput = reader.next();
        if (actionInput.toLowerCase().equals("a")) {
            System.out.println("How many " + convertList[type] + " do you want to construct?");
            String amount = reader.next();
            selection.processActionInput(amount);

            //get map.
            totalmap.putAll(selection.getTotalMap());
            //get stargate map.
            updateHashMapTotalAfterUnit(selection.getStarGateMap(), starGateMap);
            //get id, deduce the currency.
            updateTheList(type, selection);
        }
    }

    /**
     * This method is to build the type of observe.
     * @param type observe or immortal.
     * @param mineralsSpending the cost of the minerals building them.
     */
    public void observeOfUnit(int type, int mineralsSpending, int code) {
        Scanner reader = new Scanner(new InputStreamReader(System.in));
        observe selection = new observe(
                totalmap, idList.get(type), initialList.get(type),
                timeList[type], secondsTotal,
                totalMinerals, totalGas,
                mineralsSpending, gasCostList[type],
                roboticsMap, code);

        printIndividual(selection);
        String actionInput = reader.next();
        if (actionInput.toLowerCase().equals("a")) {
            System.out.println("How many " + convertList[type] + " do you want to construct?");
            String amount = reader.next();
            selection.processActionInput(amount);

            //get map.
            totalmap.putAll(selection.getTotalMap());
            //get robotics map.
            updateHashMapTotalAfterUnit(selection.getRoboticsMap(), roboticsMap);
            //get id, deduce the minerals and gas.
            updateTheList(type, selection);
        }

    }


    /**
     * This method is to set the type of units like zealot.
     * @param type the units type.
     * @param mineralsSpending the cost of building such a units.
     */
    public void zealotOfUnit(int type, int mineralsSpending, int code) {
        Scanner reader = new Scanner(new InputStreamReader(System.in));
        zealot selection = new zealot(
                totalmap, idList.get(type), initialList.get(type),
                timeList[type], secondsTotal,
                totalMinerals, totalGas,
                mineralsSpending, gasCostList[type],
                gateWayMap, code);
        printIndividual(selection);
        String actionInput = reader.next();

        //process the user's input
        if (actionInput.toLowerCase().equals("a")) {
            System.out.println("How many " + convertList[type] + " do you want to construct?");
            String amount = reader.next();
            selection.processActionInput(amount);

            //get map.
            totalmap.putAll(selection.getTotalMap());
            //update the gate way hash map.
            updateHashMapTotalAfterUnit(selection.getGateMap(), gateWayMap);
            //update the minerals and gas list.
            updateTheList(type, selection);
        }
    }


    //general method begins with here.
    public void TemplarOfBuilding(int type, int mineralsSpending, int code) {
        Scanner reader = new Scanner(new InputStreamReader(System.in));
        templarArch selection = new templarArch(
                totalmap, idList.get(type), initialList.get(type),
                timeList[type], secondsTotal,
                totalMinerals, totalGas,
                mineralsSpending, gasCostList[type],
                code
        );

        selection.printIndivadualSituation();
        selection.printGeneralSelection();
        String actionInput = reader.next();
        if (actionInput.toLowerCase().equals("a")) {
            System.out.println("How many " + convertList[type] + " do you want to construct?");
            String amount = reader.next();
            selection.processActionInput(amount);

            int newestId = selection.getNewId();
            if (newestId - idList.get(type) > 0) {
                //get the new map
                totalmap.putAll(selection.getTotalMap());
                idList.set(type, newestId);
                totalMinerals = selection.getTotalMinerals();
                totalGas = selection.getTotalGas();
            }
        }
    }
    /**
     * This method is to renew the facility hash map in order to get the situation of it.(print)
     * @param newHashMap the facility hash map after processing.
     * @param oldHashMap the facility hash map need to be updated.
     */
    public void updateHashMapTotalAfterUnit(HashMap<Integer, Boolean> newHashMap,
                                            HashMap<Integer, Boolean> oldHashMap) {
       oldHashMap.clear();
       oldHashMap.putAll(newHashMap);
    }

    /**
     * This method is to set the facility not work at the beginning of being built.
     * @param times the number of gate way need to be set.
     * @param id the unit id.(print)
     */
    public void setTotalFacilityUnavailable(int times, int id, HashMap<Integer, Boolean> facilityMap) {
        for (int i = 0; i < times; i++) {
            id++;
            facilityMap.put(id, false);
        }
    }



    /**
     * This method is to print the individual situation and its selections can be chosen.
     * @param individualType the characteristic object.
     */
    public void printIndividual(typeOfConstruction individualType) {
        individualType.printIndivadualSituation();
        individualType.printGeneralSelection();
    }

    /**
     * This method is to update the list including minerals and gas.
     * @param position the character need to be changed.
     * @param individualType the characteristic object.
     */
    public void updateTheList(int position, typeOfConstruction individualType) {
        int id = individualType.getNewId();
        idList.set(position, id);
        totalMinerals = individualType.getTotalMinerals();
        totalGas = individualType.getTotalGas();
    }


    //Personal method begins with here.

    /**
     * This method is to update the situation of the minerals patches.
     * @param newMineralPatchList the situation of patch after the assignment.
     */
    public void renewPatchList(List<Integer> newMineralPatchList) {
        mineralPatchList = new ArrayList<>();
        mineralPatchList.addAll(newMineralPatchList);
        System.out.println("This is the situation of patch: " + mineralPatchList);
    }

    /**
     * This method is to renew the gas list.
     * @param newGasList the new gas list show the probes working.
     */
    public void renewGasList(List<Integer> newGasList) {
        gasList = new ArrayList<>();
        gasList.addAll(newGasList);
        System.out.println("This is the situation of gas: " + gasList);
    }

    /**
     * This method is to set the situation of the nexus.
     */
    public void updateNexusMap() {
        Set set = nexusMap.entrySet();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            int id = (int) pair.getKey();
            boolean available = (boolean) pair.getValue();

            if (!available) {
                if ((id < initialList.get(NEXUS) + 1000)
                        && (secondsTotal - totalmap.get(id) >= timeList[NEXUS])) {
                    nexusMap.put(id, true);
                } else if ((id < initialList.get(PROBE) + 1000)
                        && (secondsTotal - totalmap.get(id) >= timeList[PROBE])) {
                    nexusMap.put(id, true);
                }
            }

        }
    }

    public void updateToWarpGate() {
        timeList[ZEALOT] = 28;
        timeList[STALKER] = 32;
        timeList[SENTRY] = 32;
        timeList[HIGH_TEMPLAR] = 45;
        timeList[DARK_TEMPLAR] = 45;
        ArrayList<Integer> removeList = new ArrayList<>();

        if (!gateWayMap.isEmpty()) { //if there is gate way.
            Set set = gateWayMap.entrySet();
            Iterator iterator = set.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                count++;
                Map.Entry pair = (Map.Entry) iterator.next();
                int id = (int) pair.getKey();
                boolean available = (boolean) pair.getValue();

                int gatWayId = initialList.get(GATEWAY) + count;       //The corresponding gate to be updated.
                int warpGateId = initialList.get(WARP_GATE) + count;    //update to the gateway map.
                //update warpGateId in the total map.
                totalmap.remove(gatWayId);
                totalmap.put(warpGateId, secondsTotal);

                if (available) {
                    removeList.add(id);
                    removeList.add(warpGateId);
                } else {//under constructing gateway.
                    int initialTime = totalmap.get(id) - 20; //The meaning of 20 is including updating the time.
                    totalmap.put(id, initialTime);
                }
            }
            //update the id list.
            int id = initialList.get(WARP_GATE);
            id = id + count;
            idList.set(WARP_GATE, id);

            //this is for available gateWay
            for (int i = 0; i < removeList.size(); i+=2) {
                gateWayMap.remove(removeList.get(i));
                gateWayMap.put(removeList.get(i + 1), false);
            }
        } else {
            System.out.println("Please construct Gateway firstly.");
        }
    }

    /**
     * This method is to set the situation of the gate way, which is used for probes.
     * True presents available.
     */
    public void updateGateWayMap() {
        if(!gateWayMap.isEmpty()) {// make sure there is value inside the hash map
            Set set = gateWayMap.entrySet();
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) { //go over all the gate way.
                Map.Entry pair = (Map.Entry) iterator.next();
                int id = (int) pair.getKey();
                boolean available = (boolean) pair.getValue();

                if (!available) {
                    //if the gate way is not in constructing or building other unit,
                    //the value will become true.
                    if ((id < initialList.get(GATEWAY) + 1000)
                            && (id >= initialList.get(GATEWAY))
                            && (secondsTotal - totalmap.get(id) >= timeList[GATEWAY])) {
                        gateWayMap.put(id, true);
                    } else if ((id < initialList.get(ZEALOT) + 1000)
                            && (id >= initialList.get(ZEALOT))
                            && (secondsTotal - totalmap.get(id) >= timeList[ZEALOT])) {
                        gateWayMap.put(id, true);
                    } else if (id < initialList.get(STALKER) + 1000
                            && (id >= initialList.get(STALKER))
                            && (secondsTotal - totalmap.get(id) >= timeList[STALKER])) {
                        gateWayMap.put(id, true);
                    } else if (id < initialList.get(SENTRY) + 1000
                            && (id >= initialList.get(SENTRY))
                            && (secondsTotal - totalmap.get(id) >= timeList[SENTRY])) {
                        gateWayMap.put(id, true);
                    } else if (id < initialList.get(HIGH_TEMPLAR) + 1000
                            && (id >= initialList.get(HIGH_TEMPLAR))
                            && (secondsTotal - totalmap.get(id) >= timeList[HIGH_TEMPLAR])) {
                        gateWayMap.put(id, true);
                    } else if (id < initialList.get(DARK_TEMPLAR) + 1000
                            && (id >= initialList.get(DARK_TEMPLAR))
                            && (secondsTotal - totalmap.get(id) >= timeList[DARK_TEMPLAR])) {
                        gateWayMap.put(id, true);
                    } else if (id < initialList.get(WARP_GATE) + 1000
                            && id >= initialList.get(WARP_GATE)
                            && (secondsTotal - totalmap.get(id) >= timeList[WARP_GATE])) {
                        gateWayMap.put(id, true);
                    }
                }
            }
        }
    }

    /**
     * This method is to set the situation of the robotics facility.
     * True presents available.
     */
    public void updateRoboticsMap() {
        if(!roboticsMap.isEmpty()) {
            Set set = roboticsMap.entrySet();
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                int id = (int) pair.getKey();
                boolean available = (boolean) pair.getValue();

                if (!available) {
                    if ((id < initialList.get(ROBOTIC) + 1000)          //last used was robotics.
                            && (secondsTotal - totalmap.get(id) >= timeList[ROBOTIC])) {
                        roboticsMap.put(id, true);
                    } else if ((id < initialList.get(OBSERVER) + 1000)  //last used was observe.
                            && (secondsTotal - totalmap.get(id) >= timeList[OBSERVER])) {
                        roboticsMap.put(id, true);
                    } else if ((id < initialList.get(IMMORTAL) + 1000)  //last used was Immortal.
                            && (secondsTotal - totalmap.get(id) >= timeList[IMMORTAL])) {
                        roboticsMap.put(id, true);
                    } else if ((id < initialList.get(COLOSSI) + 1000) //last used was COLOSSUS.
                            && (secondsTotal - totalmap.get(id) >= timeList[COLOSSI])) {
                        roboticsMap.put(id, true);
                    }
                }
            }
//            System.out.println(roboticsMap);
        }
    }

    /**
     * This method is to update the star gate map.
     */
    public void updateStarGateMap() {
        if(!starGateMap.isEmpty()) {
            Set set = starGateMap.entrySet();
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                int id = (int) pair.getKey();
                boolean available = (boolean) pair.getValue();

                if (!available) {
                    if ((id < initialList.get(STARGATE) + 1000)
                            && (secondsTotal - totalmap.get(id) >= timeList[STARGATE])) {
                        starGateMap.put(id, true);
                    } else if ((id < initialList.get(PHOENIX) + 1000)
                            && (secondsTotal - totalmap.get(id) >= timeList[PHOENIX])) {
                        starGateMap.put(id, true);
                    } else if ((id < initialList.get(VOID_RAY) + 1000)
                            && (secondsTotal - totalmap.get(id) >= timeList[VOID_RAY])) {
                        starGateMap.put(id, true);
                    } else if ((id < initialList.get(CARRIER) + 1000)
                            && (secondsTotal - totalmap.get(id) >= timeList[CARRIER])) {
                        starGateMap.put(id, true);
                        System.out.print("");
                    }
                }
            }
        }
    }


    /**
     * This method is to stop the whole game and print out the total time.
     */
    public void end() {
        totalTimer.cancel();
        printTotalTime();
    }

    /**
     * This is the printer to convert seconds to minutes.
     */
    public void printTotalTime() {
        int minute = secondsTotal / 60;
        int timerSecond = secondsTotal - minute * 60;
        System.out.println("Total time is: " + minute + " mins " + timerSecond + " seconds");
    }

    public int getMineralCount() {
        return totalMinerals;
    }

    public int getGasCount() {
        return totalGas;
    }

    public int getSeconds(){
        int minute = secondsTotal / 60;
        int timerSecond = secondsTotal - minute * 60;
        return timerSecond;
    }

    public int getMinutes(){
        int minute = secondsTotal / 60;
        return minute;

    }

    public void setTime(){
        secondsTotal = 0;
    }

}
