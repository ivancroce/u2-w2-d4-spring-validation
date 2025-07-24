package ictech.u2_w2_d4_spring_validation.payloads;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class NewAuthorPayload {
    @NotEmpty(message = "The name is mandatory")
    @Size(min = 2, max = 30, message = "The name must be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "The surname is mandatory")
    @Size(min = 2, max = 30, message = "The surname must be between 2 and 30 characters")
    private String surname;
    @NotEmpty(message = "The email is mandatory")
    @Email(message = "The provided email is not a valid address")
    private String email;
    @NotNull(message = "The date of birth is mandatory (format: YYYY-MM-DD)")
    @Past(message = "The date of birth must be in the past.")
    private LocalDate birthDate;
}
