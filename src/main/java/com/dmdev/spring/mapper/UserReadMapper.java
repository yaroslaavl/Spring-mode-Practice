package com.dmdev.spring.mapper;

import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;
    @Override
    public UserReadDto map(User object) {
        CompanyReadDto companyReadDto = Optional.of(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstName(),
                object.getLastName(),
                object.getRole(),
                companyReadDto
        );
    }
}
