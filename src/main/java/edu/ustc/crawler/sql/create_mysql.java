package edu.ustc.crawler.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class create_mysql {

	public void create() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("kkkk");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/xiaohe", "root", "20121028mmhct1314");
			// "jdbc:mysql://192.168.32.19:3306/weibo_sina?useUnicode=true&amp&characterEncoding=UTF-8", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
			Statement stat = con.createStatement();
			String sql = "create table telephoneresult(querynum text,lianxiren  varchar(60),telephonenum text,address  varchar(200),qq text,email text,websource text)";
			stat.executeUpdate(sql);
			String sql2 = "create table qqresult(querynum text,lianxiren  varchar(60),telephonenum text,address  varchar(200),qq text,email text,websource text)";
			stat.executeUpdate(sql2);
			String sql3 = "create table emailresult(querynum text,lianxiren  varchar(60),telephonenum text,address  varchar(200),qq text,email text,websource text)";
			stat.executeUpdate(sql3);
			System.out.println("hhh");
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
		// TODO Auto-generated method stub
		create_mysql zz = new create_mysql();

		zz.create();
	}

}
