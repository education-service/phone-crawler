package edu.ustc.crawler.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.ustc.crawler.sql.Insert_mysql;
import edu.ustc.crawler.sql.select_sql;
import edu.ustc.crawler.zwfc.Crawler;

public class emailDetail {

	private static String BASE_XPATH = "//*[@id=\"";
	private static String SUFFIX_XPATH = "\"]/div[1]";
	private static String SE_XPATH = "\"]/div[1]/div[2]/div[1]";
	private String baseurl1 = "http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=530180782%40qq.com%20&rsv_pq=c78e320500021ac6&rsv_t=e01czvoDW99AvvyKJeA8rpJPmhuj4lA6bDv1PAWzPwwRYuwXcjq7ktO9KAM&rsv_enter=0&rsv_sug3=1&rsv_sug4=520&rsv_sug1=1";
	private Crawler crawler;
	public int k = 0;
	public static String NO_MSG = "null";
	private String url;
	private String content, ppath;
	public String nexturl = "";
	public String selNum = "";
	public String strtelephone = "", strqq = "", straddress = "", stremail = "", strname = "", strweb = "";

	public void getUrl(String url) throws IOException {
		//String key=url.split("/W")[1];
		Crawler crawler = new Crawler(url);
		try {
			System.out.println("hhhhhhhhhhhh" + k);

			//k=k+1;
			ppath = BASE_XPATH + k + SUFFIX_XPATH;
			content = crawler.parserByXpath(ppath);
			if (content != null)
				//content="内容摘要是："+content;
				content = content;
			System.out.println("content" + content);
			// String path="E:\\mmm"+k+".txt";

			//System.out.println(ppath);
			//String path="E:\\mm"+k+".txt";

			String path = "E:\\mmm" + k + ".txt";
			File file = new File(path);
			OutputStreamWriter dos = new OutputStreamWriter(new FileOutputStream(file, true));
			BufferedWriter eos = new BufferedWriter(dos);
			eos.write(content);
			eos.write('\r'); // \r\n表示换行
			eos.write('\n');
			eos.write("  ");
			eos.write('\r'); // \r\n表示换行
			eos.write('\n');
			eos.flush();
			eos.close();
			//System.out.println(k);
			// toTxt.toTXT(tvtitle,"d:\\yangshi.txt");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void getContent() {
		try {
			// nexturl=uurl;
			// getcontent.gethtml(url);
			System.out.println("GGGGGGGGGGGG" + selNum);
			// URL url=new URL("http://www.baidu.com/link?url=qC37RJgVcxw9Yl43ESmSWq2b9WGTLO9YyMShgqW-u5jqHiCyeL35sq5VNh2bLn_oNz1YKlj3xBn5NIOauIfySq");
			URL url = new URL(nexturl);
			System.out.println("kk" + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(8 * 1000);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			//			BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			byte[] buffer = new byte[2048];
			String line, result = "";

			String path = "E:\\mmm" + k + ".txt";
			File file = new File(path);
			OutputStreamWriter dos = new OutputStreamWriter(new FileOutputStream(file, true));
			BufferedWriter eos = new BufferedWriter(dos);

			while ((line = reader.readLine()) != null)

			{
				//result=line+result;
				System.out.println(">>" + line);
				Document doc;
				doc = Jsoup.parse(line);

				result = doc.body().text();
				result = result + "   /";

				/***************匹配联系人电话*********/
				Pattern patt_detail1 = Pattern.compile("电话：(.*?) ");
				Matcher matcher_detail1 = patt_detail1.matcher(result);

				while (matcher_detail1.find()) {
					strtelephone = matcher_detail1.group(1);
					System.out.println("/////telephone" + matcher_detail1.group());
				}
				//   eos.write(strtelephone);
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
					System.out.println("/////telephone" + matcher_detail11.group());
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

				Pattern patt_detail41 = Pattern.compile("email：(.*?) ");
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

				System.out.println("yyyyyyyyyyy" + result);

			}

			System.out.println("@@@@@@2" + selNum + strname + strtelephone + straddress + strqq + stremail);

			select_sql selectsql = new select_sql();
			int count = selectsql.select(1, strname, selNum);
			if (count == 0) {
				String querynum = selNum;
				Insert_mysql xiaohe = new Insert_mysql();
				xiaohe.insertemail(querynum, strname, strtelephone, straddress, strqq, stremail, "");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
