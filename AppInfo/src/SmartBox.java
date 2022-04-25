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
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fazecast.jSerialComm.SerialPort;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
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

	static String fontName = ApiModule.getPropertyValue("FONT_NAME");

	static Label timerLabel;

	static Label timerLabel2;
	static String formattedlockOpenDate;

	static Date dir = new java.util.Date(System.currentTimeMillis());

	int array[] = new int[0];

	static int seconds = 59;

	static int minutes = 14;

	static int seconds2 = 59;

	static int minutes2 = 14;

	static String format = "ddMMyyyy";

	static DateFormat dateFormatter = new SimpleDateFormat(format);

	static String date = dateFormatter.format(dir);

	static int fontSize = Integer.parseInt(ApiModule.getPropertyValue("FONT_SIZE"));

	public static Stage primaryStage;

	public StackPane root;

	public Scene scene;

	Alert errorAlert;

	static Label scantext = null;

	static Text PhoneNumber = null;

	static Text text1 = null;

	static String status = null;

	static Label otpText = null;

	static TextField scan = null;

	static TextField otpScan = null;

	Label scantextProductCode = null;

	TextField scanProductCode = null;

	static Button Delivery;

	static Button Fulfilment;

	static Button forceClose;

	static Button enterButton;

	static Button scanButton;

	Button enterButtonProductCode = new Button("Enter");

	Button scanButtonProductCode = new Button("Scan");

	static Button homeButton;

	static Button helpButton;

	static String dcNumber = "";

	static String deliverySoidNumber = "";

	static String fulfillmentSoidNumber = "";

	static String soNumber = "";

	Stage loadingStage;

	String otpNumber = "";

	static String productCode = "";

	static int wrongOtp = 0;

	static int wrongOtp1 = 0;

	String barcode = "";

	static DropShadow shadow = new DropShadow();

	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	static Date datelog = new Date();

	static String logDate = dateFormat.format(datelog);

	static Timeline timeline;

	static Timeline timeline2;
	static HBox boHbox = null;
	static HBox hbox = null;
	static HBox delHbox1;

	static HBox delHbox2;

	static HBox delHbox3;

	static HBox delHbox4;

	static HBox thBox1;

	static HBox otpHbox1;

	static HBox otpHbox2;

	static Label deliveryDoorNametext;

	static Label fulfillmentDoorNametext;

	static HBox otpHbox3 = null;

	static VBox vbox;

	static VBox otpVBox1 = null;
	final static SceneProperty sp = new SceneProperty();

	private static double scenCapWidth = 0;
	private static double scenCapHeight = 0;

	static VirtualKeyboard vkb;
	static Button clearText;

	static Button backButton;

	static Button resendOtp = null;

	public static void main(String[] args) {

		System.out.println("VERSION_DATED - 23/03/2022");

		try {
			File jardir = new File(SmartBox.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String jarpath = jardir.getParent();
			jarpath = "." + File.separator + "Logs";
			Files.createDirectories(Paths.get(jarpath));

			String baseDir = jarpath;

			String newDir = LogFile.createDateBasedDirectory(baseDir, dir);
			ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL", "0");

			ApiModule.setpropertyValue("LOCKER_OPEN_SIGNAL", "0");
			ApiModule.setpropertyValue("ALL_CURR_TRACK_NUMBER", "");
			ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
			ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
			ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
			ApiModule.setpropertyValue("ORDER_TYPE", "");
			ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("ALREADY_SCANNED", "");
			ApiModule.setpropertyValue("CURRENT_CODE", "");
			ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", "");
			ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", "");
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

	public static void setLayout() {

		Delivery = new Button("Delivery");

		Fulfilment = new Button("Fulfilment");

		forceClose = new Button("Force Close");

		enterButton = new Button("Enter");

		scanButton = new Button("Scan");

		homeButton = new Button("Home");

		helpButton = new Button("Help");

		try {

			ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL", "0");

			ApiModule.setpropertyValue("LOCKER_OPEN_SIGNAL", "0");

			ApiModule.setpropertyValue("ALL_CURR_TRACK_NUMBER", "");
			ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
			ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
			ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
			ApiModule.setpropertyValue("ORDER_TYPE", "");
			// ApiModule.setpropertyValue("FINAL_API_DATA", "");
			ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", "0");
			ApiModule.setpropertyValue("ALREADY_SCANNED", "");
			ApiModule.setpropertyValue("CURRENT_CODE", "");
			ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", "");
			ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", "");

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
		Delivery = new Button("Delivery");
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
		Fulfilment = new Button("Fulfilment");
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

	protected static void updateComponentBasedOnHeight(SceneProperty sp) {

		Delivery.setPrefHeight(getWidthPercentage(30, sp.getSceneHeight()));
		Fulfilment.setPrefHeight(getWidthPercentage(30, sp.getSceneHeight()));
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
	}

	protected static void updateComponentBasedOnWidth(SceneProperty sp) {
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

	private static double getWidthPercentage(double p, double width) {
		return Math.round(p * width / 100);
	}

	// Delivery material Scene in javafx

	public static void deliveryMaterial(double sWidth, double sHeight) {

		String LOG_DELIVERY_SO_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_FAILURE");
		String LOG_DELIVERY_SO_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_SUCCESS");
		String DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT");
		String DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT_HEADER");

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
		Platform.runLater(() -> scan.requestFocus());
		scan.setPromptText("Enter/Scan SO Number:");
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		scan.setOnKeyReleased(eventAc -> {

			if (scan.getText().length() <= 30) {
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
						errorAlert.setHeaderText(DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT);
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
				if (scan.getText().length() <= 30) {
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
						errorAlert.setHeaderText(DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT);
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

				SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("SCANNER_PORT")); // device name TODO:
																									// must be
				// changed

				sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
				sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

				sp.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED);
				
				if (sp.openPort()) {
					System.out.println("Scanner port is opened");
					byte[] b = "<<<A0035;>>>".getBytes();
					sp.writeBytes(b, 20);
					byte[] b1 = "A0035".getBytes();
					sp.writeBytes(b1, 20);
					byte[] b2 = "<<<!A0035;>>>".getBytes();
					sp.writeBytes(b2, 20);
					byte[] b3 = "<<<?A0035;>>>".getBytes();
					sp.writeBytes(b3, 20);
					byte[] b4 = "Start: <<<A0035;>>>".getBytes();
					sp.writeBytes(b4, 20);
					byte[] b5 = "<<<A0035; >>>".getBytes();
					sp.writeBytes(b5, 20);
					byte[] b6 = "<<<A 0035;>>>".getBytes();
					sp.writeBytes(b6, 20);
					byte[] b7 = "<<<A 0035; >>>".getBytes();
					sp.writeBytes(b7, 20);
					byte[] b8 = "<<< A0035; >>>".getBytes();
					sp.writeBytes(b8, 20);
					final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

					executor.submit(new Runnable() {
						@Override
						public void run() {
							Scanner ardout = new Scanner(sp.getInputStream());
							while (ardout.hasNextLine()) {
								String res_barcode = ardout.nextLine();

								System.out.println("barcode : " + res_barcode);
								scan.setText(res_barcode);
								byte[] bb = "<<<A0036;>>>".getBytes();
								sp.writeBytes(bb, 20);
								break;
							}

						}
					});

				}

				/*
				 * try { Thread.sleep(5000); } catch (InterruptedException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace(); } if
				 * (barcodeScanner.getBarcodetext().equals("noValue")) { try {
				 * LogFile.logfile(logDate + "/s " + LOG_DELIVERY_SO_NUMBER_FAILURE); } catch
				 * (Exception e1) { e1.printStackTrace(); } Alert errorAlert = new
				 * Alert(AlertType.ERROR);
				 * errorAlert.setHeaderText(DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT_HEADER);
				 * errorAlert.setContentText(DELIVERY_SO_NUMBER_FAILURE_ERROR_ALERT);
				 * errorAlert.showAndWait(); } else {
				 * scan.setText(barcodeScanner.getBarcodetext()); try { LogFile.logfile(logDate
				 * + "/s " + LOG_DELIVERY_SO_NUMBER_SUCCESS); } catch (Exception e1) {
				 * e1.printStackTrace(); } } String baseDir = "." + File.separator + "Logs";
				 */
				// File file = new File(baseDir + File.separator + "barcode.png");

				// file.delete();

			}
		});
		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);
		vkb = new VirtualKeyboard(getWidthPercentage(5, sp.getSceneWidth()),
				getWidthPercentage(6, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");

		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		delHbox3.setAlignment(Pos.CENTER);

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
		// delHbox4.setAlignment(Pos.CENTER);
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

	protected static void updateDelCompBasedOnWidth(SceneProperty sp) {
		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		scantext.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));
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
		delHbox3.setPrefWidth(getWidthPercentage(190, sp.getSceneHeight()));
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
							keysButton.setPrefSize(getWidthPercentage(6, sp.getSceneWidth()),
									getWidthPercentage(7, sp.getSceneHeight()));
						}
					}
				}
			}
		}
	}

	protected static void updateDelCompBasedOnHeight(SceneProperty sp) {
		enterButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));
		scanButton.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
	}

	private static void countDown() {
		timerLabel = new Label();
		if (minutes == 0 && seconds == 0) {
			minutes = 14;
			seconds = 59;
			setLayout();
		}
		if (seconds == 0) {
			String timersec = String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
			timerLabel.setText(timersec);
			minutes--;
			seconds = 59;
		} else {
			String timersec = String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
			timerLabel.setText(timersec);
			seconds--;
		}

	}

	public static void deliveryOtpLayout(String soNumber, double sWidth, double sHeight) {
		timerLabel = new Label();
		String DELIVERY_OTP_FAILURE_ERROR_ALERT = ApiModule.getPropertyValue("DELIVERY_OTP_FAILURE_ERROR_ALERT");
		String DELIVERY_OTP_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_OTP_FAILURE_ERROR_ALERT_HEADER");
		String DELIVERY_RESEND_OTP_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("DELIVERY_RESEND_OTP_FAILURE_ERROR_ALERT");
		String DELIVERY_RESEND_OTP_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_RESEND_OTP_FAILURE_ERROR_ALERT_HEADER");

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
		Platform.runLater(() -> otpScan.requestFocus());

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
					errorAlert1.setHeaderText(DELIVERY_OTP_FAILURE_ERROR_ALERT_HEADER);
					errorAlert1.setContentText(DELIVERY_OTP_FAILURE_ERROR_ALERT);
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
						errorAlert1.setHeaderText(DELIVERY_RESEND_OTP_FAILURE_ERROR_ALERT_HEADER);
						errorAlert1.setContentText(DELIVERY_RESEND_OTP_FAILURE_ERROR_ALERT);
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

	protected static void updateOtpCompBasedOnWidth(SceneProperty sp) {
		timerLabel = new Label();
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

	protected static void updateOtpCompBasedOnHeight(SceneProperty sp) {
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(3, sp.getSceneHeight())));
		otpScan.setPrefHeight(getWidthPercentage(5, sp.getSceneHeight()));
	}

	// Check deliveryCheckOtp

	private static void deliveryCheckOtp(String soNumber, String otpNumber, double sWidth, double sHeight) {
		String LOG_DELIVERY_OTP_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_OTP_FAILURE");
		String LOG_DELIVERY_OTP_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_OTP_SUCCESS");
		String DELIVERY_OTP_FAILURE_ERROR_ALERT = ApiModule.getPropertyValue("DELIVERY_OTP_FAILURE_ERROR_ALERT");
		String DELIVERY_OTP_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_OTP_FAILURE_ERROR_ALERT_HEADER");

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
				errorAlert.setHeaderText(DELIVERY_OTP_FAILURE_ERROR_ALERT_HEADER);
				errorAlert.setContentText(DELIVERY_OTP_FAILURE_ERROR_ALERT);
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
				String fs = "";
				JSONArray js = (JSONArray) resultjson.get("data");
				for (int i = 0; i < js.length(); i++) {
					JSONObject objects = js.getJSONObject(i);
					Iterator key = objects.keys();

					while (key.hasNext()) {
						String k = key.next().toString();
						if (k.equals("trackingNo")) {
							// System.out.println();
							String trnum = objects.getString(k);
							// System.out.println(trnum);
							fs = fs + trnum;
							fs = fs + " ";
							String[] SOIDArray = ApiModule.toStringArray();

							if (Arrays.asList(SOIDArray).contains(trnum)) {
								String scannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");
								int scannedSOIDint = Integer.parseInt(scannedSOID);
								scannedSOIDint++;
								ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(scannedSOIDint));
							}

						}
					}

				}

				ApiModule.setpropertyValue("ALL_CURR_TRACK_NUMBER", fs);

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
			errorAlert.setHeaderText(DELIVERY_OTP_FAILURE_ERROR_ALERT_HEADER);
			errorAlert.setContentText(DELIVERY_OTP_FAILURE_ERROR_ALERT);
			errorAlert.showAndWait();
			e.printStackTrace();
		}

	}

	public static void deliverySOID(double sWidth, double sHeight) {
		
		homeButton.setDisable(false);
		forceClose.setDisable(false);
		scan.setDisable(false);
		enterButton.setDisable(false);
		scanButton.setDisable(false);

		String LOG_DELIVERY_SOID_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SOID_NUMBER_FAILURE");
		String LOG_DELIVERY_SOID_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SOID_NUMBER_SUCCESS");
		String DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT");
		String DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER");
		String DELIVERY_SOID_NUMBER_ALREADY_ERROR_ALERT = ApiModule
				.getPropertyValue("DELIVERY_SOID_NUMBER_ALREADY_ERROR_ALERT");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		String deliverytotalSOID = "";
		String deliveryscannedSOID = "";
		Label deliveryscannedSOIDtext;
		Label deliverytotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		HBox tHbox1 = new HBox();

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

		tHbox1.getChildren().addAll(homeButton, SOIDvbox);

		HBox tHbox2 = new HBox();
		tHbox2.getChildren().add(tHbox1);
		tHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(tHbox2, Pos.CENTER);
		borderPane.setTop(tHbox2);
		scantext = new Label("SOID # :");
		scantext.setTextFill(Color.WHITE);

		scan = new TextField();
		scan.setPromptText("Enter/Scan SOID Number:");
		Platform.runLater(() -> scan.requestFocus());

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

					errorAlert.setHeaderText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
					errorAlert.setContentText(DELIVERY_SOID_NUMBER_ALREADY_ERROR_ALERT);
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, deliverySoidNumber);
					if (isSoidValid) {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {

							ex.printStackTrace();
						}

						deliveryProductCode(deliverySoidNumber, sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT);
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

					errorAlert.setHeaderText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
					errorAlert.setContentText(DELIVERY_SOID_NUMBER_ALREADY_ERROR_ALERT);
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, deliverySoidNumber);

					if (isSoidValid) {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
//						SOIDArray = Arrays.copyOf(SOIDArray, SOIDArray.length + 1);
//						SOIDArray[SOIDArray.length - 1] = deliverySoidNumber; // Assign 40 to the last element
//
//						String fs = "";
//						for (int i = 0; i < SOIDArray.length; i++) {
//							fs = fs + SOIDArray[i];
//							fs = fs + " ";
//						}
//
//						try {
//							ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs);
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}

						deliveryProductCode(deliverySoidNumber, sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT);
						errorAlert.showAndWait();
					}

				}
			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("SCANNER_PORT")); // device name TODO:
																									// must be
				// changed

				sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
				sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

				sp.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED);
				
				if (sp.openPort()) {
					System.out.println("Scanner port is opened");
					byte[] b = "<<<A0035;>>>".getBytes();
					sp.writeBytes(b, 20);
					byte[] b1 = "A0035".getBytes();
					sp.writeBytes(b1, 20);
					byte[] b2 = "<<<!A0035;>>>".getBytes();
					sp.writeBytes(b2, 20);
					byte[] b3 = "<<<?A0035;>>>".getBytes();
					sp.writeBytes(b3, 20);
					byte[] b4 = "Start: <<<A0035;>>>".getBytes();
					sp.writeBytes(b4, 20);
					byte[] b5 = "<<<A0035; >>>".getBytes();
					sp.writeBytes(b5, 20);
					byte[] b6 = "<<<A 0035;>>>".getBytes();
					sp.writeBytes(b6, 20);
					byte[] b7 = "<<<A 0035; >>>".getBytes();
					sp.writeBytes(b7, 20);
					byte[] b8 = "<<< A0035; >>>".getBytes();
					sp.writeBytes(b8, 20);
					final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

					executor.submit(new Runnable() {
						@Override
						public void run() {
							Scanner ardout = new Scanner(sp.getInputStream());
							while (ardout.hasNextLine()) {
								
								String res_barcode = ardout.nextLine();

								System.out.println("barcode : " + res_barcode);
								scan.setText(res_barcode);
								byte[] bb = "<<<A0036;>>>".getBytes();
								sp.writeBytes(bb, 20);
								break;
							}

						}
					});

				}

				/*
				 * try { Thread.sleep(5000); } catch (InterruptedException e1) {
				 * e1.printStackTrace(); } if
				 * (barcodeScanner.getBarcodetext().equals("noValue")) { try {
				 * LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_FAILURE); } catch
				 * (Exception ex) { ex.printStackTrace(); } Alert errorAlert = new
				 * Alert(AlertType.ERROR);
				 * errorAlert.setHeaderText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
				 * errorAlert.setContentText(DELIVERY_SOID_NUMBER_FAILURE_ERROR_ALERT);
				 * errorAlert.showAndWait(); } else { try {
				 * 
				 * LogFile.logfile(logDate + "\s " + LOG_DELIVERY_SOID_NUMBER_SUCCESS); } catch
				 * (Exception ex) { ex.printStackTrace(); }
				 * scan.setText(barcodeScanner.getBarcodetext()); } String baseDir = "." +
				 * File.separator + "Logs";
				 */
				// File file = new File(baseDir + File.separator + "barcode.png");

				// file.delete();

			}
		});

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);
		vkb = new VirtualKeyboard(getWidthPercentage(5, sp.getSceneWidth()),
				getWidthPercentage(6, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");

		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		delHbox3.setAlignment(Pos.CENTER);
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

	public static void deliveryProductCode(String deliverySoidNumber, double sWidth, double sHeight) {
		// deliveryDoorNametext.setText("");
		try {
			ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", "");
			ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String LOG_DELIVERY_PRODUCT_CODE_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_FAILURE");
		String LOG_DELIVERY_PRODUCT_CODE_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_SUCCESS");
		String DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT");
		String DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER");
		String DELIVERY_PRODUCT_CODE_SUCCESS_ALERT = ApiModule.getPropertyValue("DELIVERY_PRODUCT_CODE_SUCCESS_ALERT");
		String DELIVERY_PRODUCT_CODE_SUCCESS_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_PRODUCT_CODE_SUCCESS_ALERT_HEADER");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		String deliverytotalSOID = "";
		String deliveryscannedSOID = "";
		Label deliveryscannedSOIDtext;
		Label deliverytotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		HBox tHbox1 = new HBox();

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

		tHbox1.getChildren().addAll(homeButton, SOIDvbox);

		HBox tHbox2 = new HBox();
		tHbox2.getChildren().add(tHbox1);
		tHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(tHbox2, Pos.CENTER);
		borderPane.setTop(tHbox2);

		Label SOIDscantext = new Label("SOID # :");
		SOIDscantext.setTextFill(Color.WHITE);
		SOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));
		SOIDscantext.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));

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
		Platform.runLater(() -> scan.requestFocus());
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (scan.getText().length() <= 30) {
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
						String qty = ApiModule.getqty(jsonArray, deliverySoidNumber);
						String lockerOpenTime = "";
						int scannedSOIDint = 0;
						int totalSOIDint = 0;

						try {
							CaptureImage.captureImage();
						} catch (Exception ex) {
							System.out.println("Camera issue");
						}
						homeButton.setDisable(true);
						forceClose.setDisable(true);
						scan.setDisable(true);
						enterButton.setDisable(true);
						scanButton.setDisable(true);
						SOIDscan.setDisable(true);
						boolean lockerOpenTime1 = LockerOpen.OpenLocker(lockname);
						if (lockerOpenTime1) {
							LocalDateTime lock_open_time = LocalDateTime.now();
							DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
							formattedlockOpenDate = lock_open_time.format(myFormatObj1);
							// System.out.println("Locker Opened At :" + formattedlockOpenDate);

							try {
								ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", formattedlockOpenDate);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							// LockerOpen.OpenLockerConfirmation();

							System.out.println("Disabled :" + homeButton.isDisabled());

							String scannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");
							scannedSOIDint = Integer.parseInt(scannedSOID);
							scannedSOIDint++;
							try {
								ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(scannedSOIDint));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							String totalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
							totalSOIDint = Integer.parseInt(totalSOID);

							deliveryscannedSOIDtext.setText("");
							deliveryscannedSOIDtext.setText("Scanned SOID: " + String.valueOf(scannedSOIDint));
							deliveryDoorNametext.setText(lockname + " is Opened ");

						} else {
							System.out.println("Port is not opened!");

							// need to work;
						}

						// System.out.println("cve");
						Validations.isValidConfirmMessage(deliverySoidNumber, lockname, productCode, qty,
								scannedSOIDint, totalSOIDint);
						gotoSetLayout();
					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT);
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

				errorAlert.setHeaderText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER);
				errorAlert.setContentText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT);
				errorAlert.showAndWait();

			}

		});

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (scan.getText().length() <= 30) {

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
						String qty = ApiModule.getqty(jsonArray, deliverySoidNumber);

						int scannedSOIDint = 0;
						int totalSOIDint = 0;

						try {
							CaptureImage.captureImage();
						} catch (Exception ex) {
							System.out.println("Camera issue");
						}
						homeButton.setDisable(true);
						forceClose.setDisable(true);
						scan.setDisable(true);
						enterButton.setDisable(true);
						scanButton.setDisable(true);
						SOIDscan.setDisable(true);

						boolean lockerOpenTime1 = LockerOpen.OpenLocker(lockname);
						if (lockerOpenTime1) {

							LocalDateTime lock_open_time = LocalDateTime.now();
							DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
							formattedlockOpenDate = lock_open_time.format(myFormatObj1);
							// System.out.println("Locker Opened At :" + formattedlockOpenDate);

							try {
								ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", formattedlockOpenDate);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							// LockerOpen.OpenLockerConfirmation();

							System.out.println("Disabled :" + homeButton.isDisabled());

							String scannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");
							scannedSOIDint = Integer.parseInt(scannedSOID);
							scannedSOIDint++;
							try {
								ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(scannedSOIDint));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							String totalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
							totalSOIDint = Integer.parseInt(totalSOID);

							deliveryscannedSOIDtext.setText("");
							deliveryscannedSOIDtext.setText("Scanned SOID: " + String.valueOf(scannedSOIDint));

							deliveryDoorNametext.setText(lockname + " is Opened ");

						} else {
							System.out.println("Port is not opened!");
							// need to work;

						}

						// System.out.println("cve");
						Validations.isValidConfirmMessage(deliverySoidNumber, lockname, productCode, qty,
								scannedSOIDint, totalSOIDint);
						gotoSetLayout();

					} else {
						try {

							LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT);
						errorAlert.showAndWait();
					}
				}
			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("SCANNER_PORT")); // device name TODO:
																									// must be
				// changed

				sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
				sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

				sp.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED);
				if (sp.openPort()) {
					System.out.println("Scanner port is opened");
					byte[] b = "<<<A0035;>>>".getBytes();
					sp.writeBytes(b, 20);
					byte[] b1 = "A0035".getBytes();
					sp.writeBytes(b1, 20);
					byte[] b2 = "<<<!A0035;>>>".getBytes();
					sp.writeBytes(b2, 20);
					byte[] b3 = "<<<?A0035;>>>".getBytes();
					sp.writeBytes(b3, 20);
					byte[] b4 = "Start: <<<A0035;>>>".getBytes();
					sp.writeBytes(b4, 20);
					byte[] b5 = "<<<A0035; >>>".getBytes();
					sp.writeBytes(b5, 20);
					byte[] b6 = "<<<A 0035;>>>".getBytes();
					sp.writeBytes(b6, 20);
					byte[] b7 = "<<<A 0035; >>>".getBytes();
					sp.writeBytes(b7, 20);
					byte[] b8 = "<<< A0035; >>>".getBytes();
					sp.writeBytes(b8, 20);
					final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

					executor.submit(new Runnable() {
						@Override
						public void run() {
							Scanner ardout = new Scanner(sp.getInputStream());

							while (ardout.hasNextLine()) {
								String res_barcode = ardout.nextLine();

								System.out.println("barcode : " + res_barcode);
								scan.setText(res_barcode);
								byte[] bb = "<<<A0036;>>>".getBytes();
								sp.writeBytes(bb, 20);
								break;
							}

						}
					});

				}

				/*
				 * try { Thread.sleep(5000); } catch (InterruptedException e1) {
				 * e1.printStackTrace(); } if
				 * (barcodeScanner.getBarcodetext().equals("noValue")) { try {
				 * 
				 * LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE); } catch
				 * (Exception ex) { ex.printStackTrace(); } Alert errorAlert = new
				 * Alert(AlertType.ERROR);
				 * errorAlert.setHeaderText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER);
				 * errorAlert.setContentText(DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT);
				 * errorAlert.showAndWait(); } else { try {
				 * 
				 * LogFile.logfile(logDate + "\s " + LOG_DELIVERY_PRODUCT_CODE_FAILURE); } catch
				 * (Exception ex) { ex.printStackTrace(); }
				 * scan.setText(barcodeScanner.getBarcodetext()); } String baseDir = "." +
				 * File.separator + "Logs";
				 */
				// File file = new File(baseDir + File.separator + "barcode.png");

				// file.delete();

			}
		});

		forceClose.setStyle("-fx-background-color: RED");
		forceClose.setTextFill(Color.WHITE);
		forceClose.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));

		forceClose.setPrefWidth(getWidthPercentage(13, sp.getSceneWidth()));
		forceClose.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));

		forceClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				deliveryForceClose();

			}
		});

		HBox delHbox0 = new HBox();
		delHbox0.getChildren().addAll(SOIDscantext, SOIDscan, SOIDenterButton, SOIDscanButton);
		delHbox0.setAlignment(Pos.CENTER);
		delHbox0.setSpacing(getWidthPercentage(2, sp.getSceneWidth()));

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton, forceClose);
		delHbox1.setAlignment(Pos.CENTER);

		vkb = new VirtualKeyboard(getWidthPercentage(5, sp.getSceneWidth()),
				getWidthPercentage(6, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		delHbox3.setAlignment(Pos.CENTER);

		HBox delHbox5 = new HBox();

		deliveryDoorNametext = new Label("");
		deliveryDoorNametext.setTextFill(Color.WHITE);
		deliveryDoorNametext.setTextAlignment(TextAlignment.CENTER);
		deliveryDoorNametext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, 15));
		deliveryDoorNametext.setText("");
		delHbox5.getChildren().add(deliveryDoorNametext);
		delHbox5.setAlignment(Pos.CENTER);

		vbox = new VBox();
		vbox.getChildren().addAll(delHbox5, delHbox0, delHbox1, delHbox3);
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

	public static void gotoSetLayout() {

		final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

		executor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("gotosetLayout - run");
				while (true) {
					if (ApiModule.getPropertyValue("GLOBAL_ALL_CLOSE").equals("1")
							&& ApiModule.getPropertyValue("SUCCESS_FLAG").equals("1")) {
						try {
							System.out.println("gotosetLayout - run -- if");

							ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL", "0");
							ApiModule.setpropertyValue("GLOBAL_ALL_CLOSE", "0");
							ApiModule.setpropertyValue("SUCCESS_FLAG", "0");
							ApiModule.setpropertyValue("LOCKER_OPEN_SIGNAL", "0");
							Platform.runLater(() -> {
								setLayout();

							});
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		});

	}

	public static void gotodeliveryLayout() {

		try {
			ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL", "0");

			ApiModule.setpropertyValue("LOCKER_OPEN_SIGNAL", "0");
		} catch (Exception e) {

		}
		Platform.runLater(() -> {
			deliverySOID(sp.getSceneWidth(), sp.getSceneHeight());
		});
	}

	public static void gotofulfillmentLayout() {
		try {
			ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL", "0");

			ApiModule.setpropertyValue("LOCKER_OPEN_SIGNAL", "0");
		} catch (Exception e) {

		}
		Platform.runLater(() -> {
			fulfillmentSOID(sp.getSceneWidth(), sp.getSceneHeight());
		});
	}

	public static void deliveryForceClose() {

		Stage secondaryStage = new Stage();

		BorderPane borderPane = new BorderPane();

		VBox reasonvbox = new VBox(10);

		Label reasonLabel = new Label("Reason :");
		reasonLabel.setTextFill(Color.WHITE);
		reasonLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));

		ComboBox<String> comboBox = new ComboBox<>();
		ObservableList<String> items = FXCollections.observableArrayList();
		comboBox.setValue("---Select---");
		comboBox.setItems(items);
		comboBox.setPrefWidth(200);
		String[] Codes = ApiModule.getPropertyValue("ERROR_CODES").split(Pattern.quote("$"));

		for (int i = 0; i < Codes.length; i++) {
			items.add(Codes[i]);

		}

		reasonvbox.getChildren().addAll(reasonLabel, comboBox);
		reasonvbox.setAlignment(Pos.CENTER);

		borderPane.setCenter(reasonvbox);
		BorderPane.setAlignment(reasonvbox, Pos.CENTER);

		Button reasonbutton = new Button("Done");

		reasonbutton.setAlignment(Pos.CENTER);
		reasonbutton.setStyle("-fx-background-color: Orange");
		reasonbutton.setTextFill(Color.WHITE);
		reasonbutton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 18));

		reasonbutton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					String comboValue = comboBox.getValue();
					ApiModule.setpropertyValue("CURRENT_CODE", comboValue);
					ApiModule.ForceClose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				secondaryStage.close();
				setLayout();
			}

		});

		borderPane.setBottom(reasonbutton);
		BorderPane.setAlignment(reasonbutton, Pos.BOTTOM_CENTER);

		BorderPane.setMargin(reasonbutton, new Insets(12, 20, 12, 12));

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, 300, 150);
		root2.setStyle("-fx-background-size: cover;-fx-opacity: 0.8;-fx-background-color: blue");

		secondaryStage.setTitle("Force close");
		secondaryStage.setScene(scene);
		secondaryStage.show();
	}

	// FulfilmentMaterial scene in javafx

	public static void FulfillmentMaterial(double sWidth, double sHeight) {

		String LOG_FULFILLMENT_DC_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_DC_NUMBER_FAILURE");
		String LOG_FULFILLMENT_DC_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_DC_NUMBER_SUCCESS");
		String FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT");
		String FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT_HEADER");

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
		Platform.runLater(() -> scan.requestFocus());
		scan.setPromptText("Enter/Scan DC Number:");
		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (scan.getText().length() <= 30) {
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
						errorAlert.setHeaderText(FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT);
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
				if (scan.getText().length() <= 30) {
					dcNumber = scan.getText();
					try {

						String result = ApiModule.RefillValidate(dcNumber);
						System.out.println(result);
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
						errorAlert.setHeaderText(FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT);
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

				SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("SCANNER_PORT")); // device name TODO:
																									// must be
				// changed

				sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
				sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

				sp.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED);
				
				if (sp.openPort()) {
					System.out.println("Scanner port is opened");
					byte[] b = "<<<A0035;>>>".getBytes();
					sp.writeBytes(b, 20);
					byte[] b1 = "A0035".getBytes();
					sp.writeBytes(b1, 20);
					byte[] b2 = "<<<!A0035;>>>".getBytes();
					sp.writeBytes(b2, 20);
					byte[] b3 = "<<<?A0035;>>>".getBytes();
					sp.writeBytes(b3, 20);
					byte[] b4 = "Start: <<<A0035;>>>".getBytes();
					sp.writeBytes(b4, 20);
					byte[] b5 = "<<<A0035; >>>".getBytes();
					sp.writeBytes(b5, 20);
					byte[] b6 = "<<<A 0035;>>>".getBytes();
					sp.writeBytes(b6, 20);
					byte[] b7 = "<<<A 0035; >>>".getBytes();
					sp.writeBytes(b7, 20);
					byte[] b8 = "<<< A0035; >>>".getBytes();
					sp.writeBytes(b8, 20);
					final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

					executor.submit(new Runnable() {
						@Override
						public void run() {
							Scanner ardout = new Scanner(sp.getInputStream());
							while (ardout.hasNextLine()) {
								String res_barcode = ardout.nextLine();

								System.out.println("barcode : " + res_barcode);
								scan.setText(res_barcode);
								byte[] bb = "<<<A0036;>>>".getBytes();
								sp.writeBytes(bb, 20);
								break;
							}

						}
					});

				}

				/*
				 * try { Thread.sleep(5000); } catch (InterruptedException e1) {
				 * e1.printStackTrace(); } if
				 * (barcodeScanner.getBarcodetext().equals("noValue")) { try {
				 * LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_DC_NUMBER_FAILURE);
				 * 
				 * } catch (Exception ex) { ex.printStackTrace(); } Alert errorAlert = new
				 * Alert(AlertType.ERROR);
				 * errorAlert.setHeaderText(FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT_HEADER);
				 * errorAlert.setContentText(FULFILLMENT_DC_NUMBER_FAILURE_ERROR_ALERT);
				 * errorAlert.showAndWait(); } else { try { LogFile.logfile(logDate + "\s " +
				 * LOG_FULFILLMENT_DC_NUMBER_SUCCESS); } catch (Exception ex) {
				 * ex.printStackTrace(); } scan.setText(barcodeScanner.getBarcodetext()); }
				 * String baseDir = "." + File.separator + "Logs";
				 */
				// File file = new File(baseDir + File.separator + "barcode.png");

				// file.delete();
			}
		});

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);
		vkb = new VirtualKeyboard(getWidthPercentage(5, sp.getSceneWidth()),
				getWidthPercentage(6, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");

		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		delHbox3.setAlignment(Pos.CENTER);

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

	private static void countDown2() {
		timerLabel2 = new Label();
		if (minutes2 == 0 && seconds2 == 0) {
			minutes2 = 14;
			seconds2 = 59;
			setLayout();
		}
		if (seconds2 == 0) {
			String timersec = String.format("%02d", minutes2) + " : " + String.format("%02d", seconds2);
			timerLabel2.setText(timersec);
			minutes2--;
			seconds2 = 59;
		} else {
			String timersec = String.format("%02d", minutes2) + " : " + String.format("%02d", seconds2);
			timerLabel2.setText(timersec);
			seconds2--;
		}

	}

	public static void fulfillmentOtpLayout(String dcNumber, double sWidth, double sHeight) {

		String FULFILLMENT_OTP_FAILURE_ERROR_ALERT = ApiModule.getPropertyValue("FULFILLMENT_OTP_FAILURE_ERROR_ALERT");
		String FULFILLMENT_OTP_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("FULFILLMENT_OTP_FAILURE_ERROR_ALERT_HEADER");
		String FULFILLMENT_RESEND_OTP_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("FULFILLMENT_RESEND_OTP_FAILURE_ERROR_ALERT");
		String FULFILLMENT_RESEND_OTP_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("FULFILLMENT_RESEND_OTP_FAILURE_ERROR_ALERT_HEADER");

		timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event2 -> {
			countDown2();
		}));
		timeline2.setCycleCount(Timeline.INDEFINITE);
		timeline2.play();

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		BorderPane borderPane = new BorderPane();
		timerLabel2 = new Label();
		timerLabel2.setTextFill(Color.WHITE);
		timerLabel2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,
				getWidthPercentage(2, sp.getSceneWidth())));
		timerLabel2.setTranslateX(getWidthPercentage(35, sp.getSceneWidth()));

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		thBox1 = new HBox();
		thBox1.getChildren().addAll(homeButton, timerLabel2);
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
		Platform.runLater(() -> otpScan.requestFocus());

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

					errorAlert1.setHeaderText(FULFILLMENT_OTP_FAILURE_ERROR_ALERT_HEADER);
					errorAlert1.setContentText(FULFILLMENT_OTP_FAILURE_ERROR_ALERT);
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

						errorAlert1.setHeaderText(FULFILLMENT_RESEND_OTP_FAILURE_ERROR_ALERT_HEADER);
						errorAlert1.setContentText(FULFILLMENT_RESEND_OTP_FAILURE_ERROR_ALERT);
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

	@SuppressWarnings("null")
	private static void fulfillmentCheckOtp(String dcNumber, String otpNumber) {

		String FULFILLMENT_OTP_FAILURE_ERROR_ALERT = ApiModule.getPropertyValue("FULFILLMENT_OTP_FAILURE_ERROR_ALERT");
		String FULFILLMENT_OTP_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("FULFILLMENT_OTP_FAILURE_ERROR_ALERT_HEADER");

		String LOG_FULFILLMENT_OTP_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_OTP_FAILURE");
		String LOG_FULFILLMENT_OTP_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_OTP_SUCCESS");

		JSONObject resultjson = null;
		try {
			String result = ApiModule.RefillValidateOTP(dcNumber, otpNumber);

			resultjson = new JSONObject(result);
			status = resultjson.getString("status");
			// System.out.println(status);
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
				errorAlert.setHeaderText(FULFILLMENT_OTP_FAILURE_ERROR_ALERT_HEADER);
				errorAlert.setContentText(FULFILLMENT_OTP_FAILURE_ERROR_ALERT);
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
					String fs = "";
					JSONArray js = (JSONArray) resultjson.get("data");
					for (int i = 0; i < js.length(); i++) {
						JSONObject objects = js.getJSONObject(i);
						Iterator key = objects.keys();

						while (key.hasNext()) {
							String k = key.next().toString();
							if (k.equals("trackingNo")) {
								// System.out.println();
								String trnum = objects.getString(k);
								System.out.println(trnum);
								fs = fs + trnum;
								fs = fs + " ";
								String[] SOIDArray = ApiModule.toStringArray();

								if (Arrays.asList(SOIDArray).contains(trnum)) {
									String fulillmentscannedSOID = ApiModule
											.getPropertyValue("FULFILLMENT_SCANNED_SOID");
									int fulfillmentscannedSOIDint = Integer.parseInt(fulillmentscannedSOID);
									fulfillmentscannedSOIDint++;
									ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID",
											String.valueOf(fulfillmentscannedSOIDint));
								}

							}
						}

					}
					// System.out.println("here");

					ApiModule.setpropertyValue("ALL_CURR_TRACK_NUMBER", fs);
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
					errorAlert.setHeaderText(FULFILLMENT_OTP_FAILURE_ERROR_ALERT_HEADER);
					errorAlert.setContentText(FULFILLMENT_OTP_FAILURE_ERROR_ALERT);
					errorAlert.showAndWait();

					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void fulfillmentSOID(double sWidth, double sHeight) {
		
		homeButton.setDisable(false);
		forceClose.setDisable(false);
		scan.setDisable(false);
		enterButton.setDisable(false);
		scanButton.setDisable(false);

		String LOG_FULFILLMENT_SOID_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_SOID_NUMBER_FAILURE");
		String LOG_FULFILLMENT_SOID_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_SOID_NUMBER_SUCCESS");
		String FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT");
		String FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER");
		String FULFILLMENT_SOID_NUMBER_ALREADY_ERROR_ALERT = ApiModule
				.getPropertyValue("FULFILLMENT_SOID_NUMBER_ALREADY_ERROR_ALERT");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		String fulfillmenttotalSOID = "";
		String fulfillmentscannedSOID = "";
		Label fulfillmentscannedSOIDtext;
		Label fulfillmenttotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		HBox tHbox1 = new HBox();

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

		tHbox1.getChildren().addAll(homeButton, SOIDvbox);

		HBox tHbox2 = new HBox();
		tHbox2.getChildren().add(tHbox1);
		tHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(tHbox2, Pos.CENTER);
		borderPane.setTop(tHbox2);

		scantext = new Label("SOID # :");
		scantext.setTextFill(Color.WHITE);

		scan = new TextField();
		scan.setPromptText("Enter/Scan SOID Number:");
		Platform.runLater(() -> scan.requestFocus());

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

					errorAlert.setHeaderText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
					errorAlert.setContentText(FULFILLMENT_SOID_NUMBER_ALREADY_ERROR_ALERT);
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, fulfillmentSoidNumber);
					if (isSoidValid) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						fulfillmentProductCode(fulfillmentSoidNumber, sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT);
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

					errorAlert.setHeaderText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
					errorAlert.setContentText(FULFILLMENT_SOID_NUMBER_ALREADY_ERROR_ALERT);
					errorAlert.showAndWait();

				} else {
					boolean isSoidValid = ApiModule.validDC(jsonArray, fulfillmentSoidNumber);
					if (isSoidValid) {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						fulfillmentProductCode(fulfillmentSoidNumber, sp.getSceneWidth(), sp.getSceneHeight());
					} else {
						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT);
						errorAlert.showAndWait();
					}
				}
			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("SCANNER_PORT")); // device name TODO:
																									// must be
				// changed

				sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
				sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

				sp.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED);
				if (sp.openPort()) {
					System.out.println("Scanner port is opened");
					byte[] b = "<<<A0035;>>>".getBytes();
					sp.writeBytes(b, 20);
					byte[] b1 = "A0035".getBytes();
					sp.writeBytes(b1, 20);
					byte[] b2 = "<<<!A0035;>>>".getBytes();
					sp.writeBytes(b2, 20);
					byte[] b3 = "<<<?A0035;>>>".getBytes();
					sp.writeBytes(b3, 20);
					byte[] b4 = "Start: <<<A0035;>>>".getBytes();
					sp.writeBytes(b4, 20);
					byte[] b5 = "<<<A0035; >>>".getBytes();
					sp.writeBytes(b5, 20);
					byte[] b6 = "<<<A 0035;>>>".getBytes();
					sp.writeBytes(b6, 20);
					byte[] b7 = "<<<A 0035; >>>".getBytes();
					sp.writeBytes(b7, 20);
					byte[] b8 = "<<< A0035; >>>".getBytes();
					sp.writeBytes(b8, 20);
					final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

					executor.submit(new Runnable() {
						@Override
						public void run() {
							Scanner ardout = new Scanner(sp.getInputStream());

							while (ardout.hasNextLine()) {
								String res_barcode = ardout.nextLine();

								System.out.println("barcode : " + res_barcode);
								scan.setText(res_barcode);
								byte[] bb = "<<<A0036;>>>".getBytes();
								sp.writeBytes(bb, 20);
								break;
							}

						}
					});

				}
				/*
				 * try { Thread.sleep(5000); } catch (InterruptedException e1) {
				 * e1.printStackTrace(); } if
				 * (barcodeScanner.getBarcodetext().equals("noValue")) { try {
				 * LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_FAILURE);
				 * 
				 * } catch (Exception ex) { ex.printStackTrace(); } Alert errorAlert = new
				 * Alert(AlertType.ERROR);
				 * errorAlert.setHeaderText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT_HEADER);
				 * errorAlert.setContentText(FULFILLMENT_SOID_NUMBER_FAILURE_ERROR_ALERT);
				 * errorAlert.showAndWait(); } else { try { LogFile.logfile(logDate + "\s " +
				 * LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);
				 * 
				 * } catch (Exception ex) { ex.printStackTrace(); }
				 * scan.setText(barcodeScanner.getBarcodetext()); } String baseDir = "." +
				 * File.separator + "Logs";
				 */
				// File file = new File(baseDir + File.separator + "barcode.png");

				// file.delete();

			}
		});

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton);
		delHbox1.setAlignment(Pos.CENTER);

		vkb = new VirtualKeyboard(getWidthPercentage(5, sp.getSceneWidth()),
				getWidthPercentage(6, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		delHbox3 = new HBox();
		delHbox3.getChildren().add(vkb.view());
		delHbox3.setAlignment(Pos.CENTER);

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

	public static void fulfillmentProductCode(String fulSoidNumber, double sWidth, double sHeight) {

		
		try {
			ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", "");
			ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String LOG_FULFILLMENT_PRODUCT_CODE_FAILURE = ApiModule
				.getPropertyValue("LOG_FULFILLMENT_PRODUCT_CODE_FAILURE");
		String LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS = ApiModule
				.getPropertyValue("LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS");
		String FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT");
		String FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER");
		String FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT = ApiModule
				.getPropertyValue("FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT");
		String FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT_HEADER = ApiModule
				.getPropertyValue("FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT_HEADER");

		sp.setSceneWidth(sWidth);
		sp.setSceneHeight(sHeight);

		String fulfillmenttotalSOID = "";
		String fulfillmentscannedSOID = "";
		Label fulfillmentscannedSOIDtext;
		Label fulfillmenttotalSOIDtext;

		BorderPane borderPane = new BorderPane();

		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setStyle("-fx-background-color: Orange");

		HBox tHbox1 = new HBox();

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

		tHbox1.getChildren().addAll(homeButton, SOIDvbox);

		HBox tHbox2 = new HBox();
		tHbox2.getChildren().add(tHbox1);
		tHbox2.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(tHbox2, Pos.CENTER);
		borderPane.setTop(tHbox2);

		Label fullSOIDscantext = new Label("SOID # :");
		fullSOIDscantext.setTextFill(Color.WHITE);
		fullSOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));
		fullSOIDscantext.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(2.5, sp.getSceneWidth())));

		TextField fullSOIDscan = new TextField();
		fullSOIDscan.setPromptText("Enter/Scan SOID Number:");
		fullSOIDscan.setPrefWidth(getWidthPercentage(21, sp.getSceneWidth()));
		fullSOIDscan.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1, sp.getSceneWidth())));

		fullSOIDscan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		fullSOIDscan.setText(fulSoidNumber);
		fullSOIDscan.setDisable(true);

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
		Platform.runLater(() -> scan.requestFocus());

		scan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		scan.setOnKeyReleased(eventAc -> {
			if (scan.getText().length() <= 30) {
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
						String qty = ApiModule.getqty(jsonArray, fulSoidNumber);

						try {
							CaptureImage.captureImage();
						} catch (Exception ex) {
							System.out.println("Camera issue");
						}
						String fulillmentscannedSOID;
						int fulfillmentscannedSOIDint = 0;
						String fulillmenttotalSOID;
						int fulFillmenttotalSOIDint = 0;
						homeButton.setDisable(true);
						forceClose.setDisable(true);
						scan.setDisable(true);
						enterButton.setDisable(true);
						scanButton.setDisable(true);
						fullSOIDscan.setDisable(true);

						boolean lockerOpenTime1 = LockerOpen.OpenLocker(lockname);
						if (lockerOpenTime1) {

							// LockerOpen.OpenLockerConfirmation();
							LocalDateTime lock_open_time = LocalDateTime.now();
							DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
							formattedlockOpenDate = lock_open_time.format(myFormatObj1);
							// System.out.println("Locker Opened At :" + formattedlockOpenDate);

							try {
								ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", formattedlockOpenDate);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}

							System.out.println("Disabled :" + homeButton.isDisabled());

							// Fullfilment soid number

							fulillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");
							fulfillmentscannedSOIDint = Integer.parseInt(fulillmentscannedSOID);
							fulfillmentscannedSOIDint++;

							fulfillmentscannedSOIDtext.setText("");
							fulfillmentscannedSOIDtext
									.setText("Scanned SOID: " + String.valueOf(fulfillmentscannedSOIDint));

							try {
								ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID",
										String.valueOf(fulfillmentscannedSOIDint));
							} catch (Exception xx) {
								xx.printStackTrace();
							}
							fulillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
							fulFillmenttotalSOIDint = Integer.parseInt(fulillmenttotalSOID);

							// System.out.println("cv");

							// System.out.println("cve");

							fulfillmentDoorNametext.setText(lockname + " is Opened ");

						} else {
							System.out.println("Port is not opened!");
							// need to work;

						}
						String lockerOpenTime2 = ApiModule.getPropertyValue("GLOBAL_LOCKER_OPEN");
						String lockerCloseTime1 = ApiModule.getPropertyValue("GLOBAL_LOCKER_CLOSE");

						// System.out.println("cve");
						Validations.isValidConfirmMessage2(fulSoidNumber, lockname, productCode, qty,
								fulfillmentscannedSOIDint, fulFillmenttotalSOIDint);
						gotoSetLayout();

					} else {

						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT);
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

				errorAlert.setHeaderText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER);
				errorAlert.setContentText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT);
				errorAlert.showAndWait();

			}

		});

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// System.out.println("en");
				if (scan.getText().length() <= 30) {
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
						String qty = ApiModule.getqty(jsonArray, fulSoidNumber);

						try {
							CaptureImage.captureImage();
						} catch (Exception ex) {
							System.out.println("Camera issue");
						}
						String fulillmentscannedSOID;
						int fulfillmentscannedSOIDint = 0;
						String fulillmenttotalSOID;
						int fulFillmenttotalSOIDint = 0;
						homeButton.setDisable(true);
						forceClose.setDisable(true);
						scan.setDisable(true);
						enterButton.setDisable(true);
						scanButton.setDisable(true);
						fullSOIDscan.setDisable(true);

						boolean lockerOpenTime1 = LockerOpen.OpenLocker(lockname);
						if (lockerOpenTime1) {

							// LockerOpen.OpenLockerConfirmation();
							LocalDateTime lock_open_time = LocalDateTime.now();
							DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
							formattedlockOpenDate = lock_open_time.format(myFormatObj1);
							// System.out.println("Locker Opened At :" + formattedlockOpenDate);

							try {
								ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", formattedlockOpenDate);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}

							System.out.println("Disabled :" + homeButton.isDisabled());

							// Fullfilment soid number

							fulillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");
							fulfillmentscannedSOIDint = Integer.parseInt(fulillmentscannedSOID);
							fulfillmentscannedSOIDint++;

							fulfillmentscannedSOIDtext.setText("");
							fulfillmentscannedSOIDtext
									.setText("Scanned SOID: " + String.valueOf(fulfillmentscannedSOIDint));

							try {
								ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID",
										String.valueOf(fulfillmentscannedSOIDint));
							} catch (Exception xx) {
								xx.printStackTrace();
							}
							fulillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
							fulFillmenttotalSOIDint = Integer.parseInt(fulillmenttotalSOID);

							// System.out.println("cv");

							// System.out.println("cve");

							fulfillmentDoorNametext.setText(lockname + " is Opened ");

						} else {
							System.out.println("Port is not opened!");
							// need to work;

						}

						Validations.isValidConfirmMessage2(fulSoidNumber, lockname, productCode, qty,
								fulfillmentscannedSOIDint, fulFillmenttotalSOIDint);
						gotoSetLayout();
						// System.out.println("cve");
						/*
						 * if (Validations.isValidConfirmMessage()) { String lockerOpenTime2 =
						 * ApiModule.getPropertyValue("GLOBAL_LOCKER_OPEN"); String lockerCloseTime1 =
						 * ApiModule.getPropertyValue("GLOBAL_LOCKER_CLOSE"); // //
						 * System.out.println(formattedlockopenDate); //
						 * System.out.println(formattedlockcloseDate); homeButton.setDisable(false);
						 * forceClose.setDisable(false); String[] Finalarray =
						 * ApiModule.toFinalApiStringArray(); // System.out.println(Finalarray.length);
						 * // System.out.println("Finalarray"+Finalarray); Finalarray =
						 * Arrays.copyOf(Finalarray, Finalarray.length + 1); String objData =
						 * fulSoidNumber + "," + lockname + "," + productCode + ",SUCCESS," +
						 * lockerOpenTime2 + "," + lockerCloseTime1 + "," + qty;
						 * Finalarray[Finalarray.length - 1] = objData; try { LogFile.logfile(logDate +
						 * "\s " + "SOID  :" + fulSoidNumber); String orderType =
						 * ApiModule.getPropertyValue("ORDER_TYPE"); String OrderValue; if
						 * (orderType.equals("REFILL")) { OrderValue =
						 * ApiModule.getPropertyValue("CURRENT_DC_NUMBER");
						 * 
						 * } else { OrderValue = ApiModule.getPropertyValue("CURRENT_SO_NUMBER");
						 * 
						 * } LogFile.logfile(logDate + "\s " + "OrderType : " + orderType);
						 * LogFile.logfile(logDate + "\s " + "DC# : " + OrderValue);
						 * LogFile.logfile(logDate + "\s " + "LockerID : " + lockname);
						 * LogFile.logfile(logDate + "\s " + "ProductCode: " + productCode);
						 * LogFile.logfile(logDate + "\s " + "Qty : " + qty); LogFile.logfile(logDate +
						 * "\s " + "LockerOpenDate : " + lockerOpenTime2); LogFile.logfile(logDate +
						 * "\s " + "LockerCloseDate : " + lockerCloseTime1); LogFile.logfile(logDate +
						 * "\s " + "SOID  :" + fulSoidNumber + ",OrderType : " + orderType + "DC# : " +
						 * OrderValue + ",LockerID : " + lockname + ",ProductCode: " + productCode +
						 * ",Qty : " + qty + ",LockerOpenDate : " + lockerOpenTime1 +
						 * ",LockerCloseDate : " + lockerCloseTime1); } catch (Exception ex) {
						 * ex.printStackTrace(); }
						 * 
						 * String fs = ""; for (int i = 0; i < Finalarray.length; i++) { fs = fs +
						 * Finalarray[i];
						 * 
						 * if (fs.length() != 0) { fs = fs + "$"; } } // System.out.println("fs"+fs);
						 * try { ApiModule.setpropertyValue("FINAL_API_DATA", fs); String[] SOIDArray =
						 * ApiModule.toStringArray(); SOIDArray = Arrays.copyOf(SOIDArray,
						 * SOIDArray.length + 1); SOIDArray[SOIDArray.length - 1] =
						 * fulfillmentSoidNumber; // Assign 40 to the last // element String fs1 = "";
						 * for (int i = 0; i < SOIDArray.length; i++) { fs1 = fs1 + SOIDArray[i]; fs1 =
						 * fs1 + " "; }
						 * 
						 * try {
						 * 
						 * ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs1); } catch
						 * (IOException e1) { e1.printStackTrace(); }
						 * 
						 * if (fulfillmentscannedSOIDint == fulFillmenttotalSOIDint) {
						 * ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", String.valueOf(0));
						 * ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));
						 * 
						 * try { LogFile.logfile(logDate + "\s " +
						 * LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);
						 * 
						 * } catch (Exception ex) { ex.printStackTrace(); } Alert errorAlert1 = new
						 * Alert(AlertType.INFORMATION);
						 * 
						 * errorAlert1.setHeaderText(FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT);
						 * errorAlert1.setContentText(FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT_HEADER);
						 * errorAlert1.showAndWait(); // System.out.println("Final api call //"); if
						 * (ApiModule.FinalApiCall()) { // System.out.println("Final api call"); //
						 * ApiModule.setpropertyValue("FINAL_API_DATA", ""); }
						 * ApiModule.setpropertyValue("ALL_CURR_TRACK_NUMBER", "");
						 * ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
						 * ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
						 * ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
						 * ApiModule.setpropertyValue("ORDER_TYPE", ""); setLayout(); } else { try {
						 * LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);
						 * 
						 * } catch (Exception ex) { ex.printStackTrace(); }
						 * fulfillmentSOID(sp.getSceneWidth(), sp.getSceneHeight()); }
						 * 
						 * } catch (Exception e1) { e1.printStackTrace(); }
						 * 
						 * }
						 */
					} else {

						try {
							LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);

						errorAlert.setHeaderText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER);
						errorAlert.setContentText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT);
						errorAlert.showAndWait();
					}
				}

			}
		});

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("SCANNER_PORT")); // device name TODO:
																									// must be
				// changed

				sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
				sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

				sp.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED);
				if (sp.openPort()) {
					System.out.println("Scanner port is opened");
					byte[] b = "<<<A0035;>>>".getBytes();
					sp.writeBytes(b, 20);
					byte[] b1 = "A0035".getBytes();
					sp.writeBytes(b1, 20);
					byte[] b2 = "<<<!A0035;>>>".getBytes();
					sp.writeBytes(b2, 20);
					byte[] b3 = "<<<?A0035;>>>".getBytes();
					sp.writeBytes(b3, 20);
					byte[] b4 = "Start: <<<A0035;>>>".getBytes();
					sp.writeBytes(b4, 20);
					byte[] b5 = "<<<A0035; >>>".getBytes();
					sp.writeBytes(b5, 20);
					byte[] b6 = "<<<A 0035;>>>".getBytes();
					sp.writeBytes(b6, 20);
					byte[] b7 = "<<<A 0035; >>>".getBytes();
					sp.writeBytes(b7, 20);
					byte[] b8 = "<<< A0035; >>>".getBytes();
					sp.writeBytes(b8, 20);
					final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

					executor.submit(new Runnable() {
						@Override
						public void run() {
							Scanner ardout = new Scanner(sp.getInputStream());

							while (ardout.hasNextLine()) {
								String res_barcode = ardout.nextLine();

								System.out.println("barcode : " + res_barcode);
								scan.setText(res_barcode);
								byte[] bb = "<<<A0036;>>>".getBytes();
								sp.writeBytes(bb, 20);
								break;
							}

						}
					});

				}

				/*
				 * try { Thread.sleep(5000); } catch (InterruptedException e1) {
				 * e1.printStackTrace(); } if
				 * (barcodeScanner.getBarcodetext().equals("noValue")) { try {
				 * LogFile.logfile(logDate + "\s " + LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);
				 * 
				 * } catch (Exception ex) { ex.printStackTrace(); } Alert errorAlert = new
				 * Alert(AlertType.ERROR);
				 * errorAlert.setHeaderText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER)
				 * ; errorAlert.setContentText(FULFILLMENT_PRODUCT_CODE_FAILURE_ERROR_ALERT);
				 * errorAlert.showAndWait(); } else {
				 * scan.setText(barcodeScanner.getBarcodetext()); } String baseDir = "." +
				 * File.separator + "Logs";
				 */
				// File file = new File(baseDir + File.separator + "barcode.png");

				// file.delete();

			}
		});

		forceClose.setStyle("-fx-background-color: RED");
		forceClose.setTextFill(Color.WHITE);
		forceClose.setFont(
				Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, getWidthPercentage(1.5, sp.getSceneWidth())));

		forceClose.setPrefWidth(getWidthPercentage(13, sp.getSceneWidth()));
		forceClose.setPrefHeight(getWidthPercentage(3, sp.getSceneWidth()));

		forceClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				fulfillmentForceClose();

			}
		});

		HBox delHbox0 = new HBox();
		delHbox0.getChildren().addAll(fullSOIDscantext, fullSOIDscan, fullSOIDenterButton, fullSOIDscanButton);
		delHbox0.setAlignment(Pos.CENTER);
		delHbox0.setSpacing(getWidthPercentage(2, sp.getSceneWidth()));

		delHbox1 = new HBox();
		delHbox1.getChildren().addAll(scantext, scan, enterButton, scanButton, forceClose);
		delHbox1.setAlignment(Pos.CENTER);

		vkb = new VirtualKeyboard(getWidthPercentage(5, sp.getSceneWidth()),
				getWidthPercentage(6, sp.getSceneHeight()));
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		delHbox3 = new HBox();
		delHbox3.setAlignment(Pos.CENTER);
		delHbox3.getChildren().add(vkb.view());
		delHbox3.setAlignment(Pos.CENTER);

		HBox delHbox5 = new HBox();

		fulfillmentDoorNametext = new Label("");
		fulfillmentDoorNametext.setTextFill(Color.WHITE);
		fulfillmentDoorNametext.setTextAlignment(TextAlignment.CENTER);
		fulfillmentDoorNametext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, 15));
		fulfillmentDoorNametext.setText("");
		delHbox5.getChildren().add(fulfillmentDoorNametext);
		delHbox5.setAlignment(Pos.CENTER);

		vbox = new VBox();
		vbox.getChildren().addAll(delHbox5, delHbox0, delHbox1, delHbox3);
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

	public static void fulfillmentForceClose() {

		Stage secondaryStage = new Stage();

		BorderPane borderPane = new BorderPane();

		VBox reasonvbox = new VBox(10);

		Label reasonLabel = new Label("Reason :");
		reasonLabel.setTextFill(Color.WHITE);
		reasonLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));

		ComboBox<String> comboBox = new ComboBox<>();
		ObservableList<String> items = FXCollections.observableArrayList();
		comboBox.setValue("---Select---");
		comboBox.setItems(items);
		comboBox.setPrefWidth(200);
		String[] Codes = ApiModule.getPropertyValue("ERROR_CODES").split(Pattern.quote("$"));

		for (int i = 0; i < Codes.length; i++) {
			items.add(Codes[i]);

		}

		reasonvbox.getChildren().addAll(reasonLabel, comboBox);
		reasonvbox.setAlignment(Pos.CENTER);

		borderPane.setCenter(reasonvbox);
		BorderPane.setAlignment(reasonvbox, Pos.CENTER);

		Button reasonbutton = new Button("Done");

		reasonbutton.setAlignment(Pos.CENTER);
		reasonbutton.setStyle("-fx-background-color: Orange");
		reasonbutton.setTextFill(Color.WHITE);
		reasonbutton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 18));

		reasonbutton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					String comboValue = comboBox.getValue();
					ApiModule.setpropertyValue("CURRENT_CODE", comboValue);
					ApiModule.ForceClose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				secondaryStage.close();
				setLayout();
			}

		});

		borderPane.setBottom(reasonbutton);
		BorderPane.setAlignment(reasonbutton, Pos.BOTTOM_CENTER);

		BorderPane.setMargin(reasonbutton, new Insets(12, 20, 12, 12));

		StackPane root2 = new StackPane(borderPane);
		Scene scene = new Scene(root2, 300, 150);
		root2.setStyle("-fx-background-size: cover;-fx-opacity: 0.8;-fx-background-color: blue");

		secondaryStage.setTitle("Force close");
		secondaryStage.setScene(scene);
		secondaryStage.show();
	}

}