package com.dmdev.spring.integration;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.service.UserService;
import com.dmdev.spring.database.pool.ConnectionPool;
import com.dmdev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Transactional
public class UserServiceIT {

    private UserService userService;
    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;

    @Test
    void findAll(){
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);
    }
    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }
    @Test
    void create(){
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "Test",
                "Testova",
                Role.ADMIN,
                COMPANY_1
        );
        UserReadDto userReadDto = userService.create(userCreateEditDto);
        assertEquals(userCreateEditDto.getUsername(),userReadDto.getUsername());
        assertEquals(userCreateEditDto.getBirthDate(),userReadDto.getBirthDate());
        assertEquals(userCreateEditDto.getFirstname(),userReadDto.getFirstname());
        assertEquals(userCreateEditDto.getLastname(),userReadDto.getLastname());
        assertEquals(userCreateEditDto.getRole(),userReadDto.getRole());
        assertSame(userCreateEditDto.getCompanyId(),userReadDto.getCompany().id());
    }
    @Test
    void update(){
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "Test",
                "Testova",
                Role.ADMIN,
                COMPANY_1
        );
        Optional<UserReadDto> update = userService.update(USER_1, userCreateEditDto);
        assertTrue(update.isPresent());
        update.ifPresent(user -> {
            assertEquals(userCreateEditDto.getUsername(),user.getUsername());
            assertEquals(userCreateEditDto.getBirthDate(),user.getBirthDate());
            assertEquals(userCreateEditDto.getFirstname(),user.getFirstname());
            assertEquals(userCreateEditDto.getLastname(),user.getLastname());
            assertEquals(userCreateEditDto.getRole(),user.getRole());
            assertSame(userCreateEditDto.getCompanyId(),user.getCompany().id());
        });
    }
    @Test
    void delete(){
        assertFalse(userService.delete(-12L));
        assertTrue(userService.delete(USER_1));
    }
}
