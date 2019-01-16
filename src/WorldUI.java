import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *  BUG WORLD
 *  
 *  Built with JavaFX
 *  
 *  Bug images by https://www.flaticon.com/authors/freepik
 *  Background image from www.toptal.com/designers/subtlepatterns/
 * 
 */

public class WorldUI extends Application {
	private int width = 500, height = 500;
	//private ArrayList<Obj> circles = new ArrayList<>();
	//private ArrayList<Obj> plants = new ArrayList<>();
	
	/* Used group for this rather than ArrayLists. All children are nodes of the Group. 
	 * -all entities are in this one group. 
	 * 	If there were more than there are it would probably be better to break this up.
	 * */
	private Group root = new Group();
	private VBox pane = new VBox(5);
	private Scene scene = new Scene(root, width, height);
	private Button playButton = new Button("\u25B6");
	private Button pauseButton = new Button("\u23F8");
	private Button stopButton = new Button(	"\u23F9");

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stu
		
		/* Populates World */
		this.randomObstacles(); 
		this.randomPlants();
		this.randomBee();
		this.randomLadybugs();
		this.randomButterfly();
		this.randomDragonfly();
		
		//Duration maximum is 60fps
		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
//				for(Obj b : circles) {
//					b.movement(scene); //run movement on each bug, pass in scene for height & width
//				}
				for(Node b : root.getChildren()) {
					/* Instances of bugs are called and movement method is used */
					if(b instanceof Bug) {
						((Bug) b).movement(scene); //run movement on each bug, pass in scene for height & width
					}
					
					/* Bug is removed from group when energy reaches 0 */
					if(b instanceof Bug) {
						if(((Bug) b).getEnergy() <= 0) {
							root.getChildren().remove(((Bug) b));
							break;
						}
					}					
					
					/* Runs grow method */
					if(b instanceof Plant) {
						((Plant) b).grow();
					}	
					
					/* If plant intersects Bug then decrease the radius of plant and give the bug energy (Max 30) 
					 * change - ladybugs and bees eat plants
					 * */
					if(b instanceof Ladybug || b instanceof Bee || b instanceof Butterfly) {
						for(Node p : root.getChildren()) {
							//A very long and convoluted if statement use to go here
							if(p instanceof Plant) {
								if(((Plant) p).getBoundsInParent().intersects(((Bug) b).getBoundsInParent())) {
									((Plant) p).setRadius(((Plant) p).getRadius() - 0.2);
									if(((Bug) b).getEnergy() < 30) { 
										((Bug) b).setEnergy(((Bug) b).getEnergy() + 0.3);
									}	
								}
							}
						}
					}
					
					/* If Dragonfly intersects Ladybug then decrease the energy 
					 * change - ladybugs and bees eat plants
					 * */
					for(Node l : root.getChildren()) {
						if(b instanceof Dragonfly && l instanceof Ladybug) {
							//A very long and convoluted if statement use to go here
							if(((Dragonfly) b).getBoundsInParent().intersects(((Ladybug) l).getBoundsInParent())) {
								((Ladybug) l).setEnergy(((Ladybug) l).getEnergy() - 30);
								((Dragonfly) b).setEnergy(((Dragonfly) b).getEnergy() + 30);
							}
						}	
					}
					
					
					/* If Bug intersects Stone then reverse
					 * */
					for(Node s : root.getChildren()) {
						if(b instanceof Bug && s instanceof Stone) {
							if(((Bug) b).getBoundsInParent().intersects(((Stone) s).getBoundsInParent())) {
								if(((Bug) b).getTranslateX() > 0) {
									((Bug) b).setTranslateX(((Bug) b).getTranslateX() + (((Bug) b).getRadius()/2));
								} else {
									((Bug) b).setTranslateX(((Bug) b).getTranslateX() - (((Bug) b).getRadius()/2));
								} 
								
								if (((Bug) b).getTranslateY() > 0) {
									((Bug) b).setTranslateY(((Bug) b).getTranslateY() + (((Bug) b).getRadius()/2));
								} else {
									((Bug) b).setTranslateY(((Bug) b).getTranslateY() - (((Bug) b).getRadius()/2));
								}
								
							}
						}	
					}					
					
				}	
			}
		});
		
		//TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();
		// TimelineBuilder is deprecated, Used Timeline below instead.
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(Timeline.INDEFINITE); 
		timeline.getKeyFrames().addAll(frame); 
		timeline.setAutoReverse(true);
