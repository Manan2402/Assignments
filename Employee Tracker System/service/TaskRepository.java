package service;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository<T> {
    private List<T> taskList = new ArrayList<>();

    public void add(T task) {
        taskList.add(task);
    }

    public boolean remove(T task) {
        return taskList.remove(task);
    }

    public List<T> getAll() {
        return taskList;
    }

    public T find(java.util.function.Predicate<T> predicate) {
        return taskList.stream().filter(predicate).findFirst().orElse(null);
    }
}
