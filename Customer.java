/*
 * Preston Hsiao, Ritik Jalisatgi, Maria Roceo Sy, Saathvika Sripathi
 * July 24, 2024
 * 
 * This class represents a customer, their wait time in the queue,
 * and their checkout time. It also prints out the statistics of the
 * customer with a toString method.
 */


import java.util.ArrayList;

public class Customer {

	//Two variables are initialized: waitTime and speed.
    ArrayList<Item> items;

    int waitTime = 0;
    int speed = 5;

	//The constructor for this class initializes a customer with the
	//number of items they carry and their speed.
    public Customer(ArrayList<Item> items, int speed){

        this.items = items;
        this.speed = speed;

    }

	//This method increments the waitTime of the customers in the checkout line.
    public void queueTick(){
        this.waitTime++;
    }

	//Depending on the checkout speed the customer was initialized with,
	//they will take a certain number of ticks to checkout each item.
    public void checkoutTick(){

        int rand = (int)(Math.random() * speed);

        if(rand == 0){
            items.remove(items.size()-1);
        }

    }

	//This method prints out all the statistics regarding the customer including:
	//item names, total wait time, and the checkout speed of the customer.
    public String toString(){

        String nicePrint = "━━━━";
        String itemsString = "Items: ";

        for(int i = 0; i < items.size(); i++){
            itemsString += items.get(i).itemName + " ";
        }

        String wait = "Total Wait: " + waitTime + " ticks";
        String speedS = "Checkout Speed: " + speed;

        return nicePrint + "\n" + itemsString + "\n" + wait + "\n" + speedS + "\n" + nicePrint;

    }



}
