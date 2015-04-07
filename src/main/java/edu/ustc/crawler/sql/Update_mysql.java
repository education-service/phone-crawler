package edu.ustc.crawler.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Update_mysql {

	public void update(String upid, String qqnum, String addresstext, String emailtext) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("kkkk");
			con = DriverManager.getConnection("jdbc:mysql://192.168.5.202/oadata", "kjqj", "zxsoft");
			//	 "jdbc:mysql://127.0.0.1:3306/xiaohe", "root","20121028mmhct1314");
			Statement stat = con.createStatement();
			//String sql="update telephoneresult set qq ='"+qqnum+"'"+","+"address ='"+addresstext+"'"+","+"email ='"+emailtext+"'"+" where querynum = '"+upid+"'";

			String sql = "update virtualinfo set QQ ='" + qqnum + "'" + "," + "Address ='" + addresstext + "'" + ","
					+ "Email ='" + emailtext + "'" + " where SJHM_Num = '" + upid + "'";
			System.out.println("***" + sql);
			stat.executeUpdate(sql);

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
	}

	public static void main(String[] args) {
		String str = "";
		Insert_mysql zz = new Insert_mysql();
		try {
			str = new String("周智".getBytes(), "utf-8");
			System.out.println(str);
		} catch (Exception e) {

		}
		Update_mysql xiaohe1 = new Update_mysql();
		xiaohe1.update("13966693776", "5555", "jgd@ggh", "乌拉圭");
		//zz.insert("zhoi", "19");str = new String(str.getBytes(),"GB2312");
	}

}
