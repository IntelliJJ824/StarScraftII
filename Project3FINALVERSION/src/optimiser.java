import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class is to process the optimizer.
 */
public class optimiser extends process {

    BufferedWriter writer = null;
    OptimiserWindow window = new OptimiserWindow();

    //This is the goal at the early stage.
    int PROBES_GOAL = 20;
    int PYLON_GOAL = 1;
    int GATEWAY_GOAL = 1;
    int ASSIMILATOR_GOAL = 2;
    int CORE_GOAL = 1;
    int SENTRY_GOAL;

    //This is the goal in the late stage.
    int IMMORTAL_FIN, COLOSSUS_FIN, STALKER_FIN, GATEWAY_FIN, PROBE_FIN, ZEALOT_FIN, SENTRY_FIN,
    PHOENIX_FIN, VOID_RAY_FIN;
    //building goal.
    int STARGATE_FIN, ROBOTICS_FIN, BAY_FIN, HIGH_TEMPLAR_FIN, TWILIGHT_FIN, TEMPLAR_FIN;
    int OBSERVER_FIN, CARRIER_FIN, DARK_TEMPLAR_FIN, BEA_FIN, DARKSHRINE_FIN;
    boolean prior = true;
    boolean phoenPro = true;
    boolean carrierPrior = false;

    //number of buildings currently.
    private int probes = 6;
    private int pylon = 0, gateWay = 0, assimilator = 0, core = 0, sentry = 0,
    zealot = 0, stalker = 0, phoenix = 0, roboticsFacility = 0, stargate = 0, rayVoid = 0;
    typeOfConstruction judgement;

    //late stage.
    private int immortal = 0, colossus = 0, bay = 0;
    private int highTemplar = 0, twilight = 0, templar = 0;
    private int darkTemplar = 0, carrier = 0, observe = 0, bea = 0, darkshrine = 0;

    public optimiser() throws IOException {
    }

    /**
     * provide the basic facility.
     */
    public void optimizerPrepare() {
        judgement = new typeOfConstruction();   //use the method in the model gaming.
        setIdList();
        buildBase();
        numberMaxVelocity = probes;
        totalNumberGasWorking = 0;
    }

    /**
     * This method is to set the gaol from the user's input.
     * @param userInput the user select the goal.
     */
    public void processGoal(String userInput) {
        optimizerPrepare();
        switch(userInput) {
            case ("1"):
                //This is int the early stage.
                System.out.println("first goal.");
                generateThePossibility();
                //set the goat for the late stage.
                STALKER_FIN = 4;
                IMMORTAL_FIN = 2;
                COLOSSUS_FIN = 2;
                ZEALOT_FIN = 1;
                //set the goal for building.
                BAY_FIN = 1;
                ROBOTICS_FIN = 2;
                highStageGoalOne();
                break;

            case ("2"):
                System.out.println("second goal.");
                GATEWAY_GOAL = 2;
                generateThePossibility();
                STARGATE_FIN = 2;
                GATEWAY_FIN = 3;
                ZEALOT_FIN = 6;
                STALKER_FIN = 2;
                SENTRY_FIN = 3;
                VOID_RAY_FIN = 4;
                highStageGoalTwo();
                break;

            case ("3"):
                System.out.println("third goal.");
                generateThePossibility();
                ZEALOT_FIN = 2;
                STALKER_FIN = 2;
                SENTRY_FIN = 1;
                COLOSSUS_FIN = 2;
                PHOENIX_FIN = 5;
                SENTRY_GOAL = 1;
                //buildings goal
                STARGATE_FIN = 1;
                ROBOTICS_FIN = 1;
                BAY_FIN = 1;
                PROBE_FIN = 1;
                highStageGoalThree();
                break;

            case ("4"):
                System.out.println("forth goal.");
                generateThePossibility();
                ZEALOT_FIN = 10;
                STALKER_FIN = 7;
                SENTRY_FIN = 2;
                HIGH_TEMPLAR_FIN = 3;
                //building goal
                GATEWAY_FIN = 4;
                TWILIGHT_FIN = 1;
                TEMPLAR_FIN = 1;
                highStageGoalFour();
                break;

            case ("5"):
                System.out.println("fifth goal.");
                generateThePossibility();
                //units goal
                ZEALOT_FIN = 8;
                STALKER_FIN = 10;
                SENTRY_FIN = 2;
                IMMORTAL_FIN = 1;
                OBSERVER_FIN = 1;
                CARRIER_FIN = 3;
                DARK_TEMPLAR_FIN = 2;
                //buildings goal
                GATEWAY_FIN = 3;
                TWILIGHT_FIN = 1;
                DARKSHRINE_FIN = 1;
                ROBOTICS_FIN = 1;
                STARGATE_FIN = 2;
                BEA_FIN = 1;
                highStageProcessFive();
                break;

                default:
                System.out.println("Enter to the game model.");
        }

        setTime();
    }

