package domain.user.repository;

import domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
	User save(User user);
	
	User update(Long userId, User user);
	
	void deleteById(Long id);
	
	void updatePassword(Long userId, String password);
	
	Optional<User> findById(Long id);
	
	Optional<User> findByEmail(String email);

	List<User> findByIds(List<Long> userIds);
}