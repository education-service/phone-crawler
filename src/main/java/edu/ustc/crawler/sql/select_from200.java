package edu.ustc.crawler.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class select_from200 {

	public int count = 0;
	public String resnum = "";
	ArrayList List = new ArrayList();

	public String selectphone(int aanum) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.5.202/oadata", "kjqj", "zxsoft");
			// "jdbc:mysql://127.0.0.1:3306/xiaohe", "root","20121028mmhct1314");
			// "jdbc:mysql://192.168.32.19:3306/weibo_sina", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
			Statement stat = con.createStatement();
			String sql = "select * from  virtualinfo where ID =" + "'" + aanum + "'";
			// String sql="select * from  telephoneresult where websource ="+"'"+aanum+"'";
			System.out.println("lloolloo" + sql);

			ResultSet connu = stat.executeQuery(sql);
			while (connu.next()) {
				resnum = connu.getString("SJHM_Num");
				// resnum = connu.getString("querynum");
				if (resnum == null) {
					resnum = "  ";
				}
				//    String b = connu.getString("LXR");
				//  List.add(resnum);
				//   System.out.println("aa///////"+resnum+b);
				count++;
			}

			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("resultListresultList" + resnum);
		return resnum;
	}

}
