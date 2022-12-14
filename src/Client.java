import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("-----客户端开始工作-----");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Socket client = new Socket("localhost", 8888);
        System.out.println("请输入用户名："); // 不考虑重名
        String name = br.readLine();

        new Thread(new Send(client, name)).start();
        new Thread(new Receive(client)).start();
    }
}
