package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.Teacher;

import java.util.Comparator;
import java.util.List;

public class Main4 {

    public static void main(String[] args) {
         printShortestEmails("KIKM",5);
    }

    public static void printShortestEmails(String department, int count)
    {
        String json = Api.getTeachersByDepartment(department);

        List<Teacher> teachers = new Gson().fromJson(
                json,
                new com.google.gson.reflect.TypeToken<List<Teacher>>() {}.getType()
        );

        teachers.stream()
                .map(t -> t.email)
                .filter(email -> email != null && !email.isEmpty())
                .sorted(Comparator.comparingInt(String::length).thenComparing(String::compareTo))
                .limit(count)
                .forEach(System.out::println);
    }
}