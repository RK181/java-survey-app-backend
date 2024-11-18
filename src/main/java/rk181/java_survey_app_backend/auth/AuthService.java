package rk181.java_survey_app_backend.auth;

import java.security.SecureRandom;
import java.util.Optional;

import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import rk181.java_survey_app_backend.auth.dto.AuthDTO;
import rk181.java_survey_app_backend.users.User;
import rk181.java_survey_app_backend.users.UserRepository;

@Service
public class AuthService {

    /***
     * We are using Argon2PasswordEncoder to hash the password.
     * Specifically, we are using the Argon2id variant with OWASP-recommended parameters.
     * <p>Link More info: https://www.owasp.org/index.php/Password_Storage_Cheat_Sheet</p>
     * The parameters are: 
     * @param memory = 47104 (46 MiB), 
     * @param iterations = 1, 
     * @param parallelism = 1,
     * @param saltLength = 16 -> 128 bits, 
     * @param keyLength = 32 -> 256 bits
     */
    private final static Argon2PasswordEncoder argon2Encoder = new Argon2PasswordEncoder(16, 32, 1, 47104 , 1);
    private final UserRepository userRepository;

    // Can be substituted with @RequiredArgsConstructor of Lombok
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /***
     * Check if the raw password matches the encoded password using Argon2PasswordEncoder
     * @param encodedPasword
     * @param rawPassword
     * @return true if the password matches, false otherwise
     */
    private boolean checkPassword(String encodedPasword, String rawPassword) {
        return argon2Encoder.matches(rawPassword, encodedPasword);
    }

    /***
     * Check if the token exists in the database
     * @param token
     * @return User or null if not found
     */
    public User checkToken(String token) {
        return userRepository.findByToken(token).orElse(null);
    }

    /***
     * Create a token for the user using the nickname and a random byte array.
     * The token is hashed using SHA3-512
     * @param user
     * @return the token string encoded in Base64
     */
    private String createToken(User user) {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        
        String token = user.getNickname() + tokenBytes.toString();

        SHA3Digest digest = new SHA3Digest(512);
        byte[] tokenHash = new byte[digest.getDigestSize()];
        digest.update(token.getBytes(), 0, token.length());
        digest.doFinal(tokenHash, 0);

        return Base64.toBase64String(tokenHash);
    }

    public String login(AuthDTO authRequest) {
        User user = userRepository.findByNickname(authRequest.getNickname()).orElse(null);
        if (user == null || !checkPassword(user.getPassword(), authRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User nickname or password is incorrect");
        }
        
        String token = createToken(user);
        user.setToken(token);
        userRepository.save(user);
        
        return user.getToken();
    }

    public User register(AuthDTO authRequest) {
        User userDB = userRepository.findByNickname(authRequest.getNickname()).orElse(null);
        if (userDB != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        User newUser = User.fromAuthDTO(authRequest);

        String encodedPassword = argon2Encoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }

    public void logout() {
        Long id = Auth.getUserIDFromContext();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User nickname or password is incorrect");
        }
        user.setToken(null);

        userRepository.save(user);
    }
}
