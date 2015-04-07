package edu.ustc.crawler.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Insert_mysql {

	public void insert(String type, String querynum, String lianxiren, String telephonenum, String address, String qq,
			String email, String websource)
	//String signname,String realname,String nickname,String lianxiren,String age,String sex,String telephonenum,String phonepicture,String address,String duty,String area,String qq,String email,String websource,String dataresource,String deletedd)
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("kkkk");
			con = DriverManager.getConnection(
			// "jdbc:mysql://192.168.32.19:3306/weibo_sina?useUnicode=true&amp&characterEncoding=UTF-8", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
					"jdbc:mysql://127.0.0.1:3306/xiaohe", "root", "20121028mmhct1314");
			//"jdbc:mysql://192.168.32.19:3306/weibo_sina", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
			Statement stat = con.createStatement();
			String sql = "insert into " + type + " values('" + querynum + "','" + lianxiren + "','" + telephonenum
					+ "','" + address + "','" + qq + "','" + email + "','" + " " + "')";
			//""+signname+"','"+realname+"','"+nickname+"','"+lianxiren+"','"+age+"','"+sex+"','"+telephonenum+"','"+phonepicture+"','"+address+"','"+duty+"','"+area+"','"+qq+"','"+email+"','"+websource+"','"+dataresource+"','"+deletedd+"')";
			stat.executeUpdate(sql);
			System.out.println("***" + sql);
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

	public void insertemail(String querytelephone, String lianxiren, String telephonenum, String address, String qq,
			String email, String websource)
	//String signname,String realname,String nickname,String lianxiren,String age,String sex,String telephonenum,String phonepicture,String address,String duty,String area,String qq,String email,String websource,String dataresource,String deletedd)
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("kkkk");
			con = DriverManager.getConnection(
			// "jdbc:mysql://192.168.32.19:3306/weibo_sina?useUnicode=true&amp&characterEncoding=UTF-8", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
					"jdbc:mysql://127.0.0.1:3306/xiaohe", "root", "20121028mmhct1314");
			//"jdbc:mysql://192.168.32.19:3306/weibo_sina", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
			Statement stat = con.createStatement();
			String sql = "insert into " + "emailresult" + " values('" + querytelephone + "','" + lianxiren + "','"
					+ telephonenum + "','" + address + "','" + qq + "','" + email + "','" + " " + "')";
			//""+signname+"','"+realname+"','"+nickname+"','"+lianxiren+"','"+age+"','"+sex+"','"+telephonenum+"','"+phonepicture+"','"+address+"','"+duty+"','"+area+"','"+qq+"','"+email+"','"+websource+"','"+dataresource+"','"+deletedd+"')";
			stat.executeUpdate(sql);
			System.out.println("***++++" + sql);
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

	public void insertqq(String querytelephone, String lianxiren, String telephonenum, String address, String qq,
			String email, String websource)
	//String signname,String realname,String nickname,String lianxiren,String age,String sex,String telephonenum,String phonepicture,String address,String duty,String area,String qq,String email,String websource,String dataresource,String deletedd)
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("kkkk");
			con = DriverManager.getConnection(
			// "jdbc:mysql://192.168.32.19:3306/weibo_sina?useUnicode=true&amp&characterEncoding=UTF-8", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
					"jdbc:mysql://127.0.0.1:3306/xiaohe", "root", "20121028mmhct1314");
			//"jdbc:mysql://192.168.32.19:3306/weibo_sina", "weibosina","OyiaV5M53qTqlZjmN0OOtA==");
			Statement stat = con.createStatement();
			String sql = "insert into " + "qqresult" + " values('" + querytelephone + "','" + lianxiren + "','"
					+ telephonenum + "','" + address + "','" + qq + "','" + email + "','" + " " + "')";
			stat.executeUpdate(sql);
			System.out.println("***++++" + sql);
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
		// TODO Auto-generated method stub
		Insert_mysql zz = new Insert_mysql();
		try {
			str = new String("周智".getBytes(), "utf-8");
			System.out.println(str);
		} catch (Exception e) {

		}
		Insert_mysql xiaohe1 = new Insert_mysql();
		// xiaohe1.insert("emailresult","hh", "strname",  "strtelephone", "straddress", "strqq", "stremail", "");
		//zz.insert("zhoi", "19");str = new String(str.getBytes(),"GB2312");
	}

}
