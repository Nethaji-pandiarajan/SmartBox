import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.fazecast.jSerialComm.SerialPort;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Validations {

	static String lockerresult = "";

	static Scanner ardout;

	static Optional<ButtonType> result;

	static int time1 = 0;
	static boolean check = false;
	volatile static CountDownLatch cl = new CountDownLatch(1);

	public static boolean isValidConfirmMessage(String deliverySoidNumber, String lockname, String productCode,
			String qty, int scannedSOIDint, int totalSOIDint) {
		System.out.println(
				"deliverySoidNumber: " + deliverySoidNumber + "\nlockname" + lockname + "\nproductCode: " + productCode
						+ "\nqty: " + qty + "\nscannedSOIDint: " + scannedSOIDint + "\ntotalSOIDint: " + totalSOIDint);

		// System.out.println("Waiting for door closed response...");
		String LOG_DELIVERY_PRODUCT_CODE_FAILURE = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_FAILURE");
		String LOG_DELIVERY_PRODUCT_CODE_SUCCESS = ApiModule.getPropertyValue("LOG_DELIVERY_SO_NUMBER_SUCCESS");
		String DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT = ApiModule
				.getPropertyValue("DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT");
		String DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_PRODUCT_CODE_FAILURE_ERROR_ALERT_HEADER");
		String DELIVERY_PRODUCT_CODE_SUCCESS_ALERT = ApiModule.getPropertyValue("DELIVERY_PRODUCT_CODE_SUCCESS_ALERT");
		String DELIVERY_PRODUCT_CODE_SUCCESS_ALERT_HEADER = ApiModule
				.getPropertyValue("DELIVERY_PRODUCT_CODE_SUCCESS_ALERT_HEADER");

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date datelog = new Date();

		String logDate = dateFormat.format(datelog);

		SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("PORT_CONF"));

		sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
		sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

		// if (sp.openPort()) {
		// sp.addDataListener(new SerialDataListener);
		// Scanner ardout2 = new Scanner(sp.getInputStream());
		// String res= ardout2.nextLine();
		// System.out.println("Port is opened");

		System.out.println("Waiting for final response");

		// Scanner ardout = new Scanner(sp.getInputStream());
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				// int x = 0;

				while (true) {
					if (Integer.parseInt(ApiModule.getPropertyValue("LOCKER_CLOSED_SIGNAL")) == 1) {
						System.out.println("Running...final close loop.");
						//
						// Scanner ardout = new Scanner(sp.getInputStream());
						// if () {
						// System.out.println("From Arduino Closed locker name :" + ardout.nextLine());
						// LocalDateTime lock_close_time = LocalDateTime.now();
						// DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd
						// HH:mm:ss");
						// String formattedlockCloseDate = lock_close_time.format(myFormatObj1);
						// System.out.println("Locker Closed At :" + formattedlockCloseDate);
						// try {
						/*
						 * LogFile.logfile(logDate + "\s " + "Locker Closed Time :" +
						 * formattedlockCloseDate);
						 * 
						 * // } catch (Exception ex) { ex.printStackTrace(); } try {
						 * ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", formattedlockCloseDate); }
						 * catch (IOException e) {
						 * 
						 * e.printStackTrace(); } lockerresult = formattedlockCloseDate;
						 */
						if (popupAlert()) {
							String lockerOpenTime2 = ApiModule.getPropertyValue("GLOBAL_LOCKER_OPEN");
							String lockerCloseTime1 = ApiModule.getPropertyValue("GLOBAL_LOCKER_CLOSE");

							// String lockerCloseTime = ApiModule.getPropertyValue("GLOBAL_LOCKER_CLOSE");

							String[] Finalarray = ApiModule.toFinalApiStringArray();
							Finalarray = Arrays.copyOf(Finalarray, Finalarray.length + 1);
							String objData = deliverySoidNumber + "," + lockname + "," + productCode + ",SUCCESS,"
									+ lockerOpenTime2 + "," + lockerCloseTime1 + "," + qty;
							Finalarray[Finalarray.length - 1] = objData; // Ass
							try {
								LogFile.logfile(logDate + "\\s " + "SOID  :" + deliverySoidNumber);
								String orderType = ApiModule.getPropertyValue("ORDER_TYPE");
								String OrderValue;
								if (orderType.equals("REFILL")) {
									OrderValue = ApiModule.getPropertyValue("CURRENT_DC_NUMBER");

								} else {
									OrderValue = ApiModule.getPropertyValue("CURRENT_SO_NUMBER");
								}
								LogFile.logfile(logDate + "\\s " + "OrderType : " + orderType);
								LogFile.logfile(logDate + "\\s " + "SO # : " + OrderValue);
								LogFile.logfile(logDate + "\\s " + "LockerID : " + lockname);
								LogFile.logfile(logDate + "\\s " + "ProductCode: " + productCode);
								LogFile.logfile(logDate + "\\s " + "Qty : " + qty);
								LogFile.logfile(logDate + "\\s " + "LockerOpenDate : " + lockerOpenTime2);
								LogFile.logfile(logDate + "\\s " + "LockerCloseDate : " + lockerCloseTime1);
								LogFile.logfile(logDate + "\\s " + "SOID  :" + deliverySoidNumber + ",OrderType : "
										+ orderType + "SO# : " + OrderValue + ",LockerID : " + lockname
										+ ",ProductCode: " + productCode + ",Qty : " + qty + ",LockerOpenDate : "
										+ lockerOpenTime2 + ",LockerCloseDate : " + lockerCloseTime1);

							} catch (Exception ex) {
								ex.printStackTrace();
							}

							String fs = "";
							for (int i = 0; i < Finalarray.length; i++) {
								fs = fs + Finalarray[i];
								if (fs.length() != 0) {
									fs = fs + "$";
								}
							}

							try {
								ApiModule.setpropertyValue("FINAL_API_DATA", fs);
								String[] SOIDArray = ApiModule.toStringArray();
								SOIDArray = Arrays.copyOf(SOIDArray, SOIDArray.length + 1);
								SOIDArray[SOIDArray.length - 1] = deliverySoidNumber; // Assign 40 to the last element
								String fs1 = "";
								for (int i = 0; i < SOIDArray.length; i++) {
									fs1 = fs1 + SOIDArray[i];
									fs1 = fs1 + " ";
								}
								try {
									ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs1);
								} catch (IOException e1) {
									e1.printStackTrace();
								}

								if (scannedSOIDint == totalSOIDint) {

									ApiModule.setpropertyValue("DELIVERY_SCANNED_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("DELIVERY_TOTAL_SOID", String.valueOf(0));
									try {
										LogFile.logfile(logDate + "\\s " + LOG_DELIVERY_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									Platform.runLater(() -> {
										Alert errorAlert1 = new Alert(AlertType.INFORMATION);

										errorAlert1.setHeaderText(DELIVERY_PRODUCT_CODE_SUCCESS_ALERT_HEADER);
										errorAlert1.setContentText(DELIVERY_PRODUCT_CODE_SUCCESS_ALERT);
										errorAlert1.showAndWait();
										try {
											ApiModule.setpropertyValue("SUCCESS_FLAG", "1");
										} catch (IOException e) {
											e.printStackTrace();
										}
									});

									if (ApiModule.FinalApiCall()) {
										// ApiModule.setpropertyValue("FINAL_API_DATA", "");
									}
									ApiModule.setpropertyValue("ALL_CURR_TRACK_NUMBER", "");
									ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
									ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
									ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
									ApiModule.setpropertyValue("ORDER_TYPE", "");

									ApiModule.setpropertyValue("GLOBAL_ALL_CLOSE", "1");
									// SmartBox.gotoSetLayout();
								} else {
									SmartBox.gotodeliveryLayout();

								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}
						break;
					}

				}
				// else {
				// System.out.println("Not receiving any Line...");
				// }

				// }
			}

		});

		sp.closePort();
		// }

		return true;
	}

	public static boolean isValidConfirmMessage2(String fulSoidNumber, String lockname, String productCode, String qty,
			int fulfillmentscannedSOIDint, int fulFillmenttotalSOIDint) {
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

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date datelog = new Date();

		String logDate = dateFormat.format(datelog);

		SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("PORT_CONF"));

		sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
		sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

		// if (sp.openPort()) {
		// Scanner ardout2 = new Scanner(sp.getInputStream());
		// String res= ardout2.nextLine();
		// System.out.println("Port is opened");
		// System.out.println("Waiting for final response");

		// Scanner ardout = new Scanner(sp.getInputStream());
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				// int x = 0;
				while (true) {

					//
					if (Integer.parseInt(ApiModule.getPropertyValue("LOCKER_CLOSED_SIGNAL")) == 1) {
//						System.out.println("From Arduino Closed locker name :" + ardout.nextLine());
						LocalDateTime lock_close_time = LocalDateTime.now();
						DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
						String formattedlockCloseDate = lock_close_time.format(myFormatObj1);
						System.out.println("Locker Closed At :" + formattedlockCloseDate);
						try {
							LogFile.logfile(logDate + "\\s " + "Locker Closed Time :" + formattedlockCloseDate);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						try {
							ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", formattedlockCloseDate);
						} catch (IOException e) {

							e.printStackTrace();
						}
						lockerresult = formattedlockCloseDate;
						if (popupAlert()) {
							String lockerOpenTime2 = ApiModule.getPropertyValue("GLOBAL_LOCKER_OPEN");
							String lockerCloseTime1 = ApiModule.getPropertyValue("GLOBAL_LOCKER_CLOSE");

							String[] Finalarray = ApiModule.toFinalApiStringArray(); //
							System.out.println(Finalarray.length); //
							System.out.println("Finalarray" + Finalarray);
							Finalarray = Arrays.copyOf(Finalarray, Finalarray.length + 1);
							String objData = fulSoidNumber + "," + lockname + "," + productCode + ",SUCCESS,"
									+ lockerOpenTime2 + "," + lockerCloseTime1 + "," + qty;
							Finalarray[Finalarray.length - 1] = objData;
							try {
								LogFile.logfile(logDate + "\\s " + "SOID  :" + fulSoidNumber);
								String orderType = ApiModule.getPropertyValue("ORDER_TYPE");
								String OrderValue;
								if (orderType.equals("REFILL")) {
									OrderValue = ApiModule.getPropertyValue("CURRENT_DC_NUMBER");

								} else {
									OrderValue = ApiModule.getPropertyValue("CURRENT_SO_NUMBER");

								}
								LogFile.logfile(logDate + "\\s " + "OrderType : " + orderType);
								LogFile.logfile(logDate + "\\s " + "DC# : " + OrderValue);
								LogFile.logfile(logDate + "\\s " + "LockerID : " + lockname);
								LogFile.logfile(logDate + "\\s " + "ProductCode: " + productCode);
								LogFile.logfile(logDate + "\\s " + "Qty : " + qty);
								LogFile.logfile(logDate + "\\s " + "LockerOpenDate : " + lockerOpenTime2);
								LogFile.logfile(logDate + "\\s " + "LockerCloseDate : " + lockerCloseTime1);
								LogFile.logfile(logDate + "\\s " + "SOID  :" + fulSoidNumber + ",OrderType : "
										+ orderType + "DC# : " + OrderValue + ",LockerID : " + lockname
										+ ",ProductCode: " + productCode + ",Qty : " + qty + ",LockerOpenDate : "
										+ lockerOpenTime2 + ",LockerCloseDate : " + lockerCloseTime1);

							} catch (Exception ex) {
								ex.printStackTrace();
							}

							String fs = "";
							for (int i = 0; i < Finalarray.length; i++) {
								fs = fs + Finalarray[i];

								if (fs.length() != 0) {
									fs = fs + "$";
								}
							} // System.out.println("fs"+fs);
							try {

								ApiModule.setpropertyValue("FINAL_API_DATA", fs);
								String[] SOIDArray = ApiModule.toStringArray();
								SOIDArray = Arrays.copyOf(SOIDArray, SOIDArray.length + 1);
								SOIDArray[SOIDArray.length - 1] = fulSoidNumber; // Assign 40 to the last // element
								String fs1 = "";
								for (int i = 0; i < SOIDArray.length; i++) {
									fs1 = fs1 + SOIDArray[i];
									fs1 = fs1 + " ";
								}

								try {
									System.out.println(fs1);
									ApiModule.setpropertyValue("CHECK_TRACKNO_ENTERED", fs1);
								} catch (IOException e1) {
									e1.printStackTrace();
								}

								if (fulfillmentscannedSOIDint == fulFillmenttotalSOIDint) {
									ApiModule.setpropertyValue("FULFILLMENT_SCANNED_SOID", String.valueOf(0));
									ApiModule.setpropertyValue("FULFILLMENT_TOTAL_SOID", String.valueOf(0));

									try {
										LogFile.logfile(logDate + "\\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}

									Platform.runLater(() -> {
										Alert errorAlert1 = new Alert(AlertType.INFORMATION);

										errorAlert1.setHeaderText(FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT_HEADER);
										errorAlert1.setContentText(FULFILLMENT_PRODUCT_CODE_SUCCESS_ALERT);
										errorAlert1.showAndWait();
										try {
											ApiModule.setpropertyValue("SUCCESS_FLAG", "1");
										} catch (IOException e) {
											e.printStackTrace();
										}
									});

									if (ApiModule.FinalApiCall()) { // System.out.println("Final api call"); //
										ApiModule.setpropertyValue("FINAL_API_DATA", "");
									}
									ApiModule.setpropertyValue("ALL_CURR_TRACK_NUMBER", "");
									ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(1));
									ApiModule.setpropertyValue("CURRENT_DC_NUMBER", "");
									ApiModule.setpropertyValue("CURRENT_SO_NUMBER", "");
									ApiModule.setpropertyValue("ORDER_TYPE", "");
									ApiModule.setpropertyValue("GLOBAL_ALL_CLOSE", "1");
									// SmartBox.gotoSetLayout();
								} else {
									try {
										LogFile.logfile(logDate + "\\s " + LOG_FULFILLMENT_PRODUCT_CODE_SUCCESS);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									SmartBox.gotofulfillmentLayout();
								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}

						// }
						break;

					}

				}
			}
		});

		sp.closePort();
//} 

		return true;

	}

	public static boolean popupAlert() {
//		boolean isValidMessage = false;
//		ButtonType Yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
//		ButtonType No = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
//
//		Platform.runLater(() -> {
//			Alert alert = new Alert(AlertType.WARNING, "Have you closed your Locker ? ", Yes, No);
//			alert.setTitle("Confirmation");
//
//			// System.out.println("here");
//			result = alert.showAndWait();
//		});
//		if (result.orElse(No) == Yes) {
//			isValidMessage = true;
//			Date curr_date = new java.util.Date(System.currentTimeMillis());
//			return isValidMessage;
//		}

		return true;
	}

}
