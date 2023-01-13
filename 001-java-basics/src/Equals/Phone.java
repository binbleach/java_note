package Equals;

public class Phone {
    private String brand;
    private double price;
    private int year;

    public Phone(String brand, double price, int year) {
        this.brand = brand;
        this.price = price;
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Phone) {
            Phone other = (Phone) obj;
            if (other.getBrand() == this.brand && other.getPrice() == this.getPrice() && other.getYear() == this.getYear()) {
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }
}
