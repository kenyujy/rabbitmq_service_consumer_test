package online_store.order_process_controller;

import com.rabbitmq.client.Channel;
import online_store.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class OrderProcess {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /*
    @RabbitListener(queues ="order")  //注解，声明这个方法监听队列，listener 会一直监听消息队列
    public void processOrder(Message message, Order order, Channel channel){

        ExecutorService service= Executors.newFixedThreadPool(4);
        for(int i=0; i<4; i++) {
            service.submit(() -> {
                try {
                    fullfilOrder(message, order, channel);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }

            });
        }
    }

     */

    public void processOrder() throws InterruptedException {

        ExecutorService service= Executors.newFixedThreadPool(4);
        for(int i=0; i<4; i++) {
            TimeUnit.SECONDS.sleep(i*i);
            int j=1;
            service.submit(() -> {
                try {
                    while (true) {
                        fullfilOrder();
                        TimeUnit.SECONDS.sleep(j);
                    }
                } catch ( InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }

            });
        }
    }

    public void fullfilOrder() {

        Order order= (Order) rabbitTemplate.receiveAndConvert("order");
        if(null!= order) {
            System.out.println(Thread.currentThread().getName() + " 执行订单: " + order.getOrderId()
                    + " 货物: " + order.getProductSku() + " 送货地址: " + order.getDeliverAddress());
        }
    }
}
