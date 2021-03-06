import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

/**
* LAKI II Harjoitustyö 2 - ASCII Art
* 
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi
*/
public class LakiHT2 {
    // ********************
    // * VAKIOATTRIBUUTIT *
    // ********************
    // käyttöliittymän tekstimuuttujat
    private static final String HELLO = "-------------------\n"
                                      + "| A S C I I A r t |\n"
                                      + "-------------------";
    private static final String ERROR1 = "Invalid command-line argument!";
    private static final String ERROR2 = "Error!";
    private static final String BYE = "Bye, see you soon.";
    private static final String COMMANDS = "printa/printi/info/filter [n]/reset/quit?";
    private static final String COMMAND1 = "printa", COMMAND2 = "printi", COMMAND3 = "info", 
        COMMAND4 = "filter", COMMAND5="reset", COMMAND6 = "quit";
    

    // **************************************
    // * METODIT TIEDOSTONKÄSITTELYÄ VARTEN *
    // **************************************

    /**
     * Laskee taulukon rivien määrän. Palauttaa kokonaislukutaulukon jossa on rivien määrä (alkio 0),
     * sekä pisimmän rivin pituus (alkio 1).
     * @param file  Viite tiedostoon jonka rivit halutaan laskea
     * @return      Kokonaislukutaulukko jossa rivien määrä ja max. rivin pituus
     */
    public static int[] countLines(Scanner lukija) {
        int max = 0;
        int lineCount = 0;
        int[] ret = new int[2];
        while (lukija.hasNextLine()) {
            String rivi = lukija.nextLine();
            lineCount++;
            if (rivi.length() > max) {
                max = rivi.length();
            }
        }
        ret[0] = lineCount;
        ret[1] = max;
        return ret;
    }

    /**
     * Lataa tiedostossa olevan merkeistä koostuvan kuvan ja muuntaa kokonaislukumuotoon.
     * @param tiedostoNimi  tiedosto joka sisältää kuvan merkkimuodossa
     * @return  kokonaislukutaulukko jossa kuvan kokonaislukuesitys
     */
    public static int[][] lataaKuva(String tiedostoNimi) {
        Scanner tiedostoLukija;
        int[] riveja = null;
        String rivi = "";
        // lasketaan ensin tiedoston rivit ja selvitetään pisimmän rivin pituus
        // jotta tiedetään minkäkokoinen taulukko tarvitaan
        try {
            tiedostoLukija = new Scanner(new File(tiedostoNimi));
            riveja = countLines(tiedostoLukija);
            tiedostoLukija.close();
        } catch (FileNotFoundException e) {
            return null;
        }
        // luodaan taulukko ja avataan tiedostolukija uudelleen
        // varsinaista tietojen siirtoa varten
        int ret[][] = new int[riveja[0]][riveja[1]];

        // Avataan Scanner-olio uudestaan jotta päästään takaisin tiedoston alkuun
        try {
            tiedostoLukija = new Scanner(new File(tiedostoNimi));
            int cursor = 0;
            while (tiedostoLukija.hasNextLine()) {
                rivi = tiedostoLukija.nextLine();
                for (int i = 0; i < rivi.length(); i++) {
                    ret[cursor][i] = convertCharToInt(rivi.charAt(i));
                }
                cursor++;
            }
            tiedostoLukija.close();
        } catch (IOException e) {
            System.out.println("I could not load.");
            ret = null;
        }

        return ret;
    }

    // *****************************
    // * METODIT KUVAN KÄSITTELYYN *
    // *****************************

    /**
     * Analysoi kuvan (koko), merkkien määrä ja tulostaa analyysin tulokset.
     * @param taulukko
     */
    public static void analysoiKuva(int[][] taulukko) {
        if (taulukko == null) {
            System.out.println(ERROR2);
            return;
        }
        // merkkitaulukko vertailua varten
        char[] merkit = {'#', '@', '&', '$', '%', 'x', '*', 'o', '|', '!', ';', ':', '\'', ',', '.', ' ' };

        // luodaan kokonaislukutaulukko johon tallennetaan merkit-taulukon esiintymät
        int[] esiintymat = new int[merkit.length];

        // laaditaan histogrammi
        for (int i = 0; i < taulukko.length; i++) {
            for (int j = 0; j < taulukko[i].length; j++) {
                int indeksi = taulukko[i][j];
                esiintymat[indeksi] += 1;
            }
        }

        // tulostetaan analyysin tulokset
        System.out.println(taulukko.length + " x " + taulukko[0].length);
        for (int k = 0; k < merkit.length; k++) {
            System.out.println(merkit[k] + " " + esiintymat[k]);
        }

    }

