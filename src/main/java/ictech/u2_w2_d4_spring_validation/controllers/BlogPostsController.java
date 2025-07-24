package ictech.u2_w2_d4_spring_validation.controllers;

import ictech.u2_w2_d4_spring_validation.entities.BlogPost;
import ictech.u2_w2_d4_spring_validation.exceptions.ValidationException;
import ictech.u2_w2_d4_spring_validation.payloads.NewBlogPostDTO;
import ictech.u2_w2_d4_spring_validation.payloads.NewBlogPostRespDTO;
import ictech.u2_w2_d4_spring_validation.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogPosts")
public class BlogPostsController {

    @Autowired
    private BlogPostsService blogPostsService;

    // 1. GET http://localhost:3001/blogPosts
    @GetMapping
    public Page<BlogPost> getBlogPosts(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy

    ) {
        return this.blogPostsService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:3001/blogPosts (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public NewBlogPostRespDTO createBlogPost(@RequestBody @Validated NewBlogPostDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            BlogPost newBlogPost = this.blogPostsService.saveBlogPost(payload);
            return new NewBlogPostRespDTO(newBlogPost.getId());
        }
    }

    // 3. GET http://localhost:3001/blogPosts/{blogPostId}
    @GetMapping("/{blogPostId}")
    public BlogPost getBlogPostById(@PathVariable UUID blogPostId) {
        return this.blogPostsService.findById(blogPostId);
    }

    // 4. PUT http://localhost:3001/blogPosts/{blogPostId} (+ payload)
    @PutMapping("/{blogPostId}")
    public BlogPost findBlogPostByIdAndUpdate(@PathVariable UUID blogPostId, @RequestBody NewBlogPostDTO payload) {
        return this.blogPostsService.findByIdAndUpdate(blogPostId, payload);
    }

    // 5. DELETE http://localhost:3001//blogPosts/{blogPostId}
    @DeleteMapping("/{blogPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void findBlogPostByIdAndDelete(@PathVariable UUID blogPostId) {
        this.blogPostsService.findByIdAndDelete(blogPostId);
    }
}

