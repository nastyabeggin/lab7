package common.collection;

import java.io.Serializable;

public class Discipline implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long lectureHours;
    private int practiceHours;
    private Long labsCount; //Поле может быть null

    public Discipline(String name,
                      long lectureHours,
                      int practiceHours,
                      Long labsCount){
        this.name = name;
        this.lectureHours = lectureHours;
        this.practiceHours = practiceHours;
        this.labsCount = labsCount;
    }

    public String getName() {
        return name;
    }

    public long getLectureHours() {

        return lectureHours;
    }

    public int getPracticeHours() {
        return practiceHours;
    }

    public Long getLabsCount() {
        return labsCount;
    }
}