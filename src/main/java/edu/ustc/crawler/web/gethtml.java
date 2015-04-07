package edu.ustc.crawler.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class gethtml {
	public static String res = "", a = "";

	public static String Gethtml(String uurl) throws IOException {

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
		{
			System.out.println(">>" + line);
			Document doc;
			//System.out.println("///////"+exhtml);

			doc = Jsoup.parse(line);
			System.out.println("bbbbb" + doc.body().text());
			Elements div = doc.select("div.nums");
			a = div.text();
			res = a + res;
		}

		is.close();
		System.out.println("******" + res);
		return res;

	}

	/** 
	 * 根据URL获得所有的html信息 
	 * @param url 
	 * @return 
	 */
	public static String Gethtmll(String url) {
		String html = "";
		HttpClient httpClient = new DefaultHttpClient();//创建httpClient对象  
		HttpGet httpget = new HttpGet(url);//以get方式请求该URL  
		try {
			HttpResponse responce = httpClient.execute(httpget);//得到responce对象  
			int resStatu = responce.getStatusLine().getStatusCode();//返回码  
			if (resStatu == HttpStatus.SC_OK) {//200正常  其他就不对  
				//获得相应实体  
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity);//获得html源代码  
					System.out.println("******" + html);
				}
			}
		} catch (Exception e) {
			System.out.println("访问【" + url + "】出现异常!");
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		// System.out.println("******"+html);
		return html;
	}

	public static String doGet(String url) {
		// 创建一个客户端，类似于打开一个浏览器
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建一个GET方法，类似于在浏览器地址栏中输入一个地址
		HttpGet httpGet = new HttpGet(url);

		// 类似于在浏览器中输入回车，获得网页内容
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			//logger.error("Exception:{}", e);
		}
		// 查看返回内容，类似于在浏览器中查看网页源码
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			// 读入内容流，并以字符串形式返回，这里指定网页编码是UTF-8
			try {
				result = EntityUtils.toString(entity, "utf-8");
				// 网页的Meta标签中指定了编码
				EntityUtils.consume(entity); // 关闭内容流
			} catch (IOException e) {
				//logger.error("Exception:{}", e);
			}
		}
		try {
			httpClient.close();
		} catch (IOException e) {
			//logger.error("Exception:{}", e);
		}
		System.out.println("******" + result);
		return result;
	}

	public static void main(String[] args) throws IOException {
		String res = doGet("http://hf.ganji.com/shoujihao/1457895455_1445307187x.htm");
	}

}