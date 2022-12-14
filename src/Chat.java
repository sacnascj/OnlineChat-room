import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.CopyOnWriteArrayList;

public class Chat {
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("---服务器开始工作---");

        ServerSocket server = new ServerSocket(8888);

        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");
            Channel c = new Channel(client);
            all.add(c); // 管理所有的成员
            new Thread(c).start();// 启动该成员的线程
        }
    }

    static class Channel implements Runnable {
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client;
        private boolean isRunning;
        private String name;

        public Channel(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());// 发送给客户端
                isRunning = true;
                // 获取名称
                this.name = receive();
                // 欢迎
                this.send("欢迎你的到来");
                sendOthers(this.name + "加入群聊", true);
            } catch (IOException e) {
                System.out.println("---构造时出现问题---");
                release();
            }
        }

        private String receive() {
            String msg = ""; // 避免空指针
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                System.out.println("---接受消息出现问题---");
                release();
            }
            return msg;
        }

        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("---发送消息出现问题---");
                release();
            }
        }

        /**
         * 群聊：把自己的消息发送给其他人
         * 私聊：约定数据格式：@XXX:msg
         * 
         * @param msg
         * @param isSys
         */
        private void sendOthers(String msg, boolean isSys) {
            boolean isPrivate = msg.startsWith("@");
            if (isPrivate) { // 私聊
                int index = msg.indexOf(":"); // 第一次冒号出现的位置
                // 获取目标和数据
                String targetName = msg.substring(1, index);
                msg = msg.substring(index + 1);
                for (Channel other : all) {
                    if (other.name.equals(targetName)) { // 目标
                        other.send(this.name + "对您说悄悄话: " + msg); // 群聊消息
                    }
                }
            } else {
                for (Channel other : all) {
                    if (other == this) {
                        continue;
                    }
                    if (!isSys) {
                        other.send(this.name + ": " + msg); // 群聊消息
                    } else {
                        other.send("系统消息：" + msg); // 系统消息
                    }
                }
            }
        }

        @Override
        public void run() {
            while (isRunning) {
                String msg = receive();
                if (!msg.equals("")) {
                    sendOthers(msg, false);
                }
            }

        }

        private void release() {
            this.isRunning = false;
            Utils.close(dis, dos, client);
            all.remove(this);
            sendOthers(this.name + "离开聊天室", true);
        }
    }
}
