package Controller;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Asegurador {

	
	private PrivateKey ObjetoLlavePrivada;
		
	
	public Asegurador(){
		
		
	}
	
	public byte[] aseguraClave(PrivateKey llavePrivada, String password)throws Exception{
		//byte[] tempBytes=abrirLlavePrivada(rutaLlavePrivada);
		byte[] tempBytes=llavePrivada.getEncoded();
		return cifrarClavePrivada(tempBytes, password);		
	}
	
	public PrivateKey desaseguraClave(byte[] bytesEnClavePrivada, String password)throws Exception{
		byte[] tempBytes=descifrarClavePrivada(bytesEnClavePrivada, password);
		generarLlavePrivada(tempBytes);
		return ObjetoLlavePrivada;
	}
	
	//Menudencia----------------------------------------------------------------------
	
	public byte[] cifrarClavePrivada(byte[] llavePrivada, String password) throws Exception{
		    SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
		    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
		    cipher.init(Cipher.ENCRYPT_MODE, key);
		    return cipher.doFinal(llavePrivada);		
	}

	
	public byte[] descifrarClavePrivada(byte[] msjEncriptado, String password) throws Exception{
	    SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    return cipher.doFinal(msjEncriptado);		
	}
	
	public byte[] abrirLlavePrivada(String args) throws Exception {
		FileInputStream keyfis = new FileInputStream(args);
		byte[] llavePrivada= new byte[keyfis.available()];
		keyfis.read(llavePrivada);
		keyfis.close();
		return llavePrivada;
	} 
	
	public void generarLlavePrivada(byte[] bytesEnLlave){
		try {
			PKCS8EncodedKeySpec pubKeySpec = new PKCS8EncodedKeySpec(bytesEnLlave);			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			ObjetoLlavePrivada = keyFactory.generatePrivate(pubKeySpec);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
