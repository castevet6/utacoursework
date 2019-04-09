/**
* SubstringFinder-luokka joka pit�� sis�ll��n Harjoitusty� 1 teht�v�nannon mukaisen toiminnallisuuden,
* jossa merkkijonon sis�st� haetaan alimerkkijonon esiintymi�.
* <p>
* Harjoitusty�, Lausekielinen ohjelmointi II, Syksy 2018
* <p>
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi, Tampereen yliopisto 
*/
public class SubstringFinder {
    /**
    * Apufunktio jonka avulla tulostetaan merkkijonon esiintym� muotoiltuna teht�v�nannon mukaisesti. 
    * Funktio tulostaa merkkej� koko merkkijonon mitalta, mutta varsinaisia merkkej� tulostetaan 
    * vain alkaen indeksist� index (int)     * pituudelta pituus (int). Muihin kohtiin tulostetaan 
    * merkki '-'    
    * @param mjono - Tulostettava merkkijono (String)
    * @param index - Mist� alkaen tulostetaan merkkijonon varsiaisia merkkej� (int)
    * @param pituus - Kuinka monta varsinaista merkki� tulostetaan (int)
    */
    private static void tulosta(String mjono, int index, int pituus) {
        int i;
        for (i = 0; i < index; i++) {
            System.out.print("-");
        }
        for (i = index; i < (index + pituus); i++) {
            System.out.print(mjono.charAt(i));
        }
        for (i = (index + pituus); i < mjono.length(); i++) {
            System.out.print("-");
        }
        System.out.println("");
    }
        
    /**
    * Apufunktio joka tarkastaa ett� sy�tetty alimerkkijono t�ytt�� teht�v�nannon vaatimukset, 
    * eli ei ole pidempi kuin varsinainen merkkijono, eik� koostu pelk�st��n merkist� '*'
    * @param mjono - merkkijono josta haetaan
    * @param alimjono - haettava merkkijono
    * @return - onko merkkijono validi (boolean)
    */
    private static boolean validoi(String mjono, String alimjono) {
        // tarkastetaan ett� alimerkkijono on oikeanlainen
        // 1. alimerkkijono ei saa olla pidempi kuin varsinainen merkkijono josta haetaan
        if (alimjono.length() > mjono.length()) {
            return false;
        // 2. alimerkkijono ei saa sis�lt�� pelk�st��n merkki� '*'
        } else if (alimjono.length() == 1 && alimjono.charAt(0) == '*') {
            return false;
        } 
        // hyl??alimerkkijono jossa villikortti sek?lussa ett?opussa
        if (alimjono.charAt(0) == '*' && alimjono.charAt(alimjono.length() - 1 ) == '*') {
            return false;
        }
        return true;
    }
    
    /**
    * Varsinainen hakufunktio, saa parametrikseen kaksi merkkijonoa (haettava, haun kohde).
    * Funktio saa lis�ksi parametrinaan kaksi lippua, joiden avulla sille kerrotaan oliko haettavassa merkkijonossa
    * villikorttimerkki '*' joko alussa tai lopussa. T�m�n perusteella funktio osaa asettaa hakusilmukan alku- ja 
    * loppuindeksit oikein. Ei palauta arvoa, vaan tulostaa kaikki l�ydetyt esiintym�t k�ytt�en
    * apufunktiota tulosta(...)
    * @param mjono  - Merkkijono josta haetaan (String)
    * @param alimjono - Haettava merkkijono (String), josta poistettu villikorttimerkki '*' jos sellainen oli
    * @param alkuVillikortti - lippu: oliko alussa villikortti
    * @param loppuVillikortti - lippu: oliko lopussa villikortti
    */
    private static void etsi(String mjono, String alimjono, boolean alkuVillikortti, boolean loppuVillikortti) {
        // apumuuttuja hakusilmukaan      
        boolean match = false;
        // silmukan alku- ja loppuindeksi, villikortit vaikuttavat n�ihin
        int alku, loppu;
        // ensin asetetaan hakusilmukan alku ja loppukohta sen mukaan onko villikorttia
        if (loppuVillikortti) {
            // tehd��n vertailu vain varsinaisen merkkijonon ensimm�isen indeksin kohdalla
            alku = 0;
            loppu = 1;
        } else if (alkuVillikortti) {
            // vertaillaan vain matkalta, jolla alimerkkijono olisi varsinaisen merkkijonon lopussa
            alku = mjono.length() - alimjono.length();
            loppu = alku + 1;
            } else {
            alku = 0;
            // rajataan viimeinen indeksi jotta v�ltet��n menem�st� merkkijonon pituuden yli
            loppu = mjono.length() - alimjono.length() + 1;
        }
        
        // varsinainen hakusilmukka
        int i, j;   // silmukan iteraatiomuuttujat
        for (i = alku; i < loppu; i++) {
            if (mjono.charAt(i) == alimjono.charAt(0)) {
                match = true;
                for(j = 1; j < alimjono.length(); j++) {
                    if (mjono.charAt(i + j) != alimjono.charAt(j)) {
                    match = false;
                    }
                }
                if (match) {
                    tulosta(mjono, i, alimjono.length());
                }
            }
        }
    }
    
