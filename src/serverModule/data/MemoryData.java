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

    public static synchronized void putUser(Long id, User user) {
        users.put(id, user);
    }

    public static synchronized void putLab(Long id, LabWork labWork) {
        labs.put(id, labWork);
    }

    public static synchronized void putCoords(Long id, Coordinates coordinates) {
        coords.put(id, coordinates);
    }

    public static synchronized void putDiscipline(Long id, Discipline discipline) {
        disciplines.put(id, discipline);
    }

    public static synchronized Map<Long, User> getUsers() {
        return new HashMap<>(users);
    }

    public static synchronized Map<Long, LabWork> getLabs() {
        return new HashMap<>(labs);
    }

    public static synchronized Map<Long, Coordinates> getCoords() {
        return new HashMap<>(coords);
    }

    public static synchronized Map<Long, Discipline> getDisciplines() {
        return new HashMap<>(disciplines);
    }

    public static synchronized StringBuilder getLabsInString(){
        StringBuilder out = new StringBuilder();
        for (Map.Entry<Long, LabWork> entry : labs.entrySet()) {
            out.append(entry.getValue().toPrintableString() + "\n\n");
        }
        return out;
    }

    public static synchronized String getInfo(){
        return "В памяти находятся " + getLabs().size() + " лабораторных, "
                + getCoords().size() + " координат, " + getDisciplines().size() + " дисциплин.";
    }
}
