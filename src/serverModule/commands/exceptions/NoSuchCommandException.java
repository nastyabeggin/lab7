package serverModule.commands.exceptions;

public class NoSuchCommandException extends CommandException {
    public NoSuchCommandException(String commandName) {
        super(String.format("Не существует команды %s", commandName));
    }
}
