package com.dmdev.spring.integration;

import com.dmdev.spring.service.CompanyService;
import com.dmdev.spring.config.DataProperties;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@IT
@RequiredArgsConstructor
/*@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)*/
/*@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationRunner.class,initializers = ConfigDataApplicationContextInitializer.class)*/
public class CompanyServiceIT {

    private final CompanyService companyService;

    private final DataProperties dataProperties;
    private static final Integer COMPANY_ID = 1;

    @Test
    void findById(){
        Optional<CompanyReadDto> byId = companyService.findById(COMPANY_ID);

        Assertions.assertTrue(byId.isPresent());
        var expected = new CompanyReadDto(COMPANY_ID,null);
        byId.ifPresent(actual -> Assertions.assertEquals(expected,actual));
    }
}
