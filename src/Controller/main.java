package Controller;

import java.io.*;
import java.security.*;


public class main {

	private static PrivateKey miLlavePrivada;
	
	public static void main(String[] args) throws Exception {
		
		escenarioDePruebas1();
		
		//escenarioDePruebas2();
		
	}
	
	public static void escenarioDePruebas1()throws Exception{
		//rutas
		String rutaArchivo="./Archivos/doc.docx";
		String rutaFirmaDigital="./FirmasDigitales/firma.txt";
		String rutaLlavePrivada="./LlavesPublicas/llavePublica.txt";
		//contraseña
		String contrasena="1111111111111111";
		
		//Ejecución
		System.out.println("Generando las llaves");
		GeneradorDeClaves elGenerador= new GeneradorDeClaves(contrasena);
		elGenerador.generar();
		miLlavePrivada = elGenerador.getPriv();
		System.out.println("->" + miLlavePrivada.getEncoded());
		elGenerador.guardarLlavePublica(rutaLlavePrivada);
		System.out.println("llaves generadas");
		
		
		//---------------------------------------------------------------------
		Asegurador elAsegurador= new Asegurador();		
		byte[] llavePrivadaAsegurada=elAsegurador.aseguraClave(miLlavePrivada, contrasena);
		System.out.println(llavePrivadaAsegurada.toString());
		PrivateKey nuevaMiLlavePrivada = elAsegurador.desaseguraClave(llavePrivadaAsegurada, contrasena);	
		//---------------------------------------------------------------------
			
		
		System.out.println("-------------------------------------------------");
		
		System.out.println("Firmando archivo en ruta: " +rutaArchivo);
		Firmador elFirmador=new Firmador(rutaArchivo);
		
		elFirmador.generadorDeFirmas();
		elFirmador.inicializarObjetoDeFirmas(nuevaMiLlavePrivada);
		elFirmador.firmarArchivo();
		elFirmador.generarArchivoDeFirma(rutaFirmaDigital);
		System.out.println("Archivo firmado con exito");
		
		System.out.println("-------------------------------------------------");
		
		System.out.println("Verificando....");
		
		Verificador elVerificador = new Verificador();
		try {
			elVerificador.verifica(rutaLlavePrivada, rutaFirmaDigital, rutaArchivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("La firma es autentica? "+elVerificador.esAutentico());
		System.out.println("-------------------------------------------------");
	}
	
	public static void escenarioDePruebas2(){
		//rutas
		String rutaArchivo="./Archivos/texto1.txt";
		String rutaFirmaDigital="./FirmasDigitales/firma.txt";
		String rutaLlavePublica="./LlavesPublicas/llavePublica.txt";
		//contraseña
		String contrasena="seguridad123";
		
		//Comprobando autenticidad de un fichero texto1.txt con firma y llavePublica generada anteriormente;
				
		Verificador elVerificador = new Verificador();
		try {
			elVerificador.verifica(rutaLlavePublica, rutaFirmaDigital, rutaArchivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("La firma es autentica? "+elVerificador.esAutentico());
		System.out.println("-------------------------------------------------");
	}

}
