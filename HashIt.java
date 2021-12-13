package application;
import java.math.BigInteger;
import java.security.*;

// A class used for hashing the passwords and storing it in the database
class HashIt{
	// Empty Constructor
	 public HashIt(){}
	 // A method that takes in a string in plain text and produces a 32 character MD5 hash of it
	 public String computeHash(String plaintext) {
		 
		 // Initialize the message digest
	    	MessageDigest m = null;
			try {
				// Set it to MD5 hashing
				m = MessageDigest.getInstance("MD5");
				// Check for exceptions
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			// Obtain the md5 digest
	    	m.reset();
	    	m.update(plaintext.getBytes());
	    	byte[] digest = m.digest();
	    	BigInteger bigInt = new BigInteger(1,digest);
	    	
	    	// Return a conversion of int to a base16 string format (hexadecimal)
	    	String hashtext = bigInt.toString(16);
	    	
	    	// Pad the zeros to obtain the full 32 chars.
	    	hashtext = "0".repeat(32 - hashtext.length()) + hashtext;
	    	return hashtext;
	    }
	    
	
}