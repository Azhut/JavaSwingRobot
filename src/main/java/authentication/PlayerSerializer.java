package authentication;

import game.model.Player;

import java.io.*;

public class PlayerSerializer {
    // Метод для сохранения объекта Player в файл
    public static void serializePlayer(Player player, String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(player);
            out.close();
            fileOut.close();
            System.out.println("Объект сохранен в " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для загрузки объекта Player из файла
    public static Player deserializePlayer(String filename) {
        Player player = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player = (Player) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Объект загружен из " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return player;
    }
}