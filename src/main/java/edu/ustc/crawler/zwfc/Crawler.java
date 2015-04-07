package edu.ustc.crawler.zwfc;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class Crawler {

	private String html = null;
	private String charset;//charset字符集
	private CleanerProperties cp;//CleanerProperties清洁性能
	private static int MIN_HTML_SIZE = 8192;
	private static StringBuffer cookie = new StringBuffer();

	private static String ROBOT_XPATH = "/html/head/title";
	private static String ROBOT_TITLE = "机器人确认";
	TagNode root;

	public Crawler(String url) throws IOException {
		//this.charset = "gb2312"; // bwi.getFileEncoding(url);
		//this.charset = "UTF-8";
		int k = 0;
		this.html = getHtml(url);
		//}
		setCp();
		root = new HtmlCleaner(cp).clean(html);
	}

	private void setCp() {
		cp = new CleanerProperties();
		cp.setOmitComments(true);
		cp.setTranslateSpecialEntities(true);
		cp.setTransResCharsToNCR(true);
		cp.setCharset(charset);
	}

	public enum ValidState { //枚举
		NULL, VALID, ROBOT, TOO_SHORT, OTHER,
	};

	public ValidState valid() { //ValidState ：有效状态
		if (this.html == null) {
			return ValidState.NULL;
		} else {
			try {
				String msg = parserByXpath(ROBOT_XPATH);
				if (msg == null) {
					return ValidState.VALID;
				} else if (msg.equals(ROBOT_TITLE)) {
					System.out.println("爬取过快，被封。。。");
					return ValidState.ROBOT;
				}
			} catch (XPatherException e) {
				e.printStackTrace();
			}
			if (this.html.length() < MIN_HTML_SIZE) {
				System.out.println("html length = " + this.html.length());
				return ValidState.TOO_SHORT;
			}
		}
		return ValidState.OTHER;
	}

	public String getHtml(String url) throws IOException {
		URL htmlurl;

		HttpClient httpclient = new DefaultHttpClient();// httpclient ：浏览器

		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);// 连接时间20s
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);// 数据传输时间60s

		// Prepare a request object:准备一个请求对象
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");

		synchronized (cookie) { //synchronized：同步方法
			if (cookie.length() > 0) {
				httpget.setHeader("Cookie", cookie.toString());
			}
		}

		// Execute the request：执行请求
		HttpResponse response = httpclient.execute(httpget);
		// Examine the response status：检查响应状态
		// Get hold of the response entity：得到响应实体
		HttpEntity entity = response.getEntity();
		Header[] headers = response.getHeaders("Set-Cookie");

		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		if (headers != null && headers.length > 0) {
			for (Header header : headers) {
				if (header.getName().equals("crawler_uuid")) {//uuid通用唯一标示符
					flag = true;
					System.out.println(header.getName() + "    " + header.getValue());
					break;
				}
				sb.append(header.getName() + "=" + header.getValue() + ";");
			}
		}
		if (!flag) {
			synchronized (cookie) {
				cookie = sb;
			}
		}

		// If the response does not enclose an entity, there is no need
		// to worry about connection release：如果响应不附上一个实体,没有必要担心连接释放
		String content = null;
		if (entity != null) {
			InputStream instream = entity.getContent();
			htmlurl = new URL(url);
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream, getFileEncoding(htmlurl)));
				// do something useful with the response：做些有用的响应
				content = IOUtils.toString(reader);
			} catch (IOException ex) {
				//
				throw ex;
			} catch (RuntimeException ex) {
				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.：
				//以防意外异常你可能想中止HTTP请求
				//以关闭底层连接和释放它回到连接管理器
				httpget.abort();
				throw ex;
			} finally {
				// Closing the input stream will trigger connection release：关闭输入流将触发连接释放
				instream.close();
			}
		}
		return content;
	}

	private String getContent(TagNode root, String expression) throws XPatherException {
		Object[] nodes = root.evaluateXPath(expression);
		if (nodes.length > 0)
			return ((TagNode) nodes[0]).getText().toString();
		else
			return null;
	}

	public String getContentNodeContent(String xpath, int index)//获得节点内容
			throws XPatherException {

		Object[] nodes = root.evaluateXPath(xpath);
		System.out.println(nodes.length);
		if (nodes.length <= 0) {
			return null;
		}
		int cnt = 0;
		for (Object node : nodes) {
			if (node instanceof TagNode) { // instanceof：判断对象类型
				TagNode tn = (TagNode) node;
				for (Object cb : tn.getAllChildren()) { //遍历节点
					if (cb instanceof ContentNode) {
						cnt++;
						if (cnt == index) {
							ContentNode cn = (ContentNode) cb;
							return cn.getContent().replaceAll("&nbsp;", "").replaceAll("\n", "").replaceAll(" ", "");
						}
					}
				}
			}
		}
		return null;
	}

	public String getNodeUrl(String xpath, String nodename)//获得节点中的Url
			throws XPatherException {

		Object[] nodes = root.evaluateXPath(xpath);
		//System.out.println(nodes.length);
		if (nodes.length <= 0) {
			return null;
		}
		for (Object node : nodes) {
			TagNode t = (TagNode) node;
			String nodeurl = t.getAttributeByName(nodename);

			//	System.out.println(nodeurl);
			return nodeurl;
		}
		return null;
	}

	public String parserByXpath(String xpath) throws XPatherException {//通过Xpath进行爬取

		String content = getContent(root, xpath);
		if (content != null) {
			content = content.replaceAll("\\s", "");
		}
		return content;
	}

	public void saveHtml(String filename) {
		try {
			// new PrettyXmlSerializer(cp).writeXmlToFile(root,filename);
			new PrettyXmlSerializer(cp).writeToFile(root, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		try {
			Crawler pc = new Crawler("http://hefei.baixing.com/shoujihaoma/a502316293.html");

			System.out.println(pc.getHtml("http://hefei.baixing.com/shoujihaoma/a502316293.html"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getFileEncoding(URL url) throws MalformedURLException, IOException {
		java.nio.charset.Charset charset = null;
		CodepageDetectorProxy codepageDetectorProxy = CodepageDetectorProxy.getInstance();

		codepageDetectorProxy.add(JChardetFacade.getInstance());
		codepageDetectorProxy.add(ASCIIDetector.getInstance());
		codepageDetectorProxy.add(UnicodeDetector.getInstance());
		codepageDetectorProxy.add(new ParsingDetector(false));
		codepageDetectorProxy.add(new ByteOrderMarkDetector());

		charset = codepageDetectorProxy.detectCodepage(url);
		System.out.println(charset.name());
		return charset.name();
	}

}