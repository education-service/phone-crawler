package edu.ustc.crawler.phone;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.ustc.crawler.sql.Update_mysql;
import edu.ustc.crawler.zwfc.Crawler;

public class phoneDetail {

	private static String BASE_XPATH = "//*[@id=\"";
	private static String SUFFIX_XPATH = "\"]/div[1]";
	private static String SE_XPATH = "\"]/div[1]/div[2]/div[1]";
	// private  String baseurl1="http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=530180782%40qq.com%20&rsv_pq=c78e320500021ac6&rsv_t=e01czvoDW99AvvyKJeA8rpJPmhuj4lA6bDv1PAWzPwwRYuwXcjq7ktO9KAM&rsv_enter=0&rsv_sug3=1&rsv_sug4=520&rsv_sug1=1";
	private Crawler crawler;
	public int k = 0;
	public static String NO_MSG = "null";
	public String nexturl = "";
	private String content = "", ppath;
	public String selNum = "", resulthtml = "";
	public String strtelephone = "", strqq = "", straddress = "", stremail = "", strname = "", strweb = "";

	public void getUrl(String url) throws IOException {
		//	 Crawler crawler = new Crawler(url);
		//		try
		//		{
		//		System.out.println("hhhhhhhhhhhh"+k);
		//			if(k==1)
		//			{
		//				ppath = "//*[@id=\"1\"]/div[1]/div/div[2]/div[2]";
		//				content=crawler.parserByXpath(ppath);
		//			 if(content!=null)
		//					{//content="内容摘要是："+content;
		//				    content=content;
		//				    System.out.println("neirong"+content);}
		//			else
		//				{
		//				//k=k+1;
		//				ppath =	BASE_XPATH +k+ SUFFIX_XPATH;
		//				content=crawler.parserByXpath(ppath);
		//				if(content!=null)
		//					//content="内容摘要是："+content;
		//					 content=content;
		//			    System.out.println("content///"+content);
		//			   // String path="E:\\mmm"+k+".txt";
		//				}
		//			//System.out.println(ppath);
		//			 //String path="E:\\mm"+k+".txt";
		//
		//			String path="E:\\mmm"+k+".txt";
		//		    File file = new File(path);
		//			 OutputStreamWriter dos = new OutputStreamWriter(new  FileOutputStream(file,true));
		//			 BufferedWriter eos=new BufferedWriter(dos);
		//		      eos.write(content);
		//		      eos.write('\r'); // \r\n表示换行
		//			  eos.write('\n');
		//			  eos.write("  ");
		//		      eos.write('\r'); // \r\n表示换行
		//			  eos.write('\n');
		//		      eos.flush();
		//		      eos.close();
		//			//System.out.println(k);
		//		 // toTxt.toTXT(tvtitle,"d:\\yangshi.txt");
		//
		//		 }catch (Exception e) {
		//			e.printStackTrace();
		//
		//		}
		//		}
	}

