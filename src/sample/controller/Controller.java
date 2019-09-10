package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.database.DBhandllar;
import sample.model.User;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller extends Window {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imaveView;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXComboBox<String> genderList;

    @FXML
    private JFXTextField nameTextField;

    @FXML
    private JFXButton openImage;

    private ObservableList<String> list= FXCollections.observableArrayList(
            "Male" ,"Female" ,"Other"
    );
    private FileInputStream fileInput;
    private File file;





    @FXML
    void initialize() throws IOException {




        genderList.setItems(list);
        saveButton.setOnAction(actionEvent -> {
            try {
                addDetails();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        uploadButton.setOnAction(actionEvent -> {
            try {
                uploadImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        openImage.setOnAction(actionEvent -> {
            try {
                openDetails();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

    }



    private void uploadImage() throws FileNotFoundException {
        FileChooser chooser=new FileChooser();
        file=chooser.showOpenDialog(this);

        if(file !=null){
            Image image=new Image(file.toURI().toString());
            imaveView.setImage(image);

        }
    }


    private void addDetails() throws SQLException, ClassNotFoundException, FileNotFoundException {
        User user=new User();
        if(genderList.getSelectionModel().getSelectedItem().equals("Male"))
          user.setGender("Male");
        else if(genderList.getSelectionModel().getSelectedItem().equals("Female"))
            user.setGender("Female");
        else {
            user.setGender("Other");
        }






        user.setName(nameTextField.getText().trim().toUpperCase());
        fileInput=new FileInputStream(file.getAbsolutePath());

        DBhandllar dBhandllar=new DBhandllar();
        dBhandllar.addDetails(user,fileInput);



    }

    private void openDetails() throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        DBhandllar dBhandllar=new DBhandllar();

        ResultSet resultSet =dBhandllar.findPhoto();

        while (resultSet.next()){
            InputStream tt=resultSet.getBinaryStream("image");
            javafx.scene.image.Image image1=new Image(tt);
            imaveView.setImage(image1);

            nameTextField.setText(resultSet.getString("username"));
            genderList.getSelectionModel().select(String.valueOf(new Label(resultSet.getString("gender"))));


        }

    }






}
