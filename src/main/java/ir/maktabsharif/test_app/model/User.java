package ir.maktabsharif.test_app.model;

import ir.maktabsharif.test_app.model.enums.Role;
import ir.maktabsharif.test_app.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")

@Entity
@Table(name = "users")
public class User extends BaseModel<Long> {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
