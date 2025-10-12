package domain.user.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class User extends BaseEntity {
	
	@Setter
	private Long userId;
	
	@Setter
	private String nickname;
	
	private String email;
	
	@Setter
	private String password;
	
	@Setter
	private String profileImage;
	
	private ArrayList<Long> articleIds;
	private ArrayList<Long> commentIds;
	
	public static User create(String nickname, String password, String email, String profileImage) {
		return new User(null, nickname, email, password, profileImage, new ArrayList<>(), new ArrayList<>());
	}
}
