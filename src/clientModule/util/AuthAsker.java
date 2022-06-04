package clientModule.util;
import common.exceptions.NotDeclaredPasswordException;
import common.exceptions.NotDeclaredValueException;

import java.io.Console;
import java.util.Scanner;

public class AuthAsker {
    private Scanner scanner;
    String pattern = "(?=\\S+$).{3,}";

    public AuthAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    public String askLogin() {
        String login;
        while (true) {
            try {
                System.out.println("Введите логин:");
                System.out.print("> ");
                login = scanner.nextLine().trim();
                if (login.equals("")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException e) {
                System.out.println("Логин не может быть пустым!");
            }
        }
        return login;
    }

    public String askPassword() {
        String password;
        while (true) {
            try {
                System.out.println("Введите пароль:");
                System.out.print("> ");
                Console console = System.console();
                if (console == null) {
                    password = scanner.nextLine();
                } else {
                    password = String.valueOf(console.readPassword());
                }
                if (password.equals("")) throw new NotDeclaredValueException();
                if (!password.matches(pattern)) throw new NotDeclaredPasswordException();
                break;
            } catch (NotDeclaredValueException e) {
                System.out.println("Пароль не может быть пустым!");
            } catch (NotDeclaredPasswordException e) {
                System.out.println("Пароль не может состоять из пробелов и должен иметь не меньше 3 символов!");
            }
        }
        return password;
    }

    public boolean askQuestion(String question) {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                System.out.println(finalQuestion);
                System.out.print("> ");
                answer = scanner.nextLine().trim();
                if (!answer.equals("+") && !answer.equals("-")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException e) {
                System.out.println("Ответ должен быть либо '+', либо '-'!");
            }
        }
        return answer.equals("+");
    }
}
