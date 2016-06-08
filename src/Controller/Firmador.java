package Controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class Firmador {

	Signature rsa;
	String archivoParaFirmar;
	
	public Firmador(String archivoParaFirmar){
		this.archivoParaFirmar=archivoParaFirmar;
	}
	
	public void generadorDeFirmas() {
		try {
			rsa = Signature.getInstance("SHA1withRSA");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inicializarObjetoDeFirmas(PrivateKey priv) {
		try {
			rsa.initSign(priv);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void firmarArchivo() {
		try {
			FileInputStream fis = new FileInputStream(archivoParaFirmar);
			BufferedInputStream bufin = new BufferedInputStream(fis);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = bufin.read(buffer)) >= 0) {

				rsa.update(buffer, 0, len);
			}
			;
			bufin.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void generarArchivoDeFirma(String ruta){
		byte[] tempBytes;
		try {
			tempBytes = generarFirma();
			guardarFirma(tempBytes,ruta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public byte[] generarFirma() throws Exception {
		return rsa.sign();
	}
	
	private void guardarFirma(byte[] firma,String ruta) throws Exception{
		FileOutputStream archivoFirma = new FileOutputStream(ruta);
		archivoFirma.write(firma);
		archivoFirma.close();
	}
	
	

}
