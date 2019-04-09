/**
* TIETORAKENTEET 2018 HARJOITUSTYÖ 
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi
* 
* TEHTÄVÄ 9:
* Kahden solmun aligraafi kertoo että solmupari on etäällä muista solmuista, ja toinen solmu on oletettavasti
* graafin pistejoukon reuna-alueella. Tätä voitaisiin tutkia laskemalla solmuparin keskinäistä etäisyyttä eli
* kaaren pituutta, ja vertaamalla tätä koko graafin keskimääräisiin graafin pituuksiin. Kolmen solmun
* aligraafissa pisteet ovat tiheämmin suhteessa toisiinsa, ja voidaan olettaa että todennäköisyys että 
* aligraafissa on reuna-alueen pisteitä on pienempi, joskaan ei mahdoton.
*
* TYÖSTÄ ON TOTEUTETTU SEURAAVAT VAIHEET (OK = tehty, ei välttämättä oikein)
* 	1. OK		(Graafin muodostaminen ja lähimpien solmujen lisäys naapurilistalle)
*	2. OK		(Toiseksi lähimpien solmujen haku ja lisäys naapurilistalle)
* 	3. OK		(Leveyshaku BFS kirjoitettuna tiedostoon BFS.txt)
* 	4. OK		(Syvyyshaku DFS kirjoitettuna tiedostoon DFS.txt)
* 	5. OK		(Solmujen lähtö- ja tuloasteet kirjoitettuna tiedostoon Degrees.txt)
* 	6. OK		(Solmun poistaminen graafista ja jäljelle jäänyt graafi tulost. tiedostoon DIM.txt)
* 	7. OK		(Solmujen lisääminen kunnes graafi on yhtenäinen, josta DFS tuloste > COMP.txt)
*	9. OK		(Pohdinta 2- ja 3-solmun aligraafin roolista)
*
* SEURAAVAT VAIHEET PUUTTUVAT TOTEUTUKSESTA
* 	8. N/A		(Minimivirittävä puu ja tulostus tiedostoon MSP.txt)
*	10. N/A		(Pistejoukon reuna-alueen pisteet sisältävät solmut > OUTLIERS.txt)
* 
* OHJELMAN RAKENNE
* - T2018.java: 	Ohjelman päätiedosto, josta suoritus käynnistetään. Päätiedosto suorittaa
* 					tehtävänannon mukaiset kutsut tietorakenteisiin ja niiden metodeihin.
* - Graph.java: 	Graafin tietorakenne joka instantioidaan pääohjelmassa. Sisältää API-metodit
					solmujen ja kaarien lisäämiseen, käsittelyyn ja tutkimiseen.
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
		System.out.println("* TIRA Harjoitustyö Syksy 2018.                              *");
		System.out.println("* author: Antti Manninen, am431734, antti.e.manninen@tuni.fi *");
		System.out.println("**************************************************************\n");
		System.out.println("Suorituksen loki: \n");
		
		// VAIHE 1
		// MUODOSTETAAN GRAAFI
		//*****************************

		// luetaan data 2D taulukkoon käyttäen tätä varten luotua apuluokkaa IOManager
		double[][] data = IOManager.readInput("Tdata.txt");
		IOManager.log(1, "Data luettu tiedostosta.");
		// Muodostetaan graafi käyttäen tätä varten luotua aputietorakennetta
		Graph g = new Graph();
		IOManager.log(1, "Luotu graafi g.");
		// lisätään jokainen piste graafiin käytten tietorakenteen metodia addNode
		for (int i = 0; i < data[0].length; i++) {
			g.addNode(data[0][i], data[1][i]);
		}
		IOManager.log(1, "Lisätty jokainen datapiste solmuksi graafiin g.");
		// lisätään jokaiselle graafin solmulle lähin naapuri Eukl. etäisyyden perusteella
		// apumetodi connectAllNodes käy jokaisen solmun läpi ja etsii lähimmän naapurin
		// jos lähin solmu on jo naapurilistalla, se jätetään huomiotta, ja palautetaan
		// seuraavaksi lähin (eli samalla metodilla voidaan etsiä toiseksi lähimmät)
		g.connectAllNodes();
		IOManager.log(1, "Yhdistetty jokaiselle solmulle lähin solmu.");

		// VAIHE 2
		// LISÄTÄÄN GRAAFIN JOKAISELLE SOLMULLE TOISEKSI LÄHINNÄ OLEVAN PISTEEN SISÄLTÄMÄ SOLMU
		// ************************************************************************************
		// käytetään samaa metodia kuin kohdassa 1 koska nyt metodi jättää jo löydetyt lähimmät 
		// solmut huomiotta
		g.connectAllNodes();
		IOManager.log(2, "Yhdistetty jokaiselle solmulle toiseksi lähin solmu.");
		
		// VAIHE 3
		// LEVEYSHAKU
		// **********
		// suoritetaan leveyshaku, palauttaa listan solmujen käymisjärjestyksestä (solmusta 1 alkaen)
		ArrayList<Node> nodeListBFS = g.breadthFirstSearchAll();
		IOManager.listWriteOutputNodes("BFS.txt", nodeListBFS);
		IOManager.log(3, "Tulostettu leveyshaun käyntijärjestys tiedostoon \"BFS.txt\".");

		// VAIHE 4
		// SYVYYSHAKU
		// **********
		// suoritetaan syvyyshaku, palauttaa listan solmujen käymisjärjestyksestä (solmusta 1 alkaen)
		ArrayList<Node> nodeListDFS = g.depthFirstSearchAll();
		IOManager.listWriteOutputNodes("DFS.txt", nodeListDFS);
		IOManager.log(4, "Tulostettu syvyyshaun käyntijärjestys tiedsostoon \"DFS.txt\".");

		// VAIHE 5 
		// SISÄ- JA ULKOASTEET
		// *******************
		// tulostetaan solmujen sisä- ja ulkoasteet muuttujaan
		String[] degrees = g.getDegreesFormatted();
		// kirjoitetaan tiedostoon
		IOManager.arrayWriteOutputLines("Degrees.txt", degrees);
		IOManager.log(5, "Tulostettu solmujen lähtö-ja tuloasteet tiedostoon \"Degrees.txt\".");

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
		IOManager.log(6, "Tulostettu poiston jälkeen syvyyshaun mukainen käyntijärjestys tiedostoon \"DIM.txt\".");

		// VAIHE 7
		// LISÄTÄÄN SOLMUJA KUNNES GRAAFI ON YHDISTETTY
		// ********************************************
		// testataan onko graafi yhdistetty syvyyshakuun perustuvan apumetodin avulla
		boolean connected = g.isConnected();
		IOManager.log(7, "Testattiin onko graafi g yhdistetty: " + String.valueOf(connected).toUpperCase());
		// jos ei yhdistetty graafi lisätään solmuja
		int lisattyjaSolmuja = 0;
		while (!connected) {
			// lisätään jokaiselle solmulle yksi lähin naapuri lisää
			g.connectAllNodes();
			lisattyjaSolmuja++;
			connected = g.isConnected();
		}
		IOManager.log(7, "Lisättiin solmuille mahdollisia lähimpiä solmuja " + lisattyjaSolmuja + " kierroksen verran.");
		IOManager.log(7, "Testattiin onko graafi g yhdistetty: " + String.valueOf(connected).toUpperCase());
		nodeListDFS = g.depthFirstSearchAll();
		IOManager.listWriteOutputNodes("COMP.txt", nodeListDFS);
		IOManager.log(7, "Tulostettu yhdistetyn graafin solmut syvyyshaun mukaisessa järjestyksessä tiedostoon \"COMP.txt\"");
		}
}