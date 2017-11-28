/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import javafx.scene.image.Image;
import main.Main;



/*******************************************************************************
 * Trida Postava - popisuje jednotlivé postavy  hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Postava" reprezentuje jedno postavu ve scénáři hry.
 *  Postava ma sve jmeno a svuj proslov
 *
 * @author    Stepan Chmel chms00 30/12/2016
 * @version   final
 */
public class Postava
{
    //== Datové atributy (statické i instancí)======================================
    private String jmeno;
    private String rec;
     private String obrazek;
     private Image obrazekFile;
    
    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  nastavime, ze jmeno a rec ktere ze budeme pouzivat 
     *  jsou stejne jako parametry objektu Postava
     *  
     */
    public Postava(String jmeno, String rec, String obrazek)
    {
        this.jmeno = jmeno;
        this.rec = rec;
         this.obrazek = obrazek;
        this.obrazekFile = new Image(Main.class.getResourceAsStream("/zdroje/" + obrazek), 100, 100, false, false);
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Getter pro jmeno postavy
     */
    public String getJmeno() {
        return jmeno;
    }
    /**
     * Getter pro rec postavy
     */
    public String getRec() {
        return rec;
    }
    
       public String getObrazek() {
        return obrazek;
    }
       public Image getObrazekFile(){
    return obrazekFile;
    }
    // Možná bude potřeba přidat settery pro atributy 'popis' a 'prenositelna'.
    // Atribut 'nazev' by se měnit neměl.
    
    //== Soukromé metody (instancí i třídy) ========================================

}
