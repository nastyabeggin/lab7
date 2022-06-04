package common.util;

import common.collection.Coordinates;
import common.collection.Difficulty;
import common.collection.Discipline;
import common.collection.LabWork;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class LabWorkBuilder {
    private static Scanner scanner = new Scanner(System.in);

    public static LabWork buildLab() {
        String name = enterName();
        Coordinates coordinates = enterCoordinates();
        float minimalPoint = enterMinimalPoint();
        long averagePoint = enterAveragePoint();
        Difficulty difficulty = enterDifficulty();
        Discipline discipline = enterDiscipline();
        LabWork labWork = new LabWork(name, coordinates, minimalPoint, averagePoint, difficulty, discipline);
        return labWork;
    }


    /**
     * Пользовательский ввод названия лабы
     *
     * @return название
     */
    private static String enterName() {
        System.out.print("Введите название лабы: ");
        String name = scanner.nextLine();
        while (name == null || name.equals("")) {
            System.out.println("Значение не может быть null или пустым, попробуйте ещё раз");
            name = scanner.nextLine();
        }
        return (name);
    }

    /**
     * Пользовательский ввод сложности
     *
     * @return название
     */
    private static Difficulty enterDifficulty() {
        Difficulty tempDifficulty = Difficulty.VERY_EASY;
        Difficulty[] difficulties = tempDifficulty.getDeclaringClass().getEnumConstants();
        System.out.println("Введите сложность лабы, доступные варианты сложности " + Arrays.toString(difficulties) + " Или оставьте пустую строку");
        String CommandManagerDifficulty = scanner.nextLine();
        while (!isInEnum(CommandManagerDifficulty, Difficulty.class) && !CommandManagerDifficulty.equals("")) {
            System.out.println("Значение не в списке сложностей, попробуйте ещё раз. Список: " + Arrays.toString(difficulties));
            CommandManagerDifficulty = scanner.nextLine();
        }
        if (CommandManagerDifficulty.equals("")) {
            return null;
        } else {
            tempDifficulty.setDifficultyOfLab(CommandManagerDifficulty);
            return (tempDifficulty);
        }
    }

    /**
     * Пользовательский ввод названия координат
     *
     * @return координаты
     */
    private static Coordinates enterCoordinates() {
        Long x = null;
        int y = 0;
        System.out.print("Введите координату Long x(>-980): ");
        while (true) {
            try {
                x = Long.parseLong(scanner.nextLine());
                if (x <= -980) throw new UnsupportedOperationException();
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите координату Long x(>-980): ");
            }
        }
        System.out.print("Введите координату int y: ");
        while (true) {
            try {
                y = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите координату int y: ");
            }
        }
        Coordinates tempCoordinates = new Coordinates(x, y);
        return (tempCoordinates);
    }

    /**
     * Пользовательский ввод минимального балла
     *
     * @return минимальный балл
     */
    private static float enterMinimalPoint() {
        System.out.print("Введите минимальный балл (>0): ");
        float tempMinimalPoint = 0.0F;
        while (true) {
            try {
                tempMinimalPoint = Float.parseFloat(scanner.nextLine());
                if (tempMinimalPoint <= 0) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите минимальный балл (>0): ");
            }
        }
        return (tempMinimalPoint);
    }

    /**
     * Пользовательский ввод минимального балла
     *
     * @return минимальный балл
     */
    private static long enterAveragePoint() {
        long tempAveragePoint = 0;
        System.out.print("Введите средний балл (>0): ");
        while (true) {
            try {
                tempAveragePoint = Long.parseLong(scanner.nextLine());
                if (tempAveragePoint <= 0) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите средний балл (>0): ");
            }
        }
        return tempAveragePoint;
    }

    /**
     * Пользовательский ввод дисциплины
     *
     * @return экземпляр класса Discipline
     */
    private static Discipline enterDiscipline() {
        String name; //Поле не может быть null, Строка не может быть пустой
        long lectureHours = 0;
        int practiceHours = 0;
        Long labsCount = null; //Поле может быть null
        System.out.print("Введите название дисциплины(обязательное поле): ");
        while (true) {
            try {
                name = (scanner.nextLine());
                if (Objects.equals(name, "") || name == null) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите название дисциплины(обязательное поле): ");
            }
        }
        System.out.print("Введите количество лекционных часов по этой дисциплине: ");
        while (true) {
            try {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    System.out.println("Вы не ввели поле. Оно будет пустым");
                    break;
                }
                lectureHours = Long.parseLong(line);
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите количество лекционных часов по этой дисциплине(или оставьте пустую строку): ");
            }
        }
        System.out.print("Введите количество практических часов по этой дисциплине: ");
        while (true) {
            try {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    System.out.println("Вы не ввели поле. Оно будет пустым");
                    break;
                }
                ;
                practiceHours = Integer.parseInt(line);
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите количество практических часов по этой дисциплине(или оставьте пустую строку): ");
            }
        }
        System.out.print("Введите количество лаб этой дисциплине: ");
        while (true) {
            try {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    System.out.println("Вы не ввели поле. Оно будет пустым");
                    break;
                }
                labsCount = Long.parseLong(line);
                break;
            } catch (Exception e) {
                System.out.print("Значение некорректно. Введите количество лаб этой дисциплине(или оставьте пустую строку): ");
            }
        }
        Discipline tempDiscipline = new Discipline(name, lectureHours, practiceHours, labsCount);
        return tempDiscipline;
    }

    public static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
