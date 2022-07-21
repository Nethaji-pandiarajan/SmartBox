import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LogFile {
	static Date dir = new java.util.Date(System.currentTimeMillis());
	static File jardir = new File(LogFile.class.getProtectionDomain().getCodeSource().getLocation().getPath());

	static String baseDir = "." + File.separator + "Logs";

	// static String baseDir = "C:\\Users\\Neth2639\\Desktop\\Logs";
	static String format = "ddMMyyyy";
	static DateFormat dateFormatter = new SimpleDateFormat(format);
	static String date = dateFormatter.format(dir);
	String newDir = createDateBasedDirectory(baseDir, dir);
	static Logger logger = Logger.getLogger("Logger");
	private static FileHandler fh;

	public static String createDateBasedDirectory(String baseDirectory, Date argDate) {
		String newDir = null;
		if (baseDirectory != null && argDate != null) {
			try {
				String format = "ddMMyyyy";
				Class cl = Class.forName("LogFile");
				ClassLoader cls = cl.getClassLoader();
				DateFormat dateFormatter = new SimpleDateFormat(format);
				String date = dateFormatter.format(argDate);
				// check if the directory exists:
				String todaysLogDir = baseDirectory + File.separator + date; // create the path as String

				Path todaysDirectoryPath = Paths.get(todaysLogDir);
				if (Files.exists(todaysDirectoryPath)) {
					return todaysDirectoryPath.toUri().toString();
				} else {

					File dir = new File(baseDir + File.separator + date);
					boolean isCreated = dir.mkdir();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newDir;

	}

	public static boolean logfile(String text) throws Exception, IOException {
		String newDir = LogFile.createDateBasedDirectory(baseDir, dir);

		try {
			String orderType = ApiModule.getPropertyValue("ORDER_TYPE");
		    String filename;
			if (orderType.equals("REFILL")) {
				filename = ApiModule.getPropertyValue("CURRENT_DC_NUMBER");
				
			} else {
				filename= ApiModule.getPropertyValue("CURRENT_SO_NUMBER");
				}
			
			File f = new File(baseDir + File.separator + date + File.separator + filename+".txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(f, true));

			out.write("\n" + text);
			out.close();
		}

		// Catch block to handle the exceptions
		catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
