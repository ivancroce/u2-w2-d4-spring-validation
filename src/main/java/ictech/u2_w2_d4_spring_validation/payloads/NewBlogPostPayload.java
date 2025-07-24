package ictech.u2_w2_d4_spring_validation.payloads;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class NewBlogPostPayload {
    @NotEmpty(message = "The genre is mandatory")
    @Size(min = 2, max = 30, message = "The genre must be between 2 and 30 characters")
    private String genre;
    @NotEmpty(message = "The title is mandatory")
    @Size(min = 5, max = 100, message = "The title must be between 5 and 30 characters")
    private String title;
    @NotEmpty(message = "The content is mandatory")
    @Size(min = 50, message = "The content must have at least 50 characters")
    private String content;
    @Min(value = 1, message = "The reading time must be at least 1 minute")
    private int readingTime;
    @NotNull(message = "The author ID is mandatory")
    private UUID authorId;
}
