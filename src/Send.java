import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable {
    private BufferedReader console;
    private DataOutputStream dos;
    private Socket client;
    private boolean isRunning;
    private String name;

    public Send(Socket client, String name) {
        this.client = client;
        console = new BufferedReader(new InputStreamReader(System.in));
        this.isRunning = true;
        this.name = name;
        try {
            dos = new DataOutputStream(client.getOutputStream());// 发给服务端
            // 发送名称
            send(name);
        } catch (IOException e) {
            System.out.println("Send类构造时异常");
            release();
        }
    }

    private String getStrFromConsole() {
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("---客户发送端发送消息异常---");
            release();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            String msg = getStrFromConsole();
            if (!msg.equals("")) {
                send(msg);
            }
        }
    }

    private void release() {
        this.isRunning = false;
        Utils.close(dos, client);
    }
}
