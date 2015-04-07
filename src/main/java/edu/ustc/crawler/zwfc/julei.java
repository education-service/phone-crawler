package edu.ustc.crawler.zwfc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class julei {

	private static String content, phonenun, k;
	public int num;
	public Sequence pku_test;

	public void getweb(String path) {
		String strXmlLine = "";
		String strXmlnow = "";
		String b = "";
		try {
			File file = new File(path);//文件路径
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			//strXmlLine = br.readLine();
			//String regEx=" [\u4e00-\u9fa5]";   
			//Pattern p = Pattern.compile(regEx);   
			//Matcher m = p.matcher(strXmlLine);   
			//String a=m.replaceAll("").trim();

			while ((strXmlLine = br.readLine()) != null) {
				strXmlnow = strXmlnow + strXmlLine;

				// System.out.println(strXmlnow);
				b = strXmlnow.replaceAll("[^0-9\\u4e00-\\u9fa5]", "");
				//System.out.println(b.replaceAll("\\d+",""));
			}
			br.close();

			b = b.replaceAll("\\d+", "");
			System.out.println(b);
			//String regEx="[^0-9]";   
			// Pattern p = Pattern.compile(regEx);   
			// Matcher m = p.matcher(b);   
			// String a=m.replaceAll("").trim();
			//System.out.println(a);
			//num=Integer.parseInt(a);
			//System.out.println(a);
			Sequence pku_test = new Sequence();
			pku_test.Sequenc(b);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		julei dc = new julei();
		//Sequence pku_test=new Sequence(pku)
		dc.getweb("E:\\mmm2.txt");
	}
}