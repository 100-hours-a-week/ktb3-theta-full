package ktb.week4.community.domain.user.repository;

import ktb.week4.community.domain.user.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
	
	@Override
	public User save(User user) {
		// ID  및 생성 시간 설정
		user.setId(users.keySet().stream()
				.max(Long::compareTo)
				.orElse(0L) + 1);
		user.setCreatedAt(LocalDateTime.now());
		
		users.put(user.getId(), user);
		return user;
	}
	
	@Override
	public User update(Long userId, User user) {
		user.setUpdatedAt(LocalDateTime.now());
		return users.put(userId, user);
	}
	
	@Override
	public void updatePassword(User user, String password) {
		user.setUpdatedAt(LocalDateTime.now());
		user.setPassword(password);
	}
	
	@Override
	public void deleteById(User user) {
		user.setDeletedAt(LocalDateTime.now());
	}
	
	@Override
		public Optional<User> findById (Long id){
			User user = users.get(id);
			if (user == null || user.getDeletedAt() != null) {
				return Optional.empty();
			}
			return Optional.of(user);
		}
		
		@Override
		public Optional<User> findByEmail (String email){
			return users.values().stream()
					.filter(user -> user.getDeletedAt() == null && user.getEmail().equals(email))
					.findFirst();
		}

	@Override
	public List<User> findByIds(List<Long> userIds) {
		return users.values().stream()
				.filter(user -> user.getDeletedAt() == null && userIds.contains(user.getId()))
				.collect(Collectors.toList());
	}
}
