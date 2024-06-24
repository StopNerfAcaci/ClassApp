package com.example.demo;

import Controllers.ClassController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ConnectionPool;
import utils.ConnectionPoolImpl;

import java.io.IOException;


public class ClassApp extends Application {


    private static final ConnectionPool connectionPool = new ConnectionPoolImpl();
    public static ConnectionPool getConnectionPool(){
        return connectionPool;
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassApp.class.getResource("/com/example/demo/ClassForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ClassForm!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
