import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JFrame;

public class Gui extends JFrame {
    private static Client client;
    private static String backMeg = "";
    static JFrame f = new JFrame("登录");
    static TextArea textArea = new TextArea(20, 50);

    public void frame() {
        // f.setSize(500, 80);
        // 边框布局
        f.setLayout(new BorderLayout(5, 5));
        // 窗口居中
        int windowWidth = f.getWidth(); // 获得窗口宽
        int windowHeight = f.getHeight(); // 获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width; // 获取屏幕的宽
        int screenHeight = screenSize.height; // 获取屏幕的高
        f.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
        // 创建一个按钮面板
        Panel panel = new Panel();
        // 按钮
        JButton button = new JButton("确认");
        button.setPreferredSize(new Dimension(100, 30));
        // 在窗体上添加按钮
        panel.add(button);
        f.add(panel, BorderLayout.EAST);
        // 创建标签
        JLabel j1 = new JLabel("用户名：");
        j1.setFont(new Font("宋体", Font.BOLD, 18));
        j1.setBounds(96, 66, 95, 30);
        f.add(j1, BorderLayout.WEST);
        // 创建文本框
        JTextField text1 = new JTextField("用户名");
        text1.setToolTipText("用户名");
        text1.setBounds(204, 102, 200, 30);
        text1.setColumns(10);
        text1.setForeground(Color.lightGray);// 设置前景色为灰色
        text1.setEditable(false);// 设置为不可编辑状态
        text1.setBackground(Color.WHITE);// 设置背景色为白色
        text1.addMouseListener(new MouseAdapter() {
            // 点击输入框去除文字,激活文本框
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!text1.isEditable()) {
                        text1.setText("");
                        text1.setForeground(Color.BLACK);
                        text1.setEditable(true);
                        text1.requestFocus();
                    }
                }
            }
        });
        f.add(text1, BorderLayout.CENTER);
        // 设置窗口为最佳大小
        f.pack();
        // 显示窗体
        f.setVisible(true);
        button.addActionListener(new ActionListener() {
            // 单击按钮执行的方法
            public void actionPerformed(ActionEvent e) {
                closeThis();
                // 窗口设置
                JFrame frame = new JFrame("在线聊天室");
                frame.setBounds(200, 200, 650, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout(5, 5));

                // 设置在屏幕的位置
                int windowWidth = frame.getWidth(); // 获得窗口宽
                int windowHeight = frame.getHeight(); // 获得窗口高
                Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
                Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
                int screenWidth = screenSize.width; // 获取屏幕的宽
                int screenHeight = screenSize.height; // 获取屏幕的高
                frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示

                // 创建输入文本框
                JTextField text2 = new JTextField("输入发送的消息");
                text2.setToolTipText("输入发送的消息");
                text2.setPreferredSize(null);
                text2.setBounds(204, 102, 500, 30);
                text2.setColumns(10);
                text2.setForeground(Color.lightGray);// 设置前景色为灰色
                text2.setEditable(false);// 设置为不可编辑状态
                text2.setBackground(Color.WHITE);// 设置背景色为白色
                text2.addMouseListener(new MouseAdapter() {
                    // 点击输入框去除文字,激活文本框
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!text2.isEditable()) {
                                text2.setText("");
                                text2.setForeground(Color.BLACK);
                                text2.setEditable(true);
                                text2.requestFocus();
                            }
                        }
                    }
                });

                // 创建按钮
                JButton button1 = new JButton("发送");
                button1.setPreferredSize(new Dimension(100, 30));

                // 创建标签
                JLabel j1 = new JLabel("请输入发送的消息");
                j1.setFont(new Font("宋体", Font.BOLD, 18));
                j1.setPreferredSize(new Dimension(50, 30));
                // j1.setBounds(96, 66, 95, 30);

                // 创建滚动条
                Container container = frame.getContentPane();

                JScrollPane jScrollPane = new JScrollPane(textArea);
                // 创建面板
                Panel p1 = new Panel();
                Panel p2 = new Panel();
                // 采用网格布局格式
                p2.setLayout(new GridLayout(1, 3));
                p1.setLayout(new GridLayout());
                p1.add(container.add(jScrollPane));
                // p1.add(jp);
                p2.add(j1);
                p2.add(text2);
                p2.add(button1);
                frame.add(p1, BorderLayout.CENTER);
                frame.add(p2, BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
            }

        });
        // 关闭窗口
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public static void closeThis() {
        f.dispose();

    }

    public void back(String m) {
        backMeg = m;
        textArea.append(backMeg + "\n");
        if (backMeg.contains(":")) {
            String context = backMeg;
            String name = "\n" + backMeg.split("@")[1].split("\\:")[0] + ":";
            context = context.replace('@', '\n'); // 时间隔开
            context = context.replace("$", name);
            textArea.append(context + "\n"); // 显示文本内容增加
        }
    }

    public static void main(String[] args) {
        new Gui().frame();

    }
}
