package serverModule.data;

import common.collection.LabWork;
import common.util.User;

import java.util.HashMap;
import java.util.Map;

//maybe immutable
public final class MemoryData {
    private static final Map<Long, User> users = new HashMap<>();
    private static final Map<Long, LabWork> labs = new HashMap<>();

    public static void putUser(Long id, User user) {
        users.put(id, user);
    }

    public static void putLab(Long id, LabWork labWork) {
        labs.put(id, labWork);
    }

    public static Map<Long, User> getUsers() {
        return new HashMap<>(users);
    }

    public static Map<Long, LabWork> getLabs() {
        return new HashMap<>(labs);
    }
}
