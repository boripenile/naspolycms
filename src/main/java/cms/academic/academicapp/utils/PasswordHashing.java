package cms.academic.academicapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashing {
	public static void main(String[] args){		
//		String password = "abubakar";
//		System.out.println(encodePassword(password));
	}
	public static String encodePassword(String plainPassword){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return (passwordEncoder.encode(plainPassword));
	}
	
	public static boolean decodePassword(String plainPassword, String hashedPassword){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(plainPassword, hashedPassword);
	}
}
