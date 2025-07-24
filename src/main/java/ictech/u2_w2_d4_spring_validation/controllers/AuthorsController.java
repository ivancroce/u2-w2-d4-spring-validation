package ictech.u2_w2_d4_spring_validation.controllers;

import ictech.u2_w2_d4_spring_validation.entities.Author;
import ictech.u2_w2_d4_spring_validation.exceptions.ValidationException;
import ictech.u2_w2_d4_spring_validation.payloads.NewAuthorDTO;
import ictech.u2_w2_d4_spring_validation.payloads.NewAuthorRespDTO;
import ictech.u2_w2_d4_spring_validation.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController // is a specialization of @Component:
// When the application starts, Spring will automatically create an instance of the class and add it to the Spring Container.
// It defines a collection of endpoints.
// It's a class that handles web requests and returns data
@RequestMapping("/authors") // endpoint of this controller => (http://localhost:3001/authors)
public class AuthorsController {

    @Autowired
    AuthorsService authorsService;

    // 1. GET http://localhost:3001/authors
    @GetMapping
    public List<Author> getAuthors() {
        return this.authorsService.findAll();
    }

    // 2. POST http://localhost:3001/authors (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public NewAuthorRespDTO createAuthor(@RequestBody @Validated NewAuthorDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Author newAuthor = this.authorsService.saveAuthor(payload);
            return new NewAuthorRespDTO(newAuthor.getId());
        }

    }

    // 3. GET http://localhost:3001/authors/{authorId}
    @GetMapping("/{authorId}")
    public Author getAuthorById(@PathVariable UUID authorId) {
        return this.authorsService.findById(authorId);
    }

    // 4. PUT http://localhost:3001/authors/{authorId} (+ payload)
    @PutMapping("/{authorId}")
    public Author findAuthorByIdAndUpdate(@PathVariable UUID authorId, @RequestBody NewAuthorDTO payload) {
        return this.authorsService.findByIdAndUpdate(authorId, payload);
    }

    // 5. DELETE http://localhost:3001/authors/{authorId}
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void findAuthorByIdAndDelete(@PathVariable UUID authorId) {
        this.authorsService.findByIdAndDelete(authorId);
    }

    // 6. PATCH http://localhost:3001/authors/{authorId}/avatar
    @PatchMapping("/{authorId}/avatar")
    public Author uploadImage(@RequestParam("avatar") MultipartFile file, @PathVariable UUID authorId) {
        // "avatar" must match EXACTLY the FormData field into which the frontend will insert the image
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return this.authorsService.uploadAvatar(file, authorId);
    }
}
