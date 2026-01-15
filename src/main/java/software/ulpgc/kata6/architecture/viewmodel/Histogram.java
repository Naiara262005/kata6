package software.ulpgc.kata6.architecture.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram implements Iterable<Integer> {
    private final Map<Integer, Integer> value;
    private final Map<String, String> labels;

    public Histogram(Map<String, String> labels) {
        this.value = new HashMap<>();
        this.labels = labels;
    }

    public void add(int bin) {
        this.value.put(bin, count(bin)+1);
    }
    public int count(int bin) {
        return this.value.getOrDefault(bin, 0);
    }

    public int size(){
        return value.size();

    }

    @Override
    public Iterator<Integer> iterator() {
        return value.keySet().iterator();
    }
    public boolean isEmpty(){
        return value.isEmpty();
    }
    public String title(){
        return labels.getOrDefault("title", "");
    }

    public String x(){
        return labels.getOrDefault("x", "");
    }

    public String legend(){
        return labels.getOrDefault("legend", "");
    }
}
