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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logika.IHra;
import logika.Vec;
import main.Main;
import utils.Observer;

/**
 *
 * @author Chms00 Stepan Chmel
 */
public class BatohInsider extends AnchorPane implements Observer {
    private ObservableList<Vec> predmety;
   private IHra hra;
   private Main main;
   private ObservableList<Object> data = FXCollections.observableArrayList();

     /* Metoda pro praci s obesrvry
     * @param ihra 
     */
    public BatohInsider(IHra hra, Main main) {
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        this.main = main;
        hra.getHerniPlan().getBatoh().registerObserver(this);
        init();
        update();
    }

    /**
     * Metoda pri vlozeni nove veci aktualizuje list obsah batohu tim,
     * ze pred po pridani smaze jeho obsah a prida novy kompletni. 
     *
     */
    public void init(){
    predmety = FXCollections.observableArrayList();
    ListView<Vec> listPredmetu = new ListView<>(predmety);
    listPredmetu.setPrefHeight(552);
    listPredmetu.setPrefWidth(250);
    listPredmetu.setCellFactory(param -> new ListCell<Vec>() {
    private ImageView imageView = new ImageView();
    @Override
    protected void updateItem(Vec item, boolean empty) {
    super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //nasteveni obrazku pro kliknuti 
                    imageView.setImage(item.getObrazekFile());
                    imageView.setFitHeight(100);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
               // co nasleduje po klinuti na obrazek
                this.setOnMousePressed(event -> {
                    main.zpracujPrikazMain("odhod " +item.getNazev());                
                    hra.getHerniPlan().notifyObservers();
                    update();
                });            
    }
    });
    VBox batohLayout = new VBox();
    Label labelBatoh = new Label("Věci v batohu:");
    batohLayout.getChildren().addAll(labelBatoh, listPredmetu);
    this.getChildren().addAll(batohLayout);
    update();
    }
    
    /**
     * Metoda pro update listu obshau
     */
    @Override
    public void update() {  
        predmety.setAll();
        predmety.addAll(hra.getHerniPlan().getBatoh().getObsah().values());
    }

    /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     *
     * @param hra objektu Hra.
     */
    public void newGame(IHra hra) {
        this.hra.getHerniPlan().removeObserver(this);
        this.hra = hra;
        this.hra.getHerniPlan().registerObserver(this);
        update();
    }

  
}
