import org.jsoup.nodes.Document;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Loginutils {
    public static String details(String result) {
        String connectresult = "";
        try {
            Document doc = org.jsoup.Jsoup.parse(result);
            org.jsoup.nodes.Element element = doc.select("script").first();
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            String script = element.childNode(0).toString();
            script = script.replaceAll("<!--", "");
            script = script.replaceAll("// -->", "");

            if (script.contains("UID")) {
                connectresult = "连接成功!";
                return connectresult;
            } else {
                engine.eval(script);
                int Msg = (int) engine.get("Msg");
                String msga = String.valueOf(engine.get("msga"));
                String pp = String.valueOf(engine.get("pp"));
                String xip = String.valueOf(engine.get("xip"));
                String mac = String.valueOf(engine.get("mac"));
                String UT = "本次使用时间 : "+String.valueOf(engine.get("time"));
                String UF = "本次使用流量 :"+((double)engine.get("flow1")/1024)+(double)engine.get("flow1")+((double)engine.get("flow0")/1024)+" MByte";
                String UM="";
                switch (Msg) {
                    case 0:
                    case 1:
                        if ((Msg == 1) && (msga != "")) {
                            switch (msga) {
                                case "error0":
                                    connectresult = "本 IP 不允许Web方式登录";
                                    break;
                                case "error1":
                                    connectresult = ("本帐号不允许Web方式登录");
                                    break;
                                case "error2":
                                    connectresult = ("本帐号不允许修改密码");
                                    break;
                                default:
                                    connectresult = (msga);
                                    break;
                            }
                        } else connectresult = ("帐号或密码不对，请重新输入");
                        break;
                    case 2:
                        connectresult = ("该账号正在使用中，请您与网管联系 !!!" + pp + xip + pp + mac);
                        break;
                    case 3:
                        connectresult = ("本帐号只能在指定地址使用 :" + pp + xip);
                        break;
                    case 4:
                        connectresult = ("本帐号费用超支或时长流量超过限制");
                        break;
                    case 5:
                        connectresult = ("本帐号暂停使用");
                        break;
                    case 6:
                        connectresult = ("System buffer full");
                        break;
                    case 8:
                        connectresult = ("本帐号正在使用,不能修改");
                        break;
                    case 9:
                        connectresult = ("新密码与确认新密码不匹配,不能修改");
                        break;
                    case 10:
                        connectresult = ("密码修改成功");
                        break;
                    case 11:
                        connectresult = ("本帐号只能在指定地址使用 :" + pp + mac);
                        break;
                    case 7:
                        connectresult = (UT + UF + UM);
                        break;
                    case 14:
                        connectresult = ("注销成功" +UT + UF + UM);
                        break;
                    case 15:
                        connectresult = ("登录成功" + pp + UT + UF + UM);
                        break;
                }
            }
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return connectresult;
    }
}
