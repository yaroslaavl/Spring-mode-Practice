package com.dmdev.spring.integration;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.repository.UserRepository;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@IT
@RequiredArgsConstructor
@Transactional
public class UserRepositoryIT {

     private final UserRepository userRepository;

     @Test
     void checkQueries(){
         List<User> allBy = userRepository.findAllBy("a", "ov");
         Assertions.assertThat(allBy).hasSize(3);
         System.out.println(allBy);
     }
     @Test
     void checkQueriesByUsername(){
         List<User> all = userRepository.findAllByUsername("sveta@gmail.com");
         Assertions.assertThat(all).hasSize(1);
         System.out.println(all);
     }
     @Test
     void checkUpdate(){
         User byId = userRepository.getById(1L);
         assertSame(Role.ADMIN,byId.getRole());

         var i = userRepository.updateRole(Role.USER, Arrays.asList(1L, 5L));
         assertEquals(2,i);

         var theSameById = userRepository.getById(1L);
         assertSame(Role.USER,theSameById.getRole());
     }
     @Test
     void checkFindTopByOrderByIdDesc(){
         Optional<User> topByOrderByIdDesc = userRepository.findTopByOrderByIdDesc();
         assertTrue(topByOrderByIdDesc.isPresent());
         topByOrderByIdDesc.ifPresent(user -> assertEquals(5L,user.getId()));
     }
     @Test
     void checkSort(){
         Sort.TypedSort<User> sort = Sort.sort(User.class);
         Sort and = sort.by(User::getFirstName).and(sort.by(User::getLastName));

         var id = Sort.by("firstname").and(Sort.by("lastname"));
         List<User> top3ByBirthDateBeforeOrderByBirthDateDesc = userRepository.findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate.now(),and);
         Assertions.assertThat(top3ByBirthDateBeforeOrderByBirthDateDesc).hasSize(3);
     }

     @Test
     void checkPageable(){
         PageRequest id = PageRequest.of(1, 2, Sort.by("id"));
         var allBy = userRepository.findAllBy(id);
         allBy.forEach(user -> System.out.println(user.getCompany().getName()));

         while(allBy.hasNext()){
            allBy = userRepository.findAllBy(allBy.nextPageable());
            allBy.forEach(user -> System.out.println(user.getCompany().getName()));
         }
     }
     @Test
     void checkProjections(){
         var allByCompanyId = userRepository.findAllByCompanyId(1);
         Assertions.assertThat(allByCompanyId).hasSize(2);
         System.out.println();
     }
     @Test
     void checkCustom(){
         UserFilter filter = new UserFilter(
                 null, "ov",LocalDate.now()
         );
         List<User> allByFilter = userRepository.findAllByFilter(filter);
         System.out.println();
     }
     @Test
     void checkAuditing(){
         User user = userRepository.findById(1L).get();
         user.setBirthDate(user.getBirthDate().plusYears(1L));
         userRepository.flush();
         System.out.println();
     }
     @Test
     void checkJDBC(){
         var users= userRepository.findAllByCompanyIdAndRole(1,Role.USER);
         Assertions.assertThat(users).hasSize(1);
         System.out.println(users);
     }
     @Test
     void checkBatch(){
         var users= userRepository.findAll();
         userRepository.updateCompanyAndRole(users);
         System.out.println();

     }
}
