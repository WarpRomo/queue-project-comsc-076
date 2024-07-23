/*
 * Preston Hsiao, Ritik Jalisatgi, Maria Roceo Sy, Saathvika Sripathi
 * July 24, 2024
 * 
 * This program creates a Customer object, assigns a Customer to the
 * station, helps the Customer to check out items, and removes the
 * Customer from the station when all the items are checked out.
 */

public class CheckoutStation {

    Customer customer = null;
	//Constructor
    public CheckoutStation(){

    }
	//If the Customer has checked out all their items, remove the
	//Customer from the station
    public Customer checkoutTick(){

        if(this.customer == null){
            return null;
        }
        else{

            if(this.customer.items.size() == 0){

                Customer finished = this.customer;
                this.customer = null;
                return finished;

            }
            else{

                this.customer.checkoutTick();
                return null;

            }

        }


    }
	//Assign a Customer to the station
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

}
