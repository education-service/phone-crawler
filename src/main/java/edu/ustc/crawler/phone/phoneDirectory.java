package edu.ustc.crawler.phone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.XPatherException;

import zx.soft.utils.codec.URLCodecUtils;
import edu.ustc.crawler.web.gethtml;
import edu.ustc.crawler.zwfc.Crawler;

public class phoneDirectory {

	private static String BASE_XPATH = "//*[@id=\"";
	private static String SUFFIX_XPATH = "\"]/h3/a";
	private static String XASE_XPATH = "//*[@id=\"";
	private static String XUFFIX_XPATH = "\"]/div[1]";

	private static String base1[] = { "http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=13966693776&rsv_pq=951e7309000a96aa&rsv_t=1dfcXVmY7LnTqe0HrqZCoVFrgwOLKcQBrlKzZ3ZTgWyO%2FdqssbN%2FLqdjSqE" };
	private static String content, ppath, phonenun, xpath;

	public static int result = 0;
	public static int count = 0;

	//public static String phoneLine = "";
	//   phoneDetail jp=new phoneDetail ();

	public static int getcount() {
		return result;
	}

	public int countitem(String baseurl) {
		// int result=0;
		try {
			String num = gethtml.Gethtml(baseurl);
			//System.out.println("///////"+exhtml);

			System.out.println("resres" + num);

			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(num);
			String a = m.replaceAll("").trim();
			System.out.println(a);

			result = Integer.parseInt(a);

			if (result > 10) {
				result = 6;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ex) {
			System.out.println("The String does not contain a parsable integer");
			result = 3;
		}
		System.out.println("JJhh" + result);
		System.out.println("baseurlbaseurl" + baseurl);

		return result;
	}

	public void getweb(String baseurll, String b) {
		count = countitem(baseurll);
		phoneDetail jp = new phoneDetail();
		String pageurl = "";
		try {
			Crawler crawler = new Crawler(baseurll);
			for (int j = 1; j < count + 1; j++) {
				if (j == 1) {
					xpath = "//*[@id=\"1\"]/div[1]/div/div[2]/div[1]/span";
					String title = crawler.parserByXpath(xpath);
				} else {
					xpath = BASE_XPATH + j + SUFFIX_XPATH;
					pageurl = crawler.getNodeUrl(xpath, "href");
					if (pageurl != null) {
						pageurl = pageurl;
					}
					String title = crawler.parserByXpath(xpath);
					if (title != null) {
						title = title;
						//title="链接标题"+"是："+title;
					}
					System.out.println(pageurl);
					System.out.println(title);
					//			     String path="E:\\mmm"+j+".txt";
					//			     File file = new File(path);
					//			     if(!file.exists()){
					//					file.createNewFile();
					//					  }
					//				 OutputStreamWriter dos1 = new OutputStreamWriter(new  FileOutputStream(file,true));
					//				 BufferedWriter eos1=new BufferedWriter(dos1);

					//			      eos1.write(pageurl);
					//			      eos1.write('\r'); // \r\n表示换行
					//				  eos1.write('\n');
					//				  eos1.write(title);
					//			      eos1.write('\r'); // \r\n表示换行
					//				  eos1.write('\n');
					//				  eos1.flush();
					//			      eos1.close();
					//	};
					jp.nexturl = pageurl;
					System.out.println(pageurl);
					if (pageurl != " ") {
						jp.getContent(pageurl, b);
						jp.k = j;
						jp.selNum = b;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPatherException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			File phonefile = new File("手机号码.txt"); // 文件路径
			FileReader fr = new FileReader(phonefile);
			BufferedReader br = new BufferedReader(fr);

			phoneDirectory dc = new phoneDirectory();
			String phoneLine = "";
			while ((phoneLine = br.readLine()) != null) {
				System.out.println("justjustjust" + phoneLine);
				String url = "http://www.baidu.com/s?wd=" + URLCodecUtils.encoder(phoneLine, "UTF-8");
				dc.getweb(url, phoneLine);
				phoneLine = "";
			}
			System.out.println("///////////////////" + "爬虫结束！！" + "///////////////////");
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
