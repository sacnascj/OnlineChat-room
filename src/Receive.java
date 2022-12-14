import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable {
    private DataInputStream dis;
    private Socket client;
    private boolean isRunning;

    public Receive(Socket client) {
        this.client = client;
        try {
            dis = new DataInputStream(client.getInputStream());// 服务端发回的数据
            isRunning = true;
        } catch (IOException e) {
            System.out.println("---客户接收端构造时异常---");
            release();
        }
    }

    private String receive() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            System.out.println("---客户接收端接收消息异常---");
            release();
        }
        return msg;
    }

    private void release() {
        isRunning = false;
        Utils.close(dis, client);
    }

    @Override
    public void run() {
        while (isRunning) {
            String msg = receive();
            if (!msg.equals("")) {
                System.out.println(msg);
            }
        }
    }
}
