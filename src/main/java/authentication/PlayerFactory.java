package authentication;

import game.model.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerFactory {
    private final Map<String, String> tokenToFileMap;

    public PlayerFactory(String tokensFile) {
        tokenToFileMap = new HashMap<>();
        loadTokens(tokensFile);
    }

    private void loadTokens(String tokensFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tokensFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    String token = parts[0].trim();
                    String filePath = parts[1].trim();
                    tokenToFileMap.put(token, filePath);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player createPlayer(String token) {
        String filePath = tokenToFileMap.get(token);
        if (filePath != null) {
            Player player = deserializePlayer(filePath);
            return player;
        }
        return null;
    }

    public void serializePlayer(Player player, String token) {
        String filePath = tokenToFileMap.get(token);
        if (filePath != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(player);
                out.close();
                fileOut.close();
                System.out.println("Игрок сохранен в " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Токен не найден в файле tokens.txt");
        }
    }

    private Player deserializePlayer(String filePath) {
        Player player = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player = (Player) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Объект игрока загружен из " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return player;
    }
}
