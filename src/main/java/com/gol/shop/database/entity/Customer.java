package com.gol.shop.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "Customer.customerChats",
        attributeNodes = @NamedAttributeNode("customerChats"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "customerChats")
@EqualsAndHashCode(of = "email", callSuper = false)
@Builder
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "customer")
public class Customer extends AuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String firstname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate birthDate;

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<CustomerChat> customerChats = new ArrayList<>();
}