    //This method is the early processing.
    /**
     * The order of building: probes, pylon, gateway, cybernetic core, assimilator, sentry.
     */
    public void generateThePossibility() {

        while (!earlyStageGoal()) {    //if the goal is achieved, while loop stops.
            secondsTotal++;//time increase.
            probesJudgeAvailable();
            updateGateWayMap();

            if (probesConditionJudge(PROBE, PROBES_GOAL, probes)
                    && judgeNexusAvailable(secondsTotal)) { //whether the nexus available to produce probe.
                probes++;
                setBuildingPrepare(PROBE, secondsTotal);

                totalmap.put(1001, secondsTotal);   //set the situation of Nexus.
                deduceCurrency(mineralsCostList[PROBE], gasCostList[PROBE]);

                System.out.print("--- Build 1 PROBE ---- ");
                printTotalTime();

            } else if (probesConditionJudge(PYLON, PYLON_GOAL, pylon)) {
                pylon++;
                setBuildingPrepare(PYLON, secondsTotal);
                deduceCurrency(mineralsCostList[PYLON], gasCostList[PYLON]);

                System.out.print("--- Build 1 Pylon ---- ");
                printTotalTime();
            } else if (probesConditionJudge(GATEWAY, GATEWAY_GOAL, gateWay)) {
                //update the gate way map(avoid the id is plus twice).
                setTotalFacilityUnavailable(1, idList.get(GATEWAY), gateWayMap);

                gateWay++;
                setBuildingPrepare(GATEWAY, secondsTotal);
                deduceCurrency(mineralsCostList[GATEWAY], gasCostList[GATEWAY]);

                System.out.print("--- Build 1 Gateway --- ");
                printTotalTime();

            } else if (probesConditionJudge(CYBERNETIC, CORE_GOAL, core)) {
                core++;
                setBuildingPrepare(CYBERNETIC, secondsTotal);
                deduceCurrency(mineralsCostList[CYBERNETIC], gasCostList[CYBERNETIC]);
                System.out.print("--- Build 1 Cybernetics Core --- ");
                printTotalTime();

            } else if (probesConditionJudge(ASSIMILATOR, ASSIMILATOR_GOAL, assimilator)) {
                assimilator++;
                setBuildingPrepare(ASSIMILATOR, secondsTotal);
                deduceCurrency(mineralsCostList[ASSIMILATOR], gasCostList[ASSIMILATOR]);
                System.out.print("--- Build 1 Assimilator --- ");
                printTotalTime();

            }
            //This is to print out the time.
            totalMinerals += mineralCalculatorGoal();
            totalGas += gasCalculatorGoal();
        }
        System.out.println("Your suggestion is in the early stage.");
    }

