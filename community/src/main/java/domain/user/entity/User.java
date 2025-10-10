package domain.user.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class User extends BaseEntity {

	private Long userId;
	private String nickname;
	private String email;
	private String password;
	private String profile_image;
	private ArrayList<Long> articleIds;
	private ArrayList<Long> commentIds;
}
