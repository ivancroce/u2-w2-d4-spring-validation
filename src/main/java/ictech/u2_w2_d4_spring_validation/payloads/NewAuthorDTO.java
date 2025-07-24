package ictech.u2_w2_d4_spring_validation.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewAuthorDTO(
        @NotEmpty(message = "The name is mandatory")
        @Size(min = 2, max = 30, message = "The name must be between 2 and 30 characters")
        String name,
        @NotEmpty(message = "The surname is mandatory")
        @Size(min = 2, max = 30, message = "The surname must be between 2 and 30 characters")
        String surname,
        @NotEmpty(message = "The email is mandatory")
        @Email(message = "The provided email is not a valid address")
        String email,
        @NotNull(message = "The date of birth is mandatory (format: YYYY-MM-DD)")
        @Past(message = "The date of birth must be in the past.")
        LocalDate birthDate) {
}
