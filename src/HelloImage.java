import javafx.application.Application;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloImage extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Image image = new Image(getClass().getResourceAsStream("LadyBugSM.png"));
		Label label2 = new Label("LadyBug", new ImageView(image));
		
		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		pane.setBackground(Background.EMPTY);
		pane.setPadding(new Insets(25,25,25,25));
		pane.getChildren().add(label2);
		
		primaryStage.setScene(new Scene(pane, 300, 300, Color.rgb(241, 196, 16)));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}
