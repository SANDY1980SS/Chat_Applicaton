package serverr;
public class MainServer {
    public static void main(String[] args) {
    Server s = new Server();    //It will invoke GUI part
    s.waitingForClient();       //It will wait for the client
    s.setIoStream(); // It will set the streams through which we will transfer the data
   
    }
}
