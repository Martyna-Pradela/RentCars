package sample;

public class ProductSearch {
    Integer num;
    String mark, model, color, rent;
    Double price;

    public ProductSearch(Integer num, String mark, String model, String color, Double price, String rent) {
        this.num = num;
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.rent = rent;
        this.price = price;
    }

    public Integer getNum() {
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

    public String getRent() {
        return rent;
    }

    public Double getPrice() {
        return price;
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

    public void setRent(String rent) {
        this.rent = rent;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
