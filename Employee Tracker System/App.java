import model.Employee;
import model.Task;
import model.Task.Status;
import model.Task.Priority;
import service.TaskManager;
import exception.TaskNotFoundException;
import util.TaskMonitor;

import java.time.LocalDate;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        Map<Integer, Employee> employees = new HashMap<>();
        int empIdCounter = 1, taskIdCounter = 1001;

        // Start background monitor
        TaskMonitor monitor = new TaskMonitor(manager);
        monitor.start();

        boolean running = true;
        while (running) {
            System.out.println("\n--- Employee Task Tracker ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Assign Task to Employee");
            System.out.println("3. Update Task Status");
            System.out.println("4. View Tasks for Employee");
            System.out.println("5. Search Tasks by Description");
            System.out.println("6. Sort Tasks by Priority (Employee)");
            System.out.println("7. Sort Tasks by Due Date (Employee)");
            System.out.println("8. Show Overdue Tasks");
            System.out.println("9. Show Tasks Due Tomorrow");
            System.out.println("10. Show Employees with >3 Pending Tasks");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            switch (choice) {
                case 1: // Add Employee
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter department: ");
                    String dept = sc.nextLine();
                    Employee emp = new Employee(empIdCounter, name, dept);
                    employees.put(empIdCounter, emp);
                    System.out.println("Added: " + emp);
                    empIdCounter++;
                    break;

                case 2: // Assign Task
                    System.out.print("Enter Employee ID: ");
                    int eid = Integer.parseInt(sc.nextLine());
                    Employee assignEmp = employees.get(eid);
                    if (assignEmp == null) {
                        System.out.println("Employee not found.");
                        break;
                    }
                    System.out.print("Enter Task Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Enter Due Date (YYYY-MM-DD): ");
                    LocalDate dueDate = LocalDate.parse(sc.nextLine());
                    System.out.print("Enter Priority (LOW, MEDIUM, HIGH): ");
                    Priority prio = Priority.valueOf(sc.nextLine().toUpperCase());
                    Task task = new Task(taskIdCounter, desc, Status.PENDING, dueDate, prio);
                    manager.assignTask(assignEmp, task);
                    System.out.println("Task assigned: " + task);
                    taskIdCounter++;
                    break;

                case 3: // Update Task Status
                    System.out.print("Enter Task ID: ");
                    int tid = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter new status (PENDING, IN_PROGRESS, COMPLETED): ");
                    Status status = Status.valueOf(sc.nextLine().toUpperCase());
                    try {
                        manager.updateTaskStatus(tid, status);
                        System.out.println("Status updated.");
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4: // View Tasks for Employee
                    System.out.print("Enter Employee ID: ");
                    eid = Integer.parseInt(sc.nextLine());
                    assignEmp = employees.get(eid);
                    if (assignEmp == null) {
                        System.out.println("Employee not found.");
                        break;
                    }
                    List<Task> tasks = manager.getTasksForEmployee(assignEmp);
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks assigned.");
                    } else {
                        tasks.forEach(System.out::println);
                    }
                    break;

                case 5: // Search Tasks by Description
                    System.out.print("Enter keyword: ");
                    String keyword = sc.nextLine();
                    manager.searchTasksByDescription(keyword).forEach(System.out::println);
                    break;

                case 6: // Sort Tasks by Priority
                    System.out.print("Enter Employee ID: ");
                    eid = Integer.parseInt(sc.nextLine());
                    assignEmp = employees.get(eid);
                    if (assignEmp == null) {
                        System.out.println("Employee not found.");
                        break;
                    }
                    manager.sortTasksByPriority(assignEmp).forEach(System.out::println);
                    break;

                case 7: // Sort Tasks by Due Date
                    System.out.print("Enter Employee ID: ");
                    eid = Integer.parseInt(sc.nextLine());
                    assignEmp = employees.get(eid);
                    if (assignEmp == null) {
                        System.out.println("Employee not found.");
                        break;
                    }
                    manager.sortTasksByDueDate(assignEmp).forEach(System.out::println);
                    break;

                case 8: // Show Overdue Tasks
                    manager.getOverdueTasks().forEach(System.out::println);
                    break;

                case 9: // Show Tasks Due Tomorrow
                    manager.getTasksDueTomorrow().forEach(System.out::println);
                    break;

                case 10: // Employees with >3 pending tasks
                    manager.getEmployeesWithMoreThan3PendingTasks().forEach(System.out::println);
                    break;

                case 0: // Exit
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        monitor.interrupt();
        sc.close();
        System.out.println("Exiting Employee Task Tracker.");
    }
}