package com.dmdev.spring.service;

import com.dmdev.spring.database.entity.Company;
import com.dmdev.spring.database.repository.CompanyRepository;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.listener.EntityEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    private static final Integer COMPANY_ID = 1;
    @InjectMocks
    private CompanyService companyService;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private UserService userService;
    @Mock
    private  ApplicationEventPublisher eventPublisher;

    @Test
    void findById() {
      Mockito.doReturn(Optional.of(new Company(COMPANY_ID,null, Collections.emptyMap())))
        .when(companyRepository).findById(COMPANY_ID);

        Optional<CompanyReadDto> byId = companyService.findById(COMPANY_ID);

        Assertions.assertTrue(byId.isPresent());
        var expected = new CompanyReadDto(COMPANY_ID,null);
        byId.ifPresent(actual -> Assertions.assertEquals(expected,actual));

        verify(eventPublisher).publishEvent(any(EntityEvent.class));
        verifyNoMoreInteractions(eventPublisher,userService);
    }


}