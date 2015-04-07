package edu.ustc.crawler.zwfc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import zx.soft.utils.codec.URLCodecUtils;
import edu.ustc.crawler.email.emailDirectory;
import edu.ustc.crawler.phone.phoneDirectory;
import edu.ustc.crawler.qq.qqDirectory;
import edu.ustc.crawler.sql.select_from200;

public class main_crawler {
	public static String phoneLine = "";
	public static String emailLine = "";
	public static String qqLine = "";
	public static int limit = 10;
	public static int initnum = 1138;
	public static String sell = "";
	//	public static String sellno="";
	public static ArrayList<String> resultList = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		resultList = getphonenum();
		phonedetails(resultList);

		//	   for(int i=0;i<limit;i++)
		//	   {
		//		  select_from200 sel=new select_from200();
		//		  sell = sel.selectphone(initnum);
		//		  resultList.add(sell);
		//	      initnum++;	  
		//	   }
		//	   System.out.println("resultListresultList"+resultList);
		//	
		//	  phoneDirectory dc = new phoneDirectory();
		//	  //遍历获得手机号码的信息
		//	  Iterator<String> it=resultList.iterator();
		//	  while(it.hasNext()){
		//	    // System.out.println(it.next());
		//	     String queryphone=it.next();
		//	     String url = "http://www.baidu.com/s?wd=" + URLCodecUtils.encoder(queryphone, "UTF-8");
		//	     System.out.println("/////"+url);
		//         dc.getweb(url,queryphone);
		//         queryphone="";
		//         
		//	  }

		//deal_email();
		//deal_qq();
		// deal_phone();
	}

	//获取手机号码的相关信息
	public static void phonedetails(ArrayList<String> hh) {
		try {
			phoneDirectory dc = new phoneDirectory();
			//遍历获得手机号码的信息
			Iterator<String> it = hh.iterator();
			while (it.hasNext()) {
				// System.out.println(it.next());
				//  String queryphone="";
				String queryphone = it.next();
				System.out.println("queryphonequeryphonequeryphone" + (queryphone.length() < 5));

				if (!queryphone.equals("") && queryphone != "0" && queryphone != null && (queryphone.length() > 5)) {
					String sellno = queryphone.replace(" ", "");
					String SELphone = URLEncoder.encode(sellno, "UTF-8");
					String url = "http://www.baidu.com/s?wd=" + SELphone;
					//   String url = "http://www.baidu.com/s?wd=" + URLCodecUtils.encoder(sellno, "UTF-8");
					System.out.println("initnuminitnuminitnum" + initnum);
					dc.getweb(url, queryphone);
					System.out.println("**************");
				}
			}

			if (!it.hasNext()) {
				System.out.println("**********////**********");
				resultList = getphonenum();
				phonedetails(resultList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//从数据库中获得要爬取的手机号码
	public static ArrayList<String> getphonenum() {
		ArrayList<String> List = new ArrayList<String>();

		for (int i = 0; i < limit; i++) {
			select_from200 sel = new select_from200();
			sell = sel.selectphone(initnum);
			// sellno=sell.replace(" ", "");
			if (sell != null) {
				List.add(sell);
			}
			initnum++;
			if (initnum == 2138) {
				initnum = 1138;
			}
		}
		//   Thread.sleep(1000);
		System.out.println("#################" + List);
		return List;
	}

	public static void deal_phone() {
		Thread mThreadphone = new Thread() {
			@Override
			public void run() {
				try {
					File phonefile = new File("E:\\手机号码.txt");//文件路径
					FileReader fr = new FileReader(phonefile);
					BufferedReader br = new BufferedReader(fr);

					phoneDirectory dc = new phoneDirectory();

					while ((phoneLine = br.readLine()) != null) {
						// System.out.println("justjustjust"+strXmlLine);
						String url = "http://www.baidu.com/s?wd=" + URLCodecUtils.encoder(phoneLine, "UTF-8");
						dc.getweb(url, phoneLine);
						phoneLine = "";
					}
					if ((phoneLine = br.readLine()) == null) {
						System.out.println("///////////////////////////////////////////");
					}
					br.close();
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		mThreadphone.start();
	}

	//开启一个爬取email的线程
	public static void deal_email() {
		Thread mThreademail = new Thread() {
			@Override
			public void run() {
				try {
					File emailfile = new File("E:\\邮箱.txt");//文件路径
					FileReader fr = new FileReader(emailfile);
					BufferedReader br = new BufferedReader(fr);

					emailDirectory dc = new emailDirectory();

					while ((emailLine = br.readLine()) != null) {
						System.out.println("justjustjust" + emailLine);
						String url = "http://www.baidu.com/s?wd=" + URLCodecUtils.encoder(emailLine, "UTF-8");

						dc.getweb(url, emailLine);
						System.out.println("待爬取的邮箱：：：：：" + emailLine);
						emailLine = "";
					}
					System.out.println("///////////////////////////////////////////");
					br.close();
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		mThreademail.start();
	}

	//开启一个爬取qq的线程
	public static void deal_qq() {
		Thread mThreadqq = new Thread() {
			@Override
			public void run() {
				try {
					File qqfile = new File("E:\\QQ.txt");//文件路径
					FileReader fr = new FileReader(qqfile);
					BufferedReader br = new BufferedReader(fr);

					qqDirectory dc = new qqDirectory();

					while ((qqLine = br.readLine()) != null) {
						// System.out.println("justjustjust"+strXmlLine);
						String url = "http://www.baidu.com/s?wd=" + URLCodecUtils.encoder(qqLine, "UTF-8");
						dc.getweb(url, qqLine);
						qqLine = "";
					}
					System.out.println("///////////////////////////////////////////");
					br.close();
					fr.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		mThreadqq.start();
	}

}