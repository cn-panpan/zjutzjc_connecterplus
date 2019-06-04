import BmobApi.BmobDemo;

public class Main {

//    final static String DDDDD = "DDDDD=";
//    final static String upass = "&upass=";
//    final static String omkKey = "&0MKKey=%B5%C7+%C2%BC";


    public static void main(String[] args) {

        BmobDemo db= new BmobDemo();
        //db.UpdatePCstate("flag",false);
        //db.Query();
        //System.out.println(db.GetObjectIdQuery("201802250413"));
        //System.out.println(db.Query("flag"));
       // db.GetObjectIdQuery("201802250413");
        //BmobDemo.UpdatePCstate(false);
        //db.Insert("201802250105");






//        String srlogin = SendLoginPost
//                .sendPost("http://192.168.100.2/",
//                        "DDDDD=201802250413&upass=17001X&0MKKey=%B5%C7+%C2%BC");
        //System.out.println(Data.LoginDataSave.DDDDD+Data.LoginDataSave.prNode.get("zjutzjc_connecterplus_username", "")+Data.LoginDataSave.upass+Data.LoginDataSave.prNode.get("zjutzjc_connecterplus_password", "")+Data.LoginDataSave.omkKey);
//        String s = SendLogoutPost.sendGet("http://192.168.100.2/F.htm", "");
//        utils utils = new utils();
//        String connectresult = utils.details(s);
//        System.out.println(connectresult);


//        String username=Data.LoginDataSave.prNode.get("username","");
//        String password=Data.LoginDataSave.prNode.get("password","");
//        //使用字符串拼接请求内容
//        //System.out.println(DDDDD+username+upass+password+omkKey);

        new view().connectView();


        //使用字符串拼接发送post登录
        /*String srlogin = SendLoginPost
                .sendPost("http://192.168.100.2/",
                        "DDDDD+username+upass+password+omkKey");*/
        //登录
        /*String srlogin = SendLoginPost
                .sendPost("http://192.168.100.2/",
                        "DDDDD=201802250413&upass=17001X&0MKKey=%B5%C7+%C2%BC");
        System.out.println(srlogin);*/

        //注销
        /*
        String s=SendLogoutPost.sendGet("http://192.168.100.2/F.htm", "");
        System.out.println(s);*/

    }
}
