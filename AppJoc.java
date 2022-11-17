package coleccionsN1E3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
Donat el fitxer countrties.txt (revisa l'apartat recursos) que conté països i capitals. El programa ha de 
llegir el fitxer i guardar les dades en un HashMap<String, String>. El programa demana el nom de 
l’usuari/ària, i després ha de mostrar un país de forma aleatòria, guardat en el HashMap. 
Es tracta de què l’usuari/ària ha d’escriure el nom de la capital del país en qüestió. 
Si l’encerta se li suma un punt. Aquesta acció es repeteix 10 vegades. 
Un cop demanades les capitals de 10 països de forma aleatòria, llavors s’ha de guardar en un fitxer 
anomenat classificacio.txt, el nom de l’usuari/ària i la seva puntuació.
**/

public class AppJoc {
	
	static HashMap<String, String> capitalCities = new HashMap<String, String>();
	

	public static void main(String[] args) {
		String nom;
		int punts = 0;
		llegirFitxer(); // Llegeix el fitxer i inicialitza el HashMap
		nom = llegirString("Introdueix el nom d'usuari: ");
		
		// Mostrar un pais de forma aleatoria, necessitem un index numèric >> ArrayList
		ArrayList<String> paisos = new ArrayList<String>();
		for (String i : capitalCities.keySet()) {
			paisos.add(i);
		}
		
		// Iniciem el bucle de 10 vegades del joc
		for (int i = 1; i <= 10; i++) {
			String paisAleatori = paisos.get((int)(Math.random() * paisos.size())); // Calcula el pais aleatori
			String capital = llegirString(nom + ", intent " + i +" de 10: Adivina la capital del següent pais: " + paisAleatori + ": ");
			if (capital.equalsIgnoreCase(capitalCities.get(paisAleatori))) { // Comprovem si ha acertat
				punts++;
				System.out.println("Molt bé! Resposta correcta. Tens 1 punt.");
			} else {
				System.out.println("Has fallat! la resposta correcta és " + capitalCities.get(paisAleatori));
			}
		}
		
		System.out.println("Joc finalitzat. En total has aconseguit " + punts +" punts.");
		escriureFitxer(nom, punts);
	}
	
	
	
	static void llegirFitxer() {
		
		// Fitxer del que volem llegir
		// (hem d'especificar la ruta -> exem: c:\\nombreCarpeta\...\fichero.txt o \var\www\fichero.txt)
		File fichero = new File("Countries.txt");
		Scanner s = null;

		try {
			System.out.println("... Llegim el contingut del fitxer ...");
			s = new Scanner(fichero);

			// Leemos linea a linea el fichero
			while (s.hasNextLine()) {
				String linea = s.nextLine(); 	// Llegim la linia
				// Guardem la linia a la HashTable, parsejant-la en dos parts si existeix l'espai separador 
				int i = linea.indexOf(' ');
				if (i > 0 ) { // (evita l'error de que el fitxer finalitza amb salts de linia)
					String pais = linea.substring(0, i);
					String capital = linea.substring(i+1);
					capitalCities.put(pais, capital);
				}	
			}
		} catch (Exception ex) {
			System.out.println("Missatge: " + ex.getMessage());
		} finally {
			// Tanquem el fitxer tant si la lectura ha estat correcta com si no
			try {
				if (s != null)
					s.close();
			} catch (Exception ex2) {
				System.out.println("Missatge 2: " + ex2.getMessage());
			}
		}		
	}
	
	
	
	static void escriureFitxer(String nom, int punts) {
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("classificacio.txt"), "UTF-8"));
			
			// Escrivim linia al fitxer amb el nom i els punts
		try {
				out.write(nom + " " + punts +"\n");
			} catch (IOException ex) {
				System.out.println("Missatge excepció escriptura: " + ex.getMessage());
			}
		} catch (UnsupportedEncodingException | FileNotFoundException ex2) {
			System.out.println("Missatge error 2: " + ex2.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException ex3) {
				System.out.println("Missatge error tancament fitxer: " + ex3.getMessage());
			}
		}
	}
	
	
	public static String llegirString(String print) {
		String var="";
		boolean error=true;
		System.out.print(print);
		while (error) {
			try {
				Scanner input=new Scanner(System.in);
				var = input.nextLine();
				if (var.length()==0) {
					throw new Exception();
				}
				error=false;
			}
			catch (Exception e) {
				System.out.print("Error de format. Torna-ho a intentar:");
			}
		}
		return var;
	}
}
