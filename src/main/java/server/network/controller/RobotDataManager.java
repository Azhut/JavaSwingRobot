package server.network.controller;

import java.io.*;

public class RobotDataManager {
    private static RobotDataManager instance;
    private final String filePath;

    private RobotDataManager(String filePath) {
        this.filePath = filePath;
    }

    public static RobotDataManager getInstance(String filePath) {
        if (instance == null) {
            instance = new RobotDataManager(filePath);
        }
        return instance;
    }

    public void serializeAndSaveData(Object data) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(data);

            System.out.println("Данные успешно сериализованы и сохранены в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сериализации и сохранении данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Object deserializeData() {
        Object data = null;
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            data = objectInputStream.readObject();

            System.out.println("Данные успешно десериализованы из файла: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при десериализации данных: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }
}
