public class CheckoutStats {

    int maxQueueLength = 0;
    int customersServed = 0;
    int customerTotalWait = 0;
    int totalCustomers = 0;

    public CheckoutStats(int maxQueueLength, int customersServed, int customerTotalWait, int totalCustomers){

        this.maxQueueLength = maxQueueLength;
        this.customersServed = customersServed;
        this.customerTotalWait = customerTotalWait;
        this.totalCustomers = totalCustomers;

    }

}
