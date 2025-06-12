package MainLib;

import java.util.*;
public class Repo<T> {
    private List<T> items = new ArrayList<>();
    public void add(T item) { items.add(item); }
    public void remove(T item) { items.remove(item); }
    public List<T> getAll() { return items; }
}