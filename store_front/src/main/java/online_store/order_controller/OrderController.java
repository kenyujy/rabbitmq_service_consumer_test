package online_store.order_controller;

import online_store.Address;
import online_store.City;
import online_store.Order;
import online_store.Province;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class OrderController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @ResponseBody
    @RequestMapping(value="/new_order/{product}", method= RequestMethod.GET)
    public String createOrder(@PathVariable("product") String product){

        Address deliveryAddress= new Address(Province.广东, City.深圳,"无人之地");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String date= formatter.format(new Date()).toString();
        Long orderID= Long.parseLong(date+"00"+new Random().nextInt(10000));

        Order order= new Order(orderID, product, 1, "10001", 1000.0, deliveryAddress, new Date());

        rabbitTemplate.convertAndSend("orderQueue.direct","new.order", order);

        return product+ " "+order.getOrderQty()+" 件"+ "下单成功";
    }
}