    //The methods are to process the goal in the later stage.
    /**
     * This method is to process gaol one.
     */
    public void highStageGoalOne() {
        while(!achieveFinalGoalOne()) {//secondsTotal <= 500)
            secondsTotal++;
            updateGateWayMap();
            updateRoboticsMap();
            balanceTheGas();
                if (probesConditionJudge(ROBOTIC, ROBOTICS_FIN, roboticsFacility)) {
                    setTotalFacilityUnavailable(1, idList.get(ROBOTIC), roboticsMap);
                    roboticsFacility++;
                    setBuildings(ROBOTIC);
                    printTotalTime();
                } else if (probesConditionJudge(ROBOTICS_BAY, BAY_FIN, bay)
                        && roboticsFacilityExist()) {
                    bay++;
                    setBuildings(ROBOTICS_BAY);
                    printTotalTime();
                }else if (probesConditionJudge(COLOSSI, COLOSSUS_FIN, colossus)
                        && bayExist()
                        && dependingBuildingAvailable(ROBOTIC, roboticsMap)) {
                    colossus++;
                    buildUnits(COLOSSI, roboticsMap);
                    printTotalTime();
                } else if (probesConditionJudge(IMMORTAL, IMMORTAL_FIN, immortal)
                        && dependingBuildingAvailable(ROBOTIC, roboticsMap)) {
                    immortal++;
                    buildUnits(IMMORTAL, roboticsMap);
                    printTotalTime();

                } else if (probesConditionJudge(STALKER, STALKER_FIN, stalker)
                        && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                    stalker++;
                    buildUnits(STALKER, gateWayMap);
                    printTotalTime();
                } else if (probesConditionJudge(ZEALOT, ZEALOT_FIN, zealot)
                        && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                    zealot++;
                    buildUnits(ZEALOT, gateWayMap);
                    printTotalTime();
                }

            totalMinerals += mineralCalculatorGoal();
            totalGas += gasCalculatorGoal();
        }
        System.out.println("Total Minerals Left: " + totalMinerals + ", Gas left: " + totalGas);
    }

    /**
     * This is method is to process the later goal in goal two.
     */
    public void highStageGoalTwo() {
        while (!achieveFinalGoalTwo()) {
           secondsTotal++;
           updateGateWayMap();
           updateStarGateMap();
           balanceTheGas();

            if (probesConditionJudge(STARGATE, STARGATE_FIN, stargate)) {
               setTotalFacilityUnavailable(1, idList.get(STARGATE), starGateMap);
               stargate ++;
               setBuildings(STARGATE);
               printTotalTime();
           } else if (probesConditionJudge(GATEWAY, GATEWAY_FIN, gateWay)) {
               setTotalFacilityUnavailable(1, idList.get(GATEWAY), gateWayMap);
               gateWay++;
               setBuildings(GATEWAY);
               printTotalTime();
           } else if (
                   prior && probesConditionJudge(VOID_RAY, VOID_RAY_FIN, rayVoid)
                   && dependingBuildingAvailable(STARGATE, starGateMap)) {
               rayVoid++;
               buildUnits(VOID_RAY, starGateMap);

               printTotalTime();
           } else if (totalmap.containsKey(15001) && probesConditionJudge(STALKER, STALKER_FIN, stalker)
                   && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                    stalker++;
                    buildUnits(STALKER, gateWayMap);
                    printTotalTime();

           } else if (
                    totalmap.containsKey(15002) && probesConditionJudge(ZEALOT, ZEALOT_FIN, zealot)
                   && dependingBuildingAvailable(GATEWAY, gateWayMap)
                    ) {
                    zealot++;
                    buildUnits(ZEALOT, gateWayMap);
                    printTotalTime();
                    if (zealot == 5) {
                            prior = true;
                    }
           } else if (
                   totalmap.containsKey(15001)
                   && probesConditionJudge(SENTRY, SENTRY_FIN, sentry)
                   && dependingBuildingAvailable(GATEWAY, gateWayMap)
                    ) {
                    sentry++;
                    buildUnits(SENTRY, gateWayMap);
                    printTotalTime();
           }
           //update every things.
            totalMinerals += mineralCalculatorGoal();
            totalGas += gasCalculatorGoal();

        }
        System.out.println("Total Minerals Left: " + totalMinerals + ", Gas left: " + totalGas);
    }

