package codeine.utils.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import codeine.utils.ExceptionUtils;

import com.google.common.base.Function;

public class HttpUtils
{
	private static final Logger log = Logger.getLogger(HttpUtils.class);
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static String doGET(String url,Map<String,String> headers)
	{
		final StringBuilder $ = new StringBuilder();
		doGET(url, new OutputToStringFunction($), headers);
		return $.toString();
	}
	
	public static void doGET(String url, Function<String, Void> function, Map<String,String> headers)
	{
		try
		{
			URL u = new URL(url);
			URLConnection c = u.openConnection();
			addHeaders(headers, c);
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;
			//TODO should have some kind of timeout mechanism
			while ((inputLine = in.readLine()) != null)
			{
				function.apply(inputLine);
			}
			in.close();
		}
		catch (Exception ex)
		{
			throw ExceptionUtils.asUnchecked(ex);
		}
	}
	
	public static void doPOST(String url, String postData, Function<String, Void> function, Map<String,String> headers) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			addHeaders(headers, con);
 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postData);
			wr.flush();
			wr.close();
 
			int responseCode = con.getResponseCode();
			log.debug("resopnse code is " + responseCode);
			if (responseCode != 200){
				throw new RuntimeException("bad response " + responseCode + " for request " + url);
			}
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				function.apply(inputLine);
			}
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static String doPOST(String url, String postData, Map<String,String> headers) {
		StringBuilder stringBuilder = new StringBuilder();
		doPOST(url, postData, new OutputToStringFunction(stringBuilder), headers);
		return stringBuilder.toString();
	}

	public static String encode(String parameters){
		try {
			return URLEncoder.encode(parameters, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String specialEncode(String value) {
		return value.replace(':', '_').
				replace(' ', '_').
				replace('>', '_').
				replace('<', '_').
				replace('*', '_').
				replace('"', '_').
				replace('|', '_').
				replace('+', '_').
				replace('/', '_').
				replace('?', '_').
				replace('\\', '_');
	}
	public static String decode(String parameters) {
		try {
			return URLDecoder.decode(parameters, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void addHeaders(Map<String, String> headers, URLConnection c) {
		if (headers != null) {
			for (Entry<String, String> e : headers.entrySet()) {
				c.setRequestProperty(e.getKey(), e.getValue());
			}
		}
	}
}
