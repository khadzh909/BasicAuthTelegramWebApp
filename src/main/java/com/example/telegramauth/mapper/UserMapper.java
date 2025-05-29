package com.example.telegramauth.mapper;

import com.example.telegramauth.DTO.UserDTO;
import com.example.telegramauth.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User DTOToUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setFirst_name(userDTO.getFirst_name());
        user.setUsername(userDTO.getUsername());
        user.setLast_name(userDTO.getLast_name());
        user.setLanguage_code(userDTO.getLanguage_code());
        user.setAllows_write_to_pm(userDTO.getAllows_write_to_pm());

        return user;
    }

}
