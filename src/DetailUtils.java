import Data.LoginDataSave;
import org.jsoup.nodes.Document;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class DetailUtils {
    public static void details(String result) {
        Document doc = org.jsoup.Jsoup.parse(result);
        org.jsoup.nodes.Element element = doc.select("script").first();
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("javascript");
        String script = element.childNode(0).toString();
        script = script.replaceAll("<!--", "");
        script = script.replaceAll("// -->", "");
        LoginDataSave.time = script.substring(script.indexOf("time='") + 6, script.indexOf("time='") + 16).trim();
        LoginDataSave.flow = script.substring(script.indexOf("flow='") + 6, script.indexOf("flow='") + 16).trim();


        LoginDataSave.fee = script.substring(script.indexOf("fee='") + 5, script.indexOf("fee='") + 15).trim();

        LoginDataSave.flow0 = Long.parseLong(LoginDataSave.flow) % 1024;
        LoginDataSave.flow1 = Long.parseLong(LoginDataSave.flow) - LoginDataSave.flow0;
        LoginDataSave.flow0 = LoginDataSave.flow0 * 1000;
        LoginDataSave.flow0 = LoginDataSave.flow0 - LoginDataSave.flow0 % 1024;
        LoginDataSave.fee1 = Long.parseLong(LoginDataSave.fee) - Long.parseLong(LoginDataSave.fee) % 100;

//        System.out.println(LoginDataSave.time);
//        System.out.println(LoginDataSave.flow1/1024+LoginDataSave.flow3+LoginDataSave.flow0/1024);
//        System.out.println(LoginDataSave.fee1/10000);

    }
}
