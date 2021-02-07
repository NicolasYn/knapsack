import java.util.*;

public class Knapsack {

    private int capacity;
    private List<Item> items;

    public Knapsack(int capacity, List<Item> items) {
        this.capacity = capacity;
        this.items = items;
    }

    public List<Item> greedyAlgorithm(){
        List<Item> itemsSorted = new ArrayList<>(items);
        itemsSorted.sort(Comparator.comparingDouble(Item::getValueOnWeight).reversed());
        int capacityItemInBag = 0;
        List<Item> itemsInBag = new ArrayList<>();
        for(Item item : itemsSorted) {
            if(item.getWeight() + capacityItemInBag > capacity) break;
            itemsInBag.add(item);
            capacityItemInBag += item.getWeight();
        }
        return itemsInBag;
    }

    public List<Item> exhaustiveEnumeration(){
        List<Item> itemsSorted = new ArrayList<>(items);
        itemsSorted.sort(Comparator.comparingDouble(Item::getValueOnWeight).reversed());
        return backtracking(items, new ArrayList<>(), new ArrayList<>());
    }

    private List<Item> backtracking(List<Item> items, List<Item> currentSolution, List<Item> maxSolution){
        for(Item item : items){
            if (item.getWeight() + getWeightList(currentSolution) <= capacity
                && getValueList(items) > getValueList(maxSolution)) {
                currentSolution.add(item);
                List<Item> removeCurrentItem = new ArrayList<>(items);
                removeCurrentItem.remove(item);
                backtracking(removeCurrentItem, currentSolution, maxSolution);
                currentSolution.remove(item);
            }
        }
        if (getValueList(currentSolution) > getValueList(maxSolution)){
            maxSolution.clear();
            maxSolution.addAll(currentSolution);
        }
        return maxSolution;
    }

    private int getWeightList(List<Item> items){
        int weight = 0;
        for(Item item : items){
            weight += item.getWeight();
        }
        return weight;
    }

    private int getValueList(List<Item> items){
        int value = 0;
        for(Item item : items){
            value += item.getValue();
        }
        return value;
    }

    public List<Item> dynamicProgramming(){
        int[][] tableMaxProfit = fillTable();
        int currentWeight = capacity;
        List<Item> result = new ArrayList<>();
        for (int i = items.size(); i > 0; i--) {
            if(currentWeight == 0) break;
            if(tableMaxProfit[i][currentWeight] == tableMaxProfit[i-1][currentWeight]) continue;
            result.add(items.get(i-1));
            currentWeight -= items.get(i-1).getWeight();
        }
        return result;
    }

    private int[][] fillTable() {
        int[][] tableMaxProfit = new int[items.size()+1][capacity+1];
       for (int i = 0; i < items.size(); i++) {
            for (int p = 0; p <= capacity; p++) {
                if(items.get(i).getWeight()<=p)
                   tableMaxProfit[i+1][p] = Math.max(tableMaxProfit[i][p],
                            tableMaxProfit[i][p-items.get(i).getWeight()]+items.get(i).getValue());
                else tableMaxProfit[i+1][p] = tableMaxProfit[i][p];
            }
        }
        return tableMaxProfit;
    }

    public void printInformation(List<Item> items){
        int weight = 0;
        int value = 0;
        for(Item item : items){
            weight+= item.getWeight();
            value += item.getValue();
        }
        System.out.println(items);
        System.out.println("Weight "+weight + " Value "+value);
    }
    public List<Item> getItems() {
        return items;
    }
}
