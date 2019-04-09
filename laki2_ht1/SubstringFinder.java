/**
* SubstringFinder-luokka joka pitää sisällään Harjoitustyö 1 tehtävänannon mukaisen toiminnallisuuden,
* jossa merkkijonon sisästä haetaan alimerkkijonon esiintymiä.
* <p>
* Harjoitustyö, Lausekielinen ohjelmointi II, Syksy 2018
* <p>
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi, Tampereen yliopisto 
*/
public class SubstringFinder {
    /**
    * Apufunktio jonka avulla tulostetaan merkkijonon esiintymä muotoiltuna tehtävänannon mukaisesti. 
    * Funktio tulostaa merkkejä koko merkkijonon mitalta, mutta varsinaisia merkkejä tulostetaan 
    * vain alkaen indeksistä index (int)     * pituudelta pituus (int). Muihin kohtiin tulostetaan 
    * merkki '-'    
    * @param mjono - Tulostettava merkkijono (String)
    * @param index - Mistä alkaen tulostetaan merkkijonon varsiaisia merkkejä (int)
    * @param pituus - Kuinka monta varsinaista merkkiä tulostetaan (int)
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
    * Apufunktio joka tarkastaa että syötetty alimerkkijono täyttää tehtävänannon vaatimukset, 
    * eli ei ole pidempi kuin varsinainen merkkijono, eikä koostu pelkästään merkistä '*'
    * @param mjono - merkkijono josta haetaan
    * @param alimjono - haettava merkkijono
    * @return - onko merkkijono validi (boolean)
    */
    private static boolean validoi(String mjono, String alimjono) {
        // tarkastetaan että alimerkkijono on oikeanlainen
        // 1. alimerkkijono ei saa olla pidempi kuin varsinainen merkkijono josta haetaan
        if (alimjono.length() > mjono.length()) {
            return false;
        // 2. alimerkkijono ei saa sisältää pelkästään merkkiä '*'
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
    * Funktio saa lisäksi parametrinaan kaksi lippua, joiden avulla sille kerrotaan oliko haettavassa merkkijonossa
    * villikorttimerkki '*' joko alussa tai lopussa. Tämän perusteella funktio osaa asettaa hakusilmukan alku- ja 
    * loppuindeksit oikein. Ei palauta arvoa, vaan tulostaa kaikki löydetyt esiintymät käyttäen
    * apufunktiota tulosta(...)
    * @param mjono  - Merkkijono josta haetaan (String)
    * @param alimjono - Haettava merkkijono (String), josta poistettu villikorttimerkki '*' jos sellainen oli
    * @param alkuVillikortti - lippu: oliko alussa villikortti
    * @param loppuVillikortti - lippu: oliko lopussa villikortti
    */
    private static void etsi(String mjono, String alimjono, boolean alkuVillikortti, boolean loppuVillikortti) {
        // apumuuttuja hakusilmukaan      
        boolean match = false;
        // silmukan alku- ja loppuindeksi, villikortit vaikuttavat näihin
        int alku, loppu;
        // ensin asetetaan hakusilmukan alku ja loppukohta sen mukaan onko villikorttia
        if (loppuVillikortti) {
            // tehdään vertailu vain varsinaisen merkkijonon ensimmäisen indeksin kohdalla
            alku = 0;
            loppu = 1;
        } else if (alkuVillikortti) {
            // vertaillaan vain matkalta, jolla alimerkkijono olisi varsinaisen merkkijonon lopussa
            alku = mjono.length() - alimjono.length();
            loppu = alku + 1;
            } else {
            alku = 0;
            // rajataan viimeinen indeksi jotta vältetään menemästä merkkijonon pituuden yli
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
    * Apufunktio jonka avulla luetaan käyttäjältä syöte
    * @param otsikko - Tekstikenttä joka näytetään ennen syötteen lukua
    * @return - palauttaa luetun merkkijonon
    */
    private static String lue(String otsikko) {
        System.out.println(otsikko);
        String ret = In.readString();
        return ret;
    }
    
    // käyttöliittymä on rakennettu main-funktion sisään
    public static void main(String[] args) {
        // käyttöliittymän tekstimuuttujien alustus
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
        boolean alkuVillikortti = false; // lippu onko haettavan merkkijonon ensimmäinen kirjain '*'
        boolean loppuVillikortti = false; // lippu onko haettavan merkkijonon viimeinen merkki '*'
        
        System.out.println(STR1);
        
        boolean mainloopRun = true;     // kontrollimuuttuja käyttöliittymän pääsilmukan suoritukseen
        
        // KÄYTTÖLIITTYMÄN PÄÄSILMUKKA
        while (mainloopRun) {
            // VAIHE 1: Luetaan ja validoidaan syötteeet
            rivi = lue(STR2);   // ei validoida perustuen tehtävännon alkuoletuksiin
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
            
            // ensimmäinen merkki villikortti
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
            
            // VAIHE 4: Kysytään halutaanko jatkaa
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