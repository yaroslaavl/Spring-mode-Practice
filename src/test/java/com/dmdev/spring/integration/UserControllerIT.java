package com.dmdev.spring.integration;

import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.dmdev.spring.dto.UserCreateEditDto.Fields.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IT
@RequiredArgsConstructor
public class UserControllerIT {

    private final MockMvc mockMvc;

    @SneakyThrows
    @Test
    void findAll(){
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(5)));
    }

    @SneakyThrows
    @Test
    void create(){
        mockMvc.perform(post("/users")
                .param(username,"test@gmail.com")
                .param(firstname,"test")
                .param(lastname,"test")
                .param(role,"ADMIN")
                .param(companyId,"company")
                        .param(birthDate,"2000-01-01")
        )
                .andExpectAll(
                        status().is2xxSuccessful(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }
}
