package Controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.SecretKey;

public class GeneradorDeClaves {
	
	KeyPairGenerator keygen;
	PrivateKey priv;
	PublicKey pub;
	String password;

	public GeneradorDeClaves(String password){
		this.password=password;
	}
	
	public void generar(){
		crearGeneradorDeParDeLlaves();
		inicializarGeneradorDeLlaves();
		generarLlavePrivadaYPublica();
	}
	
	public void crearGeneradorDeParDeLlaves(){
		try {
			keygen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inicializarGeneradorDeLlaves(){
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			
			keygen.initialize(1024, random);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generarLlavePrivadaYPublica(){
		KeyPair pair = keygen.generateKeyPair();
		priv = pair.getPrivate();
		pub = pair.getPublic();
	}
	
	public void guardarLlavePublica(String arg){		
		FileOutputStream keyfos;
		try {
			byte[] key = pub.getEncoded();
			keyfos = new FileOutputStream(arg);
			keyfos.write(key);
			keyfos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public PrivateKey getPriv() {
		return priv;
	}

	public PublicKey getPub() {
		return pub;
	}

	 
}
