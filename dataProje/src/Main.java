import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int[][] array = new int[13][6];
    static int[] tempFor1605 = new int[10]; // planned order releases for 1605

    public static void main(String[] args) throws IOException {

        //Reading the Constants file
        Scanner input_constants = new Scanner(new FileReader("Constants"));

        //Making constants file an array so we can achieve it in other parts of the code
        for (int j = 0; j <= 12; j++)
            for (int i = 0; i <= 5; i++) {
                array[j][i] = input_constants.nextInt();
            }

        //Demand part
        int item_ID_ofDemanded =array[0][0];
        Scanner input = new Scanner(System.in);

        int[] demand = new int[10];

        for (int i = 0; i < 10; i++) {
            System.out.println("Please enter " + item_ID_ofDemanded + "'s " + (i + 1) + ". weeks gross requirement." +
                    " If there is not please write 0.");
            demand[i] = input.nextInt();
        }

//            array[week][0]=item_ID;
//            array[week][1]=amountOnHand;
//            array[week][2]=scheduledReceipt;
//            array[week][3]=arrivalOnWeek;
//            array[week][4]=leadTime;
//            array[week][5]=lotSizingRule;

        int[] onHand = new int[10];
        int[] scheduledReceipt = new int[10];
        int[] netRequirements = new int[10];
        int[] timePhasedNetReq = new int[10];
        int[] plannedOrderReleases = new int[10];
        int[] plannedOrderDelivery = new int[10];


        //schedule receipt, on hand, net requirements and time phased net requirements part

        //schedule receipt part
        if ((array[0][3]) > 0) {
            scheduledReceipt[(array[0][3]) - 1] = array[0][2];
        }
        //on hand, time phased requirement and net requirements part

        onHand[0] = (array[0][1] + scheduledReceipt[0]) - demand[0];
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
            if (i >= array[0][4]) {
                timePhasedNetReq[i - array[0][4]] = netRequirements[i];
            }
            // Non L4L part
            if (array[0][5] != 0) {
                //non L4L planned order releases and planned order delivery
                int lotSizing = array[0][5];
                int temp = 0;
                for (int k = 0; temp < netRequirements[i]; k++) {
                    temp = lotSizing * k;
                }

                if (i - array[0][4] >= 0) {
                    plannedOrderReleases[i - array[0][4]] = temp;
                }
                plannedOrderDelivery[i] = temp;
                if (i + 1 < 10) {
                    onHand[i + 1] += plannedOrderDelivery[i] - netRequirements[i];
                }
            }
        }




        //L4L part
        for (int i = 0; i < 10; i++) {
            if (array[0][5] == 0) {
                plannedOrderReleases[i] = timePhasedNetReq[i];
                if (i - array[0][4] >= 0) {
                    plannedOrderDelivery[i] = plannedOrderReleases[i - array[0][4]];
                }
            }
        }


        System.arraycopy(plannedOrderReleases, 0, tempFor1605, 0, 10);

        //Print part
        System.out.print("\nDemand of " + item_ID_ofDemanded + " is :                     "
                + Arrays.toString(demand) + "\nScheduled receipt of " + item_ID_ofDemanded + " is :          "
                + Arrays.toString(scheduledReceipt) + "\nOn Hand of " + item_ID_ofDemanded + " is :                    "
                + Arrays.toString(onHand) + "\nNet Requirement of " + item_ID_ofDemanded + " is :            "
                + Arrays.toString(netRequirements) + "\nTime-Phased Net Requirement of " + item_ID_ofDemanded + " is :"
                + Arrays.toString(timePhasedNetReq) + "\nPlanned Order Releases of " + item_ID_ofDemanded + " is :     "
                + Arrays.toString(plannedOrderReleases) + "\nPlanned Order Delivery of " + item_ID_ofDemanded + " is :     "
                + Arrays.toString(plannedOrderDelivery) + "\n");


        for (int i = 1; i < 13; i++) {
            SubItems.tableMaker(array[i][0]);
        }


    }


}