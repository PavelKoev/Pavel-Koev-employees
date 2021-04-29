import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Demo {
    public static void main(String[] args) {

        File file = new File("txt_files/employees.txt");
        try {
            TreeSet<TeamWork> teamWorks = new TreeSet<>((o1,o2) -> (comparePeriod(o2.getWorkTogether(),o1.getWorkTogether())));
            HashMap<String, Employee> map = Reader.readTxtEmployees(file);
            if (map!= null) {
                List<Employee> employees = new ArrayList<>(map.values());
                for (int i = 0; i < employees.size() - 1; i++) {
                    Employee emp = employees.get(i);
                    for (int j = i + 1; j < employees.size(); j++) {
                        Employee secondEmp = employees.get(j);
                        TeamWork teamWork = teamUpdate(teamWorks, emp, secondEmp);
                        if (teamWork != null){
                            teamWorks.add(teamWork);
                        }
                    }
                }
            }
            System.out.println(teamWorks.first());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TeamWork teamUpdate(TreeSet<TeamWork> teamWorks, Employee emp, Employee secondEmp){
        // key - project id  value - list of dates where even indexes are start dates and odd are end dates
        HashMap<String, List<LocalDate>> empProjects = emp.getProjects();
        // key - project id  value - list of dates where even indexes are start dates and odd are end dates
        HashMap<String, List<LocalDate>> secondEmpProject = secondEmp.getProjects();
        for (Map.Entry<String, List<LocalDate>> entry : empProjects.entrySet()) {
            if (secondEmpProject.containsKey(entry.getKey())){
                List<LocalDate> firstEmpDates = entry.getValue();
                List<LocalDate> secondEmpDates = secondEmpProject.get(entry.getKey());
                for (int k = 0; k < firstEmpDates.size(); k+=2) {
                    LocalDate firstEmpStart = firstEmpDates.get(k);
                    LocalDate firstEmpEnd = firstEmpDates.get(k+1);
                    for (int l = 0; l < secondEmpDates.size(); l+=2) {
                        LocalDate secondEmpStart = secondEmpDates.get(l);
                        LocalDate secondEmpEnd = secondEmpDates.get(l+1);
                        Period p;
                        if (firstEmpEnd.isBefore(secondEmpStart) || secondEmpEnd.isBefore(firstEmpStart)){
                            continue;
                        }
                        if (firstEmpStart.isBefore(secondEmpStart)){
                            if (secondEmpEnd.isBefore(firstEmpEnd)){
                                p = Period.between(secondEmpStart,secondEmpEnd);
                            }else {
                                p = Period.between(secondEmpStart,firstEmpEnd);
                            }
                        }else {
                            if (firstEmpEnd.isBefore(secondEmpEnd)){
                                p = Period.between(firstEmpStart,firstEmpEnd);
                            }else {
                                p = Period.between(firstEmpStart,secondEmpEnd);
                            }
                        }
                        if (teamWorks.size() == 0){
                            TeamWork teamWork = new TeamWork(emp,secondEmp,p);
                            teamWork.addProjet(entry.getKey());
                            teamWorks.add(teamWork);
                        }else {
                            for (TeamWork teamWork :teamWorks) {
                                if (teamWork.isTheSameTeam(emp,secondEmp)){
                                    teamWork.addPeriod(p);
                                    teamWork.addProjet(entry.getKey());
                                    return teamWork;
                                }
                            }
                            TeamWork teamWork = new TeamWork(emp,secondEmp,p);
                            teamWork.addProjet(entry.getKey());
                            return teamWork;
                        }
                    }
                }
            }
        }
        return null;
    }

    private static int comparePeriod(Period firstPeriod, Period secondPeriod) {
        if (firstPeriod.getYears() == secondPeriod.getYears()){
            if (firstPeriod.getMonths() == secondPeriod.getMonths()){
                return firstPeriod.getDays()-secondPeriod.getDays();
            }else {
                return firstPeriod.getMonths()-secondPeriod.getMonths();
            }
        }else {
            return firstPeriod.getYears()-secondPeriod.getYears();
        }
    }
}
