package ru.evsmanko.mankoff.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.evsmanko.mankoff.dto.UserDto;
import ru.evsmanko.mankoff.entity.User;

@Component
public class UserConvertor {
    private final ModelMapper modelMapper;

    public UserConvertor() {
        modelMapper = new ModelMapper();
    }

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
