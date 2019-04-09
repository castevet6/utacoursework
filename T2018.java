/**
* TIETORAKENTEET 2018 HARJOITUSTY� 
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi
* 
* TEHT�V� 9:
* Kahden solmun aligraafi kertoo ett� solmupari on et��ll� muista solmuista, ja toinen solmu on oletettavasti
* graafin pistejoukon reuna-alueella. T�t� voitaisiin tutkia laskemalla solmuparin keskin�ist� et�isyytt� eli
* kaaren pituutta, ja vertaamalla t�t� koko graafin keskim��r�isiin graafin pituuksiin. Kolmen solmun
* aligraafissa pisteet ovat tihe�mmin suhteessa toisiinsa, ja voidaan olettaa ett� todenn�k�isyys ett� 
* aligraafissa on reuna-alueen pisteit� on pienempi, joskaan ei mahdoton.
*
* TY�ST� ON TOTEUTETTU SEURAAVAT VAIHEET (OK = tehty, ei v�ltt�m�tt� oikein)
* 	1. OK		(Graafin muodostaminen ja l�himpien solmujen lis�ys naapurilistalle)
*	2. OK		(Toiseksi l�himpien solmujen haku ja lis�ys naapurilistalle)
* 	3. OK		(Leveyshaku BFS kirjoitettuna tiedostoon BFS.txt)
* 	4. OK		(Syvyyshaku DFS kirjoitettuna tiedostoon DFS.txt)
* 	5. OK		(Solmujen l�ht�- ja tuloasteet kirjoitettuna tiedostoon Degrees.txt)
* 	6. OK		(Solmun poistaminen graafista ja j�ljelle j��nyt graafi tulost. tiedostoon DIM.txt)
* 	7. OK		(Solmujen lis��minen kunnes graafi on yhten�inen, josta DFS tuloste > COMP.txt)
*	9. OK		(Pohdinta 2- ja 3-solmun aligraafin roolista)
*
* SEURAAVAT VAIHEET PUUTTUVAT TOTEUTUKSESTA
* 	8. N/A		(Minimiviritt�v� puu ja tulostus tiedostoon MSP.txt)
*	10. N/A		(Pistejoukon reuna-alueen pisteet sis�lt�v�t solmut > OUTLIERS.txt)
* 
* OHJELMAN RAKENNE
* - T2018.java: 	Ohjelman p��tiedosto, josta suoritus k�ynnistet��n. P��tiedosto suorittaa
* 					teht�v�nannon mukaiset kutsut tietorakenteisiin ja niiden metodeihin.
* - Graph.java: 	Graafin tietorakenne joka instantioidaan p��ohjelmassa. Sis�lt�� API-metodit
					solmujen ja kaarien lis��miseen, k�sittelyyn ja tutkimiseen.
* - Node.java:		Graafin solmun tietorakenne omassa luokassaan.
* - Edge.java:		Graafin kaaren tietorakenne omassa luokassaan.
* - Queue.java:		Jonotietorakenne leveyshakua varten.
* - IOManager.java:	Apuluokka joka tarjoaa tiedoston luku-/kirjoitusfunktioita.
*/

import java.util.ArrayList;
import java.util.Random;