    /**
     * This method is designed for goal 3.
     */
    public void highStageGoalThree() {

        while (!achieveFinalGoalThree()) { //secondsTotal < 600
            secondsTotal++;
            updateGateWayMap();
            updateStarGateMap();
            updateRoboticsMap();
            probesJudgeAvailable();
            if (probesConditionJudge(ROBOTIC, ROBOTICS_FIN, roboticsFacility)
                    && totalmap.containsKey(8001)) {
                //update the robotics facility.
                setTotalFacilityUnavailable(1, idList.get(ROBOTIC), roboticsMap);
                roboticsFacility++;
                setBuildings(ROBOTIC);
                prior = true;
                printTotalTime();
            } else if (probesConditionJudge(STARGATE, STARGATE_FIN, stargate)
                    && prior) {
                    //update stargate.
                    setTotalFacilityUnavailable(1, idList.get(STARGATE), starGateMap);
                    stargate++;
                    setBuildings(STARGATE);
                    prior = false;
                printTotalTime();
            } else if (phoenPro && probesConditionJudge(PHOENIX, PHOENIX_FIN, phoenix)
                    && dependingBuildingAvailable(STARGATE, starGateMap)
                    ) {
                    phoenix++;
                    buildUnits(PHOENIX, starGateMap);
                    if (phoenix == 1 || phoenix == 2) {
                        phoenPro = false;
                    }
                printTotalTime();
            } else if (probesConditionJudge(ROBOTICS_BAY, BAY_FIN, bay)
                    && roboticsFacilityExist()) {
                    bay++;
                    setBuildings(ROBOTICS_BAY);
                    phoenPro = true;
                printTotalTime();
            } else if (probesConditionJudge(COLOSSI, COLOSSUS_FIN, colossus)
                    && bayExist()
                    && dependingBuildingAvailable(ROBOTIC, roboticsMap)) {
                    colossus++;
                    buildUnits(COLOSSI, roboticsMap);
                    phoenPro = true;
                printTotalTime();
            } else if (probesConditionJudge(STALKER, STALKER_FIN, stalker)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                    stalker ++;
                    buildUnits(STALKER, gateWayMap);
                printTotalTime();
            } else if (probesConditionJudge(ZEALOT, ZEALOT_FIN, zealot)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                zealot++;
                buildUnits(ZEALOT, gateWayMap);
                printTotalTime();
            } else if (probesConditionJudge(SENTRY, SENTRY_GOAL, sentry)
                    && judgeCoreBuild()
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                sentry++;
                buildUnits(SENTRY,gateWayMap);
                printTotalTime();
            }
            totalMinerals += mineralCalculatorGoal();
            totalGas += gasCalculatorGoal();
        }
        System.out.println("Total Minerals Left: " + totalMinerals + ", Gas left: " + totalGas);
    }

    /**
     * This method is to process gaol 4.
     */
    public void highStageGoalFour() {
        while (!achieveGoalFour()) {
            secondsTotal++;
            updateGateWayMap();
            if (secondsTotal <= 474) {
                balanceTheGas();
            } else {
                if (numberMaxVelocity != 20) {
                    System.out.print("*** Assign all the probes to minerals patches");
                }
                numberMaxVelocity = 20;
                totalNumberGasWorking = 0;
            }

            if (probesConditionJudge(COUNCIL, TWILIGHT_FIN, twilight)) {
                twilight++;
                setBuildings(COUNCIL);
                printTotalTime();
            } else if (probesConditionJudge(TEMPLAR_ARCHIVES, TEMPLAR_FIN, templar)
                    && twilightCouncilExist()) {
                templar ++;
                setBuildings(TEMPLAR_ARCHIVES);
                printTotalTime();

            } else if (probesConditionJudge(SENTRY, SENTRY_FIN, sentry)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                sentry++;
                buildUnits(SENTRY, gateWayMap);
                printTotalTime();

            } else if (
                    prior && probesConditionJudge(HIGH_TEMPLAR, HIGH_TEMPLAR_FIN, highTemplar)
                            && templarArchExist()
                            && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                highTemplar++;
                buildUnits(HIGH_TEMPLAR, gateWayMap);
                if (highTemplar == 2) {
                    prior = false;
                }
                printTotalTime();

            } else if (probesConditionJudge(ZEALOT, ZEALOT_FIN, zealot)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                zealot++;
                buildUnits(ZEALOT, gateWayMap);
                printTotalTime();

            } else if (probesConditionJudge(STALKER, STALKER_FIN, stalker)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                stalker++;
                buildUnits(STALKER, gateWayMap);
                if (stalker == 7) {
                    prior = true;
                }
                printTotalTime();
            } else if (probesConditionJudge(GATEWAY, GATEWAY_FIN, gateWay)) {
                setTotalFacilityUnavailable(1, idList.get(GATEWAY), gateWayMap);
                gateWay++;
                setBuildings(GATEWAY);
                printTotalTime();
            }
            totalMinerals += mineralCalculatorGoal();
            totalGas += gasCalculatorGoal();
        }
        System.out.println("Total Minerals Left: " + totalMinerals + ", Gas left: " + totalGas);
    }

