import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Demo {

	public static void main(String[] args) {


        try {
        	String message = "This string contains a secret message.";
        	KeyGenerator keygen = KeyGenerator.getInstance("DES");
            keygen.init(56);  // To use 256 bit keys, you need the "unlimited strength" encryption policy files from Sun.
            byte[] key = keygen.generateKey().getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");

    		SecureRandom random = new SecureRandom();
            byte iv[] = new byte[8];//generate random 16 byte IV AES is always 16bytes
            random.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // initialize the cipher for encrypt mode
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);

            System.out.println("Key: " + new String(key, "utf-8"));
            System.out.println("IV: " + new String(iv, "utf-8"));
            System.out.println();

            // encrypt the message
            byte[] encrypted = cipher.doFinal(message.getBytes());
            System.out.println("Ciphertext: " + asHex(encrypted) + "\n");


        } catch(Exception ex){
        	ex.printStackTrace();
        }
	}

    /**
     * Turns array of bytes into string
     *
     * @param buf   Array of bytes to convert to hex string
     * @return  Generated hex string
     */
    public static String asHex(byte buf[]) {
        StringBuilder strbuf = new StringBuilder(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if ((buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString(buf[i] & 0xff, 64));
        }
        return strbuf.toString();
    }
}

