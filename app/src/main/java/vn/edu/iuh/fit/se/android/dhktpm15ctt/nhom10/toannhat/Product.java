package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

public class Product {
    private String id;
    private String author;
    private String bookName;
    private double cost;
    private String description;
    private String thumbnail;

    public Product() {
    }

    public Product(String id) {
        this.id = id;
    }

    public Product(String author, String bookName, double cost, String description, String thumbnail) {
        this.author = author;
        this.bookName = bookName;
        this.cost = cost;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Product(String id, String author, String bookName, double cost, String description, String thumbnail) {
        this.id = id;
        this.author = author;
        this.bookName = bookName;
        this.cost = cost;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", bookName='" + bookName + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
