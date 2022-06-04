package common.collection;


import serverModule.collection.CollectionManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * Экземпляр лабораторной работы
 */
public class LabWork implements Serializable {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float minimalPoint; //Значение поля должно быть больше 0
    private long averagePoint; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Discipline discipline; //Поле не может быть null

    public LabWork(long id,
                   String name,
                   Coordinates coordinates,
                   float minimalPoint,
                   long averagePoint,
                   String difficulty,
                   Discipline discipline) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.minimalPoint = minimalPoint;
        this.averagePoint = averagePoint;
        Difficulty tempDiff = Difficulty.VERY_EASY;
        tempDiff.setDifficultyOfLab(difficulty);
        this.difficulty = tempDiff;
        this.discipline = discipline;
    }

    /**
     * Конструктор лабы, когда даны все параметры
     *
     * @param name         Название лабы. Поле не может быть null, Строка не может быть пусто
     * @param coordinates  Координаты. Поле не может быть null
     * @param minimalPoint Минимальный балл. Поле не может быть null, Значение этого поля должно генерироваться автоматически
     * @param averagePoint Средний балл. Значение поля должно быть больше 0
     * @param difficulty   Сложность. Поле может быть null
     * @param discipline   Дисциплина. Поле не может быть null
     */
    public LabWork(String name,
                   Coordinates coordinates,
                   float minimalPoint,
                   long averagePoint,
                   Difficulty difficulty,
                   Discipline discipline) {
        this.id = -1;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.minimalPoint = minimalPoint;
        this.averagePoint = averagePoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
    }

    public void generateId(CollectionManager collectionManager) {
        setId(collectionManager.getMaximalId() + 1);
    }
    /**
     * Создает id
     *
     * @return уникальный id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * геттер id
     *
     * @return возвращает id
     */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public float getMinimalPoint() {
        return minimalPoint;
    }

    public long getAveragePoint() {
        return averagePoint;
    }

    /**
     * @return описание лабы
     */
    public String toPrintableString() {
        return ("Название лабы: " + name + "\nID: " + id + "\nДата создания объекта коллекции: "
                + creationDate + "\nCoordinates X: " + coordinates.getX() + "\nCoordinates Y: " + coordinates.getY()
                + "\nМинимальный балл: " + minimalPoint + "\nСредний балл: " + averagePoint +
                "\nСложность: " + difficulty + "\nДисциплина: " + discipline.getName() + "\nКоличество лекционных часов по дисциплине: "
                + discipline.getLectureHours() + "\nКоличество практических часов по дисциплине: "
                + discipline.getPracticeHours() + "\nКоличество лаб по дисциплине: " + discipline.getLabsCount());
    }

    /**
     * Сравнивает две лабы по id
     *
     * @param l2 вторая лаба
     * @return результат сравнения айдишников
     */
    public int compare(LabWork l2) {
        return (int) (this.id - l2.getId());
    }

    public String[] getAll() {
        String st_discipline = "";
        if (difficulty == null) {
            st_discipline = null;
        } else {
            st_discipline = difficulty.getDifficultyOfLab();
        }
        return new String[]{String.valueOf(this.id), String.valueOf(this.name), String.valueOf(this.coordinates.getX()),
                String.valueOf(this.coordinates.getY()),
                String.valueOf(this.creationDate), String.valueOf(this.minimalPoint), String.valueOf(this.averagePoint),
                st_discipline, discipline.getName(), String.valueOf(discipline.getLectureHours()),
                String.valueOf(discipline.getPracticeHours()), String.valueOf(discipline.getLabsCount())};
    }


}