import java.nio.charset.StandardCharsets;

public class ReformalBuffer {

	public String outputString;

	static int outofASCII = 10;

	static String cummulativeTemporaryBuffer = "";

	public static void parseByteArray(byte[] readBuffer) {

		String readBufferSnippet = new String(readBuffer);

		//cummulativeTemporaryBuffer = cummulativeTemporaryBuffer.concat(readBufferSnippet);

		String outputString = new String (readBuffer,StandardCharsets.UTF_8);
		
		//	cummulativeTemporaryBuffer = cummulativeTemporaryBuffer.substring(cummulativeTemporaryBuffer.indexOf(outofASCII)+1);

			System.out.println("Data Through Listener "+outputString);
			if (outputString.contains("Open") || outputString.contains("open")) {
				try {
					ApiModule.setpropertyValue("LOCKER_OPEN_SIGNAL","1");}
					catch(Exception e) {
						
					}
			}
			
			if (outputString.contains("Close") || outputString.contains("close")) {
				try {
					ApiModule.setpropertyValue("LOCKER_CLOSED_SIGNAL","1");}
					catch(Exception e) {
						
					}
			}

		
	}

}
