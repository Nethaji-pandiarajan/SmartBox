
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.fazecast.jSerialComm.SerialPort;

public class LockerOpen {

	static Scanner ardout2 = null;

	static String lockerresult = "";
	//static Scanner ardout;
		
	static int time1 = 0;
	 static String formattedlockOpenDate;
	static boolean check = false;
	volatile static CountDownLatch cl = new CountDownLatch(1);
	public static boolean OpenLocker(String doorname_inp) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date datelog = new Date();

		String logDate = dateFormat.format(datelog);

		String result = "";

		String doorname = ApiModule.getPropertyValue(doorname_inp);

		byte[] b = doorname.getBytes();
		// byte[] b2 = doorname.getBytes(Charset.forName("UTF-8"));
		// byte[] b3 = doorname.getBytes(StandardCharsets.UTF_8); // Java 7+ only
		// SerialPort sp = SerialPort.getCommPort("COM5");
		SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("PORT_CONF")); // device name TODO: must be
																							// changed
		// System.out.println(SerialPort.getCommPorts()[1]);
		sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
		sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0); // block until bytes can be written
		System.out.println(b);

		if (sp.openPort()) {
			System.out.println("Port is opened");
			sp.writeBytes(b, 10);
			
			 final ThreadPoolExecutor executor = 
					  (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

					executor.submit(new Runnable() {
					     @Override 
					     public void run() { 
					    	 Scanner ardout = new Scanner(sp.getInputStream());
					    	 System.out.println("Waiting for door opening rsponse..");
					    	 
						     //  MyComPortListener mcp = new MyComPortListener();
						   //    sp.addDataListener(mcp);
					          //  while (true) {
					              //  String res1=ardout.nextLine();
					           //     System.out.println(++x);
			while(ardout.hasNextLine()) {
//					            		
//					            		res1=res1+ardout.next();
//					            		if(res1.length()==10) {
//					            			break;
//					            		}
//					            	}
//					            	System.out.println("Bytes to String"+res1);
//					            	
					        System.out.println("Got response");
							String res1 = ardout.nextLine();
							System.out.println(res1);
							//	if  (Integer.parseInt(ApiModule.getPropertyValue("LOCKER_OPEN_SIGNAL")) == 1)  {
							if(res1.contains("open") || res1.contains("Open") || res1.contains("q")) {
									System.out.println("From Arduino Opened locker name :" + res1);
									LocalDateTime lock_open_time = LocalDateTime.now();
									DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
								   formattedlockOpenDate = lock_open_time.format(myFormatObj1);
									System.out.println("Locker Opened At :" + formattedlockOpenDate);
									try {
										LogFile.logfile(logDate + "\s " + "Locker Opened Time :" + formattedlockOpenDate);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									try {
										ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", formattedlockOpenDate);
									} catch (IOException e) {

										e.printStackTrace();
									}
									lockerresult = formattedlockOpenDate;
									try {
										ApiModule.setpropertyValue("LOCKER_OPEN_SIGNAL","1");}
										catch(Exception e) {
											
										}
									System.out.println("Data received from after door opened!");
								//	sp.closePort();
									
								}
								if (res1.contains("close") || res1.contains("Close")) {
									System.out.println("From Arduino Closed locker name :" + res1);
									LocalDateTime lock_close_time = LocalDateTime.now();
									DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
									String formattedlockCloseDate = lock_close_time.format(myFormatObj1);
									System.out.println("Locker Closed At :" + formattedlockCloseDate);
									try {
										LogFile.logfile(logDate + "\s " + "Locker Closed Time :" + formattedlockCloseDate);

									} catch (Exception ex) {
										ex.printStackTrace();
									}
									try {
										ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", formattedlockCloseDate);
									} catch (IOException e) {

										e.printStackTrace();
									}
									lockerresult = formattedlockCloseDate;
									try {
									ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL","1");}
									catch(Exception e) {
										
									}
									
									sp.closePort();
									System.out.println("Response recevied after door close, Port Closed");
									break;
								}
						
								}
					     }
					});
/*
					executor.submit(new Runnable() {
					     @Override 
					     public void run() { 
					    	 System.out.println("Waiting for door closing response..");
				    	        
			 		         //   System.out.println("Running...final close loop.");
					            while (ardout.hasNextLine()) {
//					            	String res="";
//					            	
//					            	while(ardout.hasNext()) {
//					            		
//					            		res=res+ardout.next();
//					            		if(res.length()==10) {
//					            			break;
//					            		}
//					            	}
//					            	System.out.println("Bytes to String"+res);
//					            	
					            //	System.out.println("Running...final close loop.");
					           //    
					            //	Scanner ardout = new Scanner(sp.getInputStream());
							//if () {
							System.out.println("From Arduino Closed locker name :" + ardout.nextLine());
								LocalDateTime lock_close_time = LocalDateTime.now();
								DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
								String formattedlockCloseDate = lock_close_time.format(myFormatObj1);
								System.out.println("Locker Closed At :" + formattedlockCloseDate);
								try {
									LogFile.logfile(logDate + "\s " + "Locker Closed Time :" + formattedlockCloseDate);

								} catch (Exception ex) {
									ex.printStackTrace();
								}
								try {
									ApiModule.setpropertyValue("GLOBAL_LOCKER_CLOSE", formattedlockCloseDate);
								} catch (IOException e) {

									e.printStackTrace();
								}
								lockerresult = formattedlockCloseDate;
								try {
								ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL","1");}
								catch(Exception e) {
									
								}
								System.out.println("Response recevied after door close");
								break;
					            }
								
			    	        	 
					     }
					});
//				Executors.newSingleThreadExecutor().execute(new Runnable() {
//	    	        @Override
//	    	        public void run() {
//	    	        	 
//	    	        }
//	    	        
//	    	        });
				*/
//				Executors.newSingleThreadExecutor().execute(new Runnable() {
//	    	        @Override
//	    	        public void run() {
//	    	        	 
//	    	        	 
//	    	        	 
//	    	        	 
//	    	        	 
//	    	        	 
//	    	        } });


		} else {
			System.out.println("Port is not opened");

			return false;
		}
//sp.closePort();
		System.out.println("Locker open returns true");
		return true;

	}

/*	public static boolean OpenLockerConfirmation() {
		
	
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date datelog = new Date();
	    
		String logDate = dateFormat.format(datelog);

		SerialPort sp = SerialPort.getCommPort(ApiModule.getPropertyValue("PORT_CONF")); // device name TODO: must be
		// changed
		
		sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
		sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
		
		
		
		if (sp.openPort()) {

			System.out.println("Port is opened for Opening Locker!");

			 Scanner ardout = new Scanner(sp.getInputStream());
			Executors.newSingleThreadExecutor().execute(new Runnable() {
    	        @Override
    	        public void run() {
    	            System.out.println("Waiting for door opening rsponse..");
	        //    int x = 0;
	            while (ardout.hasNextLine()) {
	              
	           //     System.out.println(++x);
	        
			//	if () {
					System.out.println("From Arduino Opened locker name :" + ardout.nextLine());
					LocalDateTime lock_open_time = LocalDateTime.now();
					DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
				   formattedlockOpenDate = lock_open_time.format(myFormatObj1);
					System.out.println("Locker Opened At :" + formattedlockOpenDate);
					try {
						LogFile.logfile(logDate + "\s " + "Locker Opened Time :" + formattedlockOpenDate);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					try {
						ApiModule.setpropertyValue("GLOBAL_LOCKER_OPEN", formattedlockOpenDate);
					} catch (IOException e) {

						e.printStackTrace();
					}
					lockerresult = formattedlockOpenDate;
					System.out.println("Data received from after door opened!");
					sp.closePort();
					break;
					
					
				} 
				
				
		
				//}
    	        }
    	        
    	        });
			
		} else {
			System.out.println("Port is not opened");

			return false;
		}
		//sp.closePort();
		return check;


	}*/
	
}
