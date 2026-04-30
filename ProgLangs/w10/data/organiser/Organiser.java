package data.organiser;

import java.util.ArrayList;
import java.util.Map;

public class Organiser<T> {
    private ArrayList<T> elems;
    private ArrayList<Map.Entry<Integer, Integer>> swaps;

    public Organiser(ArrayList<T> elems) {
        this.elems = elems;
    }

    public T get(int idx) {
        return elems.get(idx);
    }

    public ArrayList<T> get() {
        return elems;
    }

    public void addSwap(int from, int to) {
        swaps.add(Map.entry(from, to));
    }

    private void swap(int from, int to) {
        T temp = elems.get(from);
        elems.set(from, elems.get(to));
        elems.set(to, temp);
    }

    public void runSwaps() {
        for (Map.Entry<Integer,Integer> pair : swaps) {
            swap(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public String toString(){
        return "Organiser{elems=" + elems + ", swaps=" + swaps + "}"; 
    }
}
