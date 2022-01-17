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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SmartBox extends Application {

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

		primaryStage.setTitle("Smart Box");

		// Setting up GridPane
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(20, 20, 20, 25));

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(180);
		helpButton.setTranslateX(280);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		Text text1 = new Text("Welcome to YCH Smart Locker");
		text1.setFill(Color.WHITE);
		text1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text1.setTextAlignment(TextAlignment.CENTER);
		text1.setTranslateY(-130);
		text1.setStroke(Color.BLUE);

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(185);
		PhoneNumber.setTranslateX(-230);
		PhoneNumber.setStroke(Color.BLUE);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		HBox hbox = new HBox(80);
		hbox.setTranslateX(-10);
		hbox.setTranslateY(10);

		// Pick up material Fx content

		Delivery.setPrefSize(120, 120);
		Delivery.textAlignmentProperty().set(TextAlignment.CENTER);
		Delivery.setStyle(
				"-fx-background-color: Orange;-fx-border-radius: 18;-fx-background-radius: 18;-fx-border-color: White; -fx-border-width: 2px;");
		Delivery.setEffect(shadow);
		shadow.setColor(Color.BLUE);

		Delivery.setTextFill(Color.WHITE);
		String fontName = ApiModule.getPropertyValue("FONT_NAME");
		Delivery.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		Delivery.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				deliveryMaterial();

			}
		});

		// Fulfilment material content in fx

		Fulfilment.setPrefSize(120, 120);
		Fulfilment.setText("Fulfilment");
		Fulfilment.textAlignmentProperty().set(TextAlignment.CENTER);
		Fulfilment.setStyle("-fx-background-color: Orange");
		Fulfilment.setTextFill(Color.WHITE);
		Fulfilment.setStyle(
				"-fx-background-color: Orange;-fx-border-radius: 18;-fx-background-radius: 18;-fx-border-color: White; -fx-border-width: 2px;");
		Fulfilment.setEffect(shadow);
		shadow.setColor(Color.BLUE);

		Fulfilment.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));
		Fulfilment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FulfillmentMaterial();

			}
		});
		hbox.getChildren().addAll(Delivery, Fulfilment);
		grid.add(hbox, 1, 0);

		enterButton.setStyle("-fx-background-color: Green");
		scanButton.setStyle("-fx-background-color: Orange");
		enterButton.setTextFill(Color.WHITE);
		scanButton.setTextFill(Color.WHITE);

		StackPane root = new StackPane(grid);
		root.getChildren().addAll(text1, PhoneNumber, helpButton);
		root.setStyle("-fx-background-image: url(\"banner.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8;");
		Scene scene = new Scene(root, 800, 480);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	// Delivery material Scene in javafx

	public void deliveryMaterial() {

		String LOG_DELIVERY_SO_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_FAILURE");
		String LOG_DELIVERY_SO_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_SUCCESS");
		
		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		scantext = new Label("Enter/Scan SO Number:");
		scantext.setTranslateX(100);
		scantext.setTranslateY(-200);
		scantext.setTextFill(Color.WHITE);
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		scan = new TextField();
		scan.setPromptText("Enter/Scan SO Number:");
		scan.setMaxWidth(190);
		scan.setTranslateX(330);
		scan.setTranslateY(-200);
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
							LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_SO_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						deliveryOtpLayout(soNumber);
					}
				}

			}

		});

		enterButton.setTranslateX(-210);
		enterButton.setTranslateY(-200);
		enterButton.setPrefSize(100, 36);

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
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
							LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_SO_NUMBER_FAILURE);
						} catch (Exception ex) {

							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Admin Msg: Work Not Done");
						errorAlert.setContentText("Wrong Part Scanned, Please try again or wrong Barcode Scanned.");
						errorAlert.showAndWait();
					} else {
						try {
							LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_SO_NUMBER_SUCCESS);
							// the following statement is used to log any messages
							// logger.info();
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						deliveryOtpLayout(soNumber);
					}
				}

			}
		});

		scanButton.setTranslateX(-150);
		scanButton.setTranslateY(-200);
		scanButton.setPrefSize(60, 36);
		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "/s "+ LOG_DELIVERY_SO_NUMBER_FAILURE);
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
						LogFile.logfile(logDate + "/s "+ LOG_DELIVERY_SO_NUMBER_SUCCESS);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vkb.view().setTranslateX(25);
		vkb.view().setTranslateY(85);

		grid2.add(scantext, 0, 5);
		grid2.add(scan, 0, 5);
		grid2.add(enterButton, 1, 5);
		grid2.add(scanButton, 1, 5);
		grid2.add(vkb.view(), 0, 3);

		StackPane root2 = new StackPane(grid2);
		root2.getChildren().addAll(homeButton, PhoneNumber, helpButton);

		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

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

		// defaultTime.setMinutes(minutes);
		// defaultTime.setSeconds(seconds);
		// timer.setText(defaultTime.toString());
	}

	public void deliveryOtpLayout(String soNumber) {

		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event2 -> {
			countDown();
			// setLayout();

		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);

		timerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		timerLabel.setTranslateX(250);
		timerLabel.setTranslateY(-60);
		timerLabel.setTextFill(Color.WHITE);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");
		homeButton.setTextFill(Color.WHITE);

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				minutes = 14;
				seconds = 59;
				timeline.stop();
				setLayout();

			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		otpText = new Label("Enter your OTP!");
		otpText.setTranslateX(10);
		otpText.setTranslateY(-100);
		otpText.setTextFill(Color.WHITE);
		otpText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		otpScan = new TextField();
		otpScan.setPromptText("Enter OTP");
		otpScan.setTranslateX(10);
		otpScan.setTranslateY(-60);
		otpScan.setMaxWidth(200);

		Button clearText = new Button("X");
		clearText.setTranslateX(78);
		clearText.setTranslateY(-60);
		clearText.setStyle("-fx-background-color: Orange");
		clearText.setTextFill(Color.WHITE);
		clearText.setPrefSize(63, 34);
		clearText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		clearText.setOnAction(new EventHandler<ActionEvent>() {

			@Override
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

		enterButton.setTranslateX(160);
		enterButton.setTranslateY(-60);
		enterButton.setPrefSize(60, 36);
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
					deliveryCheckOtp(soNumber, otp);
				}
			}
		});

		Numpad np = new Numpad();
		np.view().setStyle("-fx-border-radius: 5;");
		np.view().setTranslateX(10);
		np.view().setTranslateY(80);

		grid2.add(np.view(), 0, 3);

		HBox hbox = new HBox(520);
		hbox.setTranslateX(50);
		hbox.setTranslateY(380);

		Button backButton = new Button();

		backButton.setText("<- Back");
		backButton.setStyle("-fx-background-color: Red");
		backButton.setTextFill(Color.WHITE);
		backButton.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				minutes = 14;
				seconds = 59;
				timeline.stop();
				deliveryMaterial();

			}
		});

		Button resendOtp = new Button();

		resendOtp.setText("Resend Otp");
		resendOtp.setStyle("-fx-background-color: Green");
		resendOtp.setTextFill(Color.WHITE);
		resendOtp.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		resendOtp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
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
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		hbox.getChildren().addAll(backButton, resendOtp);

		StackPane root2 = new StackPane(grid2);
		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8;");
		root2.getChildren().addAll(otpText, otpScan, clearText, homeButton, enterButton, PhoneNumber, helpButton, hbox,
				timerLabel);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	// Check deliveryCheckOtp

	private void deliveryCheckOtp(String soNumber, String otpNumber) {
		String LOG_DELIVERY_OTP_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_OTP_FAILURE");
		String LOG_DELIVERY_OTP_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_OTP_SUCCESS");

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

					LogFile.logfile(logDate + "\s " +LOG_DELIVERY_OTP_FAILURE);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Admin Msg: OTP Invalid!");
				errorAlert.setContentText("Entered OTP is Invalid ..!, Please try again.");
				errorAlert.showAndWait();
			} else {
				try {
					LogFile.logfile(logDate + "\s "+LOG_DELIVERY_OTP_SUCCESS);

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
				deliverySOID();

			}
		} catch (IOException e) {
			try {

				LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_OTP_FAILURE);

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

	public void deliverySOID() {
		
		String LOG_DELIVERY_SOID_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SOID_NUMBER_FAILURE");
		String LOG_DELIVERY_SOID_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SOID_NUMBER_SUCCESS");

		String deliverytotalSOID = "";
		String deliveryscannedSOID = "";
		Label deliveryscannedSOIDtext;
		Label deliverytotalSOIDtext;

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		deliverytotalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
		deliveryscannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");

		deliverytotalSOIDtext = new Label("Total SOID: " + deliverytotalSOID);

		deliveryscannedSOIDtext = new Label("Scanned SOID: " + deliveryscannedSOID);

		deliverytotalSOIDtext.setMaxWidth(400);
		deliverytotalSOIDtext.setTranslateX(280);
		deliverytotalSOIDtext.setTranslateY(-190);
		deliverytotalSOIDtext.setTextFill(Color.WHITE);
		deliverytotalSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		deliveryscannedSOIDtext.setMaxWidth(400);
		deliveryscannedSOIDtext.setTranslateX(280);
		deliveryscannedSOIDtext.setTranslateY(-170);
		deliveryscannedSOIDtext.setTextFill(Color.WHITE);
		deliveryscannedSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		scantext = new Label("Enter/Scan SOID Number:");
		scantext.setTranslateX(70);
		scantext.setTranslateY(-230);
		scantext.setTextFill(Color.WHITE);
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		scan = new TextField();
		scan.setPromptText("Enter/Scan SOID Number:");
		scan.setMaxWidth(190);
		scan.setTranslateX(330);
		scan.setTranslateY(-230);
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

						LogFile.logfile(logDate + "\s "+LOG_DELIVERY_SOID_NUMBER_FAILURE);

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

							LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_SOID_NUMBER_SUCCESS);

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
						deliveryProductCode(deliverySoidNumber);
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

		enterButton.setTranslateX(-220);
		enterButton.setTranslateY(-230);
		enterButton.setPrefSize(90, 36);

		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				deliverySoidNumber = scan.getText();
				JSONObject jsonObject = new JSONObject(ApiModule.getPropertyValue("OTP_DATA"));
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				String[] SOIDArray = ApiModule.toStringArray();

				if (Arrays.asList(SOIDArray).contains(deliverySoidNumber)) {
					try {

						LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_SOID_NUMBER_FAILURE);
						// the following statement is used to log any messages
						// logger.info("Entered OTP is Invalid for Delivery");
					} catch (Exception ex) {
						// TODO Auto-generated catch block
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

							LogFile.logfile(logDate + "\s "+LOG_DELIVERY_SOID_NUMBER_SUCCESS);
							// the following statement is used to log any messages
							// logger.info("Entered OTP is Invalid for Delivery");
						} catch (Exception ex) {
							// TODO Auto-generated catch block
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
							// StringUtils.join(SOIDArray, ",");
							ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						deliveryProductCode(deliverySoidNumber);
					} else {
						try {

							LogFile.logfile(logDate + "\s "+LOG_DELIVERY_SOID_NUMBER_FAILURE);
							// the following statement is used to log any messages
							// logger.info("Entered OTP is Invalid for Delivery");
						} catch (Exception ex) {
							// TODO Auto-generated catch block
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

		scanButton.setTranslateX(-160);
		scanButton.setTranslateY(-230);
		scanButton.setPrefSize(60, 36);

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_SOID_NUMBER_FAILURE);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {

						LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_SOID_NUMBER_SUCCESS);
						// the following statement is used to log any messages
						// logger.info("Entered OTP is Invalid for Delivery");
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vkb.view().setTranslateX(25);
		vkb.view().setTranslateY(85);

		grid2.add(scantext, 0, 4);
		grid2.add(scan, 0, 4);
		grid2.add(enterButton, 1, 4);
		grid2.add(scanButton, 1, 4);

		grid2.add(vkb.view(), 0, 3);

		StackPane root2 = new StackPane(grid2);
		root2.getChildren().addAll(homeButton, PhoneNumber, helpButton, deliverytotalSOIDtext, deliveryscannedSOIDtext);

		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void deliveryProductCode(String deliverySoidNumber) {
		
		String LOG_DELIVERY_PRODUCT_CODE_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_FAILURE");
		String LOG_DELIVERY_PRODUCT_CODE_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_SUCCESS");

		String deliverytotalSOID = "";
		String deliveryscannedSOID = "";
		Label deliveryscannedSOIDtext;
		Label deliverytotalSOIDtext;

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		deliverytotalSOID = ApiModule.getPropertyValue("DELIVERY_TOTAL_SOID");
		deliveryscannedSOID = ApiModule.getPropertyValue("DELIVERY_SCANNED_SOID");

		deliverytotalSOIDtext = new Label("Total SOID: " + deliverytotalSOID);

		deliveryscannedSOIDtext = new Label("Scanned SOID: " + deliveryscannedSOID);

		deliverytotalSOIDtext.setMaxWidth(400);
		deliverytotalSOIDtext.setTranslateX(280);
		deliverytotalSOIDtext.setTranslateY(-190);
		deliverytotalSOIDtext.setTextFill(Color.WHITE);
		deliverytotalSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		deliveryscannedSOIDtext.setMaxWidth(400);
		deliveryscannedSOIDtext.setTranslateX(280);
		deliveryscannedSOIDtext.setTranslateY(-170);
		deliveryscannedSOIDtext.setTextFill(Color.WHITE);
		deliveryscannedSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		Label SOIDscantext = new Label("Enter/Scan SOID Number:");
		SOIDscantext.setMaxWidth(400);
		SOIDscantext.setTranslateX(70);
		SOIDscantext.setTranslateY(-230);
		SOIDscantext.setTextFill(Color.WHITE);
		SOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		TextField SOIDscan = new TextField();
		SOIDscan.setPromptText("Enter/Scan SOID Number:");
		SOIDscan.setMaxWidth(190);
		SOIDscan.setTranslateX(330);
		SOIDscan.setTranslateY(-230);
		SOIDscan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		SOIDscan.setText(deliverySoidNumber);

		Button SOIDenterButton = new Button("Enter");
		SOIDenterButton.setTranslateX(-220);
		SOIDenterButton.setTranslateY(-230);
		SOIDenterButton.setPrefSize(90, 36);
		SOIDenterButton.setDisable(true);

		Button SOIDscanButton = new Button("Scan");
		SOIDscanButton.setTranslateX(-160);
		SOIDscanButton.setTranslateY(-230);
		SOIDscanButton.setPrefSize(60, 36);
		SOIDscanButton.setDisable(true);

		scantext = new Label("Enter/Scan Product Code:");
		scantext.setMaxWidth(400);
		scantext.setTranslateX(70);
		scantext.setTranslateY(-200);
		scantext.setTextFill(Color.WHITE);
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		scan = new TextField();
		scan.setPromptText("Enter/Scan Product Code:");
		scan.setMaxWidth(190);
		scan.setTranslateX(330);
		scan.setTranslateY(-200);
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
							LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_PRODUCT_CODE_SUCCESS);

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
								// ImageCapture();
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
										LogFile.logfile(logDate	+ "\s " +LOG_DELIVERY_PRODUCT_CODE_SUCCESS);

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

									deliverySOID();
								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}
					} else {
						try {

							LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_PRODUCT_CODE_FAILURE);
							// the following statement is used to log any messages
							// logger.info("Entered OTP is Invalid for Delivery");
						} catch (Exception ex) {
							// TODO Auto-generated catch block
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
					LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_PRODUCT_CODE_FAILURE);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				Alert errorAlert = new Alert(AlertType.ERROR);

				errorAlert.setHeaderText("Admin Msg: Invalid Product Code");
				errorAlert.setContentText("Invalid, Please try again");
				errorAlert.showAndWait();

			}

		});

		enterButton.setTranslateX(-220);
		enterButton.setTranslateY(-200);
		enterButton.setPrefSize(90, 36);

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

							LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_PRODUCT_CODE_SUCCESS);
							// the following statement is used to log any messages
							// logger.info("Entered OTP is Invalid for Delivery");
						} catch (Exception ex) {
							// TODO Auto-generated catch block
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

									deliverySOID();
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
							// the following statement is used to log any messages
							// logger.info("Entered OTP is Invalid for Delivery");
						} catch (Exception ex) {
							// TODO Auto-generated catch block
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

		scanButton.setTranslateX(-160);
		scanButton.setTranslateY(-200);
		scanButton.setPrefSize(60, 36);

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {

						LogFile.logfile(logDate + "\s "+LOG_DELIVERY_PRODUCT_CODE_FAILURE);
						// the following statement is used to log any messages
						// logger.info("Entered OTP is Invalid for Delivery");
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {

						LogFile.logfile(logDate + "\s "+ LOG_DELIVERY_PRODUCT_CODE_FAILURE);
						// the following statement is used to log any messages
						// logger.info("Entered OTP is Invalid for Delivery");
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});
		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		forceClose.setTranslateX(300);
		forceClose.setTranslateY(-50);
		forceClose.setPrefSize(90, 36);
		forceClose.setStyle("-fx-background-color: RED");
		forceClose.setTextFill(Color.WHITE);

		forceClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					ApiModule.ForceClose();
				} catch (ParseException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vkb.view().setTranslateX(20);
		vkb.view().setTranslateY(120);

		grid2.add(SOIDscantext, 0, 4);
		grid2.add(SOIDscan, 0, 4);
		grid2.add(SOIDenterButton, 1, 4);
		grid2.add(SOIDscanButton, 1, 4);

		grid2.add(scantext, 0, 5);
		grid2.add(scan, 0, 5);
		grid2.add(enterButton, 1, 5);
		grid2.add(scanButton, 1, 5);
		grid2.add(vkb.view(), 0, 3);


		StackPane root2 = new StackPane(grid2);
		root2.getChildren().addAll(homeButton, PhoneNumber, helpButton,forceClose, deliverytotalSOIDtext, deliveryscannedSOIDtext);

		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	// FulfilmentMaterial scene in javafx

	public void FulfillmentMaterial() {
		
		String LOG_FULFILLMENT_DC_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_DC_NUMBER_FAILURE");
		String LOG_FULFILLMENT_DC_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_DC_NUMBER_SUCCESS");

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		scantext = new Label("Enter/Scan DC Number:");
		scantext.setTranslateX(100);
		scantext.setTranslateY(-200);
		scantext.setTextFill(Color.WHITE);
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		scan = new TextField();
		scan.setPromptText("Enter/Scan DC Number:");
		scan.setMaxWidth(190);
		scan.setTranslateX(330);
		scan.setTranslateY(-200);
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
							LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_DC_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Admin Msg: Work Not Done");
						errorAlert.setContentText("Wrong Part Scanned, Please try again or wrong Barcode Scanned.");
						errorAlert.showAndWait();
					} else {
						try {
							LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_DC_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						fulfillmentOtpLayout(dcNumber);
					}
				}

			}
		});

		enterButton.setTranslateX(-210);
		enterButton.setTranslateY(-200);
		enterButton.setPrefSize(100, 36);

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
							LogFile.logfile(logDate + "\s "+LOG_FULFILLMENT_DC_NUMBER_FAILURE);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Admin Msg: Work Not Done");
						errorAlert.setContentText("Wrong Part Scanned, Please try again or wrong Barcode Scanned.");
						errorAlert.showAndWait();
					} else {
						try {
							LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_DC_NUMBER_SUCCESS);

						} catch (Exception ex) {
							ex.printStackTrace();
						}

						fulfillmentOtpLayout(dcNumber);
					}

				}
			}
		});

		scanButton.setTranslateX(-150);
		scanButton.setTranslateY(-200);
		scanButton.setPrefSize(60, 36);

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_DC_NUMBER_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {
						LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_DC_NUMBER_SUCCESS);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();
			}
		});
		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vkb.view().setTranslateX(25);
		vkb.view().setTranslateY(85);

		grid2.add(scantext, 0, 5);
		grid2.add(scan, 0, 5);
		grid2.add(enterButton, 1, 5);
		grid2.add(scanButton, 1, 5);
		grid2.add(vkb.view(), 0, 3);

		StackPane root2 = new StackPane(grid2);
		root2.getChildren().addAll(homeButton, PhoneNumber, helpButton);

		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

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

		// defaultTime.setMinutes(minutes);
		// defaultTime.setSeconds(seconds);
		// timer.setText(defaultTime.toString());
	}

	public void fulfillmentOtpLayout(String dcNumber) {

		timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event2 -> {
			countDown2();
		}));
		timeline2.setCycleCount(Timeline.INDEFINITE);
		timeline2.play();

		timerLabel2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		timerLabel2.setTranslateX(250);
		timerLabel2.setTranslateY(-60);
		timerLabel2.setTextFill(Color.WHITE);

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");
		homeButton.setTextFill(Color.WHITE);

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				minutes2 = 14;
				seconds2 = 59;
				timeline2.stop();
				setLayout();
			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		otpText = new Label("\s Enter your OTP!");
		otpText.setTranslateX(10);
		otpText.setTranslateY(-100);
		otpText.setTextFill(Color.WHITE);
		otpText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		otpScan = new TextField();
		otpScan.setPromptText("Enter OTP");
		otpScan.setTranslateX(10);
		otpScan.setTranslateY(-60);
		otpScan.setMaxWidth(200);

		otpScan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");

		Button clearText = new Button("X");
		clearText.setTranslateX(78);
		clearText.setTranslateY(-60);
		clearText.setStyle("-fx-background-color: Orange");
		clearText.setTextFill(Color.WHITE);
		clearText.setPrefSize(63, 34);
		clearText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		clearText.setOnAction(new EventHandler<ActionEvent>() {

			@Override
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

		enterButton.setTranslateX(160);
		enterButton.setTranslateY(-60);
		enterButton.setPrefSize(60, 36);
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

		Numpad np = new Numpad();
		np.view().setStyle(" -fx-border-radius: 5;");
		np.view().setTranslateX(10);
		np.view().setTranslateY(80);

		grid2.add(np.view(), 0, 3);

		HBox hbox = new HBox(520);
		hbox.setTranslateX(50);
		hbox.setTranslateY(380);

		Button backButton = new Button();

		backButton.setText("<- Back");
		backButton.setStyle("-fx-background-color: Red");
		backButton.setTextFill(Color.WHITE);
		backButton.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				minutes2 = 14;
				seconds2 = 59;
				timeline2.stop();
				FulfillmentMaterial();
			}
		});

		Button resendOtp = new Button();

		resendOtp.setText("Resend Otp");
		resendOtp.setStyle("-fx-background-color: Green");
		resendOtp.setTextFill(Color.WHITE);
		resendOtp.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

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
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		hbox.getChildren().addAll(backButton, resendOtp);

		StackPane root2 = new StackPane(grid2);
		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8;");
		root2.getChildren().addAll(otpText, otpScan, clearText, homeButton, enterButton, PhoneNumber, helpButton, hbox,
				timerLabel);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

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
					LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_OTP_FAILURE);

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
					fulfillmentSOID();

				} catch (Exception e) {
					try {
						LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_OTP_FAILURE);

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

	public void fulfillmentSOID() {
		
		String LOG_FULFILLMENT_SOID_NUMBER_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_SOID_NUMBER_FAILURE");
		String LOG_FULFILLMENT_SOID_NUMBER_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_SOID_NUMBER_SUCCESS");

		String fulfillmenttotalSOID = "";
		String fulfillmentscannedSOID = "";
		Label fulfillmentscannedSOIDtext;
		Label fulfillmenttotalSOIDtext;

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		fulfillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
		fulfillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");

		fulfillmenttotalSOIDtext = new Label("Total SOID: " + fulfillmenttotalSOID);

		fulfillmentscannedSOIDtext = new Label("Scanned SOID: " + fulfillmentscannedSOID);

		fulfillmenttotalSOIDtext.setMaxWidth(400);
		fulfillmenttotalSOIDtext.setTranslateX(280);
		fulfillmenttotalSOIDtext.setTranslateY(-190);
		fulfillmenttotalSOIDtext.setTextFill(Color.WHITE);
		fulfillmenttotalSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		fulfillmentscannedSOIDtext.setMaxWidth(400);
		fulfillmentscannedSOIDtext.setTranslateX(280);
		fulfillmentscannedSOIDtext.setTranslateY(-170);
		fulfillmentscannedSOIDtext.setTextFill(Color.WHITE);
		fulfillmentscannedSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		scantext = new Label("Enter/Scan SOID Number:");
		scantext.setTranslateX(70);
		scantext.setTranslateY(-230);
		scantext.setTextFill(Color.WHITE);
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		scan = new TextField();
		scan.setPromptText("Enter/Scan SOID Number:");
		scan.setMaxWidth(190);
		scan.setTranslateX(330);
		scan.setTranslateY(-230);

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
							LogFile.logfile(
									logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

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

						fulfillmentProductCode(fulfillmentSoidNumber);
					} else {
						try {
							LogFile.logfile(
									logDate + "\s "+ LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

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

		enterButton.setTranslateX(-220);
		enterButton.setTranslateY(-230);
		enterButton.setPrefSize(90, 36);

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
							LogFile.logfile(
									logDate + "\s " + LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

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

						fulfillmentProductCode(fulfillmentSoidNumber);
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

		scanButton.setTranslateX(-160);
		scanButton.setTranslateY(-230);
		scanButton.setPrefSize(60, 36);

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_SOID_NUMBER_FAILURE);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Admin Msg: Invalid Barcode");
					errorAlert.setContentText("Invalid Barcode Scanned, Please try again or wrong Barcode Scanned.");
					errorAlert.showAndWait();
				} else {
					try {
						LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_SOID_NUMBER_SUCCESS);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					scan.setText(barcodeScanner.getBarcodetext());
				}
				File file = new File("C:\\Users\\Neth2639\\Desktop\\Logs\\barcode.png");

				file.delete();

			}
		});
		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vkb.view().setTranslateX(25);
		vkb.view().setTranslateY(85);

		grid2.add(scantext, 0, 4);
		grid2.add(scan, 0, 4);
		grid2.add(enterButton, 1, 4);
		grid2.add(scanButton, 1, 4);

		grid2.add(vkb.view(), 0, 3);


		StackPane root2 = new StackPane(grid2);
		root2.getChildren().addAll(homeButton, PhoneNumber, helpButton, fulfillmenttotalSOIDtext, fulfillmentscannedSOIDtext);

		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8");

		// scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void fulfillmentProductCode(String fulSoidNumber) {
		
		String LOG_FULFILLMENT_PRODUCT_CODE_FAILURE = ApiModule.getPropertyValue("LOG_FULFILLMENT_PRODUCT_CODE_FAILURE");
		String LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS = ApiModule.getPropertyValue("LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS");
		
		String fulfillmenttotalSOID = "";
		String fulfillmentscannedSOID = "";
		Label fulfillmentscannedSOIDtext;
		Label fulfillmenttotalSOIDtext;

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);

		homeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setTranslateY(-180);
		homeButton.setStyle("-fx-background-color: Orange");

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		helpButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));
		helpButton.setTextAlignment(TextAlignment.CENTER);
		helpButton.setTranslateY(210);
		helpButton.setTranslateX(340);
		helpButton.setStyle("-fx-background-color: Orange");
		helpButton.setTextFill(Color.WHITE);

		fulfillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
		fulfillmentscannedSOID = ApiModule.getPropertyValue("FULFILLMENT_SCANNED_SOID");

		fulfillmenttotalSOIDtext = new Label("Total SOID: " + fulfillmenttotalSOID);

		fulfillmentscannedSOIDtext = new Label("Scanned SOID: " + fulfillmentscannedSOID);

		fulfillmenttotalSOIDtext.setMaxWidth(400);
		fulfillmenttotalSOIDtext.setTranslateX(280);
		fulfillmenttotalSOIDtext.setTranslateY(-190);
		fulfillmenttotalSOIDtext.setTextFill(Color.WHITE);
		fulfillmenttotalSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		fulfillmentscannedSOIDtext.setMaxWidth(400);
		fulfillmentscannedSOIDtext.setTranslateX(280);
		fulfillmentscannedSOIDtext.setTranslateY(-170);
		fulfillmentscannedSOIDtext.setTextFill(Color.WHITE);
		fulfillmentscannedSOIDtext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));

		Label fullSOIDscantext = new Label("Enter/Scan SOID Number:");
		fullSOIDscantext.setMaxWidth(400);
		fullSOIDscantext.setTranslateX(70);
		fullSOIDscantext.setTranslateY(-230);
		fullSOIDscantext.setTextFill(Color.WHITE);
		fullSOIDscantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		TextField fullSOIDscan = new TextField();
		fullSOIDscan.setPromptText("Enter/Scan SOID Number:");
		fullSOIDscan.setMaxWidth(190);
		fullSOIDscan.setTranslateX(330);
		fullSOIDscan.setTranslateY(-230);
		fullSOIDscan.setStyle(
				"-fx-background-color:White;-fx-border-color:black;-fx-border-radius:5;-fx-base:lightblue;-fx-padding: 8;-fx-text-inner-color: Black;");
		fullSOIDscan.setText(fulSoidNumber);

		Button fullSOIDenterButton = new Button("Enter");
		fullSOIDenterButton.setTranslateX(-220);
		fullSOIDenterButton.setTranslateY(-230);
		fullSOIDenterButton.setPrefSize(90, 36);
		fullSOIDenterButton.setDisable(true);

		Button fullSOIDscanButton = new Button("Scan");
		fullSOIDscanButton.setTranslateX(-160);
		fullSOIDscanButton.setTranslateY(-230);
		fullSOIDscanButton.setPrefSize(60, 36);
		fullSOIDscanButton.setDisable(true);

		scantext = new Label("Enter/Scan Product Code:");
		scantext.setMaxWidth(400);
		scantext.setTranslateX(70);
		scantext.setTranslateY(-200);
		scantext.setTextFill(Color.WHITE);
		scantext.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, fontSize));

		scan = new TextField();
		scan.setPromptText("Enter/Scan Product Code:");
		scan.setMaxWidth(190);
		scan.setTranslateX(330);
		scan.setTranslateY(-200);
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
							LogFile.logfile(
									logDate + "\s "+ LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

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
								ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID",
										String.valueOf(fulfillmentscannedSOIDint));
								String fulillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
								int fulFillmenttotalSOIDint = Integer.parseInt(fulillmenttotalSOID);
								if (fulfillmentscannedSOIDint == fulFillmenttotalSOIDint) {
									ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));

									try {
										LogFile.logfile(logDate
												+ "\s "+ LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

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
										LogFile.logfile(logDate
												+ "\s "+ LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									deliverySOID();
								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						} else {

							Validations.isValidConfirmMessage();
						}
					} else {

						try {
							LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);

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

		enterButton.setTranslateX(-220);
		enterButton.setTranslateY(-200);
		enterButton.setPrefSize(90, 36);

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
								ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID",
										String.valueOf(fulfillmentscannedSOIDint));
								String fulillmenttotalSOID = ApiModule.getPropertyValue("FULFILLMENT_TOTAL_SOID");
								int fulFillmenttotalSOIDint = Integer.parseInt(fulillmenttotalSOID);
								if (fulfillmentscannedSOIDint == fulFillmenttotalSOIDint) {
									ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));

									try {
										LogFile.logfile(logDate
												+ "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

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
										LogFile.logfile(logDate
												+ "\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									deliverySOID();
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

		scanButton.setTranslateX(-160);
		scanButton.setTranslateY(-200);
		scanButton.setPrefSize(60, 36);

		scanButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (barcodeScanner.getBarcodetext().equals("noValue")) {
					try {
						LogFile.logfile(logDate + "\s "+ LOG_FULFILLMENT_PRODUCT_CODE_FAILURE);

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

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				setLayout();
			}
		});

		String phoneNumber = ApiModule.getPropertyValue("PhoneNumber");
		Text PhoneNumber = new Text("Contact : " + phoneNumber);
		PhoneNumber.setFill(Color.WHITE);
		PhoneNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 22));
		PhoneNumber.setTextAlignment(TextAlignment.CENTER);
		PhoneNumber.setTranslateY(205);
		PhoneNumber.setTranslateX(-220);
		PhoneNumber.setStyle("-fx-highlight-fill: White; -fx-highlight-text-fill: BLACK;");

		forceClose.setTranslateX(300);
		forceClose.setTranslateY(-50);
		forceClose.setPrefSize(90, 36);
		forceClose.setStyle("-fx-background-color: RED");
		forceClose.setTextFill(Color.WHITE);

		forceClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				try {
					ApiModule.ForceClose();
				} catch (ParseException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vkb.view().setTranslateX(20);
		vkb.view().setTranslateY(120);

		grid2.add(scantext, 0, 5);
		grid2.add(scan, 0, 5);
		grid2.add(enterButton, 1, 5);
		grid2.add(scanButton, 1, 5);
		grid2.add(vkb.view(), 0, 3);

		grid2.add(fullSOIDscantext, 0, 4);
		grid2.add(fullSOIDscan, 0, 4);
		grid2.add(fullSOIDenterButton, 1, 4);
		grid2.add(fullSOIDscanButton, 1, 4);

		StackPane root2 = new StackPane(grid2);
		root2.getChildren().addAll(homeButton, PhoneNumber, helpButton, fulfillmenttotalSOIDtext,fulfillmentscannedSOIDtext,forceClose);

		Scene scene = new Scene(root2, 800, 480);
		root2.setStyle("-fx-background-image: url(\"blue.jpg\"); -fx-background-repeat:no-repeat;-fx-opacity: 0.8");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

}