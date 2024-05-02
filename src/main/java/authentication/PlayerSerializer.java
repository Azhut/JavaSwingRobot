package authentication;

import game.model.Player;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PlayerSerializer {
    public static void savePlayerClassToFile(Class<Player> playerClass, String filePath) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(playerClass.getName());
        }
    }
}
