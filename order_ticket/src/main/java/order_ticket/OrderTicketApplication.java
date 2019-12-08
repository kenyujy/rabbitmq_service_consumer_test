package order_ticket;

import online_store.entity.OrderResult;
import online_store.entity.TicketOrder;
import order_ticket.controller.TicketOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class OrderTicketApplication implements CommandLineRunner {

    @Autowired
    TicketOrderController ticketOrderController;

    public static void main(String[] args) {
        SpringApplication.run(OrderTicketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ExecutorService threadPool= Executors.newFixedThreadPool(8);
        for(int i=0; i<20000; i++){
            threadPool.submit(()->{
               TicketOrder order= ticketOrderController.orderTicket();  //下单
                try {
                    TimeUnit.MILLISECONDS.sleep(1);    //睡一下在取下单结果
                    //应该从redis里面取结果
                    //OrderResult result= ticketOrderController.getResultFromRedis(order);
                    //if(null!= result){
                    //    System.out.println(result.getTicketOrder().getOrderId()+": "+ result.getResult());
                    //}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
    }
}
