
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Timer;

public class barcodeScanner
{
	 static File jardir = new File(LogFile.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	 
		String jarpath1 =jardir.getParent()+File.separator+"Logs";
		

		static String baseDir = jardir.getParent()+File.separator+"Logs";
		
	public static String getBarcodetext() {
		
		
		if(CaptureImage.BarcodecaptureImage()) {
			File inputDir = new File(baseDir+File.separator+"barcode.png");

			try (BufferedInputStream bfin = new BufferedInputStream(new FileInputStream(inputDir)))
			{
				BufferedImage bfi = ImageIO.read(bfin);
				if (bfi == null)
				{
					return "noValue";
				}
				LuminanceSource ls = new BufferedImageLuminanceSource(bfi);
				BinaryBitmap bmp = new BinaryBitmap(new HybridBinarizer(ls));

				GenericMultipleBarcodeReader reader = new GenericMultipleBarcodeReader(new MultiFormatReader());
				Result[] results;

				Map<DecodeHintType, Object> decodeHints = new EnumMap<>(DecodeHintType.class);
				decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

				results = reader.decodeMultiple(bmp, decodeHints);
				//System.out.println("Found " + results.length + " barcodes in " + filename);
				int i = 0;
				for (i=0;i<1;i++) {
					
					 
			 	    return results[i].getText();}
			}
			catch (NotFoundException e)
			{
				return "noValue";
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			return "noValue";
		}
		return "noValue";
		
		
	}
	
}
	
	

