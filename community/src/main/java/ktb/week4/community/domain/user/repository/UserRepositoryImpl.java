package ktb.week4.community.domain.user.repository;

import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.enums.Status;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong(1L);
	
	@Override
	public User save(User user) {
		// ID  및 생성 시간 설정
		user.setId(sequence.getAndIncrement());
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
	public void deleteById(Long userId) {
		User user = users.get(userId);
		user.setDeletedAt(LocalDateTime.now());
		user.setStatus(Status.INACTIVE);
	}
	
	@Override
		public Optional<User> findById (Long id){
			User user = users.get(id);
			if (user == null || user.getStatus() == Status.INACTIVE) {
				return Optional.empty();
			}
			return Optional.of(user);
		}
		
		@Override
		public Optional<User> findByEmail (String email){
			return users.values().stream()
					.filter(user -> user.getStatus() == Status.ACTIVE && user.getEmail().equals(email))
					.findFirst();
		}

	@Override
	public List<User> findByIds(List<Long> userIds) {
		return users.values().stream()
				.filter(user -> user.getStatus() == Status.ACTIVE && userIds.contains(user.getId()))
				.collect(Collectors.toList());
	}
}
