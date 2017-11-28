/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import main.Main;

/**
 *
 * @author Chms00 Stepan Chmel
 */
public class MenuLista extends MenuBar{
    
    private IHra ihra;
    private Hra hra;
    private Main main;
    
    public MenuLista(IHra ihra, Main main){
        this.ihra = ihra;
        this.main = main;
        init();
    }
    
    /**
     * Metoda pro inicializaci pohledu Menu 
     */
    private void init(){
        
        Menu novySoubor = new Menu("Adventura");
        Menu napoveda = new Menu("Help");
        
        MenuItem novaHra = new MenuItem("Nova hra");
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        
        MenuItem konecHry = new MenuItem("Konec hry");
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+K"));
        
        novySoubor.getItems().addAll(novaHra, konecHry);
        
        MenuItem oProgramu = new MenuItem("O programu");
        MenuItem napovedaItem = new MenuItem("Napoveda");
        
        napoveda.getItems().addAll(oProgramu, napovedaItem);
        
        this.getMenus().addAll(novySoubor, napoveda);
        
        //Definice konsekvence po jkliknuti na jednotlive itemy v Menu
        konecHry.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        //Nastveni itemu nova hra v menu
        novaHra.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ihra = new Hra();
                main.setHra(ihra);
                main.getCentralText().setText(ihra.vratUvitani());
                main.newGame(ihra);
            }
        });
        
        //Spusteni o programu
        oProgramu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert oProgramuAlert = new Alert(Alert.AlertType.INFORMATION);
                oProgramuAlert.setTitle("O pragramu");
                oProgramuAlert.setHeaderText("Adventura \"Cesta do Haify\" byla vytovrena na predmetu 4IT101");
                oProgramuAlert.setContentText("V predmetu 4IT115 byl dodelana graficka nadstavba pro tuto adventuru\n uzijte si hrani");
                oProgramuAlert.initOwner(main.getStage());
                
                oProgramuAlert.showAndWait();
            }
        });
        
        //Spusteni napovedy
        napovedaItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Stage stage = new Stage();
                stage.setTitle("Napovea");
                
                WebView webView = new WebView();
                
                webView.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());
                
                stage.setScene(new Scene(webView, 500,500));
                stage.show();
            
            }
        });
        
        
    }
    
}
