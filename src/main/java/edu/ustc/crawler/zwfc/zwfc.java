package edu.ustc.crawler.zwfc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class zwfc { //公共类                       //公共类
	//public  String  sseq;
	//public  Sequence pku_test;
	public void getresult(String path) {
		try {
			FileInputStream fis = new FileInputStream(path); //定义输入流对象加载要分词的文件
			InputStreamReader SR = new InputStreamReader(fis);
			BufferedReader BR = new BufferedReader(SR);
			String pku = "";
			//从要分词的文件中读取要比较的字符串
			while ((pku = BR.readLine()) != null) {
				//从要分词的文件中读取要比较的字符串
				// 声明一个Sequence对象，对这行字符串进行处理
				Sequence pku_test = new Sequence();
				pku_test.Sequenc(pku);
				System.out.println(" ");
			}

		} catch (IOException e) { //抛出异常
			System.out.print(e);
		}
	}

	public static void main(String args[]) {
		zwfc aw = new zwfc();
		aw.getresult("E:\\mmm2.txt");
	}
}

class Sequence { //实现分词的类
	static final int WORD_MAX_LENGTH = 9; //定义每次比较的最大字长
	//定义一个Vector对象把词表中的词存储在一个Vector对象中
	static Vector ve = new Vector();

	FileWriter FW = new FileWriter("E:\\m2m.txt", true);
	//public static 
	public static String seq;

	public Sequence() throws IOException {

	}

	public String Sequenc(String str) throws IOException { //抛出异常

		class LoadDictionary {
			public LoadDictionary(FileReader reader) throws IOException {
				BufferedReader stream = new BufferedReader(reader);
				String LD = null;
				while ((LD = stream.readLine()) != null) {
					ve.add(LD);
				}
			}

		}

		FileReader list = new FileReader("E:\\wordlist.txt");
		LoadDictionary Dic = new LoadDictionary(list);
		//和字典比较进行分词
		int num = str.length();
		if (num <= WORD_MAX_LENGTH) {
			int i, j = 0;
			while (j < num) {
				for (i = 0; i + j < num; i++) {
					seq = str.substring(j, num - i);
					if (ve.contains(seq) || (num - i - j == 1)) {
						System.out.print(seq + "   ");
						FW.write(seq + "   ");
						FW.flush();
						// FW.close();
						j += num - i - j;
						break;
					}
					//return seq;
				}
			}

		} else {
			int i, j = 0;
			while (num - j >= WORD_MAX_LENGTH) {
				i = j + WORD_MAX_LENGTH;
				for (; i > j; i--) {
					seq = str.substring(j, i);
					if (ve.contains(seq) || (i - j == 1)) {
						System.out.print(seq + "   ");
						FW.write(seq + "   ");
						FW.flush();
						//FW.close();
						j = i;
						break;
					}
					//return seq;
				}
			}
			while (j < num) {
				for (i = 0; i + j < num; i++) {
					seq = str.substring(j, num - i);
					if (ve.contains(seq) || (num - i - j == 1)) {
						System.out.print(seq + "   ");
						FW.write(seq + "   ");
						FW.flush();
						//FW.close();
						j += num - i - j;
						break;
					}
					//return seq;
				}
			}
		}
		return seq;
	}

}