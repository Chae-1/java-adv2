네트워크
==
### 예제 1
```java
//client
public class ClientV1 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");
        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());


        log("소켓 연결: " + socket);

        // 서버에게 문자 보내기
        String toSend = "Hello";
        output.writeUTF(toSend);

        log("client -> server: " + toSend);

        // 서버로부터 문자 받기.
        String received = input.readUTF();
        log("server -> client: " + received);

        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
    }

}

// server
public class ServerV1 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");

        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        // 클라이언트로부터 문자 받기
        String received = input.readUTF();
        log("client -> server: " + received);

        // 클라이언트에 문자 보내기
        String toSend = received + " World!";
        output.writeUTF(toSend);
        log("client <- server: " + toSend);

        // 자원 정리
        log("연결 종료");
        output.close();
        input.close();
        socket.close();
        serverSocket.close();
    }
}
```
**클라이언트**
- 호스트가 localhost 이면서 포트 번호가 12345인 호스트에 TCP 연결 요청을 시도한다.
  - localhost 라는 호스트를 통해 IP를 찾기 위해 내부적으로 InetAddress 클래스를 사용한다.
  - 결과적으로 127.0.0.1:12345에 TCP 연결 시도한다.
- 연결이 체결된 이후에는 Socket을 통해 서버와 데이터를 주고 받는다.
  - Socket은 데이터를 주고받기 위한 InputStream, OutputStream을 제공한다.

**서버**
- 클라이언트의 요청을 받아들이기 위해 특정 포트를 열어두어야 한다.
- 이때, 서버는 ServerSocket을 사용한다.
  - 클라이언트가 해당 포트로 서버에 연결할 수 있게된다.
- 클라이언트가 서버 포트로 연결을 시도하면 OS 계층에서 3 way handshake가 발생, TCP 연결이 완료.
- TCP 연결이 완료되면 OS backlog queue라는 곳에 클라이언트, 서버의 TCP 연결 정보를 보관한다.
- **서버 소켓은 TCP 연결만 지원하는 특별한 소켓이다.**
- accept()를 호출하면 TCP 연결 정보를 기반으로 클라이언트와 통신할 수 있는 Socket 객체를 반환한다. 

## 서버소켓과 연결
```java
public class ServerV2 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        // 1. 소켓 연결
        ServerSocket serverSocket = new ServerSocket(PORT);

        // 2. blocking -> 별도의 스레드가 생성하고 연결을 유지해야할 것 같다.

        // 소켓을 생성해주는 별도의 스레드
        Socket socket = serverSocket.accept();
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        while (true) {
            String receivedMessage = input.readUTF(); // 블로킹
            log("server <- client: " + receivedMessage);
            if ("exit".equals(receivedMessage)) {
                break ;
            }

            String sendMessage = receivedMessage + " Hello!";
            log("server -> client: " + sendMessage);
            output.writeUTF(sendMessage);
        }

        output.close();
        input.close();
        socket.close();
        serverSocket.close();
    }
}
```
- ServerSocket 을 통해 연결 요청을 시도하면 OS 내부적으로 3 way handshake가 발생하고 TCP 연결이 완료된다.
  - Socket 객체가 없다고 해서 TCP 연결이 안되는 것이 아니다.
- accept() 메서드를 통해 backlog queue를 조회한 뒤 Socket 객체를 생성하는 것이다.
- Socket 객체가 있어야 서로 메세지를 주고 받을 수 있다.

**Server 코드의 문제**
- 새로운 클라이언트 접속 시
  - 클라이언트 마다 별도의 Socket이 생성되어야 서버-클라이언트 간 메시지를 주고받을 수 있다.
  - main 스레드만으로는 accept() 메서드를 통한 별도의 Socket을 생성할 수 없다.
- 2개의 블로킹 작업이 존재한다.
  - `accept():` 클라이언트-서버의 연결을 위해 끝까지 대기하는 메서드.
  - `readXXX():` 클라이언트의 메시지를 받아서 처리하기 위해 끝까지 기한다.
- 블로킹 작업이 하나라도 있으면 하나의 스레드가 그 작업이 끝날 때까지 끝까지 대기하게 되는 문제가 발생한다.
  - 그렇기 때문에 별도의 스레드에서 동작해야한다.


## 네트워크 에외

### 연결 예외 
```java
 public class ConnectMain {
  public static void main(String[] args) throws IOException {
    unknownHostEx1();
    unknownHostEx2();
    connectionRefused();
  }

  private static void unknownHostEx1() throws IOException {
    try {
      Socket socket = new Socket("999.999.999.999", 80);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  private static void unknownHostEx2() throws IOException {
    try {
      Socket socket = new Socket("google.gogo", 80);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  private static void connectionRefused() throws IOException {
    try {
      Socket socket = new Socket("localhost", 45678); // 미사용 포트
    } catch (ConnectException e) {
      // ConnectException: Connection refused
      e.printStackTrace();
    }
  }
}
```
연결 예외는 크게 다음과 같이 2가지로 구분할 수 있다.
- UnKnownHostException
  - 호스트를 알 수 없을 때 발생하는 예외.
  - 존재하지 않는 IP에 대해 연결 요청을 시도할 경우
  - 존재하지 않는 도메인에 연결하는 경우.
