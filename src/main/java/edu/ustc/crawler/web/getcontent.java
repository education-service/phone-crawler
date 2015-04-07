package edu.ustc.crawler.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getcontent {

	public static String res = "", a = "";

	public static String gethtml(String uurl) throws IOException {

		URL url = new URL(uurl);
		System.out.println(url.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(8 * 1000);
		conn.setDoInput(true);
		conn.connect();
		InputStream is = conn.getInputStream();
		//		BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
		byte[] buffer = new byte[2048];
		String line, result = "";
		while ((line = reader.readLine()) != null)

			//result=line+result;
			//{   result=line+result;
			System.out.println(">>" + line);
		//}

		System.out.println("=======" + result);

		//		  Document doc;
		//	      doc = Jsoup.parse(line);
		//	      System.out.println("bbbbb"+doc.body().text());
		//	      Elements div = doc.select("div.second-sum-cont");
		//	      a=div.text();
		//	    //  res=a+res;
		//
		//		 is.close();
		//		 System.out.println("******"+a);
		return res;
	}

}