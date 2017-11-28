/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import java.util.*;
import utils.Observer;
import utils.Subject;

/*******************************************************************************
 * Trida Batoh - popisuje batoh, tedy misto do ktereho si muze hrac behem hry uladat veci
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Batoh" reprezentuje batoh ve scénáři hry.
 * Batoh ma fixni velikost, muzete vem pridat a odebirat veci
 *
 * @author    Stepan Chmel chms00 30/12/2016
 * @version   final
 */
public class Batoh implements Subject
{
    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    private static final int MAXobs = 7;
    //private List<Vec> obsah;
    private Map <String, Vec> seznamVeci;
    private final List<Observer> listObserveru = new ArrayList<>();
    
    /***************************************************************************
     *  Konstruktor ....
     *  
     */
    public Batoh()
    {
        seznamVeci = new HashMap<String, Vec>();
        //obsah = new ArrayList<Vec>();
    }
    
    /**
     * 
     * Pokud je v batohu misto dojde k pridani veci jinak 
     * je vypsana chybova hlaska 
     * 
     * @param vec
     * @return objekt vec pridana nebo null
     */
    public Vec pridejVec(Vec vec){
        if(isSpace()){
        seznamVeci.put(vec.getNazev(),vec);
        return vec;
        
        }
        
        System.out.println("vec nebyla vlozena do batohu, je plny");
        return null;
    }
    
   /**
    * Kontrola plnosti batohu, je-vraci true, neni vraci false
    * 
    * @return true nebo false
    */
    public boolean isSpace(){
        if(seznamVeci.size() < MAXobs){
            return true;    
        }
        return false;
    }
    
   /**
    * 
    * Informace zda-li je vec v batohu, je-vraci true, neni vraci false
    * 
     * @param hled
    * @return true po nalezeni veci v batohu
    * @return false pro nenalezeni
    */
    public boolean isIn(String hled){
      if(this.seznamVeci.containsKey(hled)){
        return true;
      }
      return false;
    }
    
    /**
     * Tato metoda vypise veskery obsah batohu
     * Vraci tedy string
     * 
     * @return 
     */
    public Map<String, Vec> getObsah() {
        return seznamVeci;
    }
    public String getObsahFull() {  // vypíše věci z batohu
        String items = "";
        for (String jmenoVeci : seznamVeci.keySet()){
            items += jmenoVeci + " ";
        }
        return items;
    }
   
    /**
     * 
     * Teto metode zadata jakou vec chcete z batohu
     * pokud ji najde vypise ji, pokud ne vrati null
     * @param nazev
     * @return 
     */
    public Vec getVec(String nazev) {  //odebere věc z batohu, která je v parametru funkce
        return seznamVeci.get(nazev);
    }
    /**
     * Metoda pro mazani veci z batohus
     * Na vstup zadate co chcete smazat,
     * Pokud je vec v batohu dojde ke smazani a je vraceo jmeno/nazev
     * smazane veci. Pokud vec neni v batohu vraci null
     */
    public Vec remVec(String nazev) {  //odebere věc z batohu, která je v parametru funkce
        return seznamVeci.remove(nazev);
    }
    
    /**
     * vraci cislo o maximalni kapacite batohu
     * 
     * @return intager o maximalni kapacite batohu
     */
    public int getMaxCap() {
        return MAXobs;
    }
    
    //== Nesoukromé metody (instancí i třídy) ======================================


    //== Soukromé metody (instancí i třídy) ========================================
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        listObserveru.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : listObserveru) {
            observer.update();
        }
    }
}
