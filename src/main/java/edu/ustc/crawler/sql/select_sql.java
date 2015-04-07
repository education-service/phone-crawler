package edu.ustc.crawler.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class select_sql {

	public int count = 0;
	public String sql = "";

	public int select(int choice, String name_title, String querynum) {
		//String signname,String realname,String nickname,String lianxiren,String age,String sex,String telephonenum,String phonepicture,String address,String duty,String area,String qq,String email,String websource,String dataresource,String deletedd)
		System.out.println("choice///////" + choice);
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
			//"jdbc:mysql://192.168.32.19:3306/weibo_sina?useUnicode=true&amp&characterEncoding=UTF-8", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
					"jdbc:mysql://127.0.0.1:3306/xiaohe", "root", "20121028mmhct1314");
			//"jdbc:mysql://192.168.32.19:3306/weibo_sina", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
			Statement stat = con.createStatement();

			if (choice == 0) {
				sql = "select * from  telephoneresult " + "  where lianxiren =" + "'" + name_title + "'"
						+ " and querynum = " + "'" + querynum + "'";

			} else if (choice == 1) {
				sql = "select * from  emailresult " + "  where lianxiren =" + "'" + name_title + "'"
						+ " and querynum = " + "'" + querynum + "'";

			} else {
				sql = "select * from qqresult " + "  where lianxiren =" + "'" + name_title + "'" + " and querynum = "
						+ "'" + querynum + "'";

			}
			// String sql="select * from  qqresult "+"  where lianxiren ="+"'"+name_title+"'"+" and querynum = "+"'"+querynum+"'";
			System.out.println(sql);

			ResultSet connu = stat.executeQuery(sql);
			while (connu.next()) {
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
		return count;
	}

	public static void main(String[] args) {
		String str = "";
		select_sql selectmysql = new select_sql();

		int a = selectmysql.select(1, "联系人：  童先生 ", "13966693776");
		System.out.println(a);
		//zz.insert("zhoi", "19");str = new String(str.getBytes(),"GB2312");
	}

}