package ictech.u2_w2_d4_spring_validation.repositories;

import ictech.u2_w2_d4_spring_validation.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
}
