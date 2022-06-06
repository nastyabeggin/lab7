package serverModule.data;

import common.collection.Coordinates;
import common.collection.Discipline;
import common.collection.LabWork;
import common.util.User;

import java.util.HashMap;
import java.util.Map;

//maybe immutable
public final class MemoryData {
    private static final Map<Long, User> users = new HashMap<>();
    private static final Map<Long, LabWork> labs = new HashMap<>();
    private static final Map<Long, Coordinates> coords = new HashMap<>();
    private static final Map<Long, Discipline> disciplines = new HashMap<>();

    public static void putUser(Long id, User user) {
        users.put(id, user);
    }

    public static void putLab(Long id, LabWork labWork) {
        labs.put(id, labWork);
    }

    public static void putCoords(Long id, Coordinates coordinates) {
        coords.put(id, coordinates);
    }

    public static void putDiscipline(Long id, Discipline discipline) {
        disciplines.put(id, discipline);
    }

    public static Map<Long, User> getUsers() {
        return new HashMap<>(users);
    }

    public static Map<Long, LabWork> getLabs() {
        return new HashMap<>(labs);
    }

    public static Map<Long, Coordinates> getCoords() {
        return new HashMap<>(coords);
    }

    public static Map<Long, Discipline> getDisciplines() {
        return new HashMap<>(disciplines);
    }

    public static StringBuilder getLabsInString(){
        StringBuilder out = new StringBuilder();
        for (Map.Entry<Long, LabWork> entry : labs.entrySet()) {
            out.append(entry.getValue().toPrintableString() + "\n\n");
        }
        return out;
    }
}
