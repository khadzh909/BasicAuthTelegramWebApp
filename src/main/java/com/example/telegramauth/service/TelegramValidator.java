package com.example.telegramauth.service;

import com.example.telegramauth.DTO.UserDTO;
import com.example.telegramauth.config.BotConfig;
import com.example.telegramauth.mapper.UserMapper;
import com.example.telegramauth.model.User;
import com.example.telegramauth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TelegramValidator {

    private final UserRepository userRepository;
    private final BotConfig config;
    private final UserMapper mapper;

    public TelegramValidator(UserRepository userRepository, BotConfig config, UserMapper mapper) {
        this.userRepository = userRepository;
        this.config = config;
        this.mapper = mapper;
    }

    //парсим наш объект, достаем оттуда пользователя и мапим в дто
    private UserDTO parseUser(String user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = objectMapper.readValue(user, UserDTO.class);
        return userDTO;
    }
    public UserDTO auth(String initData) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        Map<String, String> map = parseInitData(initData);
        String hash = map.remove("hash");

        String dataCheck = buildCheckData(map);
        byte[] secretKey = calculateHMAC("WebAppData".getBytes(), config.getToken().getBytes(StandardCharsets.UTF_8));

        byte[] calculateHashBytes = calculateHMAC(secretKey, dataCheck.getBytes(StandardCharsets.UTF_8));
        String hex = bytesToHex(calculateHashBytes);

        if (!hex.equalsIgnoreCase(hash)) {
            throw new RuntimeException("Не совпадает информация");
        }

        UserDTO userDTO = parseUser(map.get("user"));
        userRepository.save(mapper.DTOToUser(userDTO));
        return userDTO;
    }

    //метод для парсинга, получение из initData мапу с данными пользователя
    private Map<String, String> parseInitData(String initData) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = initData.split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf('=');
            if (idx <= 0) continue;
            String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
            String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);

            result.put(key, value);
        }

        return result;
    }

    //собираем check_init_data
    private String buildCheckData(Map<String, String> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())//сортируем по ключам в алфавитном порядке
                .map(entry -> entry.getKey() + "=" + entry.getValue())//каждую пару соединяем через знак равенства
                .collect(Collectors.joining("\n"));//после каждой пары добавляем знак \n
    }

    private byte[] calculateHMAC(byte[] dataChecking, byte[] token) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(dataChecking, "HmacSHA256");
        hmacSha256.init(keySpec);

        return hmacSha256.doFinal(token);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
