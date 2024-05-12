package ce.mnu.myblog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	Page<Comment> findByArticle(Article article, Pageable pagealbe);
}
