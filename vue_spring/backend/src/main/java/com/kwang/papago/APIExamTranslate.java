package com.kwang.papago;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.annotation.JsonValue;
public class APIExamTranslate {

	public static String checkPapagoLang(String input) {
		String result = null;
		if(input.equals("en-US")) {
			result = "en";
		} else if(input.equals("ja-JP")) {
			result = "ja";
		}
		return result;
	}
    public static String EngToKoR (String inputString, String startLanguage, String targetLanguage) {
        String clientId = "f9p9dh0gy0";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "hEF5eQKH90DyfMcJtu0zzm4LwxWRIq9XlYLQvYe8";//애플리케이션 클라이언트 시크릿값";

        try {
            String text = URLEncoder.encode(inputString, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            // post request
            String postParams = "source="+ startLanguage + "&target=" + targetLanguage + "&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObj = (JSONObject) obj;
            JSONObject message = (JSONObject) jsonObj.get("message");
            JSONObject result = (JSONObject) message.get("result");
            String translatedText = (String) result.get("translatedText");
            return translatedText;
        } catch (Exception e) {
            System.out.println(e);
            return "error to translate";
        }
    }
}