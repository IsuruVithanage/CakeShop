package model;

public class ProductDetail {
    private String productName;
    private String weight;
    private double price;

    public ProductDetail() {
    }

    public ProductDetail(String productName, String weight, double price) {
        this.productName = productName;
        this.weight = weight;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String wight) {
        this.weight = wight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "productName='" + productName + '\'' +
                ", wight='" + weight + '\'' +
                ", price=" + price +
                '}';
    }
}
