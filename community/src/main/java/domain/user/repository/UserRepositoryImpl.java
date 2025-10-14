package domain.user.repository;

import domain.user.entity.User;
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
		// 이미 존재하는 이메일일 경우 예외 처리
		if (findByEmail(user.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already exists");
		}
		
		// ID  및 생성 시간 설정
		user.setUserId(users.keySet().stream()
				.max(Long::compareTo)
				.orElse(0L) + 1);
		user.setCreatedAt(LocalDateTime.now());
		
		users.put(user.getUserId(), user);
		return users.get(user.getUserId());
	}
	
	@Override
	public User update(Long userId, User user) {
		User oldUser = findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if(!user.getNickname().isEmpty()) {
			oldUser.setNickname(user.getNickname());
		}
		
		if(!user.getProfileImage().isEmpty()) {
			oldUser.setProfileImage(user.getProfileImage());
		}
		
		oldUser.setUpdatedAt(LocalDateTime.now());
		return oldUser;
	}
	
	@Override
	public void updatePassword(Long userId, String password) {
		User user = findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		user.setUpdatedAt(LocalDateTime.now());
		user.setPassword(password);
	}
	
	@Override
	public void deleteById(Long id) {
		users.remove(id);
	}
	
	@Override
		public Optional<User> findById (Long id){
			return Optional.ofNullable(users.get(id));
		}
		
		@Override
		public Optional<User> findByEmail (String email){
			return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
		}

	@Override
	public List<User> findByIds(List<Long> userIds) {
		return users.values().stream()
				.filter(user -> userIds.contains(user.getUserId()))
				.collect(Collectors.toList());
	}
}
