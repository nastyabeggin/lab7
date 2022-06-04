package serverModule.collection;

import common.util.LabWorkBuilder;
import common.collection.LabWork;
import serverModule.commands.CommandHistory;
import serverModule.util.ResponseOutputer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager extends LinkedHashSet<LabWork> {
    public final String PATH = "final.csv";
    private String path;
    private CommandHistory commandHistory = new CommandHistory();
    private LocalDateTime date = LocalDateTime.now();

    public LabWork generateNew() {
        return LabWorkBuilder.buildLab();
    }

    public void objectsInfo() {
        ResponseOutputer.append("Информация об элементах коллекции\n");
        for (LabWork labWork : this) {
            ResponseOutputer.append(labWork.toPrintableString() + "\n");
        }
    }

    public String getCommandHistory() {
        return commandHistory.getHistory();
    }

    public String getInfo() {
        return ("Коллекция " + this.getClass().toString() + " LinkedHashSet" + " размером " + this.size() + " с датой создания " + this.date);
    }

    public float getMinimalMinimalPoint() {
        float n = Float.MAX_VALUE;
        for (LabWork labWork : this) {
            if (labWork.getMinimalPoint() < n) n = labWork.getMinimalPoint();
        }
        return n;
    }

    public LabWork generateNewIfMin(float CommandManagerMinimalPoint) {
        return null;
    }


    public void sort() {
        List<LabWork> list = toList();
        list.sort(LabWork::compare);
        toLinkedHashSet(list);
    }

    private void toLinkedHashSet(List<LabWork> list) {
        removeAll(this);
        this.addAll(list);
    }

    private List<LabWork> toList() {
        List<LabWork> list = new ArrayList<>();
        list.addAll(0, this);
        return list;
    }

    public void update(Long id, LabWork labWork) {
        labWork.setId(id);
        for (LabWork labWork2 : this) {
            if (labWork2.getId() == (id)) {
                remove(labWork2);
                add(labWork);
            }
        }
    }

    public long getMaximalId() {
        long n = 0;
        for (LabWork labWork : this) {
            if (labWork.getId() > n) n = labWork.getId();
        }
        return n;
    }

    public void addToCommandHistory(String command) {
        commandHistory.addCommand(command);
    }

    public void saveToFile() {
        File outputFile = new File(PATH);
        try {
            outputFile.createNewFile();
            StringBuilder csv = new StringBuilder();
            for (LabWork labWork : this) {
                String[] row = labWork.getAll();
                csv.append(String.join(",", row));
                csv.append("\n");
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(PATH));
            out.write(String.join("", csv).getBytes(StandardCharsets.UTF_8));
            System.out.println("Коллекция успешно сохранена в файл " + PATH);
            out.close();
        } catch (IOException e) {
            System.out.println("Не удалось сохранить в файл");
        }
    }
    /*public CollectionManager(String path) {
        this.path = path;
    }

    public CollectionManager() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addWithCheck() {
        int sizeBefore = size();
        try {
            add(new LabWork(this));
        } catch (Exception e) {
            System.out.println("Вы ввели значение с ошибкой!");
            if (sizeBefore < size())
                remove(size());
        }
    }

    public void update(Long id) {
        for (LabWork labWork : this) {
            if (labWork.getId() == (id)) {
                remove(labWork);
                add(new LabWork(this, id));
            }
        }
    }

*
     * @return минимальный минимальный балл


    public float getMinimalMinimalPoint() {
        float minimalMinimalPoint = Float.MAX_VALUE;
        for (LabWork labWork : this) {
            if (labWork.getMinimalPoint() < minimalMinimalPoint) minimalMinimalPoint = labWork.getMinimalPoint();
        }
        return minimalMinimalPoint;
    }



    public String getCommandHistory() {
        return commandHistory.getHistory();
    }





*
     * пеерписывает List в LinkedHashSet
     *
     * @param list -  List


    private void toLinkedHashSet(List<LabWork> list) {
        removeAll(this);
        this.addAll(list);
    }


    public LabWork generateNew() {
        return new LabWork(this);
    }

    public LabWork generateNewIfMin(float minimalPoint) {
        return new LabWork(this, minimalPoint);
    }



    public String getInfo() {
        return ("Коллекция " + this.getClass().toString() + " LinkedHashSet" + " размером " + this.size() + " с датой создания " + this.date);
    }

    public long getMaximalId() {
        long n = 0;
        for (LabWork labWork : this) {
            if (labWork.getId() > n) n = labWork.getId();
        }
        return n;
    }

    public void saveToFile(){
        File outputFile = new File(PATH);
        try {
            outputFile.createNewFile();
            StringBuilder csv = new StringBuilder();
            for (LabWork labWork : this) {
                String[] row = labWork.getAll();
                csv.append(String.join(",", row));
                csv.append("\n");
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(PATH));
            out.write(String.join("", csv).getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (IOException e){
            System.out.println("Не удалось сохранить в файл");
        }
    }*/
}

