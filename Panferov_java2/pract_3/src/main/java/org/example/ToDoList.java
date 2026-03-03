import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

    private static final String FILE_NAME = "tasks.dat";
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1:
                    showTasks();
                    break;
                case 2:
                    addTask(scanner);
                    break;
                case 3:
                    deleteTask(scanner);
                    break;
                case 4:
                    editTask(scanner);
                    break;
                case 0:
                    saveTasks();
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор!");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== TO DO LIST ===");
        System.out.println("1. Показать задачи");
        System.out.println("2. Добавить задачу");
        System.out.println("3. Удалить задачу");
        System.out.println("4. Редактировать задачу");
        System.out.println("0. Выход");
        System.out.print("Выберите пункт: ");
    }

    private static void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Введите название задачи: ");
        String title = scanner.nextLine();
        tasks.add(new Task(title));
        System.out.println("Задача добавлена.");
    }

    private static void deleteTask(Scanner scanner) {
        showTasks();
        if (tasks.isEmpty()) return;

        System.out.print("Введите номер задачи для удаления: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index > 0 && index <= tasks.size()) {
            tasks.remove(index - 1);
            System.out.println("Задача удалена.");
        } else {
            System.out.println("Неверный номер!");
        }
    }

    private static void editTask(Scanner scanner) {
        showTasks();
        if (tasks.isEmpty()) return;

        System.out.print("Введите номер задачи для редактирования: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index > 0 && index <= tasks.size()) {
            System.out.print("Введите новое название: ");
            String newTitle = scanner.nextLine();
            tasks.get(index - 1).setTitle(newTitle);
            System.out.println("Задача обновлена.");
        } else {
            System.out.println("Неверный номер!");
        }
    }

    private static void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла.");
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tasks = (ArrayList<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки файла.");
        }
    }
}