    public void highStageProcessFive() {
        while (!achieveGoalFive()) {
            secondsTotal++;
            updateGateWayMap();
            updateStarGateMap();
            updateRoboticsMap();
            if (numberMaxVelocity != 14) {
                numberMaxVelocity = 14;
                totalNumberGasWorking = 6;
            }

            if (prior && probesConditionJudge(STARGATE, STARGATE_FIN, stargate)) {
                setTotalFacilityUnavailable(1, idList.get(STARGATE), starGateMap);
                stargate++;
                setBuildings(STARGATE);
                printTotalTime();
                if (stargate == 1) {
                    prior = false;
                }

            } else if (probesConditionJudge(CARRIER, CARRIER_FIN, carrier)
                    && beaExist()
                    && dependingBuildingAvailable(STARGATE, starGateMap)) {
                carrier++;
                buildUnits(CARRIER, starGateMap);
                printTotalTime();
                if (carrier == 1) {
                    carrierPrior = true;
                }

            } else if (probesConditionJudge(ROBOTIC, ROBOTICS_FIN, roboticsFacility)) {
                setTotalFacilityUnavailable(1, idList.get(ROBOTIC), roboticsMap);
                roboticsFacility++;
                setBuildings(ROBOTIC);
                printTotalTime();

            } else if (probesConditionJudge(COUNCIL, TWILIGHT_FIN, twilight)) {
                twilight++;
                setBuildings(COUNCIL);
                printTotalTime();

            } else if (probesConditionJudge(DARK_SHRINE, DARKSHRINE_FIN, darkshrine)
                    && twilightCouncilExist()) {
                darkshrine++;
                setBuildings(DARK_SHRINE);
                printTotalTime();

            } else if (probesConditionJudge(FLEET_BEACON, BEA_FIN, bea)
                    && starGateExist()) {
                bea++;
                setBuildings(FLEET_BEACON);
                printTotalTime();
                prior = true;
            }  else if (probesConditionJudge(SENTRY, SENTRY_FIN, sentry)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                sentry++;
                buildUnits(SENTRY, gateWayMap);
                printTotalTime();

            } else if (carrierPrior && probesConditionJudge(STALKER, STALKER_FIN, stalker)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                stalker++;
                buildUnits(STALKER, gateWayMap);
                printTotalTime();
            } else if (probesConditionJudge(DARK_TEMPLAR, DARK_TEMPLAR_FIN, darkTemplar)
                    && darkShineExist()
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                darkTemplar++;
                buildUnits(DARK_TEMPLAR, gateWayMap);
                printTotalTime();

            } else if (probesConditionJudge(ZEALOT, ZEALOT_FIN, zealot)
                    && dependingBuildingAvailable(GATEWAY, gateWayMap)) {
                zealot++;
                buildUnits(ZEALOT, gateWayMap);
                printTotalTime();

            } else if (carrierPrior && probesConditionJudge(IMMORTAL, IMMORTAL_FIN, immortal)
                    && dependingBuildingAvailable(ROBOTIC, roboticsMap)) {
                immortal++;
                buildUnits(IMMORTAL, roboticsMap);
                printTotalTime();

            } else if (probesConditionJudge(OBSERVER, OBSERVER_FIN, observe)
                    && dependingBuildingAvailable(ROBOTIC, roboticsMap)) {
                observe++;
                buildUnits(OBSERVER, roboticsMap);
                printTotalTime();
            } else if (probesConditionJudge(GATEWAY, GATEWAY_FIN, gateWay)) {
                setTotalFacilityUnavailable(1, idList.get(GATEWAY), gateWayMap);
                gateWay++;
                setBuildings(GATEWAY);
                printTotalTime();
            }

            totalMinerals += mineralCalculatorGoal();
            totalGas += gasCalculatorGoal();
        }
        System.out.println("Total Minerals Left: " + totalMinerals + ", Gas left: " + totalGas);
    }


