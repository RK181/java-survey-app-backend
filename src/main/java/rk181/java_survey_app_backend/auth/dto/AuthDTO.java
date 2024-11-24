package rk181.java_survey_app_backend.auth.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthDTO {
    @NotBlank
    @Size(min = 5, max = 20, message = "Nickname must be between 5 and 20 characters")
	private String nickname;
    @NotBlank
    @Min(value = 12, message = "Password must be at least 12 characters")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).*$", message = "Password must contain at least one lowercase letter, one uppercase letter and one digit")
	private String password;

	public AuthDTO() {}

	public AuthDTO(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
	}
}