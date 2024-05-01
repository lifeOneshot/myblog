package ce.mnu.myblog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;



@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	@Query(value = "SELECT num, title, author FROM article", nativeQuery=true)
	Iterable<ArticleHeader> findArticleHeaders();
}

