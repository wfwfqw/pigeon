package pigeon.communication.privacy.pigeon;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Msocket extends Socket {

    private static final String host="192.168.1.104";
    private static final int port=5000;

    private static Msocket socket=null;

    public Msocket(String host, int port) throws UnknownHostException, IOException {
        super(host, port);
    }

    public static Socket getsocket() throws IOException {
        if(socket==null){
            socket= new Msocket(host,port);
        }
        return  socket;
    }


}