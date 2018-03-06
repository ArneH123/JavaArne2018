
package main;
import domein.OefeningBeheerder;
import gui.OefeningenSchermController;
import javafx.stage.Stage;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
public class StartUp extends Application
{
    
    private OefeningBeheerder ob = new OefeningBeheerder();
           
    @Override
   public void start(Stage primaryStage){
      OefeningenSchermController root = new OefeningenSchermController(ob);
      Scene scene = new Scene(root);
      
      primaryStage.setScene(scene);
      primaryStage.show();
   }
    
    public static void main(String[] args) {
        launch(args);
    }
}
