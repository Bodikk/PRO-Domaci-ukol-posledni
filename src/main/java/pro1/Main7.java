package pro1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pro1.apiDataModel.Specialization;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main7 {
    public static String specializationDeadlines(int year) {
        String json = Api.getSpecializations(year);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Specialization>>() {}.getType();
        List<Specialization> specs = gson.fromJson(json, listType);

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");

        return specs.stream()
                .map(spec -> spec.eprDeadlinePrihlaska)
                .filter(deadline -> deadline != null && deadline.value != null && !deadline.value.isBlank())
                .map(deadline -> LocalDate.parse(deadline.value, inputFormatter))
                .sorted(Comparator.naturalOrder())
                .map(date -> date.format(outputFormatter))
                .collect(Collectors.joining(","));
    }
}
