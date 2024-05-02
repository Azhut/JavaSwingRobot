package authentication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private Map<String, Class<?>> tokenToPlayerClass;

    public AuthenticationManager(String tokenFilePath) throws IOException {
        tokenToPlayerClass = new HashMap<>();
        loadTokens(tokenFilePath);
    }

    private void loadTokens(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String token = parts[0];
                    String playerClassName = parts[1];
                    try {
                        Class<?> playerClass = Class.forName(playerClassName);
                        tokenToPlayerClass.put(token, playerClass);
                    } catch (ClassNotFoundException e) {
                        System.err.println("Error loading player class: " + e.getMessage());
                    }
                }
            }
        }
    }

    public Class<?> getPlayerClass(String token) {
        return tokenToPlayerClass.get(token);
    }
}
