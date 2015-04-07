package edu.ustc.crawler.qq;

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

public class qqDirectory {
	private static String BASE_XPATH = "//*[@id=\"";
	private static String SUFFIX_XPATH = "\"]/h3/a";
	private static String XASE_XPATH = "//*[@id=\"";
	private static String XUFFIX_XPATH = "\"]/div[1]";
	//	public static String strXmlLine = "";

	public static int result = 0;
	public static int count = 0;
	private static String content, ppath, phonenun, xpath;

	qqDetail jp = new qqDetail();

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
				result = 10;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException ex) {
			System.out.println("The String does not contain a parsable integer");
		}
		System.out.println("JJhh" + result);
		return result;
	}

	public void getweb(String baseurl, String qq) {

		count = countitem(baseurl);
		try {
			Crawler crawler = new Crawler(baseurl);
			for (int j = 1; j < count + 1; j++) {
				xpath = BASE_XPATH + j + SUFFIX_XPATH;
				String pageurl = crawler.getNodeUrl(xpath, "href");

				if (pageurl != null) {
					//pageurl="相关链接网址"+"是："+pageurl;
					pageurl = pageurl;
				}

				String title = crawler.parserByXpath(xpath);
				if (title != null) {
					title = title;
					//title="链接标题"+"是："+title;	
				}
				//System.out.println(email);
				System.out.println("pageurl" + pageurl);
				System.out.println("title" + title);

				jp.nexturl = pageurl;
				jp.getContent();
				jp.k = j;
				jp.selNum = qq;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		System.out.println("//////////////////////////////////");
	}

	public static void main(String[] args) {

		try {
			File emailfile = new File("E:\\QQ.txt");//文件路径
			FileReader fr = new FileReader(emailfile);
			BufferedReader br = new BufferedReader(fr);

			qqDirectory dc = new qqDirectory();

			String strXmlLine = "";
			while ((strXmlLine = br.readLine()) != null) {
				// System.out.println("justjustjust"+strXmlLine);
				String url = "http://www.baidu.com/s?wd=" + URLCodecUtils.encoder(strXmlLine, "UTF-8");
				dc.getweb(url, strXmlLine);
				strXmlLine = "";
				System.out.println("/////////////////" + strXmlLine);
			}
			System.out.println("///////////////////////////////////////////");
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}