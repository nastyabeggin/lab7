package common.collection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Значение поля должно быть больше -980, Поле не может быть null
    private int y;
    public Coordinates(Long x, int y){
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
