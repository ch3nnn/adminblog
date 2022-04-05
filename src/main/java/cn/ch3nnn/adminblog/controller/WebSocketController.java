package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.entity.MessageBody;
import cn.ch3nnn.adminblog.thread.ServerLogThread;
import cn.ch3nnn.adminblog.utils.YamlConfigurerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

/**
 * websocket 实时日志
 *
 * @Author ChenTong
 * @Date 2021/10/18 15:20
 */
@Controller
public class WebSocketController {


    /**
     * 消息发送工具对象
     */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    private Process process;
    private InputStream inputStream;
    private Thread thread;

    /**
     * 广播发送消息，将消息发送到指定的目标地址
     */
    @MessageMapping("/serviceLog")
    public void sendTopicMessage(MessageBody messageBody) {
        // 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
        try {
            final String logPath = YamlConfigurerUtil.getStrYmlVal("logging.file.name");
            process = Runtime.getRuntime().exec("tail -f " + logPath);
            inputStream = process.getInputStream();
            thread = new ServerLogThread(inputStream, simpMessageSendingOperations, messageBody);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭websocket 清理 thread  inputStream process
     *
     * @return 返回响应结果
     */
    @ResponseBody
    @RequestMapping("/admin/websocketLog/close")
    public ResultCode onClose() throws Exception {

        if (thread != null) {
            thread.interrupt();
            Thread.sleep(500);
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (process != null) {
            process.destroy();
        }

        return ResultCode.success("成功断开连接");
    }


}
