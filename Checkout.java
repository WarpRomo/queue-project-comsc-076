import java.util.ArrayList;

public class Checkout {

    public static int[] customerRate = {5,10};

    public static int[] customerItems = {4,15};
    public static int[] checkoutSpeed = {2,5};

    public static int maxTicks = 100;
    public static int tickDelay = 0;
    public static int n = 3;

    public static boolean printTicks = false;

    public static void main(String[] args){

        CheckoutStats nLinesStats = nLines(n, maxTicks, tickDelay, false);
        CheckoutStats nLinesRandomStats = nLines(n, maxTicks, tickDelay, true);
        CheckoutStats oneLineStats = oneLine(n, maxTicks, tickDelay);


        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("N Line Stats Non-Random");
        Helper.printStats(nLinesStats);

        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("N Line Stats Random");
        Helper.printStats(nLinesRandomStats);

        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("One Line Stats");
        Helper.printStats(oneLineStats);

    }


    public static CheckoutStats nLines(int n, int maxTicks, int tickSleep, boolean randomChoice){

        ArrayList<CheckoutStation> stations = new ArrayList<CheckoutStation>();
        ArrayList<Queue<Customer>> lines = new ArrayList<Queue<Customer>>();

        int totalTicks = 0;

        int maxQueueLength = 0;
        int customersServed = 0;
        int customerTotalWait = 0;
        int totalCustomers = 0;

        for(int i = 0; i < n; i++){
            stations.add(new CheckoutStation());
            lines.add(new Queue<Customer>());
        }

        int nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);

        while(totalTicks < maxTicks){

            Helper.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", printTicks);
            Helper.println("Tick: " + totalTicks, printTicks);

            nextCustomerTime--;

            if(nextCustomerTime == 0){

                Customer newCustomer = new Customer(
                        Item.randomItems(Helper.random(customerItems[0], customerItems[1])),
                        Helper.random(checkoutSpeed[0], checkoutSpeed[1]));
                totalCustomers++;

                Queue<Customer> lineChoice = lines.get(0);
                int lineChoiceNum = 0;

                if(randomChoice){
                    lineChoiceNum = Helper.random(0, lines.size()-1);
                    lineChoice = lines.get(lineChoiceNum);
                }
                else{
                    for(int i = 1; i < lines.size(); i++) {

                        if (lines.get(i).size() < lineChoice.size()) {
                            lineChoice = lines.get(i);
                            lineChoiceNum = i;
                        }

                    }
                }

                lineChoice.enqueue(newCustomer);

                Helper.println("", printTicks);
                Helper.println(">New customer joined line #" + lineChoiceNum, printTicks);
                Helper.println(newCustomer.toString(), printTicks);
                Helper.println("", printTicks);

                nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);

            }

            for(int i = 0; i < stations.size(); i++){

                Customer finished = stations.get(i).checkoutTick();

                if(finished != null){
                    Helper.println("", printTicks);
                    Helper.println(">Customer finished at checkout station #" + i, printTicks);
                    Helper.println(finished.toString(), printTicks);
                    Helper.println("", printTicks);
                    customersServed++;

                }

                if(stations.get(i).customer == null){

                    Customer customer = null;

                    if(lines.get(i).size() > 0) customer = lines.get(i).dequeue();

                    if(customer != null){

                        Helper.println("", printTicks);
                        Helper.println(">New customer joined checkout #" + i, printTicks);
                        Helper.println(customer.toString(), printTicks);
                        Helper.println("", printTicks);

                        stations.get(i).setCustomer(customer);
                    }

                }

                ArrayList<Customer> waitingCustomers = lines.get(i).items;

                maxQueueLength = Math.max(maxQueueLength, waitingCustomers.size());

                for(int j = 0; j < waitingCustomers.size(); j++){
                    waitingCustomers.get(j).queueTick();
                    customerTotalWait++;
                }

            }

            Helper.println("", printTicks);
            if(printTicks) Helper.visualizeLines(lines, stations);
            Helper.println("", printTicks);

            Helper.sleep(tickSleep);
            totalTicks++;

        }

        Helper.println("━━━━", printTicks);
        Helper.println("Simulation Finished!", printTicks);

        CheckoutStats stats = new CheckoutStats(maxQueueLength, customersServed, customerTotalWait, totalCustomers);

        return stats;
    }

    public static CheckoutStats oneLine(int n, int maxTicks, int tickSleep){

        ArrayList<CheckoutStation> stations = new ArrayList<CheckoutStation>();
        ArrayList<Queue<Customer>> lines = new ArrayList<Queue<Customer>>();

        int totalTicks = 0;

        int maxQueueLength = 0;
        int customersServed = 0;
        int customerTotalWait = 0;
        int totalCustomers = 0;

        lines.add(new Queue<Customer>());

        for(int i = 0; i < n; i++){
            stations.add(new CheckoutStation());
        }

        int nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);

        while(totalTicks < maxTicks){

            Helper.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", printTicks);
            Helper.println("Tick: " + totalTicks, printTicks);

            nextCustomerTime--;

            if(nextCustomerTime == 0){

                Customer newCustomer = new Customer(
                        Item.randomItems(Helper.random(customerItems[0], customerItems[1])),
                        Helper.random(checkoutSpeed[0], checkoutSpeed[1]));
                totalCustomers++;

                Queue<Customer> lineChoice = lines.get(0);
                int lineChoiceNum = 0;

                lineChoice.enqueue(newCustomer);

                Helper.println("", printTicks);
                Helper.println(">New customer joined line #" + lineChoiceNum, printTicks);
                Helper.println(newCustomer.toString(), printTicks);
                Helper.println("", printTicks);

                nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);

            }

            for(int i = 0; i < stations.size(); i++){

                Customer finished = stations.get(i).checkoutTick();

                if(finished != null){
                    Helper.println("", printTicks);
                    Helper.println(">Customer finished at checkout station #" + i, printTicks);
                    Helper.println(finished.toString(), printTicks);
                    Helper.println("", printTicks);
                    customersServed++;
                }

                if(stations.get(i).customer == null){

                    Customer customer = null;

                    if(lines.get(0).size() > 0) customer = lines.get(0).dequeue();

                    if(customer != null){

                        Helper.println("", printTicks);
                        Helper.println(">New customer joined checkout #" + i, printTicks);
                        Helper.println(customer.toString(), printTicks);
                        Helper.println("", printTicks);

                        stations.get(i).setCustomer(customer);
                    }

                }

            }

            ArrayList<Customer> waitingCustomers = lines.get(0).items;

            maxQueueLength = Math.max(maxQueueLength, waitingCustomers.size());

            for(int j = 0; j < waitingCustomers.size(); j++){
                waitingCustomers.get(j).queueTick();
                customerTotalWait++;
            }

            Helper.println("", printTicks);
            if(printTicks) Helper.visualizeLines(lines, stations);
            Helper.println("", printTicks);

            Helper.sleep(tickSleep);
            totalTicks++;

        }

        Helper.println("━━━━", printTicks);
        Helper.println("Simulation Finished!", printTicks);
        CheckoutStats stats = new CheckoutStats(maxQueueLength, customersServed, customerTotalWait, totalCustomers);
        return stats;
    }


}
