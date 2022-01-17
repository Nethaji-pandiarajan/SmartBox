import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiModule {

	private static Properties prop;

	static {
		InputStream is = null;
		try {
			prop = new Properties();
			Class cl = Class.forName("ApiModule");
			InputStream fis;
			fis = (InputStream) cl.getResourceAsStream("conf.properties");

			// is = ClassLoader.class.getResourceAsStream("conf.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	public static void main(String[] args) {

	}

	public static String RefillValidate(String dcNum) throws IOException {

		String URL = getPropertyValue("server_host") + "/validateData";
		HttpPost post = new HttpPost(URL);
		String result = "";
		String api = getPropertyValue("api_key");
		post.addHeader("apiKey", api);
		post.addHeader("Content-Type", "application/json");
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"orderType\":\"REFILL\",");
		String dc = "\"dcNum\":" + "\"" + dcNum + "\"";
		json.append(dc);
		json.append("}");
		// send a JSON data
		post.setEntity(new StringEntity(json.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		}
		return result;
	}

	public static String DeliveryValidate(String soNum) throws IOException {

		String URL = getPropertyValue("server_host") + "/validateData";
		HttpPost post = new HttpPost(URL);
		String result = "";
		String api = getPropertyValue("api_key");
		post.addHeader("apiKey", api);
		post.addHeader("Content-Type", "application/json");
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"orderType\":\"DELIVERY\",");
		String dc = "\"soNo\":" + "\"" + soNum + "\"";
		json.append(dc);
		json.append("}");
		// send a JSON data
		post.setEntity(new StringEntity(json.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		}

		return result;
	}

	public static String RefillValidateOTP(String dcNum, String OTP) throws IOException {

		String URL = getPropertyValue("server_host") + "/getAllDetils";
		HttpPost post = new HttpPost(URL);
		String result = "";
		post.addHeader("apiKey", getPropertyValue("api_key"));
		post.addHeader("Content-Type", "application/json");
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"orderType\":\"REFILL\",");
		String dc = "\"dcNum\":" + "\"" + dcNum + "\" ,";
		json.append(dc);
		String otp = "\"otp\":" + "\"" + OTP + "\" ";
		json.append(otp);
		json.append("}");

		// send a JSON data
		post.setEntity(new StringEntity(json.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		}

		return result;
	}

	public static String deliveryValidateOTP(String soNum, String OTP) throws IOException {

		String URL = getPropertyValue("server_host") + "/getAllDetils";
		HttpPost post = new HttpPost(URL);
		String result = "";
		post.addHeader("apiKey", getPropertyValue("api_key"));
		post.addHeader("Content-Type", "application/json");
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"orderType\":\"DELIVERY\",");
		String dc = "\"soNo\":" + "\"" + soNum + "\" ,";
		json.append(dc);
		String otp = "\"otp\":" + "\"" + OTP + "\" ";
		json.append(otp);
		json.append("}");

		// send a JSON data
		post.setEntity(new StringEntity(json.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		}

		return result;
	}

	public static int getScanSize(String inpArr) {
		int num = 0;
		// String result = ApiModule.sendPOST(url, dcNumber);
		JSONObject resultjson = new JSONObject(inpArr);

		JSONArray jsonArray = resultjson.getJSONArray("data");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject explrObject = jsonArray.getJSONObject(i);
		}

		return jsonArray.length();
	}

	public static boolean validDC(JSONArray jsonArray, String dcNum) {
		return jsonArray.toString().contains("\"trackingNo\":\"" + dcNum + "\"");
	}

	public static boolean validProductCode(JSONArray jsonArray, String trackNo, String ProdCode) {
		boolean found = false;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject objects = jsonArray.getJSONObject(i);
			Iterator key = objects.keys();

			while (key.hasNext()) {
				String k = key.next().toString();

				if (k.equals("trackingNo")) {

					if (objects.getString(k).equals(trackNo)) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				if (objects.getString("prodCode").equals(ProdCode)) {
					return true;
				} else {
					return false;
				}
			}

		}

		return false;
	}

	public static String setpropertyValue(String key, String value) throws IOException {
		OutputStream out = null;
		try {
			Class cl = Class.forName("ApiModule");
			URL resource = cl.getResource("conf.properties");
			String path = resource.getPath();
			out = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop.setProperty(key, value);
		prop.store(out, null);
		out.close();
		return "success";
	}

	public static String[] toStringArray() {
		String arrayString = getPropertyValue("CHECK_TRACKNO_ENTERED");
	//	System.out.println(arrayString);
		String[] arr = arrayString.split(" ");

		return arr;

	}

	public static String[] toFinalApiStringArray() {
		String arrayString = getPropertyValue("FINAL_API_DATA");
		String[] arr = arrayString.split(" ");

		return arr;

	}

	public static String getLockerName(JSONArray jsonArray, String trackNo) {
		boolean found = false;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject objects = jsonArray.getJSONObject(i);
			Iterator key = objects.keys();

			while (key.hasNext()) {
				String k = key.next().toString();
				if (k.equals("trackingNo")) {
					if (objects.getString(k).equals(trackNo)) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				String lockName = objects.getString("lockerName");
				return lockName;
			}

		}
		return "null";

	}

	public static boolean FinalApiCall() throws ClientProtocolException, IOException {

		String arrayString = getPropertyValue("FINAL_API_DATA");
		String[] arr = arrayString.split("$");

		JSONObject objout = new JSONObject();
		String orderType = getPropertyValue("ORDER_TYPE");
		String OrderValue;
		objout.put("orderType", orderType);
		if (orderType.equals("REFILL")) {
			OrderValue = getPropertyValue("CURRENT_DC_NUMBER");
			objout.put("dcNum", OrderValue);
		} else {
			OrderValue = getPropertyValue("CURRENT_SO_NUMBER");
			objout.put("soNo", OrderValue);
		}

		JSONArray arrout = new JSONArray();
		for (int i = 0; i < arr.length; i++) {
			String[] arr2 = arr[i].split(",");

			JSONObject inarr = new JSONObject();
			inarr.put("trackingNo", arr2[0]);
			inarr.put("lockerName", arr2[1]);
			inarr.put("prodCode", arr2[2]);
			inarr.put("status", arr2[3]);
			inarr.put("lockerOpenDate", arr2[4]);
			inarr.put("lockerCloseDate", arr2[5]);
			arrout.put(inarr);
		}
		objout.put("list", arrout);

		String URL = getPropertyValue("server_host") + "/updateDetils";
		HttpPost post = new HttpPost(URL);
		String result = "";
		String api = getPropertyValue("api_key");
		post.addHeader("apiKey", api);
		post.addHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(objout.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		}

		return true;
	}

	public static boolean ForceClose() throws ParseException, IOException {

		String arrayString = getPropertyValue("FINAL_API_DATA");
		String[] arr = arrayString.split("$");

		JSONObject objout = new JSONObject();
		String orderType = getPropertyValue("ORDER_TYPE");
		String OrderValue;
		objout.put("orderType", orderType);
		if (orderType.equals("REFILL")) {
			OrderValue = getPropertyValue("CURRENT_DC_NUMBER");
			objout.put("dcNum", OrderValue);
		} else {
			OrderValue = getPropertyValue("CURRENT_SO_NUMBER");
			objout.put("soNo", OrderValue);
		}

		JSONArray arrout = new JSONArray();
		for (int i = 0; i < arr.length; i++) {
			String[] arr2 = arr[i].split(",");
			if (arr2.length > 1) {
				JSONObject inarr = new JSONObject();
				inarr.put("trackingNo", arr2[0]);
				inarr.put("lockerName", arr2[1]);
				inarr.put("prodCode", arr2[2]);
				inarr.put("status", arr2[3]);
				inarr.put("lockerOpenDate", arr2[4]);
				inarr.put("lockerCloseDate", arr2[5]);
				arrout.put(inarr);
			}
		}

		JSONObject jsonObject = new JSONObject(getPropertyValue("OTP_DATA"));
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		if (arrout.length() != 0) {

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject loop1 = (JSONObject) jsonArray.get(i);
				String track1 = (String) loop1.get("trackingNo");
				System.out.println(loop1.get("trackingNo"));
				int flag = 0;
				for (int j = 0; j < arrout.length(); j++) {
					JSONObject loop2 = (JSONObject) arrout.get(j);
					String track2 = (String) loop2.get("trackingNo");
					if (track1.equals(track2)) {
						flag = 1;
					}

				}
				if (flag == 0) {
					JSONObject inarr = new JSONObject();
					inarr.put("trackingNo", track1);
					inarr.put("lockerName", loop1.get("lockerName"));
					inarr.put("prodCode", loop1.get("prodCode"));
					inarr.put("status", "FAILURE");
					inarr.put("lockerOpenDate", "");
					inarr.put("lockerCloseDate", "");
					arrout.put(inarr);
				}

			}

		} else {

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject loop1 = (JSONObject) jsonArray.get(i);

				JSONObject inarr = new JSONObject();
				inarr.put("trackingNo", loop1.get("trackingNo"));
				inarr.put("lockerName", loop1.get("lockerName"));
				inarr.put("prodCode", loop1.get("prodCode"));
				inarr.put("status", "FAILURE");
				inarr.put("lockerOpenDate", "");
				inarr.put("lockerCloseDate", "");
				arrout.put(inarr);
			}

		}
		objout.put("list", arrout);
		System.out.println(objout);

		String URL = getPropertyValue("server_host") + "/updateDetils";
		// System.out.println(URL);
		HttpPost post = new HttpPost(URL);
		String result = "";
		String api = getPropertyValue("api_key");
		// System.out.println(api);
		post.addHeader("apiKey", api);
		post.addHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(objout.toString()));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			result = EntityUtils.toString(response.getEntity());
		}

		System.out.println("------>" + result);
		return true;
	}

}