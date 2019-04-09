import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
* IOManager luokka, joka sis�lt�� kokoelman staattisia apumetodeja tiedoston lukuun ja kirjoitukseen. 
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi
*/
public class IOManager {
	private static Date date = new Date();
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Lukee parametrina annetun tiedoston sis�ll�n 2-ulotteiseen double-tyyppiseen taulukkoon.
     * Olettaa, ett� annettu tiedosto sis�lt�� (x,y)-muotoista double-tyyppist� dataa.
     * @param filename  Luettavan tiedoston nimi
     * @return          double[][]-taulukko, johon data on luettu
     */
    public static double[][] readInput(String filename) {
        // taulukon koko kovakoodattu teht�v�nannon perusteella
        double[][] data = new double[2][400];
		String line;
		try {
			Scanner tiedostonLukija = new Scanner(new File(filename));
			int i = 0;
			while (tiedostonLukija.hasNextLine()) {
				line = tiedostonLukija.nextLine();
				String[] values=line.split(",");	
				data[0][i]=Double.parseDouble(values[0]);	
				data[1][i]=Double.parseDouble(values[1]);
				//System.out.print(data[0][i]+" , "+data[1][i]+"\n");
				i++;
			}
			tiedostonLukija.close();
		} catch(IOException e) {
			System.out.println("File not found.");
		}
		return data;
	}
	
	/**
	 * Kirjoittaa kokonaislukutaulukon sis�ll�n rivi kerrallaan tiedostoon.
	 * @param filename	Tiedoston nimi
	 * @param data		Kokonaislukutaulukko jonka tiedot kirjoitetaan
	 * @return			Totuusarvo onnistuiko kirjoitus
	 */
	public static boolean arrayWriteOutputLines(String filename, int[] data) {
		try {
			PrintWriter kirjoittaja = new PrintWriter(filename);
			String line = "";
			for (int i = 0; i < data.length; i++) {
				line = "" + data[i];
				kirjoittaja.println(line);
			}
			kirjoittaja.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			return false;
		}		
		return true;
	}

	/**
	 * Kirjoittaa merkkijonotaulukon sis�ll�n rivi kerrallaan tiedostoon.
	 * @param filename	Tiedoston nimi
	 * @param data		Merkkijonotaulukko jonka tiedot kirjoitetaan
	 * @return			Totuusarvo onnistuiko kirjoitus
	 */
	public static boolean arrayWriteOutputLines(String filename, String[] data) {
		try {
			PrintWriter kirjoittaja = new PrintWriter(filename);
			String line = "";
			for (int i = 0; i < data.length; i++) {
				line = "" + data[i];
				kirjoittaja.println(line);
			}
			kirjoittaja.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			return false;
		}
		
		return true;
	}

	/**
	 * Kirjoittaa liukulukutaulukon sis�ll�n rivi kerrallaan tiedostoon.
	 * @param filename	Tiedoston nimi
	 * @param data		Liukulukutaulukko jonka tiedot kirjoitetaan
	 * @return			Totuusarvo onnistuiko kirjoitus
	 */
	public static boolean arrayWriteOutputLines(String filename, double[] data) {
		try {
			PrintWriter kirjoittaja = new PrintWriter(filename);
			String line = "";
			for (int i = 0; i < data.length; i++) {
				line = "" + data[i];
				kirjoittaja.println(line);
			}
			kirjoittaja.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			return false;
		}
		
		return true;
	}

	/**
	 * Kirjoittaa Node-tyypin alkioita sis�lt�v�n listan sis�ll�n rivi kerrallaan tiedostoon.
	 * @param filename	Tiedoston nimi
	 * @param nodeList	Lista joka pit�� sis�ll��n Node-tyyppisi� alkioita
	 * @return			Totuusarvo onnistuiko kirjoitus
	 */
	public static boolean listWriteOutputNodes(String filename, ArrayList<Node> nodeList) {
		try {
			PrintWriter kirjoittaja = new PrintWriter(filename);
			for (Node n : nodeList) {
				kirjoittaja.println("" + n.key);
			}
			kirjoittaja.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			return false;
		}
		return true;
	}

	/**
	 * Kirjoittaa 2-ulotteisen kokonaislukutaulukon tiedostoon riveitt�in ja sarakkeittain
	 * k�ytt�en erotinmerkki�.
	 * @param filename	Tiedoston nimi
	 * @param data		Kaksiulotteinen kokonaislukutaulukko jonka sis�lt� kirjoitetaan
	 * @param separator	Erotin merkki jolla sarakkeet erotetaan toisistaan
	 * @return			Totuusarvo onnistuiko kirjoitus
	 */
	public static boolean array2DWriteOutput(String filename, int[][] data, String separator) {
		try {
			PrintWriter kirjoittaja = new PrintWriter(filename);
			String line = "";
			for (int i = 0; i < data.length; i++) {
				line = "";
				for (int j = 0; j < data[0].length; j++) {
					line += data[i][j];
					if (j < data[0].length - 1) {
						line += separator;
					}
				}
				kirjoittaja.println(line);
			}
			kirjoittaja.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			return false;
		}
		
		return true;
	}

	/**
	 * Muotoiltu kirjoitus joka v�litt�� tietoa k�ytt�j�lle ohjelman suorituksen eri vaiheista.
	 * @param vaihe	Teht�v�nannon vaihe johon viesti liittyy
	 * @param text	Tulostettava viesti sek� aikaleima muotoiltuna
	 */
	public static void log(int vaihe, String text) {
		System.out.print(sdf.format(date));
		System.out.println(" --> (Vaihe " + vaihe + "): " + text);
	}
}