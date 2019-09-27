import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Encriptar {
	private final String textoEncriptado = "xZwM7BWIpSjYyGFr9rhpEa+cYVtACW7yQKmyN6OYSCv0ZEg9jWbc6lKzzCxRSSIvOvlimQZBMZOYnOwiA9yy3YU8zk4abFSItoW6Wj0ufQ0=";
	private StringBuilder llave = new StringBuilder("29dh120_dk1_3");
	private static byte[] key;

	public Encriptar() {

	}

	private static SecretKeySpec secretKey;

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void getLlaveTanteo() {
		String llaveTmp = "";
		for (char letra = 'a'; letra != ('z' + 1); letra++) {
			for (char numero = '0'; numero < ('9' + 1); numero++) {
				llave.setCharAt(7, letra);
				llave.setCharAt(11, numero);
				llaveTmp = desencriptarAES(llave.toString());
				if (llaveTmp != null) {
					System.out.println(llaveTmp);
				}
			}
		}
	}

	public String desencriptarAES(String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			String texto = new String(cipher.doFinal(Base64.getDecoder().decode(textoEncriptado)));
			System.out.println("Texto desencriptado:\n" + texto);
			return secret;
		} catch (Exception e) {
			return null;
		}
	}

	public String encriptarAES(String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(textoEncriptado.getBytes("UTF-8")));
		} catch (Exception e) {
			return null;
		}

	}
}
