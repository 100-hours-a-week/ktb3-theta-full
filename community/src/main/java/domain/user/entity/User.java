package domain.user.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class User extends BaseEntity {
	
	@Setter
	private Long id;
	
	@Setter
	private String nickname;
	
	private String email;
	
	@Setter
	private String password;
	
	@Setter
	private String profileImage;
	
	@Setter
	private LocalDateTime deletedAt;
	
	private ArrayList<Long> articleIds;
	private ArrayList<Long> commentIds;
	
	public static User create(String nickname, String password, String email, String profileImage) {
		return new User(null, nickname, email, password, profileImage, null, new ArrayList<>(), new ArrayList<>());
	}
}
