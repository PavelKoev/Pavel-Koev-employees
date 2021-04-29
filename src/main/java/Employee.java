import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Employee {

    private String id;
    private HashMap<String, List<LocalDate>> projects;

    public Employee(String id, String projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.id = id;
        projects = new HashMap<>();
        addProject(projectId, dateFrom, dateTo);
    }

    public void addProject(String projectId, LocalDate start, LocalDate end){
        if (! projects.containsKey(projectId)) {
            projects.put(projectId, new ArrayList<>());
        }
        projects.get(projectId).add(start);
        projects.get(projectId).add(end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public HashMap<String, List<LocalDate>> getProjects() {
        return new HashMap<>(projects);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Employee - id = " + id + "   " + projects.toString();

    }
}
