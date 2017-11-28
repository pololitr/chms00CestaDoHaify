/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logika.IHra;
import logika.Postava;
import main.Main;
import utils.Observer;

/**
 *
 * @author Chms00 Stepan Chmel
 */
public class Postavy extends AnchorPane implements Observer {
    
    private IHra hra;
    private ObservableList<Postava> postavy;
    private Main main;

  
   /**
    * Konstruktor tridz Postavy
    * @param hra
    * @param main 
    */
    public Postavy(IHra hra, Main main){
        this.hra = hra;
        this.main = main;
        hra.getHerniPlan().registerObserver(this);
        hra.getHerniPlan().getAktualniProstor().registerObserver(this);
        init();
    }

    /**
     * Nastaví sledování nové hry.
     * @param hra
     */
    public void newGame(IHra hra){
        this.hra.getHerniPlan().removeObserver(this);
        this.hra = hra;
        this.hra.getHerniPlan().registerObserver(this);
        update();
    }

    /**
     * Metodda inicializace pohledu Postavy
     */
    private void init(){
        postavy = FXCollections.observableArrayList();
        ListView<Postava> listPredmetu = new ListView<>(postavy);
        listPredmetu.setOrientation(Orientation.HORIZONTAL);
        listPredmetu.setPrefHeight(200);
        listPredmetu.setPrefWidth(252);
        listPredmetu.setCellFactory((ListView<Postava> param) -> new ListCell<Postava>() {
            private ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Postava item, boolean empty) {
                super.updateItem(item, empty);
                 if (empty || item == null || item.getJmeno() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(item.getObrazekFile());
                    imageView.setFitHeight(100);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
                this.setOnMousePressed(event -> {
                    main.zpracujPrikazMain("mluv "+item.getJmeno());
                    update();
                });
                    
                
            }
        });
        VBox PersonLayout = new VBox();
        Label labelExit = new Label("Postavy v prostoru:");
        PersonLayout.getChildren().addAll(labelExit, listPredmetu);
        this.getChildren().addAll(PersonLayout);
        update();
    }


    /**
     * Metoda aktualizace listu postav
     */
    @Override
    public void update() {
        postavy.setAll();
        postavy.addAll(hra.getHerniPlan().getAktualniProstor().getPostavy().values());
    }
}
