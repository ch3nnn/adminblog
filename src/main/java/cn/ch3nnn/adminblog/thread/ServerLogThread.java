package cn.ch3nnn.adminblog.thread;

import cn.ch3nnn.adminblog.entity.MessageBody;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author ChenTong
 * @Date 2021/10/18 15:24
 */
public class ServerLogThread extends Thread {

    private final InputStream inputStream;
    private final MessageBody messageBody;
    private final SimpMessageSendingOperations simpMessageSendingOperations;


    public ServerLogThread(InputStream inputStream,
                           SimpMessageSendingOperations simpMessageSendingOperations,
                           MessageBody messageBody) {

        this.inputStream = inputStream;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.messageBody = messageBody;
    }

    @Override
    public void run() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                messageBody.setContent(line);
                simpMessageSendingOperations.convertAndSend(messageBody.getDestination(), messageBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}