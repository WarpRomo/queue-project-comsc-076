import java.util.ArrayList;

public class Item {

    public String itemName;

    // Item names
    public static String[] items = {"Banana", "Apple", "Grapes", "Oranges", "Broccoli"};

    // Constructor
    public Item(String itemName){
        this.itemName = itemName;
    }

    // Generate a random item
    public static Item randomItem(){
        // Select a random item from the items array
        return new Item(items[Helper.random(0, items.length-1)]);
    }

    // Generate a list of random items
    public static ArrayList<Item> randomItems(int itemCount){
        ArrayList<Item> items = new ArrayList<Item>();

        // Add random items to the list
        for(int i = 0; i < itemCount; i++){
            items.add(randomItem());
        }

        return items;
    }
}