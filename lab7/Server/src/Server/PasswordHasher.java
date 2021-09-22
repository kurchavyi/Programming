package Server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] bytes = md.digest(password.getBytes());
            BigInteger integers = new BigInteger(1, bytes);
            String newData = integers.toString(16);
            while (newData.length() < 32) {
                newData = "0" + newData;
            }
            return newData;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hash algorithm not found!");
            throw new IllegalStateException(e);
        }
    }
}
