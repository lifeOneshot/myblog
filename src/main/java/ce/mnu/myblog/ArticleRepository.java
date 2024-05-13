package ce.mnu.myblog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;



@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	Article findByNum(Long num);
	@Query(value = "SELECT num, title, author, viewcount FROM article", nativeQuery=true)
	Page<ArticleHeader> findArticleHeaders(Pageable pageable);
	
	@Query(value = "SELECT num, title, author, viewcount FROM article WHERE email = ?1", nativeQuery=true)
	Page<ArticleHeader> findArticleHeadersByEmail(String email, Pageable pageable);
}

