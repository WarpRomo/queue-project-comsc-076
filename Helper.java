import java.util.ArrayList;

public class Helper {

    public static void sleep(int n){

        long time = System.currentTimeMillis();

        while(System.currentTimeMillis() < time + n){}

    }

    public static int random(int n1, int n2){

        return (int) (Math.random() * (n2 - n1 + 1) + n1);

    }

    public static void printStats(CheckoutStats stats){

        System.out.println("Max Queue Length: " + stats.maxQueueLength);
        System.out.println("Customers Served: " + stats.customersServed);
        System.out.println("Average Customer Wait: " + stats.customerTotalWait);//((double)stats.customerTotalWait / stats.totalCustomers));

    }

    public static void visualizeLines(ArrayList<Queue<Customer>> lines, ArrayList<CheckoutStation> stations){

        int longestLine = 0;

        for(int i = 0; i < lines.size(); i++){
            longestLine = Math.max(longestLine, lines.get(i).size());
        }

        System.out.println("Visualizing Lines...");

        for(int i = longestLine; i > 0; i--){
            int index = i - 1;

            printPadded("", 15);

            for(int j = 0; j < lines.size(); j++){
                if(lines.get(j).size() > index){
                    printPadded("C", 5);
                }
                else{
                    printPadded("", 5);
                }
            }

            System.out.print("\n");

        }

        printPadded("Checkout | ", 15);
        for(int i = 0; i < stations.size(); i++){

            if(stations.get(i).customer != null) printPadded("C", 5);
            else printPadded("-", 5);

        }
        System.out.print("\n");

    }

    public static void printPadded(String string, int padding){

        System.out.print(string);
        for(int i = 0; i < padding - string.length(); i++){
            System.out.print(" ");
        };

    }

    public static void println(String string, boolean print){
        if(print) System.out.println(string);
    }

}
