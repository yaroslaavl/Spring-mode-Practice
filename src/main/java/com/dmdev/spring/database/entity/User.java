package com.dmdev.spring.database.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*@NamedEntityGraph(name = "User.company",
attributeNodes = @NamedAttributeNode("company"))*/
@Data
@ToString(exclude = "userChatList")
@EqualsAndHashCode(of = "username")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "spring",name = "users")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class User extends AuditingEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    private LocalDate birthDate;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Builder.Default
    @NotAudited
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChatList = new ArrayList<>();
}
