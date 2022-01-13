package sample;

import java.util.Date;

public class MyProduct {
    private String mark;
    private String model;
    private String color;
    private String datew;
    private String datez;
    private double price;
    private Integer num;

    public MyProduct(String mark, String model, String color, String datew, String datez, double price, Integer num) {
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.datew = datew;
        this.datez = datez;
        this.price = price;
        this.num = num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDatew(String datew) {
        this.datew = datew;
    }

    public void setDatez(String datez) {
        this.datez = datez;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getNum(){
        return num;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getDatew() {
        return datew;
    }

    public String getDatez() {
        return datez;
    }

    public double getPrice() {
        return price;
    }
}
