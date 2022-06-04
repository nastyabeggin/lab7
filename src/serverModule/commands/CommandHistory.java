package serverModule.commands;

import java.util.PriorityQueue;

public class CommandHistory extends PriorityQueue<String> {
    public void addCommand(String e) {
        if (size() == 6) {
            poll();
        }
        add(e);
    }

    public String getHistory() {
        StringBuilder temp = new StringBuilder();
        for (String string : this)
            temp.append(string).append("\n");
        return temp.toString();
    }
}