package ir.maktabsharif.test_app.dto.user;

import ir.maktabsharif.test_app.model.enums.Role;
import ir.maktabsharif.test_app.model.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchFilter {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Status status;
}
