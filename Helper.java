import java.util.ArrayList;

public class Helper {

    // Pause execution for n milliseconds
    public static void sleep(int n){
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() < time + n){}
    }

    // Generate a random integer between n1 and n2, inclusive
    public static int random(int n1, int n2){
        return (int) (Math.random() * (n2 - n1 + 1) + n1);
    }

    // Print checkout statistics
    public static void printStats(CheckoutStats stats){
        System.out.println("Max Queue Length: " + stats.maxQueueLength);
        System.out.println("Customers Served: " + stats.customersServed);
        System.out.println("Average Customer Wait: " + ((double)stats.customerTotalWait / stats.totalCustomers));
    }

    // Visualize checkout lines and stations
    public static void visualizeLines(ArrayList<Queue<Customer>> lines, ArrayList<CheckoutStation> stations){
        // Find the longest line
        int longestLine = 0;
        for(int i = 0; i < lines.size(); i++){
            longestLine = Math.max(longestLine, lines.get(i).size());
        }

        System.out.println("Visualizing Lines...");

        // Print customers in lines
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

        // Print checkout stations
        printPadded("Checkout | ", 15);
        for(int i = 0; i < stations.size(); i++){
            if(stations.get(i).customer != null) printPadded("C", 5);
            else printPadded("-", 5);
        }
        System.out.print("\n");
    }

    // Print a string with padding
    public static void printPadded(String string, int padding){
        System.out.print(string);
        for(int i = 0; i < padding - string.length(); i++){
            System.out.print(" ");
        }
    }

    // Print a line if the print flag is true
    public static void println(String string, boolean print){
        if(print) System.out.println(string);
    }
}