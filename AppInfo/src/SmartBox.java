import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SmartBox extends Application {

	private static final String S1 = "S1";

	private static final String S2 = "S2";

	private static final String S3 = "S3";

	String fontName = ApiModule.getPropertyValue("FONT_NAME");

	Label timerLabel = new Label();

	Label timerLabel2 = new Label();

	static Date dir = new java.util.Date(System.currentTimeMillis());

	int array[] = new int[0];

	int seconds = 59;

	int minutes = 14;

	int seconds2 = 59;

	int minutes2 = 14;

	static String format = "ddMMyyyy";

	static DateFormat dateFormatter = new SimpleDateFormat(format);

	static String date = dateFormatter.format(dir);

	int fontSize = Integer.parseInt(ApiModule.getPropertyValue("FONT_SIZE"));

	public Stage primaryStage;

	public StackPane root;

	public Scene scene;

	Alert errorAlert;

	GridPane grid = new GridPane();

	RadioButton r1 = new RadioButton("Pick Material");

	RadioButton r2 = new RadioButton("Fulfilment Material");

	Label scantext = null;

	Text PhoneNumber = null;

	Text text1 = null;

	Label bringParcel = null;

	String status = null;

	Label otpText = null;

	TextField scan = null;

	TextField otpScan = null;

	Label scantextProductCode = null;

	TextField scanProductCode = null;

	Button Delivery = new Button("Delivery");

	Button Fulfilment = new Button();

	Button forceClose = new Button("Force Close");

	Button enterButton = new Button("Enter");

	Button scanButton = new Button("Scan");

	Button enterButtonProductCode = new Button("Enter");

	Button scanButtonProductCode = new Button("Scan");

	Button homeButton = new Button("Home");

	Button helpButton = new Button("Help");

	String dcNumber = "";

	String deliverySoidNumber = "";

	String fulfillmentSoidNumber = "";

	String soNumber = "";

	Stage loadingStage;

	String otpNumber = "";

	String productCode = "";

	int wrongOtp = 0;

	int wrongOtp1 = 0;

	String barcode = "";

	DropShadow shadow = new DropShadow();

	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	static Date datelog = new Date();

	static String logDate = dateFormat.format(datelog);

	Timeline timeline;

	Timeline timeline2;
	HBox boHbox = null;
	HBox hbox = null;
	HBox delHbox1, delHbox2, delHbox3, delHbox4, thBox1, otpHbox1, otpHbox2, otpHbox3 = null;
	VBox vbox, otpVBox1 = null;
	final SceneProperty sp = new SceneProperty();

	private double scenCapWidth = 0;
	private double scenCapHeight = 0;

	VirtualKeyboard vkb;
	Button clearText, backButton, resendOtp = null;

	public static void main(String[] args) {
		try {
			File jardir = new File(SmartBox.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String jarpath = jardir.getParent();
			jarpath = "." + File.separator + "Logs";
			Files.createDirectories(Paths.get(jarpath));

			String baseDir = jarpath;

			String newDir = LogFile.createDateBasedDirectory(baseDir, dir);

			ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", "");
			ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
			ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
			ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
			ApiModule.setpropertyValue("ORDER_TYPE", "");
			ApiModule.setpropertyValue("FINAL_API_DATA", "");
			ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("ALREADY_SCANNED", "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		setLayout();
	}

	public void setLayout() {

		try {
			ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", "");
			ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
			ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
			ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
			ApiModule.setpropertyValue("ORDER_TYPE", "");
			ApiModule.setpropertyValue("FINAL_API_DATA", "");
			ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("ALREADY_SCANNED", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sp.setSceneName(S1);
		BorderPane borderPane = new BorderPane();
		StackPane root = new StackPane(borderPane);
		root.setStyle("-fx-background-image: url(\"Picture3.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");
		Scene scene;
		if (scenCapHeight > 0 && scenCapWidth > 0) {
			scene = new Scene(root, scenCapWidth, scenCapHeight);
		} else {
			scene = new Scene(root);
		}
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		sp.setSceneWidth(bounds.getWidth());
		sp.setSceneHeight(bounds.getHeight());
		primaryStage.setTitle("Smart Box");
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		text1 = new Text("YCH SMART LOCKER");
		text1.setFill(Color.WHITE);
		text1.setTextAlignment(TextAlignment.CENTER);
		text1.setStroke(Color.BLUE);
		HBox tHbox = new HBox();
		tHbox.getChildren().add(text1);
		tHbox.setAlignment(Pos.CENTER);
		borderPane.setTop(tHbox);

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStroke(Color.YELLOW);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		boHbox = new HBox();
		boHbox.getChildren().addAll(PhoneNumber, helpButton);
		BorderPane.setAlignment(boHbox, Pos.BOTTOM_CENTER);
		borderPane.setBottom(boHbox);

		hbox = new HBox();
		Delivery.textAlignmentProperty().set(TextAlignment.CENTER);
		Delivery.setStyle(
				"-fx-background-color: Orange;-fx-border-radius: 18;-fx-background-radius: 18;-fx-border-color: White; -fx-border-width: 2px;");
		Delivery.setEffect(shadow);
		shadow.setColor(Color.BLUE);
		Delivery.setTextFill(Color.WHITE);
		Delivery.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				deliveryMaterial(scene.getWidth(), scene.getHeight());
			}
		});
		// Fulfilment material content in fx
		Fulfilment.setText("Fulfilment");
		Fulfilment.textAlignmentProperty().set(TextAlignment.CENTER);
		Fulfilment.setStyle("-fx-background-color: Orange");
		Fulfilment.setTextFill(Color.WHITE);
		Fulfilment.setStyle(
				"-fx-background-color: Orange;-fx-border-radius: 18;-fx-background-radius: 18;-fx-border-color: White; -fx-border-width: 2px;");
		Fulfilment.setEffect(shadow);
		shadow.setColor(Color.BLUE);
		Fulfilment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FulfillmentMaterial(scene.getWidth(), scene.getHeight());
			}
		});
		hbox.getChildren().addAll(Delivery, Fulfilment);
		hbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(hbox, Pos.TOP_CENTER);
		borderPane.setCenter(hbox);
		enterButton.setStyle("-fx-background-color: Green");
		scanButton.setStyle("-fx-background-color: Orange");
		enterButton.setTextFill(Color.WHITE);
		scanButton.setTextFill(Color.WHITE);

		primaryStage.setMaximized(Boolean.TRUE);
		if (sp.getSceneName().equalsIgnoreCase(S1)) {
			updateComponentBasedOnWidth(sp);
		}
		if (sp.getSceneName().equalsIgnoreCase(S1)) {
			updateComponentBasedOnHeight(sp);
		}
		// primaryStage.setFullScreen(Boolean.TRUE);
		primaryStage.setScene(scene);
		primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				System.out.println(newSceneWidth);
				sp.setSceneWidth((Double) newSceneWidth);
				if (sp.getSceneName().equalsIgnoreCase(S1)) {
					updateComponentBasedOnWidth(sp);
				}
				if (sp.getSceneName().equalsIgnoreCase(S2)) {
					updateDelCompBasedOnWidth(sp);
				}
				if (sp.getSceneName().equalsIgnoreCase(S3)) {
					updateOtpCompBasedOnWidth(sp);
				}
			}
		});
		primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				sp.setSceneHeight((Double) newSceneHeight);
				System.out.println(newSceneHeight);
				if (sp.getSceneName().equalsIgnoreCase(S1)) {
					updateComponentBasedOnHeight(sp);
				}
				if (sp.getSceneName().equalsIgnoreCase(S2)) {
					updateDelCompBasedOnHeight(sp);
				}
				if (sp.getSceneName().equalsIgnoreCase(S3)) {
					updateOtpCompBasedOnHeight(sp);
				}
			}
		});
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	protected void updateComponentBasedOnHeight(SceneProperty sp) {
		Delivery.setPrefHeight(getWidthPercentage(30, sp.getSceneHeight()));
		Fulfilment.setPrefHeight(getWidthPercentage(30, sp.getSceneHeight()));
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
	}

	protected void updateComponentBasedOnWidth(SceneProperty sp) {
		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		text1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(4, sp.getSceneWidth())));
		PhoneNumber.setTranslateX(getWidthPercentage(18, sp.getSceneWidth()));
		boHbox.setSpacing(getWidthPercentage(70, sp.getSceneWidth()));
		hbox.setSpacing(getWidthPercentage(22, sp.getSceneWidth()));
		Delivery.setPrefWidth(getWidthPercentage(20, sp.getSceneWidth()));
		Delivery.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
		Fulfilment.setPrefWidth(getWidthPercentage(20, sp.getSceneWidth()));
		Fulfilment.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
	}

	private double getWidthPercentage(double p, double width) {
		return Math.round(p * width / 100);
	}

	// Delivery material Scene in javafx

	public void deliveryMaterial(double sWidth, double sHeight) {

		String LOG_DELIVERY_SO_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_FAILURE");
		String LOG_DELIVERY_SO_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_SUCCESS");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);
		sp.setSceneName(S2);
		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");
		HBox tHbox = new HBox();
		tHbox.getChildren().add(homeButton);
		tHbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(homeButton);
		scenCapWidth = sWidth;
		scenCapHeight = sHeight;
		homeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				setLayout();
			}
		});
		scantext = new Label("SO # :");
		scantext.setTextFill(Color.WHITE);
		scan = new TextField();
		scan.requestFocus();
		scan.setFocusTraversable(Boolean.TRUE);
		scan.setPromptText("Enter/Scan SO Number:");
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		scan.setOnKeyReleased(eventAc -> {
			if (scan.getText().length() <= 20) {
				if (eventAc.getCode() == KeyCode.ENTER) {
					soNumber = scan.getText();
					try {

						String result = ApiModule.DeliveryValidate(soNumber);
						JSONObject resultjson = new JSONObject(result);
						status = resultjson.getString("status");

					} catch (IOException e) {
						e.printStackTrace();
					}
					if (status.equalsIgnoreCase("FAILURE")) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SO_NUMBER_FAILURE);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Admin Msg: Work Not Done");
						errorAlert.setContentText("Invalid So Number");
						errorAlert.showAndWait();
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SO_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						deliveryOtpLayout(soNumber, sp.getSceneWidth(), sp.getSceneHeight());
					}
				}

			}

		});
		enterButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (scan.getText().length() <= 20) {
					soNumber = scan.getText();
					try {

						String result = ApiModule.DeliveryValidate(soNumber);
						JSONObject resultjson = new JSONObject(result);
						status = resultjson.getString("status");

					} catch (IOException ex) {
						ex.printStackTrace();
					}
					if (status.equalsIgnoreCase("FAILURE")) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SO_NUMBER_FAILURE);
						} catch (Exception ex) {

							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Admin Msg: Work Not Done");
						errorAlert.setContentText("Wrong Part Scanned, Please try again or wrong Barcode Scanned.");
						errorAlert.showAndWait();
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SO_NUMBER_SUCCESS);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						deliveryOtpLayout(soNumber, sp.getSceneWidth(), sp.getSceneHeight());
					}
				}

			}
		});
		scanButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "/s " + LOG_DELIVERY_SO_NUMBER_FAILURE);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					scan.setText(barcodeScanner.getBarcodetext());
					try {
						LogFile.logfile(logDate + "/s " + LOG_DELIVERY_SO_NUMBER_SUCCESS);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});
		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);
		vkb = new VirtualKeyboard(getWidthPercentage(4, sp.getSceneWidth()),
				getWidthPercentage(5, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");

		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		vbox = new VBox();
		vbox.getChildren().addAll(delHbox1, delHbox3);
		vbox.setAlignment(Pos.CENTER);
		delHbox2 = new HBox();
		delHbox2.getChildren().add(vbox);
		delHbox2.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(delHbox2, Pos.CENTER);
		borderPane.setCenter(delHbox2);
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);
		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStroke(Color.YELLOW);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");
		delHbox4 = new HBox();
		delHbox4.getChildren().addAll(PhoneNumber, helpButton);
		BorderPane.setAlignment(delHbox4, Pos.BOTTOM_CENTER);
		borderPane.setBottom(delHbox4);

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sWidth, sHeight);
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");
		updateDelCompBasedOnWidth(sp);
		updateDelCompBasedOnHeight(sp);
		primaryStage.setScene(scene);
	}

	protected void updateDelCompBasedOnWidth(SceneProperty sp) {
		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
		delHbox1.setSpacing(getWidthPercentage(2, sp.getSceneWidth()));
		scan.setPrefWidth(getWidthPercentage(21, sp.getSceneWidth()));
		scan.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1, sp.getSceneWidth())));
		enterButton.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		scanButton.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		enterButton.setPrefWidth(getWidthPercentage(10, sp.getSceneWidth()));
		scanButton.setPrefWidth(getWidthPercentage(10, sp.getSceneWidth()));
		PhoneNumber.setTranslateX(getWidthPercentage(18, sp.getSceneWidth()));
		delHbox4.setSpacing(getWidthPercentage(70, sp.getSceneWidth()));
		vbox.setSpacing(getWidthPercentage(2.5, sp.getSceneWidth()));
		if (vkb != null) {
			VBox keyboardBox = (VBox) vkb.view();
			if (keyboardBox != null && keyboardBox.getChildren().size() > 0) {
				for (Node hboxNode : keyboardBox.getChildren()) {
					HBox kHbox = (HBox) hboxNode;
					for (Node node : kHbox.getChildren()) {
						if (node instanceof Button) {
							Button keysButton = (Button) node;
							keysButton.setPrefSize(getWidthPercentage(4, sp.getSceneWidth()),
									getWidthPercentage(5, sp.getSceneHeight()));
						}
					}
				}
			}
		}
	}

	protected void updateDelCompBasedOnHeight(SceneProperty sp) {
		enterButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));
		scanButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
	}

	private void countDown() {
		if (minutes == 0 && seconds == 0) {
			minutes = 14;
			seconds = 59;
			setLayout();
		}
		if (seconds == 0) {
			String timersec = String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
			// System.out.println(String.valueOf(minutes)+" : "+String.valueOf(seconds));
			timerLabel.setText(timersec);
			minutes--;
			seconds = 59;
		} else {
			String timersec = String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
			timerLabel.setText(timersec);
			seconds--;
		}

	}

	public void deliveryOtpLayout(String soNumber, double sWidth, double sHeight) {

		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event2 -> {
			countDown();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);
		sp.setSceneName(S3);
		BorderPane borderPane = new BorderPane();
		timerLabel.setTextFill(Color.WHITE);
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");
		thBox1 = new HBox();
		thBox1.getChildren().addAll(homeButton, timerLabel);
		thBox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(thBox1);
		homeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				minutes = 14;
				seconds = 59;
				timeline.stop();
				setLayout();

			}
		});

		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);
		otpHbox1 = new HBox();

		otpText = new Label("ENTER YOUR OTP!");
		otpText.setTextFill(Color.WHITE);
		otpScan = new TextField();
		otpScan.setPromptText("Enter OTP");

		clearText = new Button("X");
		clearText.setStyle("-fx-background-color: Orange");
		clearText.setTextFill(Color.WHITE);
		clearText.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					otpScan.setText(otpScan.getText().substring(0, otpScan.getText().length() - 1));
				} catch (Exception e) {
					Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);
					errorAlert1.setHeaderText("Admin Msg: No Data");
					errorAlert1.setContentText("Textfield is empty");
					errorAlert1.showAndWait();
				}

			}
		});
		otpScan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		enterButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String otp = otpScan.getText();
				if (otp.equals("")) {
					Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);
					errorAlert1.setHeaderText("Admin Msg: Invalid OTP");
					errorAlert1.setContentText("Please enter valid OTP");
					errorAlert1.showAndWait();
				} else {
					deliveryCheckOtp(soNumber, otp, sp.getSceneWidth(), sp.getSceneHeight());
				}
			}
		});
		otpHbox1.setAlignment(Pos.CENTER);
		otpHbox1.getChildren().addAll(otpScan, clearText, enterButton);

		Numpad np = new Numpad(getWidthPercentage(8, sp.getSceneWidth()), getWidthPercentage(4, sp.getSceneWidth()));
		np.view().setStyle("-fx-border-radius: 5;");

		otpHbox2 = new HBox();
		otpHbox2.setAlignment(Pos.CENTER);
		backButton = new Button();
		backButton.setText("<- Back");
		backButton.setStyle("-fx-background-color: Orange");
		backButton.setTextFill(Color.WHITE);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				minutes = 14;
				seconds = 59;
				timeline.stop();
				deliveryMaterial(sp.getSceneWidth(), sp.getSceneHeight());
			}
		});
		resendOtp = new Button();
		resendOtp.setText("Resend Otp");
		resendOtp.setStyle("-fx-background-color: Green");
		resendOtp.setTextFill(Color.WHITE);
		resendOtp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					String result = ApiModule.DeliveryValidate(soNumber);
					JSONObject resultjson = new JSONObject(result);
					status = resultjson.getString("status");
					if (status.equalsIgnoreCase("SUCCESS")) {
						Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);
						errorAlert1.setHeaderText("Admin Msg: Success");
						errorAlert1.setContentText("OTP Successfully resend");
						errorAlert1.showAndWait();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.YELLOW);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		otpHbox2.getChildren().addAll(backButton, resendOtp);
		otpVBox1 = new VBox();
		otpVBox1.getChildren().addAll(otpText, otpHbox1, np.view(), otpHbox2);
		otpVBox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(otpVBox1, Pos.TOP_CENTER);
		borderPane.setCenter(otpVBox1);

		otpHbox3 = new HBox();
		otpHbox3.getChildren().addAll(PhoneNumber, helpButton);
		otpHbox3.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(otpHbox3, Pos.BOTTOM_CENTER);
		borderPane.setBottom(otpHbox3);
		updateOtpCompBasedOnWidth(sp);
		updateOtpCompBasedOnHeight(sp);
		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sp.getSceneWidth(), sp.getSceneHeight());
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");
		primaryStage.setScene(scene);
	}

	protected void updateOtpCompBasedOnWidth(SceneProperty sp) {
		timerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		otpText.setFont(
				Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2, sp.getSceneWidth())));
		otpScan.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1, sp.getSceneWidth())));
		clearText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(1.5, sp.getSceneWidth())));
		backButton.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
		resendOtp.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
		timerLabel.setTranslateX(getWidthPercentage(35, sp.getSceneWidth()));
		otpHbox1.setSpacing(getWidthPercentage(1, sp.getSceneWidth()));
		otpScan.setPrefWidth(getWidthPercentage(12, sp.getSceneWidth()));
		otpHbox2.setSpacing(getWidthPercentage(37, sp.getSceneWidth()));
		otpVBox1.setSpacing(getWidthPercentage(1, sp.getSceneWidth()));
		PhoneNumber.setTranslateX(getWidthPercentage(18, sp.getSceneWidth()));
		otpHbox3.setSpacing(getWidthPercentage(70, sp.getSceneWidth()));
	}

	protected void updateOtpCompBasedOnHeight(SceneProperty sp) {
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
		otpScan.setPrefHeight(getWidthPercentage(5, sp.getSceneHeight()));
	}

	// Check deliveryCheckOtp

	private void deliveryCheckOtp(String soNumber, String otpNumber, double sWidth, double sHeight) {
		String LOG_DELIVERY_OTP_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_OTP_FAILURE");
		String LOG_DELIVERY_OTP_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_OTP_SUCCESS");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		JSONObject resultjson = null;
		try {

			String result = ApiModule.deliveryValidateOTP(soNumber, otpNumber);
			resultjson = new JSONObject(result);
			status = resultjson.getString("status");

			if (wrongOtp == 3) {
				wrongOtp = 0;
				setLayout();
			}
			if (status.equalsIgnoreCase("FAILURE")) {
				wrongOtp++;
				try {

					LogFile.logfile(logDate + "\s " + LOG_DELIVERY_OTP_FAILURE);

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Admin Msg: OTP Invalid!");
				errorAlert.setContentText("Entered OTP is Invalid ..!, Please try again.");
				errorAlert.showAndWait();
			} else {
				try {
					LogFile.logfile(logDate + "\s " + LOG_DELIVERY_OTP_SUCCESS);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				minutes = 14;
				seconds = 59;
				ApiModule.setpropertyValue("OTP_DATA", resultjson.toString());
				ApiModule.setpropertyValue("CURRENT_SO_NUMBER", soNumber);
				ApiModule.setpropertyValue("ORDER_TYPE", "DELIVERY");
				int scanSize = ApiModule.getScanSize(resultjson.toString());
				ApiModule.setpropertyValue("DELIVERY_TOTAL_SOID", String.valueOf(scanSize));
				deliverySOID(sp.getSceneWidth(), sp.getSceneHeight());

			}
		} catch (IOException e) {
			try {

				LogFile.logfile(logDate + "\s " + LOG_DELIVERY_OTP_FAILURE);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Admin Msg: OTP Invalid!");
			errorAlert.setContentText("Entered OTP is Invalid ..!, Please try again.");
			errorAlert.showAndWait();
			e.printStackTrace();
		}

	}

	public void deliverySOID(double sWidth, double sHeight) {

		String LOG_DELIVERY_SOID_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SOID_NUMBER_FAILURE");
		String LOG_DELIVERY_SOID_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SOID_NUMBER_SUCCESS");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		String deliverytotalSOID = "";
		String deliveryscannedSOID = "";
		Label deliveryscannedSOIDtext;
		Label deliverytotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		HBox tHbox = new HBox();
		tHbox.getChildren().add(homeButton);
		tHbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(homeButton);
		scenCapWidth = sWidth;
		scenCapHeight = sHeight;

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		VBox SOIDvbox = new VBox();
		deliverytotalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
		deliveryscannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");

		deliverytotalSOIDtext = new Label("Total SOID: " + deliverytotalSOID);

		deliveryscannedSOIDtext = new Label("Scanned SOID: " + deliveryscannedSOID);

		deliverytotalSOIDtext.setTextFill(Color.WHITE);
		deliverytotalSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		deliverytotalSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));

		deliveryscannedSOIDtext.setTextFill(Color.WHITE);
		deliveryscannedSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		deliveryscannedSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));

		SOIDvbox.getChildren().addAll(deliverytotalSOIDtext, deliveryscannedSOIDtext);
		SOIDvbox.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(SOIDvbox, Pos.TOP_RIGHT);
		borderPane.setTop(SOIDvbox);

		scantext = new Label("SOID # :");
		scantext.setTextFill(Color.WHITE);

		scan = new TextField();
		scan.setPromptText("Enter/Scan SOID Number:");
		scan.requestFocus();
		scan.setFocusTraversable(Boolean.TRUE);
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (eventAc.getCode() == KeyCode.ENTER) {

				deliverySoidNumber = scan.getText();
				JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				String[] SOIDArray = ApiModule.toStringArray();
				if (Arrays.asList(SOIDArray).contains(deliverySoidNumber)) {
					try {

						LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);

					errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
					errorAlert.setContentText("SOID is already Entered!");
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, deliverySoidNumber);
					if (isSoidValid) {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {

							ex.printStackTrace();
						}

						SOIDArray = Arrays.copyOf(SOIDArray, SOIDArray.length + 1);
						SOIDArray[SOIDArray.length - 1] = deliverySoidNumber; // Assign 40 to the last element
						String fs = "";
						for (int i = 0; i < SOIDArray.length; i++) {
							fs = fs + SOIDArray[i];
							fs = fs + " ";
						}
						try {
							ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						deliveryProductCode(deliverySoidNumber,sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
						errorAlert.setContentText("Invalid SOID, Please try again");
						errorAlert.showAndWait();
					}
				}
			}
		});

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				deliverySoidNumber = scan.getText();
				JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				String[] SOIDArray = ApiModule.toStringArray();

				if (Arrays.asList(SOIDArray).contains(deliverySoidNumber)) {
					try {

						LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);

					errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
					errorAlert.setContentText("SOID is already Entered!");
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, deliverySoidNumber);

					if (isSoidValid) {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						SOIDArray = Arrays.copyOf(SOIDArray, SOIDArray.length + 1);
						SOIDArray[SOIDArray.length - 1] = deliverySoidNumber; // Assign 40 to the last element

						String fs = "";
						for (int i = 0; i < SOIDArray.length; i++) {
							fs = fs + SOIDArray[i];
							fs = fs + " ";
						}

						try {
							ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						deliveryProductCode(deliverySoidNumber,sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
						errorAlert.setContentText("Invalid SOID, Please try again");
						errorAlert.showAndWait();
					}

				}
			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {

						LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_SUCCESS);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);
		vkb = new VirtualKeyboard(getWidthPercentage(4, sp.getSceneWidth()),
				getWidthPercentage(5, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");

		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		vbox = new VBox();
		vbox.getChildren().addAll(delHbox1, delHbox3);
		vbox.setAlignment(Pos.CENTER);
		delHbox2 = new HBox();
		delHbox2.getChildren().add(vbox);
		delHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(delHbox2, Pos.CENTER);
		borderPane.setCenter(delHbox2);

		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStroke(Color.YELLOW);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		delHbox4 = new HBox();
		delHbox4.getChildren().addAll(PhoneNumber, helpButton);
		BorderPane.setAlignment(delHbox4, Pos.BOTTOM_CENTER);
		borderPane.setBottom(delHbox4);

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sWidth, sHeight);
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");

		updateDelCompBasedOnWidth(sp);
		updateDelCompBasedOnHeight(sp);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

	}

	public void deliveryProductCode(String deliverySoidNumber,double sWidth,double sHeight) {

		String LOG_DELIVERY_PRODUCT_CODE_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_FAILURE");
		String LOG_DELIVERY_PRODUCT_CODE_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_SUCCESS");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);
		
		String deliverytotalSOID = "";
		String deliveryscannedSOID = "";
		Label deliveryscannedSOIDtext;
		Label deliverytotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");
		
		HBox tHbox = new HBox();
		tHbox.getChildren().add(homeButton);
		tHbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(homeButton);
		scenCapWidth = sWidth;
		scenCapHeight = sHeight;

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		VBox SOIDvbox = new VBox();		

		deliverytotalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
		deliveryscannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");

		deliverytotalSOIDtext = new Label("Total SOID: " + deliverytotalSOID);
		deliverytotalSOIDtext.setTextFill(Color.WHITE);
		deliverytotalSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		deliverytotalSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
		
		deliveryscannedSOIDtext = new Label("Scanned SOID: " + deliveryscannedSOID);
		deliveryscannedSOIDtext.setTextFill(Color.WHITE);
		deliveryscannedSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		deliveryscannedSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
		
		SOIDvbox.getChildren().addAll(deliverytotalSOIDtext, deliveryscannedSOIDtext);
		SOIDvbox.setAlignment(Pos.CENTER);


		Label SOIDscantext = new Label("SOID # :");
		SOIDscantext.setTextFill(Color.WHITE);
		SOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));
		SOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
		
		TextField SOIDscan = new TextField();
		SOIDscan.setPromptText("Enter/Scan SOID Number:");
		SOIDscan.setPrefWidth(getWidthPercentage(21, sp.getSceneWidth()));
		SOIDscan.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1, sp.getSceneWidth())));

		SOIDscan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		SOIDscan.setText(deliverySoidNumber);

		Button SOIDenterButton = new Button("Enter");
		SOIDenterButton.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		SOIDenterButton.setPrefWidth(getWidthPercentage(10, sp.getSceneWidth()));
		SOIDenterButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));	
		SOIDenterButton.setDisable(true);

		Button SOIDscanButton = new Button("Scan");
		SOIDscanButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));
		SOIDscanButton.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		SOIDscanButton.setPrefWidth(getWidthPercentage(10, sp.getSceneWidth()));
		SOIDscanButton.setDisable(true);

		scantext = new Label("PRODUCT CODE # :");
		scantext.setTextFill(Color.WHITE);
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		scan = new TextField();
		scan.setPromptText("Enter/Scan Product Code:");
		scan.requestFocus();
		scan.setFocusTraversable(Boolean.TRUE);
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (scan.getText().length() <= 20) {
				if (eventAc.getCode() == KeyCode.ENTER) {
					productCode = scan.getText();
					JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					boolean isProductCodeValid = ApiModule.validProductCode(jsonArray, deliverySoidNumber, productCode);
					if (isProductCodeValid) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						String lockname = ApiModule.getLockerName(jsonArray, deliverySoidNumber);
						LockerOpen.OpenLocker(lockname);
						LocalDateTime lock_open_time = LocalDateTime.now();
						DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
						String formattedlockopenDate = lock_open_time.format(myFormatObj);
						CaptureImage.captureImage();
						if (Validations.isValidConfirmMessage()) {
							LocalDateTime lock_close_time = LocalDateTime.now();
							DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
							String formattedlockcloseDate = lock_close_time.format(myFormatObj1);

							String[] Finalarray = ApiModule.toFinalApiStringArray();
							Finalarray = Arrays.copyOf(Finalarray, Finalarray.length + 1);
							String objData = deliverySoidNumber + "," + lockname + "," + productCode + ",SUCCESS,"
									+ formattedlockopenDate + "," + formattedlockcloseDate;
							Finalarray[Finalarray.length - 1] = objData; // Ass

							String fs = "";
							for (int i = 0; i < Finalarray.length; i++) {
								fs = fs + Finalarray[i];
								if (fs.length() != 0) {
									fs = fs + "$";
								}
							}

							try {
								ApiModule.setpropertyValue("FINAL_API_DATA", fs);
								String scannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");
								int scannedSOIDint = Integer.parseInt(scannedSOID);
								scannedSOIDint++;
								ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(scannedSOIDint));
								String totalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
								int totalSOIDint = Integer.parseInt(totalSOID);
								if (scannedSOIDint == totalSOIDint) {

									ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("DELIVERY_TOTAL_SOID", String.valueOf(0));
									try {
										LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);

									errorAlert1.setHeaderText("Admin Msg: Success");
									errorAlert1.setContentText("Successfully Scanned All SOID");
									errorAlert1.showAndWait();
									if (ApiModule.FinalApiCall()) {
										ApiModule.setpropertyValue("FINAL_API_DATA", "");
									}
									ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", "");
									ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
									ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
									ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
									ApiModule.setpropertyValue("ORDER_TYPE", "");

									setLayout();
								} else {

									deliverySOID(sp.getSceneWidth(), sp.getSceneHeight());
								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}
					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE);
							
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid Product Code");
						errorAlert.setContentText("Invalid Product Code..!, Please try again");
						errorAlert.showAndWait();
					}
				}

			} else {
				try {
					LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				Alert errorAlert = new Alert(AlertType.ERROR);

				errorAlert.setHeaderText("Admin Msg: Invalid Product Code");
				errorAlert.setContentText("Invalid, Please try again");
				errorAlert.showAndWait();

			}

		});


		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (scan.getText().length() <= 20) {

					productCode = scan.getText();
					JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					boolean isProductCodeValid = ApiModule.validProductCode(jsonArray, deliverySoidNumber, productCode);
					if (isProductCodeValid) {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_SUCCESS);
							
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						CaptureImage.captureImage();
						if (Validations.isValidConfirmMessage()) {
							String lockname = ApiModule.getLockerName(jsonArray, deliverySoidNumber);
							LockerOpen.OpenLocker(lockname);
							Date curr_date = new java.util.Date(System.currentTimeMillis());

							try {
								String scannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");
								int scannedSOIDint = Integer.parseInt(scannedSOID);
								scannedSOIDint++;
								ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(scannedSOIDint));
								String totalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
								int totalSOIDint = Integer.parseInt(totalSOID);
								if (scannedSOIDint == totalSOIDint) {
									ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("DELIVERY_TOTAL_SOID", String.valueOf(0));

									Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);

									errorAlert1.setHeaderText("Admin Msg: Success");
									errorAlert1.setContentText("Successfully Scanned All SOID");
									errorAlert1.showAndWait();
									ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
									setLayout();
								} else {

									deliverySOID(sp.getSceneWidth(), sp.getSceneHeight());
								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						} else {
							Validations.isValidConfirmMessage();
						}
					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid Product Code");
						errorAlert.setContentText("Invalid Product Code..!, Please try again");
						errorAlert.showAndWait();
					}
				}

			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {

						LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {

						LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});

		forceClose.setStyle("-fx-background-color: RED");
		forceClose.setTextFill(Color.WHITE);
		forceClose.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		
		forceClose.setPrefWidth(getWidthPercentage(13, sp.getSceneWidth()));
		forceClose.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));

		forceClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				try {
					ApiModule.ForceClose();
					setLayout();
				} catch (ParseException | IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		
		HBox delHbox0 = new HBox();
		delHbox0.getChildren().addAll(SOIDscantext, SOIDscan, SOIDenterButton, SOIDscanButton);
		delHbox0.setAlignment(Pos.CENTER);
		delHbox0.setSpacing(getWidthPercentage(2, sp.getSceneWidth()));

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton, forceClose);
		delHbox1.setAlignment(Pos.CENTER);

		vkb = new VirtualKeyboard(getWidthPercentage(4, sp.getSceneWidth()),
				getWidthPercentage(5, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		delHbox3.setAlignment(Pos.CENTER);


		vbox = new VBox();
		vbox.getChildren().addAll(delHbox0, delHbox1, delHbox3);
		vbox.setAlignment(Pos.CENTER);

		delHbox2 = new HBox();
		delHbox2.getChildren().add(vbox);
		delHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(delHbox2, Pos.CENTER);
		borderPane.setCenter(delHbox2);

		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStroke(Color.YELLOW);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");
		
		delHbox4 = new HBox();
		delHbox4.getChildren().addAll(PhoneNumber, helpButton);
		BorderPane.setAlignment(delHbox4, Pos.BOTTOM_CENTER);
		borderPane.setBottom(delHbox4);

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sWidth, sHeight);
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");

		updateDelCompBasedOnWidth(sp);
		updateDelCompBasedOnHeight(sp);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

	}

	// FulfilmentMaterial scene in javafx

	public void FulfillmentMaterial(double sWidth, double sHeight) {

		String LOG_FULFILLMENT_DC_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_DC_NUMBER_FAILURE");
		String LOG_FULFILLMENT_DC_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_DC_NUMBER_SUCCESS");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);
		sp.setSceneName(S2);

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");
		HBox tHbox = new HBox();
		tHbox.getChildren().add(homeButton);
		tHbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(homeButton);
		scenCapWidth = sWidth;
		scenCapHeight = sHeight;

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		scantext = new Label("DC # :");
		scantext.setTextFill(Color.WHITE);

		scan = new TextField();
		scan.requestFocus();
		scan.setFocusTraversable(Boolean.TRUE);
		scan.setPromptText("Enter/Scan DC Number:");
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (scan.getText().length() <= 20) {
				if (eventAc.getCode() == KeyCode.ENTER) {
					dcNumber = scan.getText();
					try {

						String result = ApiModule.RefillValidate(dcNumber);
						JSONObject resultjson = new JSONObject(result);
						status = resultjson.getString("status");

					} catch (IOException e) {
						e.printStackTrace();
					}
					if (status.equalsIgnoreCase("FAILURE")) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_DC_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Admin Msg: Work Not Done");
						errorAlert.setContentText("Wrong Part Scanned, Please try again or wrong Barcode Scanned.");
						errorAlert.showAndWait();
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_DC_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						fulfillmentOtpLayout(dcNumber, sp.getSceneWidth(), sp.getSceneHeight());
					}
				}

			}
		});

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (scan.getText().length() <= 20) {
					dcNumber = scan.getText();
					try {

						String result = ApiModule.RefillValidate(dcNumber);
						JSONObject resultjson = new JSONObject(result);
						status = resultjson.getString("status");

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if (status.equalsIgnoreCase("FAILURE")) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_DC_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Admin Msg: Work Not Done");
						errorAlert.setContentText("Wrong Part Scanned, Please try again or wrong Barcode Scanned.");
						errorAlert.showAndWait();
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_DC_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						fulfillmentOtpLayout(dcNumber, sp.getSceneWidth(), sp.getSceneHeight());
					}

				}
			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_DC_NUMBER_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_DC_NUMBER_SUCCESS);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();
			}
		});

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);
		vkb = new VirtualKeyboard(getWidthPercentage(4, sp.getSceneWidth()),
				getWidthPercentage(5, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");

		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		vbox = new VBox();
		vbox.getChildren().addAll(delHbox1, delHbox3);
		vbox.setAlignment(Pos.CENTER);

		delHbox2 = new HBox();
		delHbox2.getChildren().add(vbox);
		delHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(delHbox2, Pos.CENTER);
		borderPane.setCenter(delHbox2);
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStroke(Color.YELLOW);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		delHbox4 = new HBox();
		delHbox4.getChildren().addAll(PhoneNumber, helpButton);
		BorderPane.setAlignment(delHbox4, Pos.BOTTOM_CENTER);
		borderPane.setBottom(delHbox4);

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sWidth, sHeight);
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");
		updateDelCompBasedOnWidth(sp);
		updateDelCompBasedOnHeight(sp);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);

	}

	private void countDown2() {
		if (minutes2 == 0 && seconds2 == 0) {
			minutes2 = 14;
			seconds2 = 59;
			setLayout();
		}
		if (seconds2 == 0) {
			String timersec = String.format("%02d", minutes2) + " : " + String.format("%02d", seconds2);
			// System.out.println(String.valueOf(minutes)+" : "+String.valueOf(seconds));
			timerLabel2.setText(timersec);
			minutes2--;
			seconds2 = 59;
		} else {
			String timersec = String.format("%02d", minutes2) + " : " + String.format("%02d", seconds2);
			timerLabel2.setText(timersec);
			seconds2--;
		}

	}

	public void fulfillmentOtpLayout(String dcNumber, double sWidth, double sHeight) {

		timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event2 -> {
			countDown2();
		}));
		timeline2.setCycleCount(Timeline.INDEFINITE);
		timeline2.play();

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		BorderPane borderPane = new BorderPane();
		timerLabel2.setTextFill(Color.WHITE);

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		thBox1 = new HBox();
		thBox1.getChildren().addAll(homeButton, timerLabel);
		thBox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(thBox1);

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				minutes2 = 14;
				seconds2 = 59;
				timeline2.stop();
				setLayout();
			}
		});

		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);
		otpHbox1 = new HBox();

		otpText = new Label("Enter your OTP!");
		otpText.setTextFill(Color.WHITE);

		otpScan = new TextField();
		otpScan.setPromptText("Enter OTP");

		clearText = new Button("X");
		clearText.setStyle("-fx-background-color: Orange");
		clearText.setTextFill(Color.WHITE);
		clearText.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					otpScan.setText(otpScan.getText().substring(0, otpScan.getText().length() - 1));
				} catch (Exception e) {
					Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);
					errorAlert1.setHeaderText("Admin Msg: No Data");
					errorAlert1.setContentText("Textfield is empty");
					errorAlert1.showAndWait();
				}

			}
		});

		otpScan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String otp = otpScan.getText();
				if (otp.equals("")) {
					Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);

					errorAlert1.setHeaderText("Admin Msg: Invalid OTP");
					errorAlert1.setContentText("Please enter valid OTP");
					errorAlert1.showAndWait();
				} else {
					fulfillmentCheckOtp(dcNumber, otp);
				}
			}
		});

		otpHbox1.setAlignment(Pos.CENTER);
		otpHbox1.getChildren().addAll(otpScan, clearText, enterButton);

		Numpad np = new Numpad(getWidthPercentage(8, sp.getSceneWidth()), getWidthPercentage(4, sp.getSceneWidth()));
		np.view().setStyle("-fx-border-radius: 5;");

		otpHbox2 = new HBox();
		otpHbox2.setAlignment(Pos.CENTER);

		backButton = new Button();
		backButton.setText("<- Back");
		backButton.setStyle("-fx-background-color: Orange");
		backButton.setTextFill(Color.WHITE);

		backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				minutes2 = 14;
				seconds2 = 59;
				timeline2.stop();
				FulfillmentMaterial(sp.getSceneWidth(), sp.getSceneHeight());
			}
		});

		resendOtp = new Button();

		resendOtp.setText("Resend Otp");
		resendOtp.setStyle("-fx-background-color: Green");
		resendOtp.setTextFill(Color.WHITE);

		resendOtp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {

					String result = ApiModule.RefillValidate(dcNumber);
					JSONObject resultjson = new JSONObject(result);
					status = resultjson.getString("status");
					if (status.equalsIgnoreCase("SUCCESS")) {
						Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);

						errorAlert1.setHeaderText("Admin Msg: Success");
						errorAlert1.setContentText("OTP Successfully resend");
						errorAlert1.showAndWait();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.YELLOW);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		otpHbox2.getChildren().addAll(backButton, resendOtp);
		otpVBox1 = new VBox();
		otpVBox1.getChildren().addAll(otpText, otpHbox1, np.view(), otpHbox2);
		otpVBox1.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(otpVBox1, Pos.TOP_CENTER);
		borderPane.setCenter(otpVBox1);

		otpHbox3 = new HBox();
		otpHbox3.getChildren().addAll(PhoneNumber, helpButton);
		otpHbox3.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(otpHbox3, Pos.BOTTOM_CENTER);
		borderPane.setBottom(otpHbox3);
		updateOtpCompBasedOnWidth(sp);
		updateOtpCompBasedOnHeight(sp);

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sp.getSceneWidth(), sp.getSceneHeight());
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);

	}

	private void fulfillmentCheckOtp(String dcNumber, String otpNumber) {

		String LOG_FULFILLMENT_OTP_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_OTP_FAILURE");
		String LOG_FULFILLMENT_OTP_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_OTP_SUCCESS");

		JSONObject resultjson = null;
		try {
			String result = ApiModule.RefillValidateOTP(dcNumber, otpNumber);

			resultjson = new JSONObject(result);
			status = resultjson.getString("status");

			if (wrongOtp1 == 3) {
				wrongOtp1 = 0;
				setLayout();
			}
			if (status.equals("FAILURE")) {
				try {
					LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_OTP_FAILURE);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				wrongOtp1++;
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Admin Msg: OTP Invalid!");
				errorAlert.setContentText("Entered OTP is Invalid ..!, Please try again.");
				errorAlert.showAndWait();
			} else {
				try {
					try {
						LogFile.logfile(logDate + "\s" + LOG_FULFILLMENT_OTP_SUCCESS);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					minutes2 = 14;
					seconds2 = 59;
					ApiModule.setpropertyValue("OTP_DATA", resultjson.toString());
					ApiModule.setpropertyValue("CURRENT_DC_NUMBER", dcNumber);
					ApiModule.setpropertyValue("ORDER_TYPE", "REFILL");
					int scanSize = ApiModule.getScanSize(resultjson.toString());
					ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(scanSize));
					fulfillmentSOID(sp.getSceneWidth(), sp.getSceneHeight());

				} catch (Exception e) {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_OTP_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: OTP Invalid!");
					errorAlert.setContentText("Entered OTP is Invalid ..!, Please try again.");
					errorAlert.showAndWait();

					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fulfillmentSOID(double sWidth, double sHeight) {

		String LOG_FULFILLMENT_SOID_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_SOID_NUMBER_FAILURE");
		String LOG_FULFILLMENT_SOID_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_SOID_NUMBER_SUCCESS");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		String fulfillmenttotalSOID = "";
		String fulfillmentscannedSOID = "";
		Label fulfillmentscannedSOIDtext;
		Label fulfillmenttotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		HBox tHbox = new HBox();
		tHbox.getChildren().add(homeButton);
		tHbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(homeButton);
		scenCapWidth = sWidth;
		scenCapHeight = sHeight;

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		VBox SOIDvbox = new VBox();
		fulfillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
		fulfillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");

		fulfillmenttotalSOIDtext = new Label("Total SOID: " + fulfillmenttotalSOID);
		fulfillmenttotalSOIDtext.setTextFill(Color.WHITE);
		fulfillmenttotalSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		fulfillmenttotalSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));

		fulfillmentscannedSOIDtext = new Label("Scanned SOID: " + fulfillmentscannedSOID);
		fulfillmentscannedSOIDtext.setTextFill(Color.WHITE);
		fulfillmentscannedSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		fulfillmentscannedSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));

		SOIDvbox.getChildren().addAll(fulfillmenttotalSOIDtext, fulfillmentscannedSOIDtext);
		SOIDvbox.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(SOIDvbox, Pos.TOP_RIGHT);
		borderPane.setTop(SOIDvbox);

		scantext = new Label("SOID # :");
		scantext.setTextFill(Color.WHITE);

		scan = new TextField();
		scan.setPromptText("Enter/Scan SOID Number:");
		scan.requestFocus();
		scan.setFocusTraversable(Boolean.TRUE);
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (eventAc.getCode() == KeyCode.ENTER) {

				fulfillmentSoidNumber = scan.getText();
				JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				String[] SOIDArray = ApiModule.toStringArray();

				if (Arrays.asList(SOIDArray).contains(fulfillmentSoidNumber)) {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);

					errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
					errorAlert.setContentText("SOID is already Entered!");
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, fulfillmentSoidNumber);
					if (isSoidValid) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						SOIDArray = Arrays.copyOf(SOIDArray, SOIDArray.length + 1);
						SOIDArray[SOIDArray.length - 1] = fulfillmentSoidNumber; // Assign 40 to the last element
						String fs = "";
						for (int i = 0; i < SOIDArray.length; i++) {
							fs = fs + SOIDArray[i];
							fs = fs + " ";
						}

						try {

							ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						fulfillmentProductCode(fulfillmentSoidNumber, sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
						errorAlert.setContentText("Invalid SOID, Please try again");
						errorAlert.showAndWait();
					}
				}
			}
		});

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				fulfillmentSoidNumber = scan.getText();
				JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				String[] SOIDArray = ApiModule.toStringArray();

				if (Arrays.asList(SOIDArray).contains(fulfillmentSoidNumber)) {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);

					errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
					errorAlert.setContentText("SOID is already Entered!");
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, fulfillmentSoidNumber);
					if (isSoidValid) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						SOIDArray = Arrays.copyOf(SOIDArray, SOIDArray.length + 1);
						SOIDArray[SOIDArray.length - 1] = fulfillmentSoidNumber; // Assign 40 to the last element

						String fs = "";
						for (int i = 0; i < SOIDArray.length; i++) {
							fs = fs + SOIDArray[i];
							fs = fs + " ";
						}

						try {
							ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						fulfillmentProductCode(fulfillmentSoidNumber, sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid SOID number");
						errorAlert.setContentText("Invalid SOID, Please try again");
						errorAlert.showAndWait();
					}
				}
			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);

		vkb = new VirtualKeyboard(getWidthPercentage(4, sp.getSceneWidth()),
				getWidthPercentage(5, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());

		vbox = new VBox();
		vbox.getChildren().addAll(delHbox1, delHbox3);
		vbox.setAlignment(Pos.CENTER);

		delHbox2 = new HBox();
		delHbox2.getChildren().add(vbox);
		delHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(delHbox2, Pos.CENTER);
		borderPane.setCenter(delHbox2);

		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStroke(Color.YELLOW);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		delHbox4 = new HBox();
		delHbox4.getChildren().addAll(PhoneNumber, helpButton);
		BorderPane.setAlignment(delHbox4, Pos.BOTTOM_CENTER);
		borderPane.setBottom(delHbox4);

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sWidth, sHeight);
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");

		updateDelCompBasedOnWidth(sp);
		updateDelCompBasedOnHeight(sp);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

	}

	public void fulfillmentProductCode(String fulSoidNumber, double sWidth, double sHeight) {

		String LOG_FULFILLMENT_PRODUCT_CODE_FAILURE = ApiModule
				.getPropertyValue("LOG_FULFILLMENT_PRODUCT_CODE_FAILURE");
		String LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS = ApiModule
				.getPropertyValue("LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		String fulfillmenttotalSOID = "";
		String fulfillmentscannedSOID = "";
		Label fulfillmentscannedSOIDtext;
		Label fulfillmenttotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		HBox tHbox = new HBox();
		tHbox.getChildren().add(homeButton);
		tHbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(homeButton, Pos.TOP_CENTER);
		borderPane.setTop(homeButton);
		scenCapWidth = sWidth;
		scenCapHeight = sHeight;

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		fulfillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
		fulfillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");

		VBox SOIDvbox = new VBox();
		fulfillmenttotalSOIDtext = new Label("Total SOID: " + fulfillmenttotalSOID);
		fulfillmenttotalSOIDtext.setTextFill(Color.WHITE);
		fulfillmenttotalSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		fulfillmenttotalSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));

		fulfillmentscannedSOIDtext = new Label("Scanned SOID: " + fulfillmentscannedSOID);
		fulfillmentscannedSOIDtext.setTextFill(Color.WHITE);
		fulfillmentscannedSOIDtext.setTranslateX(getWidthPercentage(14, sp.getSceneWidth()));
		fulfillmentscannedSOIDtext.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));

		SOIDvbox.getChildren().addAll(fulfillmenttotalSOIDtext, fulfillmentscannedSOIDtext);
		SOIDvbox.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(SOIDvbox, Pos.TOP_RIGHT);
		borderPane.setTop(SOIDvbox);

		Label fullSOIDscantext = new Label("SOID # :");
		fullSOIDscantext.setTextFill(Color.WHITE);
		fullSOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));
		fullSOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
		

		TextField fullSOIDscan = new TextField();
		fullSOIDscan.setPromptText("Enter/Scan SOID Number:");
		fullSOIDscan.setPrefWidth(getWidthPercentage(21, sp.getSceneWidth()));
		fullSOIDscan.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1, sp.getSceneWidth())));
		
		fullSOIDscan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		fullSOIDscan.setText(fulSoidNumber);

		Button fullSOIDenterButton = new Button("Enter");
		fullSOIDenterButton.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		fullSOIDenterButton.setPrefWidth(getWidthPercentage(10, sp.getSceneWidth()));
		fullSOIDenterButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));	
		fullSOIDenterButton.setDisable(true);
	

		Button fullSOIDscanButton = new Button("Scan");
		fullSOIDscanButton.setDisable(true);
		fullSOIDscanButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));
		fullSOIDscanButton.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		fullSOIDscanButton.setPrefWidth(getWidthPercentage(10, sp.getSceneWidth()));


		scantext = new Label("PRODUCT CODE # :");
		scantext.setTextFill(Color.WHITE);

		scan = new TextField();
		scan.setPromptText("Enter/Scan SOID Number:");
		scan.requestFocus();
		scan.setFocusTraversable(Boolean.TRUE);
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (scan.getText().length() <= 20) {
				if (eventAc.getCode() == KeyCode.ENTER) {
					productCode = scan.getText();
					JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					boolean isProductCodeValid = ApiModule.validProductCode(jsonArray, fulSoidNumber, productCode);
					if (isProductCodeValid) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						String lockname = ApiModule.getLockerName(jsonArray, fulSoidNumber);

						LockerOpen.OpenLocker(lockname);

						LocalDateTime lock_open_time = LocalDateTime.now();
						DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
						String formattedlockopenDate = lock_open_time.format(myFormatObj);
						CaptureImage.captureImage();
						if (Validations.isValidConfirmMessage()) {
							LocalDateTime lock_close_time = LocalDateTime.now();
							DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
							String formattedlockcloseDate = lock_close_time.format(myFormatObj1);

							String[] Finalarray = ApiModule.toFinalApiStringArray();
							Finalarray = Arrays.copyOf(Finalarray, Finalarray.length + 1);
							String objData = fulSoidNumber + "," + lockname + "," + productCode + ",SUCCESS,"
									+ formattedlockopenDate + "," + formattedlockcloseDate;
							Finalarray[Finalarray.length - 1] = objData; // Ass

							String fs = "";
							for (int i = 0; i < Finalarray.length; i++) {
								fs = fs + Finalarray[i];
								if (fs.length() != 0) {
									fs = fs + "$";
								}
							}

							try {
								ApiModule.setpropertyValue("FINAL_API_DATA", fs);
								String fulillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");
								int fulfillmentscannedSOIDint = Integer.parseInt(fulillmentscannedSOID);
								fulfillmentscannedSOIDint++;
								ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID",
										String.valueOf(fulfillmentscannedSOIDint));
								String fulillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
								int fulFillmenttotalSOIDint = Integer.parseInt(fulillmenttotalSOID);
								if (fulfillmentscannedSOIDint == fulFillmenttotalSOIDint) {
									ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));

									try {
										LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);

									errorAlert1.setHeaderText("Admin Msg: Success");
									errorAlert1.setContentText("Successfully Scanned All SOID");
									errorAlert1.showAndWait();
									if (ApiModule.FinalApiCall()) {
										ApiModule.setpropertyValue("FINAL_API_DATA", "");
									}
									ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", "");
									ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
									ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
									ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
									ApiModule.setpropertyValue("ORDER_TYPE", "");
									setLayout();
								} else {
									try {
										LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									fulfillmentSOID(sp.getSceneWidth(), sp.getSceneHeight());
								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						} else {

							Validations.isValidConfirmMessage();
						}
					} else {

						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid Product Code");
						errorAlert.setContentText("Invalid Product Code..!, Please try again");
						errorAlert.showAndWait();
					}
				}

			} else {
				try {
					LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Alert errorAlert = new Alert(AlertType.ERROR);

				errorAlert.setHeaderText("Admin Msg: Invalid Product Code");
				errorAlert.setContentText("Invalid, Please try again");
				errorAlert.showAndWait();

			}

		});

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (scan.getText().length() <= 20) {
					productCode = scan.getText();
					JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					boolean isProductCodeValid = ApiModule.validProductCode(jsonArray, fulSoidNumber, productCode);
					if (isProductCodeValid) {
						CaptureImage.captureImage();
						if (Validations.isValidConfirmMessage()) {
							String lockname = ApiModule.getLockerName(jsonArray, fulSoidNumber);
							LockerOpen.OpenLocker(lockname);
							Date curr_date = new java.util.Date(System.currentTimeMillis());
							try {

								String fulillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");

								int fulfillmentscannedSOIDint = Integer.parseInt(fulillmentscannedSOID);
								fulfillmentscannedSOIDint++;
								ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID",
										String.valueOf(fulfillmentscannedSOIDint));
								String fulillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
								int fulFillmenttotalSOIDint = Integer.parseInt(fulillmenttotalSOID);

								if (fulfillmentscannedSOIDint == fulFillmenttotalSOIDint) {
									ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));

									try {
										LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									Alert errorAlert1 = new Alert(AlertType.CONFIRMATION);

									errorAlert1.setHeaderText("Admin Msg: Success");
									errorAlert1.setContentText("Successfully Scanned All SOID");
									errorAlert1.showAndWait();
									setLayout();
								} else {
									try {
										LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									fulfillmentSOID(sp.getSceneWidth(), sp.getSceneHeight());
								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						} else {
							Validations.isValidConfirmMessage();
						}
					} else {

						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText("Admin Msg: Invalid Product Code");
						errorAlert.setContentText("Invalid Product Code..!, Please try again");
						errorAlert.showAndWait();
					}

				}
			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});

		forceClose.setStyle("-fx-background-color: RED");
		forceClose.setTextFill(Color.WHITE);
		forceClose.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));
		
		forceClose.setPrefWidth(getWidthPercentage(13, sp.getSceneWidth()));
		forceClose.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));

		forceClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				try {
					ApiModule.ForceClose();
					setLayout();
				} catch (ParseException | IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		HBox delHbox0 = new HBox();
		delHbox0.getChildren().addAll(fullSOIDscantext, fullSOIDscan, fullSOIDenterButton, fullSOIDscanButton);
		delHbox0.setAlignment(Pos.CENTER);
		delHbox0.setSpacing(getWidthPercentage(2, sp.getSceneWidth()));

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton, forceClose);
		delHbox1.setAlignment(Pos.CENTER);

		vkb = new VirtualKeyboard(getWidthPercentage(4, sp.getSceneWidth()),
				getWidthPercentage(5, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		delHbox3 = new HBox();
		delHbox3.setAlignment(Pos.CENTER);
		delHbox3.getChildren().add(vkb.view());

		vbox = new VBox();
		vbox.getChildren().addAll(delHbox0, delHbox1, delHbox3);
		vbox.setAlignment(Pos.CENTER);

		delHbox2 = new HBox();
		delHbox2.getChildren().add(vbox);
		delHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(delHbox2, Pos.CENTER);
		borderPane.setCenter(delHbox2);

		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setStroke(Color.YELLOW);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		delHbox4 = new HBox();
		delHbox4.getChildren().addAll(PhoneNumber, helpButton);
		BorderPane.setAlignment(delHbox4, Pos.BOTTOM_CENTER);
		borderPane.setBottom(delHbox4);

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, sWidth, sHeight);
		root2.setStyle("-fx-background-image: url(\"Picture4.png\"); -fx-background-size: cover;-fx-opacity: 0.8;");

		updateDelCompBasedOnWidth(sp);
		updateDelCompBasedOnHeight(sp);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

	}

}