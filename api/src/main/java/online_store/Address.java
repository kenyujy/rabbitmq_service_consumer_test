package online_store;


import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private String province;
    private String city;
    private String detailAddress;

    public Address() { }

    public Address(String province, String city, String detailAddress) {
        this.province = province;
        this.city = city;
        this.detailAddress = detailAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }
}
