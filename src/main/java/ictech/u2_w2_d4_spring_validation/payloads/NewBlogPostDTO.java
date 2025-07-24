package ictech.u2_w2_d4_spring_validation.payloads;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewBlogPostDTO(@NotEmpty(message = "The genre is mandatory")
                             @Size(min = 2, max = 30, message = "The genre must be between 2 and 30 characters")
                             String genre,
                             @NotEmpty(message = "The title is mandatory")
                             @Size(min = 2, max = 100, message = "The title must be between 2 and 100 characters")
                             String title,
                             @NotEmpty(message = "The content is mandatory")
                             @Size(min = 5, message = "The content must have at least 5 characters")
                             String content,
                             @Min(value = 1, message = "The reading time must be at least 1 minute")
                             int readingTime,
                             @NotNull(message = "The author ID is mandatory")
                             UUID authorId) {
}
