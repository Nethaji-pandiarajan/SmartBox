
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class CaptureImage {
	// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	// Store image as 2D matrix

	static Logger logger = Logger.getLogger("Logger");
	static FileHandler fh;
	static Date argdate = new java.util.Date(System.currentTimeMillis());

	static String format = "ddMMyyyy";
	static DateFormat dateFormatter = new SimpleDateFormat(format);
	static String date = dateFormatter.format(argdate);

	 static File jardir = new File(CaptureImage.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	 
		String jarpath1 ="."+File.separator+"Logs";
		

		static String baseDir = "."+File.separator+"Logs";

	public static boolean captureImage() {
		String text = "";
		Webcam webcam = Webcam.getDefault(); // Get default camera
		if (webcam == null) // Can't find camera
			return false;

		webcam.close();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open(); // Open the camera
		try { // save the picture captured
			Date dir = new java.util.Date(System.currentTimeMillis());
		//	String baseDir = "C:\\Users\\Neth2639\\Desktop\\Logs";
			LogFile.createDateBasedDirectory(text, dir);
			String format = "ddMMyyyy";
			DateFormat dateFormatter = new SimpleDateFormat(format);
			String date = dateFormatter.format(argdate);
			String count = ApiModule.getPropertyValue("IMAGE_COUNT");
			int countdup = Integer.parseInt(count);
			if (ApiModule.getPropertyValue("CURRENT_SO_NUMBER").equals("")) {
				text = ApiModule.getPropertyValue("CURRENT_DC_NUMBER");
			} else {
				text = ApiModule.getPropertyValue("CURRENT_SO_NUMBER");
			}
			String countprefix = String.format("%03d", countdup);
			ImageIO.write(webcam.getImage(), "PNG",
					new File(baseDir+File.separator + date + File.separator+ text + "_" + count + ".png"));
			int countupdate = Integer.parseInt(count);
			countupdate++;
			ApiModule.setpropertyValue("IMAGE_COUNT", String.valueOf(countupdate));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		webcam.close(); // Close the camera
		return true;

	}

	public static boolean BarcodecaptureImage() {

		Webcam webcam = Webcam.getDefault(); // Get default camera
		if (webcam == null) // Can't find camera
			return false;

		webcam.close();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open(); // Open the camera
		try { // save the picture captured
				// String count =

			ImageIO.write(webcam.getImage(), "PNG", new File(baseDir+File.separator+"barcode.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		webcam.close(); // Close the camera
		return true;

	}

}
