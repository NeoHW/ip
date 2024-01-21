import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
import java.util.stream.Collectors;

public class Duke {
    // class variable
    static String CHATBOT_NAME = "ByteBuddy";
    static String solidLineBreak = "____________________________________________________________";
    static String START_MESSAGE = "Hello! I'm " + CHATBOT_NAME + "\n" + "\t What can I do for you?";
    static String BYE_MESSAGE = "Sad to see you leave :(";

    static ArrayList<Task> taskList;

    public static void main(String[] args) throws DukeException {
        Scanner sc = new Scanner(System.in);
        taskList = new ArrayList<>();

        // start
        printWithSolidLineBreak(START_MESSAGE);

        // repeating user commands
        label:
        while (true) {
            String command = sc.next();
            String info = sc.nextLine().trim();

            try {
                switch (command) {
                    case "bye":
                        break label;
                    case "list":
                        printTaskList(taskList);
                        break;
                    case "mark":
                        int markIndex = Integer.parseInt(info.trim()) - 1;
                        if (markIndex < 0 || markIndex >= taskList.size()) {
                            throw new DukeException("we do not have this task number!!");
                        }
                        String markToPrint = taskList.get(markIndex).markAsDone();
                        printWithSolidLineBreak(markToPrint);
                        break;
                    case "unmark":
                        int unmarkIndex = Integer.parseInt(info.trim()) - 1;
                        if (unmarkIndex < 0 || unmarkIndex >= taskList.size()) {
                            throw new DukeException("we do not have this task number!!");
                        }
                        String unmarkToPrint =taskList.get(unmarkIndex).unmarkAsDone();
                        printWithSolidLineBreak(unmarkToPrint);
                        break;
                    case "todo":
                        if (info.isEmpty()) {
                            throw new DukeException("The description of a todo cannot be empty??");
                        }
                        Task todo = new Todo(info);
                        taskList.add(todo);
                        printTaskAddedWithSolidLineBreak(todo);
                        break;
                    case "deadline":
                        if (info.isEmpty()) {
                            throw new DukeException("The description of a deadline cannot be empty??");
                        }
                        List<String> deadlineInfo = splitStringWithTrim(info, "/");
                        Task deadline = new Deadline(deadlineInfo.get(0), deadlineInfo.get(1).substring(3));
                        taskList.add(deadline);
                        printTaskAddedWithSolidLineBreak(deadline);
                        break;
                    case "event":
                        if (info.isEmpty()) {
                            throw new DukeException("The description of a event cannot be empty??");
                        }
                        List<String> eventInfo = splitStringWithTrim(info, "/");
                        Task event = new Event(eventInfo.get(0), eventInfo.get(1).substring(5), eventInfo.get(2).substring(3));
                        taskList.add(event);
                        printTaskAddedWithSolidLineBreak(event);
                        break;
                    default:
                        throw new DukeException("Sorry but this command does not exist~");
                }
            } catch (DukeException e) {
                printWithSolidLineBreak(e.getMessage());
            }
        }

        // bye
        printWithSolidLineBreak(BYE_MESSAGE);
    }

    public static List<String> splitStringWithTrim(String info, String separator) {
        return Arrays.stream(info.split(separator)).map(String::trim).collect(Collectors.toList());
    }

    public static void printWithSolidLineBreak(String s) {
        System.out.println("\t" + solidLineBreak);
        System.out.println("\t " + s);
        System.out.println("\t" + solidLineBreak);
    }

    public static void printTaskAddedWithSolidLineBreak(Task task) {
        System.out.println("\t" + solidLineBreak);
        System.out.println("\t Got it. I've Added this task:");
        System.out.println("\t\t " + task);
        System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("\t" + solidLineBreak);
    }

    public static void printTaskList(ArrayList<Task> taskList) {
        System.out.println("\t" + solidLineBreak);
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("\t " + (i+1) + "." + taskList.get(i));
        }
        System.out.println("\t" + solidLineBreak);
    }
}