    //This methods are to check whether the gaol is achieved.

    /**
     * early stage to build the basic facility.
     * @return true present early goal is finished.
     */
    public boolean earlyStageGoal() {
        return (probes == PROBES_GOAL && pylon == PYLON_GOAL && gateWay == GATEWAY_GOAL
                && core == CORE_GOAL && assimilator == ASSIMILATOR_GOAL);
    }

    /**
     * This method is to judge whether it has achieved the goal.
     * @return false present not achieved.
     */
    public boolean achieveFinalGoalOne() {
        return ((stalker == STALKER_FIN)
                && (immortal == IMMORTAL_FIN) && (colossus == COLOSSUS_FIN)
                && (zealot == ZEALOT_FIN));
    }

    /**
     * Test whether the goal two is finished.
     * @return true presents goal is achieved.
     */
    public boolean achieveFinalGoalTwo() {
        return ((zealot == ZEALOT_FIN) && (stalker == STALKER_FIN) && (sentry == SENTRY_FIN) && (rayVoid == VOID_RAY_FIN));
    }

    /**
     * Whether the goal three has achieved.
     * @return true presents achieving.
     */
    public boolean achieveFinalGoalThree() {
        return ((zealot == ZEALOT_FIN) && (stalker == STALKER_FIN) && (sentry == SENTRY_FIN)
                && (colossus == COLOSSUS_FIN) && (phoenix == PHOENIX_FIN) && sentry == SENTRY_GOAL);
    }

    public boolean achieveGoalFour() {
        return ((stalker == STALKER_FIN) && (zealot == ZEALOT_FIN)
                && (sentry == SENTRY_FIN) && (highTemplar == HIGH_TEMPLAR_FIN));
    }

    public boolean achieveGoalFive() {
        return ((zealot == ZEALOT_FIN) && (stalker == STALKER_FIN) && (sentry == SENTRY_FIN)
                && (darkTemplar == DARK_TEMPLAR_FIN) && (carrier == CARRIER_FIN) && (observe == OBSERVER_FIN)
                && (immortal == IMMORTAL_FIN));
    }

    //check building exist method.
    public boolean templarArchExist() {
        return (totalmap.containsKey(17001) && (secondsTotal - totalmap.get(17001) >= timeList[TEMPLAR_ARCHIVES]));
    }

    public boolean twilightCouncilExist() {
        return (totalmap.containsKey(16001) && (secondsTotal - totalmap.get(16001) >= timeList[COUNCIL]));
    }

    public boolean darkShineExist() {
        return (totalmap.containsKey(18001) && (secondsTotal- totalmap.get(18001) >= timeList[DARK_SHRINE]));
    }

    public boolean starGateExist() {
        return (totalmap.containsKey(8001) && (secondsTotal - totalmap.get(8001) >= timeList[STARGATE]));
    }

    public boolean beaExist() {
        return (totalmap.containsKey(20001) && (secondsTotal - totalmap.get(20001) >= timeList[FLEET_BEACON]));
    }

    /**
     * Check whether robotics bay exist.
     * @return true presents exist.
     */
    public boolean bayExist() {
        return (totalmap.containsKey(19001) && secondsTotal - totalmap.get(19001) >= timeList[ROBOTICS_BAY]);
    }

    /**
     * check whether the robotics facility exists.
     * @return true presents it exist.
     */
    public boolean roboticsFacilityExist() {
        return (totalmap.containsKey(7001) && secondsTotal - totalmap.get(7001) >= timeList[ROBOTIC]);
    }

