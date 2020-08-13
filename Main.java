package application;
/**
 * @author Nikitha Reddy
 * @since 11-11-2018 
 * Time : 10:30 AM
 * Purpose : Main class contains javafx mobile application 
 */
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	static MobilePhone mobilephone;
	String nm = null;
	String pNo = null;
	/**
	 * Method start: Application starts here.
	 */
	@Override
	public void start(Stage primaryStage) {
		mobilephone = new MobilePhone();
		try {
			primaryStage.setTitle("SAU Contact System");
	        primaryStage.setWidth(900);
	        primaryStage.setHeight(800);
	        //create gridPane
	        GridPane gridPane = createContactFormPane();
	        //add UIControls to gridPane
	        addUIControls(gridPane);
	       
			// Creating a scene object   
			Scene scene = new Scene(gridPane);  
			// apply stylesheet to the scene graph
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					
			// Adding scene to the stage 
			primaryStage.setScene(scene);
			//Displaying the contents of the stage 
			primaryStage.show(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method createContactFormPane :  creates gridPane and returns gridPane.
	 * @return
	 */
	
	private GridPane createContactFormPane() {
        
		GridPane gridPane = new GridPane(); 
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
        gridPane.setStyle("-fx-background-color: #408080;"); //inline css
		return gridPane;
		
	}
	/**
	 * Method addUIControls:  Adds all UI controls to gridPane
	 * @param gridPane
	 */
	private void addUIControls(GridPane gridPane) {
		 //creating labels and giving styles
        Label title = new Label ("SAU Contact System");
        Label nmText = new Label ("Name");
        Label phNoText = new Label ("Phone No");
        Label contactRecordText = new Label ("Result");
        
        title.setId("title-text");
       
        //Creating Text Filed for name and phone number
        TextField name = new TextField();
        TextField phoneNo = new TextField();
        
        TextArea contactRecords = new TextArea();
        contactRecords.setPrefSize(300, 300);
        contactRecords.clear();
        contactRecords.setEditable(false);
        
        //Create alert to handle empty field errors
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        
        //create Button add, delete, update, search, display.
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        Button searchButton = new Button("Search");
        Button displayButton = new Button("Display");
        
        // adding ui controls to gridPane
        gridPane.add(title, 4, 1);
        gridPane.add(nmText, 2, 7);
        gridPane.add(phNoText, 2, 11);
        gridPane.add(contactRecordText, 2, 15,2,1);
        
        gridPane.add(name, 4, 7);
        gridPane.add(phoneNo, 4, 11);
        gridPane.add(contactRecords, 4, 16);
        
        gridPane.add(addButton, 10, 7);
        gridPane.add(deleteButton, 10, 9);
        gridPane.add(updateButton, 10, 11);
        gridPane.add(searchButton, 10, 13);
        gridPane.add(displayButton, 10, 15);
        
        /**
         * Adding listeners for textfields
         */
        
        // force the name field to be alphabets and space only
        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
            	if (!newValue.matches("[a-zA-Z \\.]+")) {
                	name.setText(newValue.replaceAll("[^a-z|^A-Z|^\\s|^\\.]", ""));
                }
            }
        });
        
        // force the phoneNo field to be numeric/+/- only
        phoneNo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*|\\-|\\+")) {
                	phoneNo.setText(newValue.replaceAll("[^\\d|^\\-|^\\+]", ""));
                }
            }
        });

        /**
         * Adding EventHandler for Buttons 
         */
        
		addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                public void handle(MouseEvent event) {
                	nm = name.getText();
                	pNo = phoneNo.getText();
                	contactRecords.clear();
                	if(nm.isEmpty()||pNo.isEmpty()) {
                		alert.setHeaderText(null);
                		alert.setContentText("Name and Phone Number Field REQUIRED***");
                		alert.showAndWait();
                	}
                	else {
	                	String response = mobilephone.addNewContact(mobilephone.camelCase(nm),pNo);
	                	contactRecords.setText(response);
	                    event.consume();
                	}
                }
           });
		deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
            	nm = name.getText();
            	contactRecords.clear();
            	if(nm.isEmpty()) {
            		alert.setHeaderText(null);
            		alert.setContentText("Name Field REQUIRED***");
            		alert.showAndWait();
            	}
            	else {
                	String response = mobilephone.removeContact(mobilephone.camelCase(nm));
                	contactRecords.setText(response);
                    event.consume();
            	}
            }
       });
		updateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
            	nm = name.getText();
        		pNo = phoneNo.getText();
            	if(nm.isEmpty()||pNo.isEmpty()) {
            		alert.setHeaderText(null);
            		alert.setContentText("Name and Phone Number Field REQUIRED***");
            		alert.showAndWait();
            	}
            	else {
                	contactRecords.clear();
                	String response = mobilephone.updateContact(mobilephone.camelCase(nm), pNo);
                	contactRecords.setText(response);
                    event.consume();
            	}
            }
       });
		
		searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
            	nm = name.getText();
            	contactRecords.clear();
            	if(nm.isEmpty()) {
            		alert.setHeaderText(null);
            		alert.setContentText("Name Field REQUIRED***");
            		alert.showAndWait();
            	}
            	else {
                	String response = mobilephone.searchContact(mobilephone.camelCase(nm));
                	contactRecords.setText(response);
                    event.consume();
            	}
            }
       });
		displayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
            	nm = name.getText();
            	contactRecords.clear();
            	name.clear();
            	phoneNo.clear();
            	String response = mobilephone.printContacts();
            	contactRecords.setText(response);
                event.consume();
            }
       });

					
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
