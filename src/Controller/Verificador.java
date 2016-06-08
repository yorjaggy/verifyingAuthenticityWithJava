package Controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class Verificador {

	byte[] llavePublica;
	byte[] firmaDigital;
	PublicKey ObjetoLlavePublica;
	private boolean autentico;
	
	public Verificador(){
		
	}

	public void abrirLlavePublica(String args) throws Exception {
		FileInputStream keyfis = new FileInputStream(args);
		llavePublica = new byte[keyfis.available()];
		keyfis.read(llavePublica);
		keyfis.close();
	}

	public void abrirFirmaDigital(String args) throws Exception {
		FileInputStream sigfis = new FileInputStream(args);
		firmaDigital = new byte[sigfis.available()];
		sigfis.read(firmaDigital);
		sigfis.close();
	}

	public void generarLlaveSegunLoEspecificado(){
		try {
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(llavePublica);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			ObjetoLlavePublica = keyFactory.generatePublic(pubKeySpec);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean verificacionDeFirmaConLlavePublica(PublicKey llaveP, String nombreArchivoFirmado) throws Exception {
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initVerify(llaveP);

		FileInputStream datafis = new FileInputStream(nombreArchivoFirmado);
		BufferedInputStream bufin = new BufferedInputStream(datafis);

		byte[] buffer = new byte[1024];
		int len;
		while (bufin.available() != 0) {
			len = bufin.read(buffer);
			sig.update(buffer, 0, len);
		}
		;

		bufin.close();

		boolean verifies = sig.verify(firmaDigital);
		System.out.println("signature verifies: " + verifies);
		return verifies;

	}
	
	public void verifica(String rutaLlavePublica, String rutaFirmaDigital, String nombreArchivoFirmado) throws Exception{
		abrirLlavePublica(rutaLlavePublica);
		abrirFirmaDigital(rutaFirmaDigital);
		generarLlaveSegunLoEspecificado();
		autentico =  verificacionDeFirmaConLlavePublica(ObjetoLlavePublica, nombreArchivoFirmado);		
	}
	
	public boolean esAutentico(){
		return autentico;
	}

}
