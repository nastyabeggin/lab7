package clientModule.exceptions;

public class ParamException extends WrongInputException {
    public ParamException() {
        super("Некорректные параметры");
    }
}

