
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

public class SendLoginPost {
    public static String sendPost(String url, String param) throws ConnectException, SocketException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Host", "192.168.100.2");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; rv:66.0) Gecko/20100101 Firefox/66.0");
            conn.setRequestProperty("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language",
                    "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Referer", " http://192.168.100.2/");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch (ConnectException e1){
            throw e1;
        }
        catch (SocketException se){
            throw se;
        }
        catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("post结果为：" + result);
        return result;
    }


}

