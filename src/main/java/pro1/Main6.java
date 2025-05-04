package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.ActionsList;

import java.util.Map;
import java.util.stream.Collectors;

public class Main6 {

    public static void main(String[] args) {
        System.out.println(idOfBestTeacher("KIKM",2024));
    }

    public static long idOfBestTeacher(String department, int year)
    {
        String json = Api.getActionsByDepartment(department, year);
        ActionsList actionsList = new Gson().fromJson(json, ActionsList.class);

        return actionsList.actions.stream()
                .filter(a -> a.teacherId != 0)
                .collect(Collectors.groupingBy(
                        a -> a.teacherId,
                        Collectors.summingLong(a -> a.personsCount)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0L);
    }
}