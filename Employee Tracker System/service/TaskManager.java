package service;

import model.Employee;
import model.Task;
import model.Task.Status;
import exception.TaskNotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private Map<Employee, List<Task>> employeeTasks = new HashMap<>();
    private TaskRepository<Task> taskRepo = new TaskRepository<>();

    public void assignTask(Employee emp, Task task) {
        employeeTasks.computeIfAbsent(emp, k -> new ArrayList<>()).add(task);
        taskRepo.add(task);
    }

    public void updateTaskStatus(int taskId, Status status) throws TaskNotFoundException {
        Task task = taskRepo.find(t -> t.getId() == taskId);
        if (task == null) throw new TaskNotFoundException("Task ID " + taskId + " not found.");
        task.setStatus(status);
    }

    public List<Task> getTasksForEmployee(Employee emp) {
        return employeeTasks.getOrDefault(emp, Collections.emptyList());
    }

    public List<Task> searchTasksByDescription(String keyword) {
        return taskRepo.getAll().stream()
                .filter(t -> t.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> sortTasksByPriority(Employee emp) {
        return getTasksForEmployee(emp).stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Task> sortTasksByDueDate(Employee emp) {
        return getTasksForEmployee(emp).stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    public List<Task> getOverdueTasks() {
        LocalDate now = LocalDate.now();
        return taskRepo.getAll().stream()
                .filter(t -> t.getDueDate().isBefore(now) && t.getStatus() != Status.COMPLETED)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksDueTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return taskRepo.getAll().stream()
                .filter(t -> t.getDueDate().equals(tomorrow))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesWithMoreThan3PendingTasks() {
        return employeeTasks.entrySet().stream()
                .filter(e -> e.getValue().stream()
                        .filter(t -> t.getStatus() == Status.PENDING)
                        .count() > 3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
