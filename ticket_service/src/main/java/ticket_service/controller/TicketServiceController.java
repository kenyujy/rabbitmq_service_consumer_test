package ticket_service.controller;


import online_store.entity.OrderResult;
import online_store.entity.TicketOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Queue;
import java.util.concurrent.TimeUnit;


@Service
public class TicketServiceController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    public void getTicket(Queue q) {

        TicketOrder order= (TicketOrder) rabbitTemplate.receiveAndConvert("ticketRequest");
        //从消息队列取出订票请求, 处理请求
        if(null!= order) {

            Integer obj = (Integer) q.poll();  //从ConcurrentLinkedQueue 取出
            if (null != obj) {
                rabbitTemplate.convertAndSend("response.direct",
                        "getTicketResponse", new OrderResult(order, "抢票成功，票号: " + obj,1));//抢到票
            } else {
                rabbitTemplate.convertAndSend("response.direct",
                        "getTicketResponse", new OrderResult(order, "抢票失败",0));
            }
        }
    }

    //把结果队列写到 redis
    public void writeResultToRedis() {

        OrderResult result= (OrderResult) rabbitTemplate.receiveAndConvert("ticketResponse");
        //把结果写到 redis
        if(null!= result) {   //需要判断，否则覆盖
            redisTemplate.opsForHash().putIfAbsent("ticketOrderResult",
                    result.getTicketOrder().getOrderId(), result);

            //redisTemplate.expire("ticketOrderResult", 20, TimeUnit.SECONDS);
        }
    }
}
