package rk181.java_survey_app_backend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@SpringBootTest
class JavaSurveyAppBackendApplicationTests {

    @Test
    public void givenRawPassword_whenEncodedWithArgon2_thenMatchesEncodedPassword() {
        String rawPassword = "Baeldung";
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        String springBouncyHash = arg2SpringSecurity.encode(rawPassword);
            
        assertTrue(arg2SpringSecurity.matches(rawPassword, springBouncyHash));
    }

}
