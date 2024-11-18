package rk181.java_survey_app_backend.auth.dto;

import lombok.Data;

@Data
public class AuthDTO {

	private String nickname;
	private String password;

	public AuthDTO() {}

	public AuthDTO(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
	}
}