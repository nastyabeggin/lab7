package serverModule.commands;

import common.util.User;
import common.util.response.ResponseBody;
import serverModule.commands.exceptions.CommandException;

/* интерфейс выполнения команды, который реализуют все классы команд */
public interface Command {
    /**
     * Метод для взаимодействия с коллекцией
     * @param params - аргументы, которые передаются с командой
     */
    ResponseBody execute(String params, Object objectArgument, User user) throws CommandException;
    String getName();
    String getDescription();
    void addToHistory();
    String getCommandHistory();
    String getParams();
}
