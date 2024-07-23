/*
 * Preston Hsiao, Ritik Jalisatgi, Maria Roceo Sy, Saathvika Sripathi
 * July 24, 2024
 * 
 * This program uses queues to compare three different models for
 * self-checkout stations at a grocery store.
 * 
 * Customers enter a queue between every 8 to 20 minutes. Based on the
 * model being tested, a Customer will enter a queue to later enter one
 * of the three checkout stations. If the "fewest people" model is being
 * tested, then each Customer is assigned to the queue with the fewest
 * number of people. If the random choice model is being tested, then
 * each Customer is assigned to a random queue. If the one line model is
 * being tested, then each Customer is assigned to the same one queue.
 * When a Customer enters the checkout system, based on the number of
 * items they have, it will take a specific time for them to finish.
 * They will check out between 4 and 15 items. Depending on the checkout
 * speed the customer was initialized with, they will take a certain
 * number of ticks to checkout each item. At the end of the program, the
 * max queue length, total number of customers served, and them average
 * wait time for each Customer.
 */

import java.util.ArrayList;

public class Checkout {

    public final static int[] customerRate = {8,20};

    public final static int[] customerItems = {4,15};
    public final static int[] checkoutSpeed = {2,5};

    public final static int maxTicks = 2*3600;
    public final static int tickDelay = 0;
    public final static int n = 3;

    public final static boolean printTicks = false;