	public void getContent(String urrl, String selnum) {
		try {
			// nexturl=uurl;
			//strqq="";straddress="";stremail="";strname="";strweb="";

			// URL url=null;
			System.out.println("GGGGGGGGGGGG   " + nexturl);
			// url=new URL(urrl);
			// 创建一个客户端，类似于打开一个浏览器
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			if (urrl != null) {// 创建一个GET方法，类似于在浏览器地址栏中输入一个地址
				HttpGet httpGet = new HttpGet(urrl);
				// 类似于在浏览器中输入回车，获得网页内容
				HttpResponse response = null;
				try {
					response = httpClient.execute(httpGet);
				} catch (IOException e) {
					//logger.error("Exception:{}", e);
				}
				// 查看返回内容，类似于在浏览器中查看网页源码
				HttpEntity entity = null;
				if (response != null) {
					entity = response.getEntity();
				}

				resulthtml = null;
				if (entity != null) {
					// 读入内容流，并以字符串形式返回，这里指定网页编码是UTF-8
					try {
						resulthtml = EntityUtils.toString(entity, "utf-8");
						// 网页的Meta标签中指定了编码
						EntityUtils.consume(entity); // 关闭内容流
					} catch (IOException e) {
						//logger.error("Exception:{}", e);
					}
				}
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				//logger.error("Exception:{}", e);
			}

			System.out.println("kk" + resulthtml);
			//				HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			//				conn.setConnectTimeout(8*1000);
			//				conn.setDoInput(true);
			//				conn.connect();
			//				InputStream is=conn.getInputStream();
			////				BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
			//				BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
			//				byte[] buffer=new byte[2048];
			String line, result = "";

			if (resulthtml != null)
			//while((line=reader.readLine())!=null)

			{ //String strtelephone = "",strqq="",straddress="",stremail="",strname="",strweb="";
				//result=line+result;
				//     System.out.println(">>"+line);
				Document doc;
				doc = Jsoup.parse(resulthtml);

				result = doc.body().text();
				result = result + "   /";

				/***************匹配联系人电话*********/
				Pattern patt_detail1 = Pattern.compile("电话：(.*?) ");
				Matcher matcher_detail1 = patt_detail1.matcher(result);

				while (matcher_detail1.find()) {
					strtelephone = matcher_detail1.group(1);
					System.out.println("/////telephone" + matcher_detail1.group());
				}
				//    strtelephone="";
				//	}

				Pattern patt_detail11 = Pattern.compile("手机：(.*?) ");
				Matcher matcher_detail11 = patt_detail11.matcher(result);
				while (matcher_detail11.find()) {
					strtelephone = matcher_detail11.group(1);
					System.out.println("/////telephone" + matcher_detail11.group());
				}

				Pattern patt_detail12 = Pattern.compile("联系方式：(.*?) ");
				Matcher matcher_detail12 = patt_detail12.matcher(result);
				while (matcher_detail12.find()) {
					strtelephone = matcher_detail12.group(1);
					System.out.println("/////telephone" + matcher_detail12.group());
				}

				/******匹配QQ*******************/
				Pattern patt_detail2 = Pattern.compile("QQ：(.*?) ");
				Matcher matcher_detail2 = patt_detail2.matcher(result);

				while (matcher_detail2.find()) {
					strqq = matcher_detail2.group();
					System.out.println("////qq" + matcher_detail2.group());
				}
				// strqq="";

				/******匹配地址*******************/
				Pattern patt_detail3 = Pattern.compile("实体店铺：(.*?) ");
				Matcher matcher_detail3 = patt_detail3.matcher(result);

				while (matcher_detail3.find()) {
					straddress = matcher_detail3.group();
					System.out.println("///address" + matcher_detail3.group());
				}
				// straddress="";

				Pattern patt_detail31 = Pattern.compile("地址：(.*?) ");
				Matcher matcher_detail31 = patt_detail31.matcher(result);

				while (matcher_detail31.find()) {
					straddress = matcher_detail31.group();
					System.out.println("///address" + matcher_detail31.group());
				}
				/******匹配邮箱*******************/
				Pattern patt_detail4 = Pattern.compile("邮箱：(.*?) ");
				Matcher matcher_detail4 = patt_detail4.matcher(result);
				while (matcher_detail4.find()) {
					stremail = matcher_detail4.group();
					System.out.println("///email" + matcher_detail4.group());
				}
				// stremail="";

				Pattern patt_detail41 = Pattern.compile("Email: (.*?) ");
				Matcher matcher_detail41 = patt_detail41.matcher(result);
				while (matcher_detail41.find()) {
					stremail = matcher_detail41.group();
					System.out.println("///email" + matcher_detail41.group());
				}

				/******匹配姓名*******************/
				Pattern patt_detail5 = Pattern.compile("姓名：(.*?) ");
				Matcher matcher_detail5 = patt_detail5.matcher(result);
				while (matcher_detail5.find()) {
					strname = matcher_detail5.group();
					System.out.println("///name" + matcher_detail5.group());
				}
				// strname="";

				Pattern patt_detail6 = Pattern.compile("联系人：(.*?) ");
				Matcher matcher_detail6 = patt_detail6.matcher(result);
				while (matcher_detail6.find()) {
					strname = matcher_detail6.group();
					System.out.println("///name" + matcher_detail6.group());
				}
				//strname="";

				/******匹配网址*******************/
				Pattern patt_detail8 = Pattern.compile("网址：(.*?) ");
				Matcher matcher_detail8 = patt_detail8.matcher(result);
				while (matcher_detail8.find()) {
					strweb = matcher_detail8.group();
					System.out.println("///name" + matcher_detail8.group());
				}

				// strname="";
				Pattern patt_detail81 = Pattern.compile("网站：(.*?) ");
				Matcher matcher_detail81 = patt_detail81.matcher(result);
				while (matcher_detail81.find()) {
					strweb = matcher_detail81.group();
					System.out.println("///name" + matcher_detail81.group());
				}

				if (straddress.length() > 40) {
					straddress = straddress.substring(0, 20);
				}

				boolean flag = (strqq != "" || straddress != "" || stremail != "");
				if (flag == true) {
					Update_mysql updatenew = new Update_mysql();
					System.out.println("*&&&&2" + flag);
					updatenew.update(selnum, strqq, straddress, stremail);
				}

			}
			//System.out.println("*&&&&2"+selNum+ strtelephone+ straddress+strqq+stremail);
			//				if(strqq!=""||straddress!=""||stremail!="")
			//
			//				{
			//				   Update_mysql updatenew=new Update_mysql();
			//				   System.out.println("*&&&&2");
			//				   updatenew.update(selnum,strqq,straddress,stremail);}

			//	strqq="";straddress="";stremail="";strname="";strweb="";

			//				 select_sql selectmysql=new select_sql();
			//				 int count = selectmysql.select(0,strname,selnum);
			//	               if(count==0){
			//	            	 Insert_mysql xiaohe=new Insert_mysql();
			//	            	 String a=selNum;
			//	            	 System.out.println("*&&&&2"+selNum+ strtelephone+ straddress+strqq+stremail);
			//	  				 xiaohe.insert("telephoneresult",selnum, strname,  strtelephone, straddress, strqq, stremail, "");
			//	             	      }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String UsePattern(String using) {
		//匹配联系人
		Pattern patt_sender = Pattern.compile("联系人: (.*?) ");
		Matcher matcher_sender = patt_sender.matcher(using);

		while (matcher_sender.find()) {
			// System.out.println(matcher.group(1));
			//result=matcher_sender.group(1);
			using = matcher_sender.replaceAll("").trim();
		}

		Pattern patt_detail = Pattern.compile("电话：(.*?) ");
		Matcher matcher_detail = patt_detail.matcher(using);

		while (matcher_detail.find()) {
			using = matcher_detail.group(1);
			System.out.println("//////////" + matcher_detail.group(1));
		}
		return using;
	}

}
