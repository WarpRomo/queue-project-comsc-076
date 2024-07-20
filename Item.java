import java.util.ArrayList;

public class Item {

    public String itemName;

    public static String[] items = {"Banana", "Apple", "Grapes", "Oranges", "Broccoli"};

    public Item(String itemName){
        this.itemName = itemName;
    }

    public static Item randomItem(){

        return new Item(items[Helper.random(0, items.length-1)]);

    }

    public static ArrayList<Item> randomItems(int itemCount){

        ArrayList<Item> items = new ArrayList<Item>();

        for(int i = 0; i < itemCount; i++){

            items.add(randomItem());

        }

        return items;

    }

}
