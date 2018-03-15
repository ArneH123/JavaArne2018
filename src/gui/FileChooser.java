///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package gui;
//
//import java.awt.Desktop;
//import java.awt.event.ActionEvent;
//import java.io.File;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.event.EventHandler;
//import javafx.scene.control.Button;
//import javafx.stage.Stage;
//import javax.swing.JButton;
//
///**
// *
// * @author TessaWalraet
// */
//public class FileChooser extends OefeningenSchermController
//{
//    private Desktop desktop = Desktop.getDesktop();
//    
//    public void start(final Stage stage) {
//        stage.setTitle("Wijzig pdf");
//        
//        final FileChooser fileChooser = new FileChooser();
//        final JButton btnWijzigOpgave = new JButton("Wijzig");
//        final Button btnWijzigFeedback = new Button("Wijzig");
//        
//        btnWijzigOpgave.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event)
//            {
//                File file = fileChooser.showOpenDialog(stage);
//                if(file != null) {
//                    openFile(file);
//                }
//            }
//        
//        });
//        
//        btnWijzigFeedback.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event)
//            {
//                File file = fileChooser.showOpenDialog(stage);
//                if(file != null) {
//                    openFile(file);
//                }
//            }
//        });
//    }
//    
//    private void openFile(File file) {
//        try {
//            desktop.open(file);
//        }
//        catch(IOException ex) {
//            Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//}
