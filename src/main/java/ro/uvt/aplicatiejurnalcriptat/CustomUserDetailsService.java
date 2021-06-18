
package ro.uvt.aplicatiejurnalcriptat;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private RepoUser repoUser;

	@Autowired
	private RepoRole repoRole;

	@Autowired
	private RepoDiary repoDiary;

	@Autowired
	private BCryptPasswordEncoder bCryptPassEncoder;

	private static final Random RANDOM = new SecureRandom();

	public User findUserByEmail(String email) {
		return repoUser.findByEmail(email);
	}

	public void saveUser(User user) {
		String currentPassword = user.getPassword();
		String encodedPassword = bCryptPassEncoder.encode(currentPassword);
		user.setPassword(encodedPassword);

		user.setEnabled(true);

		Role userRole = repoRole.findByRole("USER");
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		repoUser.save(user);
	}

	public void saveDiary(Diary diary) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException,
			InvalidKeySpecException {
		String currentDiary = diary.getDiary();
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		String currentSalt = Base64.getEncoder().encodeToString(salt);
		User password = new User();
		password.getPassword();
		String pass = password.toString();
		SecretKey key = EncryptDecryptDiary.getKeyFromPassword(pass, currentSalt);
		IvParameterSpec ivParameterSpec = EncryptDecryptDiary.generateIv();
		String algorithm = "AES/CBC/PKCS5Padding";
		String cipherText = EncryptDecryptDiary.encryption(algorithm, currentDiary, key, ivParameterSpec);

		Diary di = new Diary();
		di.setDiary(cipherText);
		String plainText = EncryptDecryptDiary.decryption(algorithm, cipherText, key, ivParameterSpec);

		
		repoDiary.save(di);
		

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = repoUser.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Username or password is incorrect.");
		} else {
			List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());

			return buildUserForAuthentication(user, authorities);
		}
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach(role -> {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		});

		return new ArrayList<>(roles);
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
