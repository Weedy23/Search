import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    static String[] Name = new String[5906];
    static int[] Month = new int[5906];
    static int[] Day = new int[5906];
    static String path = "C:\\Users\\Administrator\\IdeaProjects\\Seerch\\src\\data\\vardi.txt";

    public static void main(String[] args) {
        makeData();
        String answer;
        if (getNameOrDate()) {
            String name = getName();
            answer = searchByName(name);
        } else {
            int[] date = getDate();
            if (date == null || !dateCheck(date)) {
                System.out.println("ISSUE: wrong input");
                return;
            }
            answer = searchByDate(date);
        }
        System.out.println(answer);
    }

    public static void makeData() {
        String[] data = getData();
        String[] split = new String[3];

        for (int i = 0; i < 5906; i++) {
            split = data[i].split(";");
            Name[i] = split[0];
            Month[i] = Integer.parseInt(split[1]);
            Day[i] = Integer.parseInt(split[2]);
        }
    }

    public static String[] getData() {
        String[] data = new String[5906];

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(String.valueOf(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.useDelimiter("\n");

        int i;
        for (i = 0; scanner.hasNext(); i++) {
            data[i] = scanner.nextLine();
        }

        return data;
    }

    public static boolean getNameOrDate() {
        System.out.println("Do you want to search by name?");
        System.out.println("YES/NO");

        String answer = strScanner();

        if (answer.equals("YES")) {
            return true;
        } else if (answer.equals("NO")) {
            return false;
        } else {
            System.out.println("ISSUE: wrong input, try again");
            getNameOrDate();
        }
        return false;
    }

    public static String strScanner() {
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        return answer;
    }

    public static String getName() {
        System.out.println("Enter name to find");

        String answer = strScanner();

        return answer;
    }

    public static String searchByName(String name) {
        for (int i = 0; i < 5906; i++) {
            if (Name[i].equals(name)) {
                return "" + Month[i] + "." + Day[i];
            }
        }
        return "ISSUE: no information about this name";
    }

    public static int[] getDate() {
        System.out.println("Enter date to find");
        System.out.println("M.D");

        int[] answer = intScanner();

        return answer;
    }

    public static int[] intScanner() {
        String answer = strScanner();

        String[] strAnswer = new String[2];
        String res = "";
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == '.') {
                strAnswer[0] = res;
                strAnswer[1] = answer.substring(i + 1);
                break;
            }
            res += answer.charAt(i);
        }

        if (strAnswer.length < 2) {
            return null;
        }

        int[] intAnswer = new int[2];
        for (int i = 0; i < 2; i++) {
            intAnswer[i] = Integer.parseInt(strAnswer[i]);
        }
        return intAnswer;
    }

    public static boolean dateCheck(int[] date) {
        if (date[0] >= 1 && date[0] <= 12) {
            if(date[1] >= 1 && date[1] <= 31) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String searchByDate(int[] date) {
        int month = searchByMonth(date[0]);
        if (month == -1) {
            return "ISSUE: no month";
        }
        int start;
        int end;
        for (start = month; Month[start] == date[0]; start--) { }
        for (end = month; Month[end] == date[0]; end++) { }
        start += 1;
        end -= 1;

        int day = searchByDay(date[1], start, end);
        if (day == -1) {
            return "ISSUE: no day";
        }

        for (start = day; Day[start] == date[1]; start--) { }
        for (end = day; Day[end] == date[1]; end++) { }
        start += 1;
        end -= 1;

        String answer = "";
        for (; start <= end; start++) {
            answer += Name[start];
        }
        return answer;
    }

    public static int searchByMonth(int month) {
        int start = 0;
        int end = 5906;

        for (; end - start != 0;) {
            int i = (end + start)/2;
            if (Month[i] == month) {
                return i;
            } else if (Month[i] < month) {
                end = i;
            } else {
                start = i;
            }
        }
        return -1;
    }

    public static int searchByDay(int day, int start, int end) {
        for (; end - start != 0;) {
            int i = (end + start)/2;
            if (Day[i] == day) {
                return i;
            } else if (Day[i] < day) {
                end = i;
            } else {
                start = i;
            }
        }
        return -1;
    }
}