    /**
    * Apufunktio jonka avulla luetaan k�ytt�j�lt� sy�te
    * @param otsikko - Tekstikentt� joka n�ytet��n ennen sy�tteen lukua
    * @return - palauttaa luetun merkkijonon
    */
    private static String lue(String otsikko) {
        System.out.println(otsikko);
        String ret = In.readString();
        return ret;
    }
    
    // k�ytt�liittym� on rakennettu main-funktion sis��n
    public static void main(String[] args) {
        // k�ytt�liittym�n tekstimuuttujien alustus
        final String VIRHE = "Error!";
        final String JATKETAAN = "y";
        final String EIJATKETA = "n";
        final String STR1 = "Hello! I find substrings.";
        final String STR2 = "Please, enter a string:";
        final String STR3 = "Please, enter a substring:";
        final String STR4 = "Continue (y/n)?";
        final String STR5 = "See you soon.";
        
        // muiden muuttujien alustus
        String rivi = "";
        String alirivi = "";
        String lopeta = "";
        boolean alkuVillikortti = false; // lippu onko haettavan merkkijonon ensimm�inen kirjain '*'
        boolean loppuVillikortti = false; // lippu onko haettavan merkkijonon viimeinen merkki '*'
        
        System.out.println(STR1);
        
        boolean mainloopRun = true;     // kontrollimuuttuja k�ytt�liittym�n p��silmukan suoritukseen
        
        // K�YTT�LIITTYM�N P��SILMUKKA
        while (mainloopRun) {
            // VAIHE 1: Luetaan ja validoidaan sy�tteeet
            rivi = lue(STR2);   // ei validoida perustuen teht�v�nnon alkuoletuksiin
            boolean kelpaa = false;
            alkuVillikortti = false;    // nollataan villikortit
            loppuVillikortti = false;
            do {
                alirivi = lue(STR3);
                kelpaa = validoi(rivi, alirivi);
                if (!kelpaa) {
                    System.out.println(VIRHE);
                }
            } while (!kelpaa);
            
            // VAIHE 2: Tarkastetaan villikortit
            
            // ensimm�inen merkki villikortti
            if (alirivi.charAt(0) == '*') {
                alkuVillikortti = true;
                // poistetaan '*' alusta
                String temp = "";
                for (int i = 1; i < alirivi.length(); i++) {
                    temp += alirivi.charAt(i);
                }
                alirivi = temp;
            // viimeinen merkki villikortti
            } else if (alirivi.charAt(alirivi.length() - 1) == '*') {
                loppuVillikortti = true;
                // poistetaan '*' lopusta
                String temp = "";
                for (int i = 0; i < alirivi.length() -1; i++) {
                    temp += alirivi.charAt(i);
                }
                alirivi = temp;
            }
            
            // VAIHE 3: suoritetaan varsinainen haku
            etsi(rivi, alirivi, alkuVillikortti, loppuVillikortti);
            
            // VAIHE 4: Kysyt��n halutaanko jatkaa
            lopeta = "";
            do {
                lopeta = lue(STR4);
                if (!(lopeta.equals(JATKETAAN) || lopeta.equals(EIJATKETA))) {
                    System.out.println(VIRHE);
                }
            } while (!(lopeta.equals(JATKETAAN) || lopeta.equals(EIJATKETA)));
            if (lopeta.equals(EIJATKETA)) {
                mainloopRun = false;
            }
        }
        // VAIHE 5: tulostetaan lopetusviesti ja lopetetaan ohjelman suoritus
        System.out.println(STR5);        
    }
}