    /**
     * Core exists.
     * @return true presents core is begun to build.
     */
    public boolean judgeCoreBuild() {
        return (totalmap.containsKey(6001));
    }

    //general method to set the buildings and units.

    public void setBuildings(int type) {
        setBuildingPrepare(type, secondsTotal);
        deduceCurrency(mineralsCostList[type], gasCostList[type]);
        System.out.print("--- Build 1 " + convertList[type] + " --- ");
    }

    /**
     * This is the unit generator method.
     * @param type the unit type.
     * @param facilityMap the corresponding map for the unit to build.
     */
    public void buildUnits(int type, HashMap<Integer, Boolean> facilityMap) {
        setBuildingPrepare(type, secondsTotal);
        deduceCurrency(mineralsCostList[type], gasCostList[type]);
        //update the gateway map.
        facilityMap.put(idList.get(type), false);
        System.out.print("--- Build 1 " + convertList[type] + " --- ");
    }



    /**
     * This method is to assign the probes from minerals patch to gas.
     */
    public void balanceTheGas () {
        if (totalMinerals + 280 <= totalGas) {  //if the gas is much larger than minerals.
            if (numberMaxVelocity != 20) {
                System.out.print("*** Assign all the Probes from gas to minerals. ***");
                printTotalTime();
            }
            numberMaxVelocity = 20;
            totalNumberGasWorking = 0;
        } else if (totalGas <= totalMinerals + 150) { //if the gas is close to the minerals.
            if (numberMaxVelocity != 14) {
                System.out.print("*** Assign 6 probes to work for gas and 13 probes to work for minerals. *** ");
                printTotalTime();
            }
            totalNumberGasWorking = 6;
            numberMaxVelocity = 14;
        }
    }

    /**
     * This is to print out  the five goals in the specification.
     */
    public void printGoalSelection() throws IOException {

        writer = new BufferedWriter(new FileWriter("goalSelection.txt"));

        writer.write("1. 1 Zealot, 4 Stalkers, 2 Immortals, and 2 Colossi.");
        writer.write(System.getProperty( "line.separator" ));
        writer.write("2. 6 Zealot, 2 Stalkers, 3 Sentries, 4 Void Rays.");
        writer.write(System.getProperty( "line.separator" ));
        writer.write("3. 2 Zealot, 2 Stalkers, 1 Sentry, 2 Colossi, 5.Phoenix.");
        writer.write(System.getProperty( "line.separator" ));
        writer.write("4. 10 Zealots, 7 Stalkers, 2 Sentries, 3 High Templar.");
        writer.write(System.getProperty( "line.separator" ));
        writer.write("5. 8 Zealots, 10 Stalkers, 2 Sentries, 1 Immortal, 1 Observer, 3 Carriers,"
                + " 2 Dark Templar.");
        writer.write(System.getProperty( "line.separator" ));
        writer.write("6. Skip this part! Play by myself.");
        writer.write(System.getProperty( "line.separator" ));
        writer.write("--Enter the number to select your goal.");
        writer.close();

        //window.startUp();
        //window.setVisible(true);

        System.out.println("1. 1 Zealot, 4 Stalkers, 2 Immortals, and 2 Colossi.");
        System.out.println("2. 6 Zealot, 2 Stalkers, 3 Sentries, 4 Void Rays.");
        System.out.println("3. 2 Zealot, 2 Stalkers, 1 Sentry, 2 Colossi, 5.Phoenix.");
        System.out.println("4. 10 Zealots, 7 Stalkers, 2 Sentries, 3 High Templar.");
        System.out.println("5. 8 Zealots, 10 Stalkers, 2 Sentries, 1 Immortal, 1 Observer, 3 Carriers,"
                + " 2 Dark Templar.");
        System.out.println("6. Skip this part! Play by myself.");
        System.out.println("--Enter the number to select your goal.");
    }

