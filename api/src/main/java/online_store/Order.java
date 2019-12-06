package online_store;

import lombok.Data;
import online_store.Address;
import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private long orderId;
    private String productSku;
    private int orderQty;
    private String customerId;
    private double orderValue;
    private Address deliverAddress;
    private Date createDate;

    public Order() { }

    public Order(long orderId, String productSku, int orderQty,
                 String customerId, double orderValue, Address deliverAddress, Date createDate) {
        this.orderId = orderId;
        this.productSku = productSku;
        this.orderQty = orderQty;
        this.customerId = customerId;
        this.orderValue = orderValue;
        this.deliverAddress = deliverAddress;
        this.createDate = createDate;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    public Address getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(Address deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
