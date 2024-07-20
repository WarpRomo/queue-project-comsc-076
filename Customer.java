import java.util.ArrayList;

public class Customer {

    ArrayList<Item> items;

    int waitTime = 0;
    int speed = 5;

    public Customer(ArrayList<Item> items, int speed){

        this.items = items;
        this.speed = speed;

    }

    public void queueTick(){
        this.waitTime++;
    }

    public void checkoutTick(){

        int rand = (int)(Math.random() * speed);

        if(rand == 0){
            items.remove(items.size()-1);
        }

    }

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