    /**
     * assign probe to work for minerals or gas. (full push for gathering gas.)
     */
    public void probesJudgeAvailable() {
        if (secondsTotal - totalmap.get(idList.get(PROBE)) == timeList[PROBE]) { //The probe is available.
            if (totalmap.containsKey(4001) && (secondsTotal <= 235)
                    && secondsTotal - totalmap.get(4001) >= timeList[ASSIMILATOR] //assimilator has finished.
                    && totalNumberGasWorking + 1 <= assimilator * 3) { //to get the limit of the assimilator.
                System.out.print(">>> Assign three to gas working, two of them are from minerals patches");
                numberMaxVelocity -= 2;
                totalNumberGasWorking = totalNumberGasWorking + 3;
            } else {
                System.out.print("*** Assign one probe to minerals patch working " );
                numberMaxVelocity++;
            }
        }
    }

    /**
     * This is to put the building and units to the hash map and update the id List.
     * @param type units or building.
     * @param currentTime the time they are built.
     */
    public void setBuildingPrepare(int type, int currentTime) {
        int id = idList.get(type);
        id ++;
        idList.set(type, id);
        totalmap.put(id, currentTime);
    }

    /**
     * basic condition judgement apply to all the units and buildings.
     * @param type unit/building.
     * @param goal goal of building them.
     * @param currAmount current number of the units or buildings.
     * @return true goal not achieved and enough currency.
     */
    public boolean probesConditionJudge(int type, int goal, int currAmount) {
        return ((!achievementJudge(goal, currAmount))// goal achievement
                && (currencyJudgement(mineralsCostList[type], gasCostList[type])) //enough currency.
        );
    }

   //The following methods are to change the building situation.
    /**
     * Judge whether the depending building is available to build such a units
     * @param type unit type
     * @param facilityMap the map of building the unit.
     * @return true presents available.
     */
    public boolean dependingBuildingAvailable(int type, HashMap<Integer, Boolean> facilityMap) {
        int id = initialList.get(type) + 1;
        if (totalmap.containsKey(id)) {   //there exist gate way.
            int times = idList.get(type) - initialList.get(type);
            Set set = facilityMap.entrySet();
            Iterator iterator = set.iterator();
            for (int i = 0; i < times; i++) {
                Map.Entry pair = (Map.Entry) iterator.next(); //go over all the gateway map.
                boolean status = (boolean)pair.getValue();
//                System.out.println(status);
                if (status) { //check whether it is available.
                    facilityMap.remove(pair.getKey());
//                    System.out.println("This is true.");
                    return true;
                }

            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * make a judgement whether the nexus is enough for building a probe.
     * @param currTime the time need to be set in.
     * @return true presents it is valid.
     */
    public boolean judgeNexusAvailable(int currTime) {
        if (totalmap.get(1001) == 0) {
            return true;
        } else {
            return (currTime - totalmap.get(1001) >= 17);
        }
    }

    //general method for after building.
    /**
     * deduce the minerals and gas.
     * @param spendingMiners minerals need to be spent.
     * @param spendingGas gas need to be spent.
     */
    public void deduceCurrency(int spendingMiners, int spendingGas) {
        totalMinerals = totalMinerals - spendingMiners;
        totalGas = totalGas - spendingGas;
    }

    /**
     * make a judgment whether minerals and cost are enough for building the units or buildings.
     * @param achievement The aim of building such a units or buildings.
     * @param currAmount  The current number of them user has.
     * @return true small goal has achieved.
     */
    public boolean achievementJudge(int achievement, int currAmount) {
        return (currAmount == achievement);
    }

    /**
     * whether it is enough currency to build this units or building.
     * @param mineralsCost minerals cost of the units.
     * @param gasCost gas cost of the buildings.
     * @return true presents enough.
     */
    public boolean currencyJudgement(int mineralsCost, int gasCost) {
        return (mineralsCost <= totalMinerals && gasCost <= totalGas);
    }


    //This is the calculator.
    public int mineralCalculatorGoal() {
        double minerals = (41.0 / 60.0 * numberMaxVelocity);
        int addMinerals = (int) Math.round(minerals);
        return addMinerals;
    }

    public int gasCalculatorGoal() {
        double gas = (38.0 / 60.0 * totalNumberGasWorking);
        int addGas = (int) Math.round(gas);
        return addGas;
    }
}
