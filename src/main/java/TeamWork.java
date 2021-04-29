
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TeamWork {

    private Employee employee;
    private Employee partner;
    private Period workTogether;
    private Set<String> projects;

    public TeamWork(Employee employee, Employee partner, Period workTogether) {
        this.employee = employee;
        this.partner = partner;
        this.workTogether = workTogether;
        this.projects = new HashSet<>();
    }

    public boolean isTheSameTeam(Employee employee, Employee partner){
        int counter = 0;
        if (employee.equals(this.employee) || employee.equals(this.partner)){
            counter++;
        }
        if (partner.equals(this.employee) || partner.equals(this.partner)){
            counter++;
        }
        if (counter < 2){
            return false;
        }
        return true;
    }

    public Period getWorkTogether() {
        return workTogether;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Employee getPartner() {
        return partner;
    }

    public Set<String> getProjects() {
        return projects;
    }

    public void addPeriod(Period p) {
        this.workTogether.plus(p);
    }

    public void addProjet(String project) {
        this.projects.add(project);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamWork teamWork = (TeamWork) o;
        return employee.equals(teamWork.employee) &&
                partner.equals(teamWork.partner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, partner);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Team members: ").append(this.employee).append(" and ").append(this.partner).append(" \n")
                .append("projects :").append(this.projects).append("\n")
                .append("Period of work together: ").append(this.workTogether.getYears()).append(" years, ")
                .append(this.workTogether.getMonths()).append(" monghs and ")
                .append(this.workTogether.getDays()).append(" days!");
        return stringBuilder.toString();
    }
}
