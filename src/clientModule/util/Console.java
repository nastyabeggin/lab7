package clientModule.util;

import clientModule.exceptions.ParamException;
import clientModule.exceptions.WrongInputException;
import common.collection.LabWork;
import common.util.*;
import common.util.response.ResponseCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Console {
    private Scanner userScanner;
    private AuthManager authManager;

    public Console(Scanner userScanner, AuthManager authManager) {
        this.userScanner = userScanner;
        this.authManager = authManager;
    }

    public Request interactiveMode(ResponseCode serverResponseCode, User user) {
        String CommandManagerInput;
        String[] CommandManagerCommand = {"", ""};
        CommandCode commandCode = null;
        do {
            try {
                if (serverResponseCode == ResponseCode.SERVER_EXIT || serverResponseCode == ResponseCode.ERROR) {
                    throw new WrongInputException("Проблемы со вводом");
                }
                System.out.print("# ");
                CommandManagerInput = userScanner.nextLine().toLowerCase();
                CommandManagerCommand = (CommandManagerInput.trim() + " ").split(" ", 2);
                CommandManagerCommand[1] = CommandManagerCommand[1].trim();
                commandCode = checkCommand(CommandManagerCommand[0], CommandManagerCommand[1]);
            } catch (WrongInputException e) {
                System.out.println("Проблемы со вводом" + e);
            }
        } while (commandCode == CommandCode.ERROR || CommandManagerCommand[0].isEmpty());
        try {
            switch (commandCode) {
                case USER:
                    return new Request(CommandManagerCommand[0], CommandManagerCommand[1], user);
                case ADD:
                case UPDATE:
                    LabWork labWork = LabWorkBuilder.buildLab();
                    return new Request(CommandManagerCommand[0], CommandManagerCommand[1], labWork, user);
                case SCRIPT:
                    File scriptFile = new File(CommandManagerCommand[1]);
                    if (!scriptFile.exists()) throw new FileNotFoundException();
                    userScanner = new Scanner(scriptFile);
                    System.out.println("Выполняю скрипт '" + scriptFile.getName() + "'!");
                    break;
                case LOG_IN:
                case SIGN_UP:
                    return authManager.handle();
                case LOG_OUT:
                    return new Request(CommandManagerCommand[0], CommandManagerCommand[1], null, null);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Файл со скриптом не найден!");

        }
        return new Request(CommandManagerCommand[0], CommandManagerCommand[1]);


    }

    private CommandCode checkCommand(String commandName, String commandArguments) {
        try {
            switch (commandName) {
                case "":
                    return CommandCode.ERROR;
                case "help":
                case "info":
                case "show":
                case "history":
                case "average_of_average_point":
                    return CommandCode.SPECIAL;
                case "whoami":
                case "clear":
                    return CommandCode.USER;
                case "exit":
                    if (!commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.EXIT;
                case "add":
                    if (!commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.ADD;
                case "add_if_min":
                    if (commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.ADD;
                case "remove_all_by_minimal_point":
                case "count_less_than_average_point":
                    if (commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.PARAM;
                case "remove_lower":
                case "remove_by_id":
                    if (commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.USER;
                case "update":
                    if (commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.UPDATE;
                case "execute_script":
                    if (commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.SCRIPT;
                case "save":
                    System.out.println("Эта команда недоступна клиентам!");
                    return CommandCode.ERROR;
                case "sign_in":
                    if (!commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.LOG_IN;
                case "sign_up":
                    if (!commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.SIGN_UP;
                case "log_out":
                    if (!commandArguments.isEmpty()) throw new ParamException();
                    return CommandCode.LOG_OUT;
                default:
                    System.out.println("Команда '" + commandName + "' не найдена. Наберите 'help' для справки.");
                    return CommandCode.ERROR;
            }
        } catch (ParamException e) {
            System.out.println("Проверьте правильность ввода аргументов!");
            return CommandCode.ERROR;
        }
    }


    public static void print(Object toOut) {
        System.out.print(toOut);
    }


    public static void println(Object toOut) {
        System.out.println(toOut);
    }

}
