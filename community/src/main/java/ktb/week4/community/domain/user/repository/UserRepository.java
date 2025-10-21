package ktb.week4.community.domain.user.repository;

import ktb.week4.community.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
	User save(User user);
	
	User update(Long userId, User user);
	
	void deleteById(User user);
	
	void updatePassword(User user, String password);
	
	Optional<User> findById(Long id);
	
	Optional<User> findByEmail(String email);

	List<User> findByIds(List<Long> userIds);
}