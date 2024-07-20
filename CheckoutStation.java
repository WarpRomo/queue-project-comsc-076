public class CheckoutStation {

    Customer customer = null;

    public CheckoutStation(){

    }

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

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

}
