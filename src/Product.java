import java.util.Objects;

public class Product {
    private int nameLen = 35;
    private int descLen = 75;
    private int IDLen = 6;

    private String name;
    private String description;
    private String ID;
    private double cost;

    public Product(String name, String description, String ID, double cost) {
        this.name = padString(name, nameLen);
        this.description = padString(description, descLen);
        this.ID = padString(ID, IDLen);
        this.cost = cost;
    }

    private String padString(String value, int length) {
        if (value.length() >= length) {
            return value.substring(0, length);
        }
        return String.format("%-" + length + "s", value);
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return ID;
    }

    public double getCost() {
        return cost;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    //OTHERS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(cost, product.cost) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(ID, product.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, cost);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name.trim() + '\'' +
                ", description='" + description.trim() + '\'' +
                ", ID='" + ID.trim() + '\'' +
                ", cost=" + cost +
                '}';
    }

    public String toCSV(){
        return  this.ID + " , " + this.name + " , " + this.description + " , " + this.cost;
    }
}
