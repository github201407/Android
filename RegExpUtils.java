import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式验证工具
 * 
 * @author mngqn
 * 
 */
public class FormatUtils {
	/* 邮箱验证 sample@domain.com */
	public static boolean isEmail(String email) {
		String pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		return matchPattern(pattern, email);
	}

	/* 手机号验证 11位 CN */
	public static boolean isMobile(String mobiles) {
		String pattern = "^(13[0-9]|14[5|7]|15[^4,\\D]|18[0-9]|17[0|6|7|8])\\d{8}$";
		return matchPattern(pattern, mobiles);
	}

	/**
	 * 微信号是否正确 微信号是登录微信时使用的账号，支持6-20个字母、数字、下划线和减号，必须以字母开头
	 */
	public static boolean isWxh(String wxh) {
		String pattern = "^[a-zA-Z]{1}[a-zA-Z0-9_-]{5,19}$";
		return matchPattern(pattern, wxh);
	}

	private static boolean matchPattern(String pattern, String input) {
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(input);
			return m.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
