package util;

import service.TaskManager;
import model.Task;

public class TaskMonitor extends Thread {
    private TaskManager taskManager;

    public TaskMonitor(TaskManager taskManager) {
        this.taskManager = taskManager;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                var overdue = taskManager.getOverdueTasks();
                if (!overdue.isEmpty()) {
                    System.out.println("[TaskMonitor] Overdue Tasks:");
                    overdue.forEach(System.out::println);
                }
                Thread.sleep(60000); // 1 min
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
