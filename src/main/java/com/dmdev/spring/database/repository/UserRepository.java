package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.PersonalInfo2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, FilterUserRepository, RevisionRepository<User,Long,Integer>, QuerydslPredicateExecutor<User> {

     /*@Query(nativeQuery = true,
     value = "SELECT u.firstname,u.lastname FROM spring.users u WHERE u.firstname like :firstname and u.lastname like :lastname")*/
    @Query("select u from User u" +
             " where u.firstName like %:firstname% and u.lastName like %:lastname%")
     List<User> findAllBy(@Param("firstname") String firstname, @Param("lastname") String lastname);

     @Query(value = "SELECT u.* FROM spring.users u WHERE u.username = :username",
             nativeQuery = true)
     List<User> findAllByUsername(@Param("username") String username);

     @Modifying(clearAutomatically = true)
     @Query("update User u" +
             " set u.role = :role " +
             "where u.id in(:ids)")
     int updateRole(@Param("role")Role role, @Param("ids")List<Long> ids);


     Optional<User> findTopByOrderByIdDesc();

     @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
     @Lock(LockModeType.PESSIMISTIC_READ)
     List<User> findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthDate, Sort sort);

    //Collection Stream
    //Streamable, Slice, Page
    /*@EntityGraph("User.company")*/
    @EntityGraph(attributePaths = {"company","company.locales"})
    @Query(value = "select u from User u",
           countQuery = "select count(distinct u.firstName) from User u")
     Page<User> findAllBy(Pageable pageable);

    /*List<PersonalInfo> findAllByCompanyId(Integer companyId);*/
    @Query(nativeQuery = true,
           value = "SELECT firstname,lastname,birth_date birthDate FROM spring.users WHERE company_id = :companyId")
    List<PersonalInfo2> findAllByCompanyId(@Param("companyId") Integer companyId);
}
