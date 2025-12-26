package ir.maktabsharif.test_app.dto.user;

import ir.maktabsharif.test_app.model.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Status status;
}
