/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.BatohInsider;
import GUI.Vychody;
import GUI.Mapa;
import GUI.MenuLista;
import GUI.Postavy;
import GUI.Veci;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.*;
import uiText.TextoveRozhrani;

/**
 *
 * @author Chms00 Stepan Chmel
 */
public class Main extends Application {

    private TextArea centralText;
    private IHra ihra = new Hra();
    private Hra hra;
    
    public void setHra(IHra ihra) {
        this.ihra = ihra;
    }
    
    public TextField zadejPrikazTextArea;
    
    private Mapa mapa;
    private MenuLista menuLista;
    private BatohInsider batohInsider;
    private Postavy postavy;
    private Vychody vychody;
    private Stage stage;
    private Veci veci;
  
   
    @Override
    public void start(Stage primaryStage) {
        this.setStage(primaryStage);
        
        // definovani borderpainu
        BorderPane borderPane = new BorderPane();
        BorderPane pravaLista = new BorderPane();
        BorderPane levaLista = new BorderPane();
  
        BorderPane prostorPane = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
     
        // nastavni promenych
        batohInsider = new BatohInsider(ihra, this);
        vychody = new Vychody(ihra, this);
        veci = new Veci(ihra, this);
        mapa = new Mapa(ihra);
        menuLista = new MenuLista(ihra, this);
        postavy = new Postavy(ihra, this);
             
        
        // Text s prubehem hry
        centralText = new TextArea();
        centralText.setText(ihra.vratUvitani());
        centralText.setEditable(false);
        centralText.setPrefHeight(552);
        VBox centralTextLayout = new VBox();
        centralTextLayout.setMinWidth(300);
        
        //Label pro centralni text
        Label labelCentralText = new Label("Výpis textového rozhraní");
        centralTextLayout.getChildren().addAll(labelCentralText, centralText);
        
        borderPane2.setTop(centralTextLayout);
        borderPane.setCenter(borderPane2);

        //label s textem zadej prikaz
        Label zadejPrikazLabel = new Label("Zadej prikaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // text area do ktere piseme prikazy
        zadejPrikazTextArea = new TextField("...");
        zadejPrikazTextArea.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * Metoda ktera se stara po presparcovani priklazu zadnzch do textobeho pole
             * @param event 
             */
            @Override
            public void handle(ActionEvent event) {
           
                String vstupniPrikaz = zadejPrikazTextArea.getText();
                String odpovedHry = ihra.zpracujPrikaz(vstupniPrikaz);
                centralText.appendText("\n" + vstupniPrikaz + "\n");
                centralText.appendText("\n" + odpovedHry + "\n");
                
                zadejPrikazTextArea.setText("");
                
                if (ihra.konecHry()) {
                    zadejPrikazTextArea.setEditable(false);
                    centralText.appendText(ihra.vratEpilog());
                }
            }
        });
        
       /**
        * Nastaevni jednotlivych lokaci pro borderpainy
        */
    
        FlowPane dolniLista = new FlowPane();
        dolniLista.setAlignment(Pos.CENTER);
        dolniLista.setBackground(Background.EMPTY);
        dolniLista.getChildren().addAll(zadejPrikazLabel,zadejPrikazTextArea, prostorPane);
        
        pravaLista.setLeft(batohInsider);
        pravaLista.setRight(vychody);
        pravaLista.setBottom(veci);
        
        prostorPane.setLeft(postavy);
        prostorPane.setRight(veci);
      
        levaLista.setTop(mapa);
        centralText.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        levaLista.setCenter(prostorPane);
        
        borderPane.setStyle("-fx-background-colour: gray");
        
        borderPane.setLeft(levaLista);
        borderPane.setRight(pravaLista);
        
        borderPane.setBottom(dolniLista);
        borderPane.setTop(menuLista);
        
        Scene scene = new Scene(borderPane, 2000, 750, Color.WHITE);
        primaryStage.setTitle("Adventura");

        primaryStage.setScene(scene);
        primaryStage.show();
        zadejPrikazTextArea.requestFocus();
    }

    //metoda ktera vraci centralni text
    public TextArea getCentralText() {
        return centralText;
    }
    
    //metoda ktera vraci mapu
    public Mapa getMapa() {
        return mapa;
    }
    //metoda ktera vraci objekt hra typu Ihra
     public IHra getHra() {
        return hra;
    }

 
    /**
     * Zpracuje String příkazu, vypíše odpověď. Pokus hra skončila, nastaví gui stav disabled.
     * @param prikaz
     */
     
    public void zpracujPrikazMain(String prikaz){
        String odpovedHry1 = ihra.zpracujPrikaz(prikaz);
        
        centralText.appendText("\n" + prikaz + "\n");
        centralText.appendText("\n" + odpovedHry1 + "\n");
        if(ihra.konecHry()){
            zadejPrikazTextArea.setEditable(false);
            
            //vesechny hlani prvky jsou viditelne a funkcni
            vychody.setDisable(true);
            mapa.setDisable(true);
            batohInsider.setDisable(true);
            veci.setDisable(true);
            postavy.setDisable(true);
            batohInsider.setDisable(true);

            centralText.appendText(ihra.vratEpilog());
        }
    };    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        }
        else{
            if (args[0].equals("-txt")) {
                IHra hra = new Hra();
                TextoveRozhrani textHra = new TextoveRozhrani(hra);
                textHra.hraj();
                
            }
            else{
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Nastavni pro novu hru cele adventury
     * @param hra 
     */
    public void newGame(IHra hra){

        this.mapa.newGame(hra);
        this.vychody.novaHra(hra);
        this.veci.newGame(hra);
        this.postavy.newGame(hra);
        this.batohInsider.newGame(hra);
        

        this.vychody.setDisable(false);
        this.veci.setDisable(false);
        this.postavy.setDisable(false);
        this.batohInsider.setDisable(false);
        
        this.zadejPrikazTextArea.setEditable(true);
    }

}
