package ce.mnu.myblog;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<BlogUser, Long> {
	BlogUser findByEmail(String email);
	BlogUser findByNo(Long userNo);
	List<BlogUser> findByName(String name);
}
