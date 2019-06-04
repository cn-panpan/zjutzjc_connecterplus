import BmobApi.BmobDemo;
import Data.LoginDataSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class view {
    java.util.Timer timer;

    //用于判断改窗体是否被打开
    Map judgewindows = new HashMap();


    public void connectView() {

        //定时器是否开启
        boolean timerflag;

        //初始化窗口
        JFrame jf_main = new JFrame("zjutzjc_connecterplus");
        jf_main.setSize(300, 140);//设置窗口大小
        jf_main.setResizable(false);//设置窗口大小不可变
        jf_main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置窗体关闭方式

        // 创建图片对象
        ImageIcon img_connect = new ImageIcon(this.getClass().getResource("connect.png"));//已连接
        ImageIcon img_unconnect = new ImageIcon(this.getClass().getResource("unconnect.png"));//未连接
        //初始化元素
        JButton bt_connect = new JButton("Connect");//连接按钮
        JButton bt_setting = new JButton("Setting");//设置按钮
        Label lb_currentaccount = new Label("  当前帐号：", Label.RIGHT);//当前帐号：

        JLabel lb_currentconnectimg = new JLabel(img_unconnect, SwingConstants.RIGHT);//状态图片
        Label lb_currentconnect = new Label("未连接", Label.LEFT);//状态提示
        lb_currentconnect.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 16));
        JTextField jf_currentaccount;
        if (LoginDataSave.prNode.get("zjutzjc_connecterplus_username", "") == null) {
            jf_currentaccount = new JTextField("请输入帐号密码");//显示当前帐号为文本

        } else {
            jf_currentaccount = new JTextField(LoginDataSave.prNode.get("zjutzjc_connecterplus_username", ""));//显示当前帐号为文本
        }

        bt_connect.setMargin(new Insets(6, 0, 6, 0));
        bt_connect.setMargin(new Insets(6, 0, 6, 0));

        bt_connect.setFont(new Font("宋体", Font.ITALIC, 14));
        bt_setting.setFont(new Font("宋体", Font.ITALIC, 14));
        lb_currentconnect.setFont(new Font("宋体", Font.BOLD, 18));
        lb_currentaccount.setFont(new Font("宋体", Font.ITALIC, 18));
        jf_currentaccount.setFont(new Font("谐体", Font.BOLD | Font.ITALIC, 20));
        jf_currentaccount.setEnabled(false);
        jf_currentaccount.setHorizontalAlignment(JTextField.CENTER);

        //绑定回车键
        jf_main.getRootPane().setDefaultButton(bt_connect);
        // remove the binding for pressed
        jf_main.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ENTER"), "none");
        // retarget the binding for released
        jf_main.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released ENTER"), "press");

        //设置集合中窗口的默认打开状态
        judgewindows.put("jf_setting", false);
        judgewindows.put("jf_detail", false);


        //初始化面板
        JPanel jPanel_currentaccount = new JPanel();
        JPanel jPanel_currentconnect = new JPanel(new GridLayout(1, 2, 0, -10));
        JPanel jPanel_bt = new JPanel(new GridLayout(1, 2, 0, 10));


        //Connect按钮的点击事件
        bt_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //----定时任务-----


                //----定时任务结束-------
                //用于测试
                //jf_currentaccount.setText(Data.LoginDataSave.prNode.get("zjutzjc_connecterplus_username", ""));
