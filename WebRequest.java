import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 网络数据请求
 * 
 * @author cmq
 * 
 */
public class WebRequest {

	private String getRequest(String uri) {
		// 1.新建一个请求
		HttpGet httpGet = new HttpGet(uri);
		// 2.建立一个客户端
		HttpClient httpClient = new DefaultHttpClient();
		try {
			// 3.使用httpClient发送请求
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// 4.获取结果集
			HttpEntity httpEntity = httpResponse.getEntity();
			// 5.读取内容
			InputStream inputStream = httpEntity.getContent();
			// 6.内容结果
			
			return getString(inputStream);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void postRequest(String uri) {
		// 1.建立请求
		HttpPost httpPost = new HttpPost(uri);
		// 2.建立请求实体
		// &word=15260663913
		NameValuePair mNameValuePair = new BasicNameValuePair("user", "123");
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(mNameValuePair);
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
			// 请求参数NameValuePair，添加到HttpPost请求
			httpPost.setEntity(httpEntity);
			//test uri
			String path;
			try {
				path = httpPost.getURI().toURL().toString();
				System.out.println("Path:" + path);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// 3.建立客户端
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			// 处理httpResponse
			String result = EntityUtils.toString(httpResponse.getEntity(),
					"GB2312");
			System.out.println(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取手机号码归属地,截取字符串
	 * 
	 * @param str
	 * @return
	 */
	public String getMobileAdd(String phoneNum) {
		String uri = "http://wap.baidu.com/ssid=0/from=0/bd_page_type=1/uid=0/baiduid=D3F14D0DAC9902DA58C61C9B75C1EF57/s?word="
				+ phoneNum
				+ "&uc_param_str=upssntdnvelami&sa=ib&st_1=111041&st_2=102041&pu=sz%40224_220%2Cta%40middle___3_537&idx=20000&tn_1=middle&tn_2=middle&ct_1=%E6%90%9C%E7%BD%91%E9%A1%B5";

		String str = getRequest(uri);
		int begin = str.indexOf("<span>手机号码</span>：<span>");
		int end = str.indexOf("手机号码归属地数据由");
		String res = str.substring(begin, end);
		System.out.println(res);
		// <span>手机号码</span>：<span>13900008888</span><br
		// /><span>新疆&#160;乌鲁木齐&#160;中国移动</span>
		String location = res.substring(
				res.indexOf("<span>", res.indexOf("<br />")) + 6,
				res.indexOf("</span>", res.lastIndexOf("</span>")));// 新疆&#160;乌鲁木齐&#160;中国移动
		String[] loc = location.split("&#160;");
		// Map<String, String> mAdd = new HashMap<String, String>();
		// mAdd.put("province", loc[0]);
		// mAdd.put("city", loc[1]);
		// mAdd.put("operator", loc[2]);
		return loc[0] + ";" + loc[1] + ";" + loc[2];
	}

	/**
	 * io流的处理,返回字符串
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private String getString(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream, "gb2312"));
		String result = "";
		String line = "";
		while ((line = br.readLine()) != null) {
			result += line;
		}
		return result;
	}

	public static void main(String[] args) {
		// ----post test---
		String uri = "http://www.w3school.com.cn/html/html_form_action.asp";
		new WebRequest().postRequest(uri);
		// ----get test---
//		 String url = "http://www.w3school.com.cn/html/html_form_action.asp?user=123";
//		 System.out.println(new WebRequest().getRequest(url));
	}

}