    public static void main(String[] args){

        CheckoutStats nLinesStats = nLines(n, maxTicks, tickDelay, false); //fewest people model
        CheckoutStats nLinesRandomStats = nLines(n, maxTicks, tickDelay, true); //random choice model
        CheckoutStats oneLineStats = oneLine(n, maxTicks, tickDelay); //one line model

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


/* 
 *  This method creates n number of stations and lines for each of the
 *  stations. In a loop, new Customers are created, the line sizes are
 *  determined, and depending on if it is using the random choice, or
 *  the fewest people choice, those customers are added to a specific
 *  line and enqueued in the queue.
 * 
 *  If a checkout station is empty, a customer is dequeued from its
 *  line, and put into that station. The wait time for the rest of the
 *  customers in that line is also calculated. After the 2 hours of
 *  running the program, the while loop ends and the recorded stats are
 *  assigned to a CheckoutStats object, where the results are stored.
 */
	public static CheckoutStats nLines(int n, int maxTicks, int tickSleep, boolean randomChoice){
		//Declaration of stations and lines
        ArrayList<CheckoutStation> stations = new ArrayList<CheckoutStation>();
        ArrayList<Queue<Customer>> lines = new ArrayList<Queue<Customer>>();
		//Initializing variables
        int totalTicks = 0;

        int maxQueueLength = 0;
        int customersServed = 0;
        int customerTotalWait = 0;
        int totalCustomers = 0;
		//n number of stations and lines are created
        for(int i = 0; i < n; i++){
            stations.add(new CheckoutStation());
            lines.add(new Queue<Customer>());
        }
		//Random wait time between an upper and lower limit is generated
        int nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);
		//This while loop runs for two hours (7200 seconds)
        while(totalTicks < maxTicks){

            Helper.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", printTicks);
            Helper.println("Tick: " + totalTicks, printTicks);

            nextCustomerTime--;
			//if the Random wait time is less than 0
            if(nextCustomerTime <= 0){
				//new Customer object created
                Customer newCustomer = new Customer(
                        Item.randomItems(Helper.random(customerItems[0], customerItems[1])),
                        Helper.random(checkoutSpeed[0], checkoutSpeed[1]));
                totalCustomers++;

                Queue<Customer> lineChoice = null;
                int lineChoiceNum = -1;
                int lineChoiceSize = Integer.MAX_VALUE;
				//for the randomChoice model, a random line choice number
				//and the corresponding line choices are chosen
                if(randomChoice){
                    lineChoiceNum = Helper.random(0, lines.size()-1);
                    lineChoice = lines.get(lineChoiceNum);
                }
                //for the fewestPeople model, the smallest line is selected
                else{
                    for(int i = 0; i < lines.size(); i++) {
                        int size = lines.get(i).size() + (stations.get(i).customer != null ? 1 : 0);

                        if (size < lineChoiceSize) {
                            lineChoice = lines.get(i);
                            lineChoiceNum = i;
                            lineChoiceSize = size;
                        }

                    }
                }
				//the Customer is enqueued in that line
                lineChoice.enqueue(newCustomer);

                Helper.println("", printTicks);
                Helper.println(">New customer joined line #" + lineChoiceNum, printTicks);
                Helper.println(newCustomer.toString(), printTicks);
                Helper.println("", printTicks);
				//Random wait time between an upper and lower limit is generated
                nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);

            }
			//for each of the stations
            for(int i = 0; i < stations.size(); i++){
				//check if the station has a Customer assigned
                Customer finished = stations.get(i).checkoutTick();
				//if a Customer is assigned, customersServed is incremented
                if(finished != null){
                    Helper.println("", printTicks);
                    Helper.println(">Customer finished at checkout station #" + i, printTicks);
                    Helper.println(finished.toString(), printTicks);
                    Helper.println("", printTicks);
                    customersServed++;

                }
				//if a Customer is not assigned
                if(stations.get(i).customer == null){
					//new Customer object is created
                    Customer customer = null;
					//if the line size is greater than 0, dequeue a
					//Customer from the line of that checkout station
                    if(lines.get(i).size() > 0) customer = lines.get(i).dequeue();

                    if(customer != null){

                        Helper.println("", printTicks);
                        Helper.println(">New customer joined checkout #" + i, printTicks);
                        Helper.println(customer.toString(), printTicks);
                        Helper.println("", printTicks);
						//assign that Customer to that checkout station
                        stations.get(i).setCustomer(customer);
                    }

                }
				//get the count of the Customers waiting in that checkout line
                ArrayList<Customer> waitingCustomers = lines.get(i).items;

                maxQueueLength = Math.max(maxQueueLength, waitingCustomers.size());

                //System.out.println(waitingCustomers.size());

				//increment the waitTime for each Customer
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
		//store all the data in a CheckoutStats object
        CheckoutStats stats = new CheckoutStats(maxQueueLength, customersServed, customerTotalWait, totalCustomers);

        return stats;
    }

/* 
 *  This method creates n number of stations and 1 queue. In a loop,
 *  new Customers are created and enqueued in the only queue.
 * 
 *  If a checkout station is empty, a customer is dequeued from the
 *  line, and put into that station. The wait time for the rest of the
 *  customers in the line is also calculated. After the 2 hours of
 *  running the program, the while loop ends and the recorded stats are
 *  assigned to a CheckoutStats object, where the results are stored.
 */
    public static CheckoutStats oneLine(int n, int maxTicks, int tickSleep){
		//Declaration of stations and lines
        ArrayList<CheckoutStation> stations = new ArrayList<CheckoutStation>();
        ArrayList<Queue<Customer>> lines = new ArrayList<Queue<Customer>>();
		//Initializing variables
        int totalTicks = 0;

        int maxQueueLength = 0;
        int customersServed = 0;
        int customerTotalWait = 0;
        int totalCustomers = 0;
		//only one line is created
        lines.add(new Queue<Customer>());
		//n number of stations are created
        for(int i = 0; i < n; i++){
            stations.add(new CheckoutStation());
        }
		//Random wait time between an upper and lower limit is generated
        int nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);
		//This while loop runs for two hours (7200 seconds)
        while(totalTicks < maxTicks){

            Helper.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", printTicks);
            Helper.println("Tick: " + totalTicks, printTicks);

            nextCustomerTime--;
			//if the random wait time is equal to 0
            if(nextCustomerTime == 0){
				//new Customer object created
                Customer newCustomer = new Customer(
                        Item.randomItems(Helper.random(customerItems[0], customerItems[1])),
                        Helper.random(checkoutSpeed[0], checkoutSpeed[1]));
                totalCustomers++;

                Queue<Customer> lineChoice = lines.get(0);
                int lineChoiceNum = 0;
				//Customer is enqueued to the only available queue
                lineChoice.enqueue(newCustomer);

                Helper.println("", printTicks);
                Helper.println(">New customer joined line #" + lineChoiceNum, printTicks);
                Helper.println(newCustomer.toString(), printTicks);
                Helper.println("", printTicks);
				//Random wait time between an upper and lower limit is generated
                nextCustomerTime = Helper.random(customerRate[0], customerRate[1]);

            }
			//for each of the stations
            for(int i = 0; i < stations.size(); i++){
				//check if the station has a Customer assigned
                Customer finished = stations.get(i).checkoutTick();
				//if a Customer is assigned, customersServed is incremented
                if(finished != null){
                    Helper.println("", printTicks);
                    Helper.println(">Customer finished at checkout station #" + i, printTicks);
                    Helper.println(finished.toString(), printTicks);
                    Helper.println("", printTicks);
                    customersServed++;
                }
				//if a Customer is not assigned
                if(stations.get(i).customer == null){
					//new Customer object is created
                    Customer customer = null;
					//if the line size is greater than 0, dequeue a
					//Customer from the line
                    if(lines.get(0).size() > 0) customer = lines.get(0).dequeue();

                    if(customer != null){

                        Helper.println("", printTicks);
                        Helper.println(">New customer joined checkout #" + i, printTicks);
                        Helper.println(customer.toString(), printTicks);
                        Helper.println("", printTicks);
						//assign that Customer to that checkout station
                        stations.get(i).setCustomer(customer);
                    }

                }

            }
			//get the count of the Customers waiting in the line
            ArrayList<Customer> waitingCustomers = lines.get(0).items;

            maxQueueLength = Math.max(maxQueueLength, waitingCustomers.size());
			//increment the wait time for each Customer
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
        //store all the data in a CheckoutStats object
        CheckoutStats stats = new CheckoutStats(maxQueueLength, customersServed, customerTotalWait, totalCustomers);
        
        return stats;
    }


}