- ConnectionException
  - IP에 대해서는 접속했지만, 유효하지 않는 PORT를 사용할 경우 발생할 수 있다.
  - 방화벽에 의해서도 막힐 수 있다.
  - 이 경우 OS는 TCP Reset 패킷을 전송한다.
  - 클라이언트가 연결 시도 중에 RST 패킷을 전송받으면 예외가 발생한다.

### 타임 아웃

```java
public class SoTimeoutServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket socket = serverSocket.accept();

        Thread.sleep(1000000);
    }

}

public class SoTimeoutClient {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 12345);
    InputStream input = socket.getInputStream();

    try {
      socket.setSoTimeout(3000); // 타임아웃 시간 설정.
      int read = input.read();
      System.out.println("read = " + read);
    } catch (Exception e) {
      e.printStackTrace();
    }
    socket.close();
  }
}

```
- 연결 대상 IP가 존재하지 않을 때 많은 시간동안 TCP 연결을 위해 대기한다.
- 무한정 기다리지 않고 일정 시간동안 연결이 체결되지 않았을 때, 강제적으로 종료시킬 수 있다. 이를 **타임 아웃**이라고 한다.
- 소켓 연결 시점에 timeout을 지정할 수 있다.
- read 타임아웃도 설정할 수 있다.
### 정상 종료
- TCP의 정상 연결 종료 과정은 다음과 같다.
- 서버나 클라이언트가 연결 종료를 요청하는 FIN 패킷을 전송한다.
- FIN 패킷을 전송받은 호스트는 ACK 패킷을 응답으로 전송한다. 이때 절반 연결이 닫히게 된다.
- ACK 패킷을 전송받은 호스트는 FIN + ACK 패킷을 동시에 보낸다.
- 이에 대한 ACK 패킷을 전송하는 것으로 양 호스트에 모든 연결이 해제된다.

다음과 같은 TCP 연결 해제과정을 4 way handshake라고 부른다.

```java
public class NormalCloseClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결 = " + socket);
        InputStream inputStream = socket.getInputStream();

        readByInputStream(socket, inputStream);
        readByBufferedReader(socket, inputStream);
        readByDataInputStream(socket, inputStream);
    }

    private static void readByDataInputStream(Socket socket, InputStream inputStream) throws IOException {
        DataInputStream input = new DataInputStream(inputStream);

        try {
            input.readUTF();
        } catch (EOFException e) {
            throw new RuntimeException(e);
        } finally {
            input.close();
            socket.close();
        }
    }

    private static void readByBufferedReader(Socket socket, InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String readString = br.readLine();
        log("readString = " + readString);
        if (readString == null) {
            br.close();
            socket.close();
        }

    }

    private static void readByInputStream(Socket socket, InputStream inputStream) throws IOException {
        int read = inputStream.read();
        log("read = " + read);
        if (read == -1) {
            inputStream.close();
            socket.close();
        }
    }

}
```
- 소켓이 종료된 이후에 메시지를 메시지를 읽게되면 더 이상 읽을 수 없다는 의미로 EOF(End of File)를 반환한다.
- 클라이언트가 EOF에 대한 응답을 반환받았다면 반드시 close()를 호출해서 정상 종료를 시켜야한다.

### 강제 종료
TCP 연결 도중 문제가 발생하면 RST 패킷이 발생한다.
- 절반 연결 해제 작업이 수행된다. (FIN에 대한 ACK 패킷 전송 까지)
- 서버 -> 클라이언트 방향에 대한 스트림은 닫힌다.
- 이 때, 클라이언트가 데이터를 전송할 수는 있지만 그에 대한 응답을 보낼 수 없기때문에 문제가 발생할 수 있다.
- 응답을 할 수 없는 상태이기때문에 RST 패킷을 보내 TCP 연결에 문제가 있어 종료한다.

```java
public class ResetCloseServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);

        socket.close();
        serverSocket.close();
        log("소캣 종료");
    }
}

public class ResetCloseClient {
  public static void main(String[] args) throws InterruptedException, IOException {
    Socket socket = new Socket("localhost", 12345);
    log("소캣 연결: " + socket);
    InputStream input = socket.getInputStream();
    OutputStream output = socket.getOutputStream();
    // client <- server: FIN
    Thread.sleep(1000); // 서버가 close() 호출할 때 까지 잠시 대기

    // client -> server: PUSH[1]
    output.write(1);

    // client <- server : RST
    Thread.sleep(1000);

    try {
      // RST 패킷을 받았다.
      int read = input.read();
      System.out.println("read = " + read);
    } catch (SocketException e) {
      e.printStackTrace();
    }

    try {
      // RST 패킷을 받았다.
      output.write(1);
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

}
```

