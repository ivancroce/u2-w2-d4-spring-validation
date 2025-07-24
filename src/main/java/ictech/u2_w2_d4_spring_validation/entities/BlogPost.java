package ictech.u2_w2_d4_spring_validation.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "blog_posts")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BlogPost {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String cover;
    @Column(nullable = false)
    private String content;
    @Column(name = "reading_time", nullable = false)
    private int readingTime;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public BlogPost(String genre, String title, String cover, String content, int readingTime, Author author) {
        this.genre = genre;
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.readingTime = readingTime;
        this.author = author;
    }
}
