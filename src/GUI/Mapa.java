/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 *
 * @author Chms00 Stepan Chmel
 */
public class Mapa extends AnchorPane implements Observer {

    private IHra hra;
     private ImageView tecka;
     public AudioClip warcry;

     /**
      * Konstruktor tridy mapa
      * @param hra 
      */
    public Mapa(IHra hra) {
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
    }
    
    /**
     * Inicilaizace mapy a obrazku postavy ktera slouzi k identifikaci lokace hrace.
     */
    private void init() {
        ImageView obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.png"), 504, 352, false, true));
        tecka = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/postava.png"), 30, 35, true, true));
        this.getChildren().addAll(obrazekImageView, tecka); 
        update();
    }
    
    /**
     * Definovani nove hry
     * @param novaHra objektu Hra 
     */
    public void newGame(IHra novaHra){
        hra.getHerniPlan().removeObserver(this);
        hra = novaHra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    /**
     * Aktualizovani lokace hrace pomoci postavicky.
     */
    @Override
    public void update() {
        this.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosTop());
        this.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosLeft());
    }

}
