package authentication;

import game.model.Player;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PlayerFactory {
    private final Map<String, Supplier<Player>> playerCreators;

    public PlayerFactory(String tokenFilePath) throws IOException {
        this.playerCreators = new HashMap<>();
        loadTokens(tokenFilePath);
    }

    // Загрузка токенов и соответствующих классов Player из файла
    private void loadTokens(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    String token = parts[0];
                    String playerClassFilePath = parts[1];
                    try {
                        Class<?> playerClass = loadPlayerClassFromFile(playerClassFilePath);
                        if (Player.class.isAssignableFrom(playerClass)) {
                            @SuppressWarnings("unchecked")
                            Class<? extends Player> playerSubclass = (Class<? extends Player>) playerClass;
                            Constructor<? extends Player> constructor = playerSubclass.getConstructor();
                            playerCreators.put(token, () -> {
                                try {
                                    return constructor.newInstance();
                                } catch (Exception e) {
                                    throw new RuntimeException("Error creating player instance", e);
                                }
                            });
                        } else {
                            throw new IllegalArgumentException("Error loading player class: " + playerClass.getName() + " is not a subclass of Player");
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        throw new IOException("Error loading player class: " + e.getMessage());
                    }
                }
            }
        }
    }

    // Загрузка класса Player из файла
    private Class<?> loadPlayerClassFromFile(String filePath) throws ClassNotFoundException, IOException {
        // Создаем экземпляр ClassLoader, который будет загружать классы из указанного файла
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            // Получаем массив байт из файла
            byte[] classData = new byte[bis.available()];
            bis.read(classData);
            // Загружаем класс из массива байт
            return new ClassLoader() {
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    return defineClass(name, classData, 0, classData.length);
                }
            }.loadClass(null);
        }
    }


    // Создание экземпляра Player по токену
    public Player createPlayer(String token) {
        Supplier<Player> creator = playerCreators.get(token);
        if (creator != null) {
            return creator.get();
        } else {
            throw new IllegalArgumentException("Unknown token: " + token);
        }
    }

    // Сохранение токена и пути к файлу класса Player
    public static void savePlayerToken(String token, String playerClassFilePath, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(token + " - " + playerClassFilePath);
            writer.newLine();
        }
    }

    // Сохранение токена и пути к файлу класса Player
    public static void savePlayerClassFilePath(String token, String playerClassFilePath, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(token + " - " + playerClassFilePath);
            writer.newLine();
        }
    }
}
