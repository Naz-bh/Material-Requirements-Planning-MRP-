import java.util.Arrays;

public class SubItems extends Main {

    static int[] tempFor13122 = new int[10]; // planned order releases for 13122
    static int[] tempFor314 = new int[10]; // planned order releases for 1314
    static int[] tempFor11495 = new int[10]; // planned order releases for 11495

    public static void tableMaker(int item_ID_ofDemanded) {

        int[] demand = new int[10];
        int[] scheduledReceipt = new int[10];
        int[] netRequirements = new int[10];
        int[] timePhasedNetReq = new int[10];
        int[] plannedOrderReleases = new int[10];
        int[] plannedOrderDelivery = new int[10];
        int[] onHand = new int[10];

        // We are taking parent's planned order releases and assign it to child's demand.
        for (int i = 0; i < 10; i++) {
            if (item_ID_ofDemanded == 13122) {
                demand[i] = tempFor1605[i];
            } else if (item_ID_ofDemanded == 48) {
                demand[i] = tempFor1605[i];
            } else if (item_ID_ofDemanded == 118) {
                demand[i] = tempFor1605[i];
            } else if (item_ID_ofDemanded == 62) {
                demand[i] = (4 * tempFor1605[i]) + (2 * tempFor13122[i]); // 062 is used in two parts
            } else if (item_ID_ofDemanded == 314) {
                demand[i] = tempFor1605[i];
            } else if (item_ID_ofDemanded == 14127) {
                demand[i] = (4 * tempFor1605[i]) + (6 * tempFor314[i]); // 14127 is used in two parts
            } else if (item_ID_ofDemanded == 457) {
                demand[i] = tempFor13122[i];
            } else if (item_ID_ofDemanded == 11495) {
                demand[i] = tempFor13122[i];
            } else if (item_ID_ofDemanded == 2142) {
                demand[i] = tempFor314[i];
            } else if (item_ID_ofDemanded == 19) {
                demand[i] = tempFor314[i];
            } else if (item_ID_ofDemanded == 129) {
                demand[i] = tempFor11495[i];
            } else if (item_ID_ofDemanded == 1118) {
                demand[i] = tempFor11495[i];
            }


        }

        //schedule receipt, on hand, net requirements and time phased net requirements part

        for (int j = 0; j < 13; j++) {
            //schedule receipt part
            if (item_ID_ofDemanded == array[j][0] && (array[j][3]) > 0) {
                scheduledReceipt[(array[j][3]) - 1] = array[j][2];
            }
            //on hand, time phased requirement and net requirements part
            if (item_ID_ofDemanded == array[j][0]) {
                onHand[0] = (array[j][1] + scheduledReceipt[0]) - demand[0];
                for (int i = 0; i < 10; i++) {
                    //On hand part
                    if (onHand[i] + scheduledReceipt[i] >= demand[i]) {
                        if (i < 9) {
                            onHand[i + 1] = (onHand[i] + scheduledReceipt[i]) - demand[i];
                        }
                        //Net requirements part
                    } else {
                        netRequirements[i] = demand[i] - (onHand[i] + scheduledReceipt[i]);
                    }

                    //Time phased net requirements part
                    if (i >= array[j][4]) {
                        timePhasedNetReq[i - array[j][4]] = netRequirements[i];
                    }
                    // Non L4L part
                    if (array[j][5] != 0) {
                        //non L4L planned order releases and planned order delivery
                        int lotSizing = array[j][5];
                        int temp = 0;
                        for (int k = 0; temp < netRequirements[i]; k++) {
                            temp = lotSizing * k;
                        }
                        if (i - array[j][4] >= 0) {
                            plannedOrderReleases[i - array[j][4]] = temp;
                        }
                        plannedOrderDelivery[i] = temp;
                        if (i + 1 < 10) {
                            onHand[i + 1] += plannedOrderDelivery[i] - netRequirements[i];
                        }

                    }

                }
            }

        }

        //L4L part
        for (int j = 0; j < 13; j++) {
            if (item_ID_ofDemanded == array[j][0]) {
                for (int i = 0; i < 10; i++) {
                    if (array[j][5] == 0) {
                        plannedOrderReleases[i] = timePhasedNetReq[i];
                        if (i - array[j][4] >= 0) {
                            plannedOrderDelivery[i] = plannedOrderReleases[i - array[j][4]];
                        }
                    }
                }
            }
        }

        if (item_ID_ofDemanded == 13122) {
            System.arraycopy(plannedOrderReleases, 0, tempFor13122, 0, 10);
        } else if (item_ID_ofDemanded == 314) {
            System.arraycopy(plannedOrderReleases, 0, tempFor314, 0, 10);
        } else if (item_ID_ofDemanded == 11495) {
            System.arraycopy(plannedOrderReleases, 0, tempFor11495, 0, 10);
        }

        // Print part
        System.out.print("\nDemand of " + item_ID_ofDemanded + " is :                     "
                + Arrays.toString(demand) + "\nScheduled receipt of " + item_ID_ofDemanded + " is :          "
                + Arrays.toString(scheduledReceipt) + "\nOn Hand of " + item_ID_ofDemanded + " is :                    "
                + Arrays.toString(onHand) + "\nNet Requirement of " + item_ID_ofDemanded + " is :            "
                + Arrays.toString(netRequirements) + "\nTime-Phased Net Requirement of " + item_ID_ofDemanded + " is :"
                + Arrays.toString(timePhasedNetReq) + "\nPlanned Order Releases of " + item_ID_ofDemanded + " is :     "
                + Arrays.toString(plannedOrderReleases) + "\nPlanned Order Delivery of " + item_ID_ofDemanded + " is :     "
                + Arrays.toString(plannedOrderDelivery) + "\n");

    }


}