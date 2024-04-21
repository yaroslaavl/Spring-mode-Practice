package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.querydsl.QPredicates;
import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.UserFilter;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/*import static com.dmdev.spring.database.entity.QUser.user;*/
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String FIND_BY_COMPANYID_AND_ROLE = """
            SELECT firstname,lastname,birth_date 
            from spring.users 
            where company_id = ?
            and role = ?
            """;
    private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
            UPDATE spring.users
            SET company_id = :companyId,
            role = :role
            where id = :id
            """;
    private static final String UPDATE_COMPANY_AND_ROLE = """
            UPDATE spring.users
            SET company_id = ?,
            role = ?
            where id = ?
            """;
    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

        var from = query.from(User.class);
        query.select(from);
        List<Predicate> predicates = new ArrayList<>();
        if(filter.firstName() != null){
            predicates.add(criteriaBuilder.like(from.get("firstName"), filter.firstName()));
        }
        if(filter.lastName() != null){
            predicates.add(criteriaBuilder.like(from.get("lastName"), filter.lastName()));
        }
        if(filter.birthDate() != null){
            predicates.add(criteriaBuilder.lessThan(from.get("birthDate"), filter.birthDate()));
        }
        query.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANYID_AND_ROLE,(rs, rowNum) ->
                 new PersonalInfo(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birth_date").toLocalDate()
                ), companyId, role.name());
            }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        var args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .toList();
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE,args);
    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        var args= users.stream()
                        .map(user -> Map.of(
                                "companyId",user.getCompany().getId(),
                                "role", user.getRole().name(),
                                "id", user.getId()
                        ))
                                .map(MapSqlParameterSource::new)
                                        .toArray(MapSqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED,args);
    }
}
   /* @Override
    public List<User> findAllByFilter(UserFilter filter){
        var predicate = QPredicates.buider()
                .add(filter.firstName(),user.firstname::containsIgnoreCase)
                .add(filter.lastName(),user.lastname::containsIgnoreCase)
                .add(filter.birthDate(),user.birthDate::before)
                .build();
        return new JPAQuery<User>(entityManager)
                .select(QUser.user)
                .from(QUser.user)
                .where(predicate)
                .fetch();
    }*/

