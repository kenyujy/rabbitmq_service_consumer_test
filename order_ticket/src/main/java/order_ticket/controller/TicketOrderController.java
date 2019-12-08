package order_ticket.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import online_store.entity.OrderResult;
import online_store.entity.TicketOrder;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class TicketOrderController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ConnectionFactory connectionFactory;  //注入配置的 rabbit mq connection factory

    @Autowired
    RedisTemplate redisTemplate;

    //向消息队列发送订票请求
    public TicketOrder orderTicket(){

        TicketOrder order= new TicketOrder();
        rabbitTemplate.convertAndSend("orderQueue.direct","buyTicket", order);
        return order;
    }

    //从redis 取出结果
    public OrderResult getResultFromRedis(TicketOrder order) {

        OrderResult result= (OrderResult) redisTemplate.opsForHash().get("ticketOrderResult",order.getOrderId());
        return result;
    }

    public void getOrderResult(TicketOrder order) throws IOException, TimeoutException {

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

                if (result.getTicketOrder().getOrderId() == order.getOrderId()) {
                    System.out.println(result.getResult());
                    channel.basicAck(b.getEnvelope().getDeliveryTag(), false); //确认消息
                    break;
                } else {   //不是本次请求的响应结果放回消息
                    channel.basicNack(b.getEnvelope().getDeliveryTag(), false, true);
                }
            }else flag= false;
        }
        channel.close();
    }
}
