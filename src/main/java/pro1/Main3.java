package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.ActionsList;
import pro1.apiDataModel.Teacher;

import java.util.Comparator;
import java.util.List;

public class Main3 {

    public static void main(String[] args) {
        System.out.println(emailOfBestTeacher("KIKM",2024));
    }

    public static String emailOfBestTeacher(String department, int year)
    {
        String teachersJson = Api.getTeachersByDepartment(department);
        String actionsJson = Api.getActionsByDepartment(department, year);

        List<Teacher> teachers = new Gson().fromJson(
                teachersJson,
                new com.google.gson.reflect.TypeToken<List<Teacher>>() {}.getType()
        );

        ActionsList departmentSchedule = new Gson().fromJson(actionsJson, ActionsList.class);

        return teachers.stream()
                .max(Comparator.comparingLong(t -> TeacherScore(t.id, departmentSchedule)))
                .map(t -> t.email)
                .orElse("");

    }

    public static long TeacherScore(long teacherId, ActionsList departmentSchedule)
    {
        return departmentSchedule.actions.stream()
                .filter(action -> action.teacherId == teacherId)
                .mapToLong(action -> action.personsCount)
                .sum();
    }
}