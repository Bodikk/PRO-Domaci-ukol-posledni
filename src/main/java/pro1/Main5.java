package pro1;

import com.google.gson.Gson;
import pro1.Api;
import pro1.apiDataModel.ActionsList;

import java.util.Objects;
import java.util.stream.Collectors;

public class Main5 {

    public static void main(String[] args) {
        System.out.println(roomsSummary("KIKM",2024));
    }

    public static String roomsSummary(String department, int year)
    {
        String json = Api.getActionsByDepartment(department, year);
        ActionsList actions = new Gson().fromJson(json, ActionsList.class);

        return actions.actions.stream()
                .map(a -> a.room)
                .filter(Objects::nonNull)
                .filter(r -> !r.isBlank())
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));
    }
}