//		timeline.play();

		pane.getChildren().add(playButton);
		pane.getChildren().add(pauseButton);
		pane.getChildren().add(stopButton);

		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setBackground(Background.EMPTY);
		root.getChildren().add(pane);
		
		Image background = new Image(getClass().getResourceAsStream("light-veneer.png"));
		scene.setFill(new ImagePattern(background));
		
		playButton.setStyle("-fx-font: 15 arial; -fx-base: #353535;-fx-text-fill: white; -fx-pref-width: 28px; -fx-pref-height: 28px;");
		pauseButton.setStyle("-fx-font: 12 arial; -fx-base: #353535;-fx-text-fill: white; -fx-pref-width: 28px; -fx-pref-height: 28px;");
		stopButton.setStyle("-fx-font: 10 arial; -fx-base: #353535;-fx-text-fill: white; -fx-pref-width: 28px; -fx-pref-height: 28px;");
		
		playButton(timeline);
		pauseButton(timeline);
		stopButton(timeline);	
		
		primaryStage.setTitle("Bug World");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}	

	/* Generic Bugs */
//	public void randomBugs() {
//		/* Random Generic Bugs */
//		int randomAmount = (int)(Math.random()*((width*height)/((width*height)/10)));
//		Bug bug;
//		for(int i = 1; i <= randomAmount; i++) {
//			int randomX = (int)(Math.random()*(width));
//			int randomY = (int)(Math.random()*(height));
//			bug = new Bug(randomX, randomY, 5);
//			//circles.add(bug);
//			root.getChildren().add(bug);		
//		}
//	}	
	
	public void randomLadybugs() {
		// added 1+ to randomAmount to insure there is at least 1 ladybug
		int randomAmount = 1 + (int)(Math.random()*((width*height)/((width*height)/10)));
		Ladybug ladybug;
		for(int i = 1; i <= randomAmount; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			ladybug = new Ladybug(randomX, randomY, 7);
			Image image = new Image(getClass().getResourceAsStream("ladybug.png"));
			ladybug.setFill(new ImagePattern(image));
			//circles.add(ladybug);
			root.getChildren().add(ladybug);		
		}
	}	
	
	public void randomBee() {
		int randomAmount = 1 + (int)(Math.random()*((width*height)/((width*height)/14)));
		Bee bee;
		for(int i = 1; i <= randomAmount; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			bee = new Bee(randomX, randomY, 10);
			Image image = new Image(getClass().getResourceAsStream("bee.png"));
			bee.setFill(new ImagePattern(image));
			root.getChildren().add(bee);		
		}
	}	

	public void randomButterfly() {
		int randomAmount = 1 + (int)(Math.random()*((width*height)/((width*height)/6)));
		Butterfly butterfly;
		for(int i = 1; i <= randomAmount; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			butterfly = new Butterfly(randomX, randomY, 14);
			Image image = new Image(getClass().getResourceAsStream("butterfly.png"));
			butterfly.setFill(new ImagePattern(image));
			root.getChildren().add(butterfly);		
		}
	}
	
	public void randomDragonfly() {
		int randomAmount = 1 + (int)(Math.random()*3);
		Dragonfly dragonfly;
		for(int i = 1; i <= randomAmount; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			dragonfly = new Dragonfly(randomX, randomY, 14);
			Image image = new Image(getClass().getResourceAsStream("dragonfly.png"));
			dragonfly.setFill(new ImagePattern(image));
			root.getChildren().add(dragonfly);		
		}
	}	
	
	public void randomPlants() {
		/* Random Generic Plants */
		int randomAmount = 5 + (int)(Math.random()*((width*height)/((width*height)/70)));
		Plant plant;
		for(int i = 0; i <= randomAmount; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));
			//int randomSize = 10;
//			if(randomSize <= 0) {
//				randomSize = 1;
//			}
			plant = new Plant(randomX, randomY, 15);
			Image image = new Image(getClass().getResourceAsStream("grass.png"));
			plant.setFill(new ImagePattern(image));			
			
			//plants.add(plant);
			root.getChildren().add(plant);		
		}
	}		
	
	public void randomObstacles() {
		/* Random Generic Stones */
		int randomAmount = 2 + (int)(Math.random()* 3);
		Stone stone;
		for(int i = 0; i <= randomAmount; i++) {
			int randomX = (int)(Math.random()*(width));
			int randomY = (int)(Math.random()*(height));

			stone = new Stone(randomX, randomY, 18);
			Image image = new Image(getClass().getResourceAsStream("stone.png"));
			stone.setFill(new ImagePattern(image));			

			root.getChildren().add(stone);		
		}
	}			
	
	
	public void playButton(Timeline timeline) {
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				timeline.play();
			}
		});
	}

	public void pauseButton(Timeline timeline) {
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				timeline.pause();
			}
		});
	}
	
	public void stopButton(Timeline timeline) {
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				timeline.stop();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

}
