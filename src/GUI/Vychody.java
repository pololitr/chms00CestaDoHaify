/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import logika.IHra;
import logika.Prostor;
import main.Main;
import utils.Observer;


/**
 *
 * @author Chms00 Stepan Chmel
 */
public class Vychody extends AnchorPane implements Observer  {
    
    private IHra hra;
    private ObservableList<Prostor> vychody;
    private Main main;
    private AudioClip warcry;


    /**
     * Konstruktor pro iniciaci promenych, observeru a metody init().
     *
     * @param hra
     * @param main
     */
    public Vychody(IHra hra, Main main) {
        this.hra = hra;
        this.main = main;
        hra.getHerniPlan().registerObserver(this);  
        hra.getHerniPlan().getAktualniProstor().registerObserver(this);
        init();
    }
    
    /**
     * Nastaveni metody nova hra napriklad pro metodu nova hra ve tride main.
     * @param hra 
     */
    public void novaHra(IHra hra) {
        this.hra.getHerniPlan().removeObserver(this);
        this.hra = hra;
        this.hra.getHerniPlan().registerObserver(this);
        update();
    }
  
        // proved metodu init
        public void init() {
        
        // nastaveni okna vyzchody    
        vychody = FXCollections.observableArrayList();
        ListView<Prostor> listVychodu = new ListView<>(vychody);
        listVychodu.setPrefHeight(552);
        
        listVychodu.setCellFactory(param -> new ListCell<Prostor>() {
            @Override
            protected void updateItem(Prostor item, boolean empty) {
              
                super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setText(null);
                } else {
                    setText(item.getNazev());
                    setFont(Font.font(15));
                                     
                }
                this.setOnMousePressed(event -> {
                    
                    main.zpracujPrikazMain("jdi "+item.getNazev());
                    
                    //Spusteny hudby
                    if(hra.getHerniPlan().getAktualniProstor().getNazev().equals("Haifa")){
                        warcry = new AudioClip(this.getClass().getResource("/zdroje/warcry.wav").toString());
                        warcry.play();
                      }  
                   update();
                });
            }

        });
        VBox ExitLayout = new VBox();
         
        Label labelExit = new Label("Východy:");
        ExitLayout.getChildren().addAll(labelExit, listVychodu);
        this.getChildren().addAll(ExitLayout);
        update();
    }

    /**
     * Metoda aktualizuje seznam při přechodu z místnosti do místnosti.
     */
    @Override
    public void update() {
        vychody.clear();
        vychody.setAll();
        vychody.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     *
     * @param hra
     */
  
    
}
