
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class Reader {

    public static HashMap<String, Employee> readTxtEmployees(File file) throws FileNotFoundException {
        HashMap<String,Employee> employees = new HashMap<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] params = line.split(", ");
            LocalDate startAt = dateParser(params[2]);
            LocalDate endAt;
            if (params[3].equalsIgnoreCase("NULL")){
                endAt = LocalDate.now();
            }else {
                endAt = dateParser(params[3]);
            }
            if (employees.containsKey(params[0])){
                employees.get(params[0]).addProject(params[1], startAt, endAt);
            }else {
                Employee employee = new Employee(params[0], params[1], startAt, endAt);
                employees.put(employee.getId(), employee);
            }
        }
        return employees;
    }

    private static LocalDate dateParser(String text) {
        char separator = text.replaceAll("[0-9]","").charAt(0);
        String[] params = text.split("" + separator);
        LocalDate date;
        if (params[0].length() == 4){
            date = LocalDate.of(Integer.parseInt(params[0]),
                    Integer.parseInt(params[1]),
                    Integer.parseInt(params[2]));
        }else {
            date = LocalDate.of(Integer.parseInt(params[2]),
                    Integer.parseInt(params[1]),
                    Integer.parseInt(params[0]));
        }
        return date;
    }
}
