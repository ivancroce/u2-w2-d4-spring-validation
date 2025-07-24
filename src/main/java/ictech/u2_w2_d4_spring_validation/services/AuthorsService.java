package ictech.u2_w2_d4_spring_validation.services;

import ictech.u2_w2_d4_spring_validation.entities.Author;
import ictech.u2_w2_d4_spring_validation.exceptions.BadRequestException;
import ictech.u2_w2_d4_spring_validation.exceptions.NotFoundException;
import ictech.u2_w2_d4_spring_validation.payloads.NewAuthorPayload;
import ictech.u2_w2_d4_spring_validation.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AuthorsService {

    @Autowired
    AuthorRepository authorRepository;

    public Author saveAuthor(NewAuthorPayload payload) {
        // Verify if the email is already in use
        this.authorRepository.findByEmail(payload.getEmail()).ifPresent(author -> {
            throw new BadRequestException("The email '" + payload.getEmail() + "' is already in use.");
        });

        // Add server-generated values
        Author newAuthor = new Author(payload.getName(), payload.getSurname(), payload.getEmail(), payload.getBirthDate());
        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" + payload.getName() + "+" + payload.getSurname());

        Author savedAuthor = this.authorRepository.save(newAuthor);

        log.info("The Author with ID '" + savedAuthor.getId() + "' was created.");
        return savedAuthor;
    }

    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    public Author findById(UUID authorId) {
        return this.authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public Author findByIdAndUpdate(UUID authorId, NewAuthorPayload payload) {
        // find the author in the DB
        Author found = this.findById(authorId);

        // if the email doesn't exist
        if (!found.getEmail().equals(payload.getEmail()))
            this.authorRepository.findByEmail(payload.getEmail()).ifPresent(author -> {
                throw new BadRequestException("The email '" + author.getEmail() + "' is already in use.");
            });

        found.setName(payload.getName());
        found.setSurname(payload.getSurname());
        found.setEmail(payload.getEmail());
        found.setBirthDate(payload.getBirthDate());
        found.setAvatar("https://ui-avatars.com/api/?name=" + payload.getName() + "+" + payload.getSurname());

        Author updatedAuthor = this.authorRepository.save(found);

        log.info("The Author with ID '" + updatedAuthor.getId() + "' was updated.");

        return updatedAuthor;
    }

    public void findByIdAndDelete(UUID authorId) {
        Author found = this.findById(authorId);
        this.authorRepository.delete(found);
    }
}
