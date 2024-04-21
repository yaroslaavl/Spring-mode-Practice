package com.dmdev.spring.mapper;

import com.dmdev.spring.database.entity.Company;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.repository.CompanyRepository;
import com.dmdev.spring.dto.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject,toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object,user);
        return user;
    }

    private Company getCompany(Integer companyId){
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
    private void copy(UserCreateEditDto object,User user){
        user.setUsername(object.getUsername());
        user.setFirstName(object.getFirstname());
        user.setLastName(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));
    }
}
