package ticket_service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ticket_service.controller.TicketServiceController;

import java.util.Queue;
import java.util.concurrent.*;

@SpringBootApplication
public class TicketServiceApplication implements CommandLineRunner {

    @Autowired
    TicketServiceController ticketServiceController;

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Queue q= new ConcurrentLinkedQueue();
        for(int i=1; i<=10; i++){   //模拟有x张票
            q.add(i);
        }

        ExecutorService threadPool= Executors.newFixedThreadPool(16);
        //4 个进程抢票,结果输出到 队列
        for(int i=0; i<16; i++) {
            threadPool.submit(() -> {
                while (true) {
                    ticketServiceController.getTicket(q);
                }
            });
        }
        //4 个进程从队列取出结果写入到 redis
        /*
        for(int i=0; i<4; i++) {
            threadPool.submit(() -> {
                while (true) {
                    ticketServiceController.writeResultToRedis();
                }
            });
        }

         */
    }
}