//                System.out.println(
//                        Data.LoginDataSave.DDDDD + Data.LoginDataSave.prNode.get
//                                ("zjutzjc_connecterplus_username", "")
//                                + Data.LoginDataSave.upass + Data.LoginDataSave.prNode.get
//                                ("zjutzjc_connecterplus_password", "")
//                                + Data.LoginDataSave.omkKey);
                try {
                    if (bt_connect.getLabel().equals("Connect")) {
                        String srlogin = SendLoginPost
                                .sendPost("http://192.168.100.2/",
                                        LoginDataSave.DDDDD + LoginDataSave.prNode.get
                                                ("zjutzjc_connecterplus_username", "")
                                                + LoginDataSave.upass + LoginDataSave.prNode.get
                                                ("zjutzjc_connecterplus_password", "")
                                                + LoginDataSave.omkKey);

                        Loginutils utils = new Loginutils();
                        String connectresult = utils.details(srlogin);
                        System.out.println(connectresult);

                        if (connectresult != null && connectresult.equals("连接成功!")) {
                            //同步数据库pcstate
                            System.out.println(LoginDataSave.prNode.get("zjutzjc_connecterplus_objectId", ""));
                            bt_connect.setLabel("Unconnect");
                            lb_currentconnect.setText("已连接");
                            lb_currentconnectimg.setIcon(img_connect);
                            jf_main.setVisible(true);//重绘
                            //判断数据库中是否存在改帐号

                            if (LoginDataSave.prNode.get("zjutzjc_connecterplus_objectId", "").toString().equals("")) {
                                if (BmobDemo.GetObjectIdQuery(lb_currentaccount.getText().toString()) == null) {
                                    System.out.println("bucunzai");
                                    //数据库中不存在该帐号
                                    BmobDemo.Insert(jf_currentaccount.getText().toString());
                                    LoginDataSave.prNode.put("zjutzjc_connecterplus_objectId", BmobDemo.GetObjectIdQuery(LoginDataSave.prNode.get("zjutzjc_connecterplus_username", "").toString()));
                                }
                            }

                            BmobDemo.UpdatePCstate("PCstate", true);
                            //--------检查手机信号-------

                            timer = new java.util.Timer();
                            //设置定时任务
                            long delay = 0;
                            long intevalPeriod = 5*60 * 1000;
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    if (BmobDemo.Query("flag").toString().equals("true")) {
                                        //pc状态断开
                                        timer.cancel();
                                        BmobDemo.UpdatePCstate("PCstate", false);
                                        BmobDemo.UpdatePCstate("flag", false);
                                        System.out.println("退网");
                                        String result = SendLogoutPost.sendGet("http://192.168.100.2/F.htm", "");
                                        String connectresult = utils.details(result);
                                        JOptionPane.showMessageDialog(null, "系统检测到您已离开，已自动断线", "Warning", JOptionPane.WARNING_MESSAGE);
                                        bt_connect.setLabel("Connect");
                                        lb_currentconnect.setText("未连接");
                                        lb_currentconnectimg.setIcon(img_unconnect);
                                        jf_main.setVisible(true);//重绘

                                    }

                                }
                            };
                            timer.scheduleAtFixedRate(task, delay, intevalPeriod);
                            //----end---

                        } else {
                            JOptionPane.showMessageDialog(null, connectresult, "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (bt_connect.getLabel().equals("Unconnect")) {
                        BmobDemo.UpdatePCstate("PCstate", false);
                        String result = SendLogoutPost.sendGet("http://192.168.100.2/F.htm", "");
                        String connectresult = LogoutUtils.details(result);
                        if (connectresult.contains("注销成功")) {
                            timer.cancel();
                            bt_connect.setLabel("Connect");
                            lb_currentconnect.setText("未连接");
                            lb_currentconnectimg.setIcon(img_unconnect);
                            jf_main.setVisible(true);//重绘
                        }
                    }
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(null, "联网失败，请连接校园网后重试", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                catch (SocketException se){
                    JOptionPane.showMessageDialog(null, "联网失败，请连接校园网后重试", "Warning", JOptionPane.WARNING_MESSAGE);

                }
            }
        });

        //Setting按钮的点击事件
        bt_setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (judgewindows.get("jf_setting").equals(false)) {
                    //Setting页面
                    JFrame jf_setting = new JFrame("Setting");
                    //将集合中的打开状态设置为true
                    judgewindows.put("jf_setting", true);
                    jf_setting.setSize(180, 330);//设置窗口大小
                    jf_setting.setResizable(false);//设置窗口大小不可变
                    jf_setting.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置窗体关闭方式

                    // 第 1 个 JPanel, 使用默认的浮动布局
                    JPanel panel_setting_01 = new JPanel();
                    panel_setting_01.add(new JLabel("帐号："));
                    JTextField jf_username = new JTextField(12);
                    panel_setting_01.add(jf_username);

                    // 第 2 个 JPanel, 使用默认的浮动布局
                    JPanel panel_setting_02 = new JPanel();
                    panel_setting_02.add(new JLabel("密码："));
                    JPasswordField jpf_password = new JPasswordField(12);
                    panel_setting_02.add(jpf_password);

                    // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
                    JPanel panel_setting_03 = new JPanel(new GridLayout(1, 1, 0, 0));
                    JButton bt_change = new JButton("更改");
                    panel_setting_03.add(bt_change);

                    //绑定回车键
                    jf_setting.getRootPane().setDefaultButton(bt_change);
                    // remove the binding for pressed
                    jf_setting.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                            .put(KeyStroke.getKeyStroke("ENTER"), "none");
                    // retarget the binding for released
                    jf_setting.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                            .put(KeyStroke.getKeyStroke("released ENTER"), "press");

                    bt_change.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            //将帐号密码数据库Id号码写进注册表
                            LoginDataSave.prNode.put("zjutzjc_connecterplus_username", jf_username.getText().toString());
                            LoginDataSave.prNode.put("zjutzjc_connecterplus_password", jpf_password.getText().toString());
                            //LoginDataSave.prNode.put("zjutzjc_connecterplus_objectId", BmobDemo.GetObjectIdQuery(LoginDataSave.prNode.get("zjutzjc_connecterplus_username", "").toString()));
                            jf_currentaccount.setText(LoginDataSave.prNode.get("zjutzjc_connecterplus_username", ""));
                            jf_setting.dispatchEvent(new WindowEvent(jf_setting, WindowEvent.WINDOW_CLOSING));
                            judgewindows.put("jf_setting", false);
                            //将集合中的打开状态设置为false
                        /*Data.LoginDataSave.LOGIN_USERS.put("username", jf_username.getText().toString());
                        Data.LoginDataSave.LOGIN_USERS.put("password", jf_password.getText().toString());*/

                        }
                    });
                    //设置窗口关闭事件
                    jf_setting.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            judgewindows.put("jf_setting", false);
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {

                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });


                    // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
                    Box box_setting = Box.createVerticalBox();
                    box_setting.add(panel_setting_01);
                    box_setting.add(panel_setting_02);
                    box_setting.add(panel_setting_03);
                    jf_setting.setContentPane(box_setting);
                    jf_setting.pack();


                    jf_setting.setVisible(true);//重绘
                }
            }
        });

        //查看联网详情（上网时间，流量，账户余额）

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


                if (lb_currentconnect.getText().equals("已连接") && judgewindows.get("jf_detail").equals(false)) {
                    String s = SendDetailGet.sendGet("http://192.168.100.2", "");
                    DetailUtils detailUtils = new DetailUtils();
                    detailUtils.details(s);
                    //Detail页面
                    JFrame jf_detail = new JFrame("Detail");
                    //将集合中jf_detail的状态设置为true
                    judgewindows.put("jf_detail", true);
                    jf_detail.setSize(180, 330);//设置窗口大小
                    jf_detail.setResizable(false);//设置窗口大小不可变
                    jf_detail.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置窗体关闭方式

                    // 第 1 个 JPanel, 使用默认的浮动布局
                    JPanel panel_detail_01 = new JPanel(new GridLayout(1, 1, 0, 0));
                    panel_detail_01.add(new JLabel("累计时间:" + LoginDataSave.time + " Min"));

                    // 第 2 个 JPanel, 使用默认的浮动布局
                    JPanel panel_detail_02 = new JPanel(new GridLayout(1, 1, 0, 0));
                    panel_detail_02.add(new JLabel("累计流量:" + LoginDataSave.flow1 / 1024 + LoginDataSave.flow3 + LoginDataSave.flow0 / 1024 + " MByte"));


                    // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
                    JPanel panel_detail_03 = new JPanel(new GridLayout(1, 1, 0, 0));
                    panel_detail_03.add(new JLabel("余额:" + LoginDataSave.fee1 / 10000 + "元"));


                    // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
                    Box box_setting = Box.createVerticalBox();
                    box_setting.add(panel_detail_01);
                    box_setting.add(panel_detail_02);
                    box_setting.add(panel_detail_03);
                    jf_detail.setContentPane(box_setting);
                    jf_detail.pack();
                    jf_detail.setVisible(true);//重绘

                    //Detail窗口关闭时，将集合中的jf_detail设置为false
                    jf_detail.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {

                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            judgewindows.put("jf_detail", false);
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });
                } else if (lb_currentconnect.getText().equals("未连接") && judgewindows.get("jf_detail").equals(false)) {
                    //Detail页面
                    JFrame jf_detail = new JFrame("Detail");
                    //将集合中jf_detail的状态设置为true
                    judgewindows.put("jf_detail", true);
                    jf_detail.setSize(180, 330);//设置窗口大小
                    jf_detail.setResizable(false);//设置窗口大小不可变
                    jf_detail.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置窗体关闭方式

                    // 第 1 个 JPanel, 使用默认的浮动布局
                    JPanel panel_detail_01 = new JPanel(new GridLayout(1, 1, 0, 0));
                    panel_detail_01.add(new JLabel("上次使用时间:" + LoginDataSave.time + " Min"));

                    // 第 2 个 JPanel, 使用默认的浮动布局
                    JPanel panel_detail_02 = new JPanel(new GridLayout(1, 1, 0, 0));
                    panel_detail_02.add(new JLabel("上次使用流量:" + LoginDataSave.flow1 / 1024 + LoginDataSave.flow3 + LoginDataSave.flow0 / 1024 + " MByte"));


                    // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
                    JPanel panel_detail_03 = new JPanel(new GridLayout(1, 1, 0, 0));
                    panel_detail_03.add(new JLabel("当前余额:" + LoginDataSave.fee1 / 10000 + "元"));


                    // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
                    Box box_setting = Box.createVerticalBox();
                    box_setting.add(panel_detail_01);
                    box_setting.add(panel_detail_02);
                    box_setting.add(panel_detail_03);
                    jf_detail.setContentPane(box_setting);
                    jf_detail.pack();
                    jf_detail.setVisible(true);//重绘

                    //Detail窗口关闭时，将集合中的jf_detail设置为false
                    jf_detail.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {

                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            judgewindows.put("jf_detail", false);
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });
                }


            }


            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        };


        lb_currentconnect.addMouseListener(mouseListener);
        lb_currentconnectimg.addMouseListener(mouseListener);


        //jPanel面板
        jPanel_currentaccount.add(lb_currentaccount);
        jPanel_currentaccount.add(jf_currentaccount);

        jPanel_currentconnect.add(lb_currentconnectimg);
        jPanel_currentconnect.add(lb_currentconnect);
        jPanel_bt.add(bt_connect);
        jPanel_bt.add(bt_setting);


        Box box_main = Box.createVerticalBox();
        box_main.add(jPanel_currentaccount);
        box_main.add(Box.createVerticalStrut(2));
        box_main.add(jPanel_currentconnect);
        box_main.add(Box.createVerticalStrut(8));
        box_main.add(jPanel_bt);
        jf_main.setContentPane(box_main);
        jf_main.pack();
        jf_main.setVisible(true);//重绘


    }


}
