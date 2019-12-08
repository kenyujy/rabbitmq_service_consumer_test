package ticket_service;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import online_store.entity.OrderResult;
import online_store.entity.TicketOrder;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ConnectionFactory connectionFactory;  //注入配置的 rabbit mq connection factory

    @Autowired
    RedisTemplate redisTemplate;

    @org.junit.Test
    public void test1(){

        Queue q= new ConcurrentLinkedQueue();
        for(int i=1; i<=100; i++){
            q.add(i);
        }

        TicketOrder order= (TicketOrder) rabbitTemplate.receiveAndConvert("ticketRequest");

        Integer obj= (Integer) q.poll();
        if(null!= obj){
            rabbitTemplate.convertAndSend("response.direct",
                    "getTicketResponse", new OrderResult(order,"抢票成功，票号: "+obj,1));//抢到票
        }else {
            rabbitTemplate.convertAndSend("response.direct",
                    "getTicketResponse", new OrderResult(order,"抢票失败",0));
        }
    }

    @org.junit.Test
    public void test2(){
        rabbitTemplate.convertAndSend("response.direct",
                "getTicketResponse", "msg");//抢到票
    }

    @org.junit.Test //从队列 读取结果, 并写到 redis
    public void test3() throws IOException, TimeoutException {
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        channel.basicQos(1);
        boolean flag= true;
        while (flag) {
            GetResponse b = channel.basicGet("ticketResponse", false);
            if (null != b) {
                String s = new String(b.getBody());
                Gson gson = new Gson();
                OrderResult result = gson.fromJson(s, OrderResult.class);
                redisTemplate.opsForHash().putIfAbsent("ticketOrderResult",
                        result.getTicketOrder().getOrderId(), result);
                channel.basicAck(b.getEnvelope().getDeliveryTag(), false);  //确认消息
            }else flag= false;
        }
        channel.close();
    }

    @org.junit.Test
    public void test4(){
        OrderResult result= (OrderResult) rabbitTemplate.receiveAndConvert("ticketResponse");
        //把结果写到 redis
        if(null!= result)
            redisTemplate.opsForHash().putIfAbsent("ticketOrderResult",
                    result.getTicketOrder().getOrderId(), result);
    }
}
