package ictech.u2_w2_d4_spring_validation.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ictech.u2_w2_d4_spring_validation.entities.Author;
import ictech.u2_w2_d4_spring_validation.exceptions.BadRequestException;
import ictech.u2_w2_d4_spring_validation.exceptions.NotFoundException;
import ictech.u2_w2_d4_spring_validation.payloads.NewAuthorDTO;
import ictech.u2_w2_d4_spring_validation.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AuthorsService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Cloudinary imgUploader;

    public Author saveAuthor(NewAuthorDTO payload) {
        // Verify if the email is already in use
        this.authorRepository.findByEmail(payload.email()).ifPresent(author -> {
            throw new BadRequestException("The email '" + payload.email() + "' is already in use.");
        });

        // Add server-generated values
        Author newAuthor = new Author(payload.name(), payload.surname(), payload.email(), payload.birthDate());
        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());

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

    public Author findByIdAndUpdate(UUID authorId, NewAuthorDTO payload) {
        // find the author in the DB
        Author found = this.findById(authorId);

        // if the email doesn't exist
        if (!found.getEmail().equals(payload.email()))
            this.authorRepository.findByEmail(payload.email()).ifPresent(author -> {
                throw new BadRequestException("The email '" + author.getEmail() + "' is already in use.");
            });

        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        found.setBirthDate(payload.birthDate());
        found.setAvatar("https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());

        Author updatedAuthor = this.authorRepository.save(found);

        log.info("The Author with ID '" + updatedAuthor.getId() + "' was updated.");

        return updatedAuthor;
    }

    public void findByIdAndDelete(UUID authorId) {
        Author found = this.findById(authorId);
        this.authorRepository.delete(found);
    }

    public String uploadAvatar(MultipartFile file) {
        try {
            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageURL = (String) result.get("url"); // I read the url from Cloudinary's response.
            return imageURL;
        } catch (Exception e) {
            throw new BadRequestException("There were problems saving the file.");
        }
    }
}