    /**
     * Tulosta kuvan merkkijonoesityksenä. Syötteenä kokonaislukuesitys taulukkona.
     * @param taulukko  kuvan kokonaislukuesitys taulukkomuodossa
     */
    public static void tulostaKuva(int[][] taulukko) {
        if (taulukko == null) {
            System.out.println("Error!");
        } else {
            for (int i = 0; i < taulukko.length; i++) {
                for (int j = 0; j < taulukko[0].length; j++) {
                    char merkki = convertIntToChar(taulukko[i][j]);
                    System.out.print(merkki);
                }
                System.out.println();
            }
        }        
    }

    /**
     * Tulostaa kuvan kokonaislukuesityksenä. Syötteenä kokonaislukuesitys taulukkona.
     * @param taulukko  kuvan kokonaislukuesitys taulukkomuodossa
     */
    public static void tulostaKuvaInt(int[][] taulukko) {
        if (taulukko == null) {
            System.out.println("Error!");
        } else {
            for (int i = 0; i < taulukko.length; i++) {
                for (int j = 0; j < taulukko[0].length; j++) {
                    System.out.printf("%2d", taulukko[i][j]);
                    if (j < taulukko[0].length - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Konvertoi merkin kokonaislukuesitykseen kiinteän koodauksen perusteella.
     * @param merkki    koodattava merkki
     * @return  merkin koodattu kokonaislukuesitys
     */
    public static int convertCharToInt(char merkki) {
        char[] merkit = {'#', '@', '&', '$', '%', 'x', '*', 'o', '|', '!', ';', ':', '\'', ',', '.', ' ' };

        for (int i = 0; i < merkit.length; i++) {
            if (merkki == merkit[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Konvertoi kokonaisluvun merkiksi kiinteän koodauksen perusteella.
     * @param indeksi   koodattava luku (nimi viittaa koodaustaulukon indeksiin)
     * @return  koodauksen mukainen merkki
     */
    public static char convertIntToChar(int indeksi) {
        char ret;
        char[] merkit = {'#', '@', '&', '$', '%', 'x', '*', 'o', '|', '!', ';', ':', '\'', ',', '.', ' ' };

        if (indeksi >= 0 && indeksi <= 15) {
            ret = merkit[indeksi];
        } else {
            ret = Character.MAX_VALUE;
        }
        return ret;
    }

    /**
     * Metodi suodattaa kuvan keskiarvosuodattimella, jonka dimensio annetaan kuvataulukon lisäksi
     * parametrina.
     * @param imageData kuvan data 2D-kokonaislukutaulukkona
     * @param dimension suodattimen dimensio (pariton, oletus: mahtuu kuvaan)
     * @return  Suodatettu taulukko
     */
    public static int[][] filter(int[][] imageData, int dimension) {
        // tarkistetaan että kuvadata-muuttuja on olemassa, ja suodattimen dimensio pariton
        if (imageData == null || dimension % 2 == 0) {
            return null;
        }
        // tarkistetaan ettei suodattimen dimension ylitä taulukon kokoa
        if (dimension < 0 || dimension > imageData.length || dimension > imageData[0].length) {
            return null;
        }

        int riv = imageData.length;
        int sar = imageData[0].length;
        
        // kopioidaan ensin koko kuvadata suodatettavaan taulukkoon jotta reunat saadaan mukaan        
        int[][] filtered = intArrayDeepCopy(imageData);
        if (filtered == null) {
            System.out.println(ERROR2);
            return null;
        }

        // iteroidaan kuva-alan yli, muuttujat i ja j kuvaavat suodattimen ikkunan
        // vasemman yläkulman sijaintia
        for (int i = 0; i <= (riv-dimension); i++) {
            for (int j = 0; j <= (sar-dimension); j++) {
                // lasketaan suodatinikkunan keskiarvo
                int summa = 0;
                for (int itery = i; itery <= (i + dimension - 1); itery++) {
                    for (int iterx = j; iterx <= (j + dimension - 1); iterx++) {
                        summa += imageData[itery][iterx];
                    }
                }
                double keskiarvo = Math.round(1.0 * summa / (dimension * dimension));
                filtered[i + dimension/2][j + dimension/2] = (int) keskiarvo;
            }
        }

        return filtered;
    }

    /**
     * Suodatusmetodin kutsu suodattimen vakiodimensiolla 3. Kutsuu varsinaista toteutusta.
     * @param imageData suodatettava taulukko
     * @return  suodatettu kuva
     */
    public static int[][] filter(int[][] imageData) {
        return filter(imageData, 3);
    }

    // *******************
    // * MUUT APUMETODIT *
    // *******************

    /**
     * Kopioi taulukon sisällön toiseen taulukkoon alkio kerrallaan
     * @param taulukko  lähdetaulukko
     * @return  kohdetaulukko = kopio lähdetaulukosta
     */
    public static int[][] intArrayDeepCopy(int[][] taulukko) {
        if (taulukko == null) {
            System.out.println(ERROR2);
            return null;
        }
        int[][] kopio = new int[taulukko.length][taulukko[0].length];

        for (int i = 0; i < taulukko.length; i++) {
            for (int j = 0; j < taulukko[0].length; j++) {
                kopio[i][j] = taulukko[i][j];
            }
        }
        return kopio;
    }

    /**
     * Lukee käyttäjältä suoritettavan komennon syötteenä
     * @param header    näytettävä otsikkoteksti
     * @return  komento tekstimuodossa
     */
    public static String lueKomento(String header) {
        System.out.println(header);
        String komento = In.readString();
        return komento;
    }

    /**
     * Käyttöliittymä eriytettynä omaan metodiinsa. Luettava kuvatiedosto annetaan parametrina.
     * main-metodin suoritus päättyy myös kun tämä metodi palaa ilman paluuarvoa.
     * @param tiedostonimi  Luettava kuvatiedosto
     */
    public static void startUI(String tiedostonimi) {
        // luokkamuuttuja Scanner-oliota varten
        boolean mainLoopRun = true;     // lippumuuttuja käyttöliittymän silmukalle
        int[][] imageDataInt = null;    // muuttuja kuvan kokonaislukuesitykselle
        imageDataInt = lataaKuva(tiedostonimi);
        // jos kuvan lukeminen epäonnistuu, ei käynnistetä pääsilmukkaa lainkaan
        if (imageDataInt == null) {
            System.out.println(ERROR1);
            System.out.println(BYE);
            mainLoopRun = false;
        }

        // KÄYTTÖLIITTYMÄN PÄÄSILMUKKA
        // ***************************
        while (mainLoopRun) {
            String syote = lueKomento(COMMANDS);
            String[] komento = syote.split("[ ]");
            if (komento[0].equals(COMMAND4)) {
                if (komento.length > 1) {
                    int dimensio = Integer.parseInt(komento[1]);
                    int[][] filtered = filter(imageDataInt, dimensio);
                    // varmistetaan että suodatus onnistui ennenkuin päivitetään kuvadata
                    if (filtered != null) {
                        // asetetaan suodatettu kuva alkuperäisen kuvan paikalle
                        imageDataInt = filtered;
                        // poistetaan välimuuttujan muistinvaraus
                        filtered = null;
                    }
                } else {
                    int[][] filtered = filter(imageDataInt);
                    // varmistetaan että suodatus onnistui ennenkuin päivitetään kuvadata
                    if (filtered != null) {
                        // asetetaan suodatettu kuva alkuperäisen kuvan paikalle
                        imageDataInt = filtered;
                        // poistetaan välimuuttujan muistinvaraus
                        filtered = null;
                    }
                }
            } else if (komento[0].equals(COMMAND1)) {
                tulostaKuva(imageDataInt);
            } else if (komento[0].equals(COMMAND2)) {
                tulostaKuvaInt(imageDataInt);
            } else if (komento[0].equals(COMMAND3)) {
                analysoiKuva(imageDataInt);
            } else if (komento[0].equals(COMMAND5)) {
                imageDataInt = lataaKuva(tiedostonimi);
            } else if (komento[0].equals(COMMAND6)) {
                System.out.println(BYE);
                mainLoopRun = false;
            } else {
                System.out.println(ERROR2);
            }
            syote = "";
        }
    }

    public static void main(String[] args) {
        String tiedostonimi = "";       // muuttuja kuvatiedoston nimelle
        System.out.println(HELLO);
        // Tarkistetaan että komentoriviargumentteja on tasan yksi
        if (args.length != 1) {
            System.out.println(ERROR1);
            System.out.println(BYE);
        } else {
            // argumentit oikein, asetetaan 1. argumentti tiedostonimeksi
            tiedostonimi = args[0];
            // käynnistetään käyttöliittymä
            startUI(tiedostonimi);
        }
    }
}