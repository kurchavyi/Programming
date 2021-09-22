package Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SendingAnswerThread extends Thread{
    private final String answer;
    private final SocketChannel socketChannel;

    public SendingAnswerThread(SocketChannel socketChannel, String answer) {
        this.socketChannel = socketChannel;
        this.answer = answer;
    }

    @Override
    public void run() {
        send();
    }

    private void send() {
        ByteBuffer senderBuffer = ByteBuffer.wrap(answer.getBytes(StandardCharsets.US_ASCII));
        try {
            socketChannel.write(senderBuffer);
        } catch (IOException e) {
            System.out.println("error connections");
        }
        senderBuffer.clear();
    }
}
