package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class JavaFX extends Application {
	final Group root = new Group();
    final Xform world = new Xform();
    final Xform moleculeGroup = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);  
    final Xform cameraXform = new Xform();  
    final Xform cameraXform2 = new Xform();  
    final Xform cameraXform3 = new Xform();  
    private RotateTransition rotateTransition;
    private static final double CAMERA_INITIAL_DISTANCE = -450;  
    private static final double CAMERA_INITIAL_X_ANGLE = 70.0;  
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;  
    private static final double CAMERA_NEAR_CLIP = 0.1;  
    private static final double CAMERA_FAR_CLIP = 10000.0; 
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JavaFX Welcome");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		actiontarget.setFill(Color.FIREBRICK);
		actiontarget.setId("actiontarget");

		grid.add(actiontarget, 1, 6);

		btn.setOnAction(e -> {
			String username = userTextField.getText();
			String password = pwBox.getText();
			if ((username.compareTo("admin")==0) && (password.compareTo("admin")==0)) {
				universe(primaryStage);
			} else {
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Something is wrong");
			}
			userTextField.clear();
			pwBox.clear();
		});
		
		Scene scene = new Scene(grid, 1000, 650);
		primaryStage.setScene(scene);
		
		scene.getStylesheets().add(JavaFX.class.getResource("application.css").toExternalForm());
		primaryStage.show();
	}
	
    public void universe(Stage primaryStage) {  
		primaryStage.setTitle("Solar System");

        Group circles = new Group();  
        for (int i = 0; i < 70; i++) {  
            Circle circle = new Circle(5, Color.web("white", 0.8));  
            circle.setStrokeType(StrokeType.OUTSIDE);  
            circle.setStroke(Color.web("white", 0.16));  
            circle.setStrokeWidth(4);  
            circles.getChildren().add(circle);  
        }  
        circles.setEffect(new BoxBlur(10, 10, 3));  
        
        Scene scene = new Scene(root, 1000, 650, Color.BLACK);  
  
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),  
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,  
                        new Stop[] { new Stop(0, Color.web("#f8bd55")),  
                                new Stop(0.14, Color.web("#c0fe56")),  
                                new Stop(0.28, Color.web("#5dfbc1")),  
                                new Stop(0.43, Color.web("#64c2f8")),  
                                new Stop(0.57, Color.web("#be4af7")),  
                                new Stop(0.71, Color.web("#ed5fc2")),  
                                new Stop(0.85, Color.web("#ef504c")),  
                                new Stop(1, Color.web("#f2660f")), }));  
  
        colors.widthProperty().bind(scene.widthProperty());  
        colors.heightProperty().bind(scene.heightProperty());  
  
        Group blendModeGroup = new Group(new Group(new Rectangle(  
                scene.getWidth(), scene.getHeight(), Color.BLACK), circles),  
                colors);  
          
        colors.setBlendMode(BlendMode.OVERLAY);  
        
        Timeline timeline = new Timeline();  
        for (Node circle: circles.getChildren()) {  
            timeline.getKeyFrames().addAll(  
                new KeyFrame(Duration.ZERO, // set start position at 0  
                    new KeyValue(circle.translateXProperty(), Math.random() * 1000),  
                    new KeyValue(circle.translateYProperty(), Math.random() * 650)  
                ),
                new KeyFrame(new Duration(40000), // set end position at 40s  
                    new KeyValue(circle.translateXProperty(), Math.random() * 1000),  
                    new KeyValue(circle.translateYProperty(), Math.random() * 650)  
                ) 
            );  
        }  
        // play 40s of animation  
        timeline.play();  
        
        SubScene sun = createSubScene(false);
        root.getChildren().add(blendModeGroup);
        root.getChildren().add(sun);
        
        primaryStage.setScene(scene); 
        primaryStage.show();  
    }

    // Create subsence
 	private SubScene createSubScene(boolean msaa) {
 		Group droot = new Group();
 		buildCamera(droot);  
     	buildMolecule();
 		droot.getChildren().add(world);  
     	
 		SubScene subScene = new SubScene(droot, 1000, 650, true,
 				msaa ? SceneAntialiasing.BALANCED : SceneAntialiasing.DISABLED);
 		subScene.setCamera(camera);
 		return subScene;
 	}
 	
 	private void buildCamera(Group droot) {
 		droot.getChildren().add(cameraXform);
         cameraXform.getChildren().add(cameraXform2);
         cameraXform2.getChildren().add(cameraXform3);
         cameraXform3.getChildren().add(camera);
         cameraXform3.setRotateZ(180.0);
  
         camera.setNearClip(CAMERA_NEAR_CLIP);
         camera.setFarClip(CAMERA_FAR_CLIP);
         camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
         cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
         cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }
  
    private void buildMolecule() {
         final PhongMaterial goldMaterial = new PhongMaterial();
         goldMaterial.setDiffuseColor(Color.GOLD);
         
         final PhongMaterial blueMaterial = new PhongMaterial();
         blueMaterial.setDiffuseColor(Color.BLUE);
         
         Xform moleculeXform = new Xform();
         Xform sunXform = new Xform();
         Xform earthXform = new Xform();

         // The sun
         Sphere sunSphere = new Sphere(25.0);
         sunSphere.setMaterial(goldMaterial);
         
         // The earth
         Sphere earthSphere = new Sphere(8.0);
         earthSphere.setMaterial(blueMaterial);

         moleculeXform.getChildren().add(sunXform);
         moleculeXform.getChildren().add(earthXform);
         sunXform.getChildren().add(sunSphere);
         earthXform.getChildren().add(earthSphere);
          
         earthXform.setTranslate(70, 70, 0);
         rotateTransition = new RotateTransition(Duration.millis(3000), earthXform);
 	     rotateTransition.setByAngle(360);
 	     rotateTransition.setCycleCount(100);
 	     rotateTransition.setAxis(Rotate.Z_AXIS);
 	     rotateTransition.setAutoReverse(true);
 	     
 	    rotateTransition.play();
         
         moleculeGroup.getChildren().add(moleculeXform);
         world.getChildren().add(moleculeGroup);
    }
     
	public static void main(String[] args) {
		launch(args);
	}
}