public class T2018 {
	public static void main(String[] args) { 
		// TULOSTETAAN TERVEHDYSTEKSTI
		System.out.println("\n**************************************************************");
		System.out.println("* TIRA Harjoitusty� Syksy 2018.                              *");
		System.out.println("* author: Antti Manninen, am431734, antti.e.manninen@tuni.fi *");
		System.out.println("**************************************************************\n");
		System.out.println("Suorituksen loki: \n");
		
		// VAIHE 1
		// MUODOSTETAAN GRAAFI
		//*****************************

		// luetaan data 2D taulukkoon k�ytt�en t�t� varten luotua apuluokkaa IOManager
		double[][] data = IOManager.readInput("Tdata.txt");
		IOManager.log(1, "Data luettu tiedostosta.");
		// Muodostetaan graafi k�ytt�en t�t� varten luotua aputietorakennetta
		Graph g = new Graph();
		IOManager.log(1, "Luotu graafi g.");
		// lis�t��n jokainen piste graafiin k�ytten tietorakenteen metodia addNode
		for (int i = 0; i < data[0].length; i++) {
			g.addNode(data[0][i], data[1][i]);
		}
		IOManager.log(1, "Lis�tty jokainen datapiste solmuksi graafiin g.");
		// lis�t��n jokaiselle graafin solmulle l�hin naapuri Eukl. et�isyyden perusteella
		// apumetodi connectAllNodes k�y jokaisen solmun l�pi ja etsii l�himm�n naapurin
		// jos l�hin solmu on jo naapurilistalla, se j�tet��n huomiotta, ja palautetaan
		// seuraavaksi l�hin (eli samalla metodilla voidaan etsi� toiseksi l�himm�t)
		g.connectAllNodes();
		IOManager.log(1, "Yhdistetty jokaiselle solmulle l�hin solmu.");

		// VAIHE 2
		// LIS�T��N GRAAFIN JOKAISELLE SOLMULLE TOISEKSI L�HINN� OLEVAN PISTEEN SIS�LT�M� SOLMU
		// ************************************************************************************
		// k�ytet��n samaa metodia kuin kohdassa 1 koska nyt metodi j�tt�� jo l�ydetyt l�himm�t 
		// solmut huomiotta
		g.connectAllNodes();
		IOManager.log(2, "Yhdistetty jokaiselle solmulle toiseksi l�hin solmu.");
		
		// VAIHE 3
		// LEVEYSHAKU
		// **********
		// suoritetaan leveyshaku, palauttaa listan solmujen k�ymisj�rjestyksest� (solmusta 1 alkaen)
		ArrayList<Node> nodeListBFS = g.breadthFirstSearchAll();
		IOManager.listWriteOutputNodes("BFS.txt", nodeListBFS);
		IOManager.log(3, "Tulostettu leveyshaun k�yntij�rjestys tiedostoon \"BFS.txt\".");

		// VAIHE 4
		// SYVYYSHAKU
		// **********
		// suoritetaan syvyyshaku, palauttaa listan solmujen k�ymisj�rjestyksest� (solmusta 1 alkaen)
		ArrayList<Node> nodeListDFS = g.depthFirstSearchAll();
		IOManager.listWriteOutputNodes("DFS.txt", nodeListDFS);
		IOManager.log(4, "Tulostettu syvyyshaun k�yntij�rjestys tiedsostoon \"DFS.txt\".");

		// VAIHE 5 
		// SIS�- JA ULKOASTEET
		// *******************
		// tulostetaan solmujen sis�- ja ulkoasteet muuttujaan
		String[] degrees = g.getDegreesFormatted();
		// kirjoitetaan tiedostoon
		IOManager.arrayWriteOutputLines("Degrees.txt", degrees);
		IOManager.log(5, "Tulostettu solmujen l�ht�-ja tuloasteet tiedostoon \"Degrees.txt\".");

		// VAIHE 6
		// SOLMUN POISTO
		// *************
		// poistetaan solmu satunnaisella avaimella n
		Random r = new Random();
		int rnd = 1 + r.nextInt(g.numNodes());
		IOManager.log(6, "Poistetaan satunnainen solmu " + rnd + ".");
		g.removeNode(rnd);
		nodeListBFS = g.breadthFirstSearchAll();
		IOManager.listWriteOutputNodes("DIM.txt", nodeListBFS);
		IOManager.log(6, "Tulostettu poiston j�lkeen syvyyshaun mukainen k�yntij�rjestys tiedostoon \"DIM.txt\".");

		// VAIHE 7
		// LIS�T��N SOLMUJA KUNNES GRAAFI ON YHDISTETTY
		// ********************************************
		// testataan onko graafi yhdistetty syvyyshakuun perustuvan apumetodin avulla
		boolean connected = g.isConnected();
		IOManager.log(7, "Testattiin onko graafi g yhdistetty: " + String.valueOf(connected).toUpperCase());
		// jos ei yhdistetty graafi lis�t��n solmuja
		int lisattyjaSolmuja = 0;
		while (!connected) {
			// lis�t��n jokaiselle solmulle yksi l�hin naapuri lis��
			g.connectAllNodes();
			lisattyjaSolmuja++;
			connected = g.isConnected();
		}
		IOManager.log(7, "Lis�ttiin solmuille mahdollisia l�himpi� solmuja " + lisattyjaSolmuja + " kierroksen verran.");
		IOManager.log(7, "Testattiin onko graafi g yhdistetty: " + String.valueOf(connected).toUpperCase());
		nodeListDFS = g.depthFirstSearchAll();
		IOManager.listWriteOutputNodes("COMP.txt", nodeListDFS);
		IOManager.log(7, "Tulostettu yhdistetyn graafin solmut syvyyshaun mukaisessa j�rjestyksess� tiedostoon \"COMP.txt\"");
		}
}