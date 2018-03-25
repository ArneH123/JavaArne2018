package main;
import domein.DomeinController;
import gui.OefeningenSchermController;
import javafx.stage.Stage;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class StartUp extends Application
{
    private DomeinController dc = new DomeinController();
           
    @Override
   public void start(Stage primaryStage){
    
      OefeningenSchermController Controller = new OefeningenSchermController(primaryStage);
      Parent AnchorPane = Controller.InitialiseerController(dc);
      

      primaryStage.setScene(new Scene(AnchorPane));
      primaryStage.show();
   }
    
    public static void main(String[] args) {
        launch(args);
    }
}
