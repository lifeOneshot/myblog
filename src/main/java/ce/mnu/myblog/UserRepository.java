package ce.mnu.myblog;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<BlogUser, Long> {
	BlogUser findByEmail(String email);
}
