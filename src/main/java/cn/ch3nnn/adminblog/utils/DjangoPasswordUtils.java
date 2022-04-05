package cn.ch3nnn.adminblog.utils;


import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


/**
 * 类似于Django的PasswordHasher的密码哈希器的示例实现需要Java8（但应易于移植到较旧的JRE），目前仅适用于pbkdf2_sha256算法
 * Django code: https://github.com/django/django/blob/1.6.5/django/contrib/auth/hashers.py#L221
 *
 * @Author ChenTong
 * @Date 2021/1/25 17:24
 */
public class DjangoPasswordUtils {
    public static final String SALT = "xSKNipvfOC96";
    public final String algorithm = "pbkdf2_sha256";
    public final Integer DEFAULT_ITERATIONS = 12000;


    public DjangoPasswordUtils() {
    }

    public String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            System.exit(1);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Could NOT generate secret key");
            e.printStackTrace();
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    public String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    public String encode(String password, String salt) {
        return this.encode(password, salt, this.DEFAULT_ITERATIONS);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and
        // HASH; parts are joined with dollar character ("$")
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }

    private static void passwordShouldMatch(String password, String expectedHash) {
        DjangoPasswordUtils djangoPasswordUtils = new DjangoPasswordUtils();

        if (djangoPasswordUtils.checkPassword(password, expectedHash)) {
            System.out.println(" => OK");
        } else {
            String[] parts = expectedHash.split("\\$");
            if (parts.length != 4) {
                System.out.printf(" => Wrong hash provided: '%s'\n", expectedHash);
                return;
            }
            String salt = parts[2];
            String resultHash = djangoPasswordUtils.encode(password, salt);
            String msg = " => Wrong! Password '%s' hash expected to be '%s' but is '%s'\n";
            System.out.printf(msg, password, expectedHash, resultHash);
        }
    }

    private static void passwordShouldNotMatch(String password, String expectedHash) {
        DjangoPasswordUtils djangoPasswordUtils = new DjangoPasswordUtils();

        if (djangoPasswordUtils.checkPassword(password, expectedHash)) {
            System.out.printf(" => Wrong (password '%s' did '%s' match but were not supposed to)\n", password, expectedHash);
        } else {
            System.out.println(" => OK (password didn't match)");
        }
    }

}