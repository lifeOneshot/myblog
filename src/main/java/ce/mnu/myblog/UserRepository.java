package ce.mnu.myblog;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<BlogUser, Long> {
	BlogUser findByEmail(String email);
	List<BlogUser> findByName(String name);
}
