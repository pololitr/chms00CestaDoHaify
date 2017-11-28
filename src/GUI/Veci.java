/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class Veci extends AnchorPane implements Observer  {
     private IHra hra;
    private ObservableList<Vec> predmety;
    private Main main;
     
    /**
     * Konstruktor pro inicializaci promenych a observeru.
     * @param hra
     * @param main 
     */
     public Veci(IHra hra, Main main){
       this.hra = hra;
        this.main = main;
        hra.getHerniPlan().registerObserver(this);  
        hra.getHerniPlan().getAktualniProstor().registerObserver(this);
        init();
     }
       
     /**
      * Metoda novaHra pro nastaveni situace nove hry
      * @param hra 
      */
       public void newGame(IHra hra) {
        this.hra.getHerniPlan().removeObserver(this);
        this.hra = hra;
        this.hra.getHerniPlan().registerObserver(this);
        update();
        }
       
      /**
       * Hlavni inicializace 
       */
       private void init(){
        predmety = FXCollections.observableArrayList();
        ListView<Vec> listPredmetu = new ListView<>(predmety);
        listPredmetu.setOrientation(Orientation.HORIZONTAL);
        listPredmetu.setPrefHeight(200);
        listPredmetu.setPrefWidth(252);
        listPredmetu.setCellFactory(param -> new ListCell<Vec>() {
            private ImageView imageView = new ImageView();
            
            @Override
            protected void updateItem(Vec item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //velikost obrazku ktery se zobrazi v borderpainu pohledu Veci
                    imageView.setImage(item.getObrazekFile());
                    imageView.setFitHeight(100);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
                //Definovani konsekvence po klinuti mysi na obrazek
                this.setOnMousePressed((MouseEvent event) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                //definovani alert boxu
                alert.setTitle("VYBER");
                alert.setHeaderText("Copak by ste rad provedl s '"+item.getNazev()+"'");
                alert.setContentText("Vyberte akci kterou provedete s veci: '"+item.getNazev()+"'");
                ButtonType buttonTypeOne = new ButtonType("Pouzij");
                ButtonType buttonTypeTwo = new ButtonType("Vezmi");
                ButtonType buttonTypeThree = new ButtonType("Prozkoumej");
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);
                Optional<ButtonType> result = alert.showAndWait();
                // jednotlive priklady po klinuti
                if (result.get() == buttonTypeOne){
                    main.zpracujPrikazMain("pouzij " + item.getNazev());
                    hra.getHerniPlan().getAktualniProstor().odeberVec(item.getNazev());
                    hra.getHerniPlan().notifyObservers();
                    update();
                } 
                else if(result.get() == buttonTypeTwo){
                    main.zpracujPrikazMain("vezmi " + item.getNazev());
                   
                    
                    update();
                }
                else if(result.get() == buttonTypeThree){
                    main.zpracujPrikazMain("prozkoumej " + item.getNazev());
                   
                    
                    update();
                }
                    
                    
                });
            }

        });
        VBox veciLayout = new VBox();
        Label labelVeci = new Label("Věci v prostoru:");
   
        veciLayout.getChildren().addAll(labelVeci, listPredmetu);
        this.getChildren().addAll(veciLayout);
        update();
         
         
          
        }
     
  
    /**
     * Metoda pro update listu veci v oken Veci
     */
   @Override
    public void update() {  
       predmety.setAll();
        predmety.addAll(hra.getHerniPlan().getAktualniProstor().getVeci().values());
    }


    /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     *
     * @param hra
     */
  
    
}
