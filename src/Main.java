import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Item> items = new ArrayList<>();

    public static void main(String[] args) {

       addItems("./instances/sac0.txt");

       //items.add(new Item(1, 1));
       //items.add(new Item(2, 2));
       //items.add(new Item(3, 3));
       //items.add(new Item(4, 4));

        Knapsack knapsack = new Knapsack(1000, items);
        long start = System.nanoTime();
        knapsack.printInformation(knapsack.exhaustiveEnumeration());
        //knapsack.printInformation(knapsack.dynamicProgramming());
        long end = System.nanoTime();
        System.out.println("Time: "+(end-start)/1000000 +" ms");
    }

    private static void addItems(String file){
        try {
            Scanner scanner = readFiles(file);
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] split = scanner.nextLine().split(" ");
                setItems(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Scanner readFiles(String file) throws FileNotFoundException {
        return new Scanner(new FileReader(file));
    }

    private static void setItems(int weight, int value){
        items.add(new Item(weight, value));
    }

}
