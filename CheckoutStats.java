/*
 * Preston Hsiao, Ritik Jalisatgi, Maria Roceo Sy, Saathvika Sripathi
 * July 24, 2024
 * 
 * This class initializes four variables: maxQueueLength, customersServed
 * customerTotalWait, and the totalCustomers.
 * 
 * When a new CheckoutStats object is created with specific results, those
 * results are stored in that object using the constructor.
 */


public class CheckoutStats {
	//Initializing variables
    int maxQueueLength = 0;
    int customersServed = 0;
    int customerTotalWait = 0;
    int totalCustomers = 0;
	//Constructor
    public CheckoutStats(int maxQueueLength, int customersServed, int customerTotalWait, int totalCustomers){

        this.maxQueueLength = maxQueueLength;
        this.customersServed = customersServed;
        this.customerTotalWait = customerTotalWait;
        this.totalCustomers = totalCustomers;

    }

}
