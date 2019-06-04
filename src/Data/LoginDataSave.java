package Data;

import java.util.prefs.Preferences;

public final class LoginDataSave {
    /*存放用户登录所需的帐号和密码——已废弃
    public final static Map<String, String> LOGIN_USERS = new HashMap<>();*/
    public final static String DDDDD = "DDDDD=";
    public final static String upass = "&upass=";
    public final static String omkKey = "&0MKKey=%B5%C7+%C2%BC";

	public final static Preferences pr = Preferences.userRoot();
	public final static Preferences prNode = pr.node("/zjutzjc_connecterplus");

    public static String time;
    public static String flow;
    public static String fee;
    public static long flow0;
    public static long flow1;
    public static double fee1;
    public final static String flow3=".";





    //暂未使用
//    public static String fsele;
//    public static String xsele;
//    public final static String xip="000.000.000.000.";

}
