import java.io.Serializable;
import java.util.Objects;

public class Laptop implements Serializable {
    private String model;
    private int ram;

    public Laptop(String model, int ram) {
        this.model = model;
        this.ram = ram;
    }

    @Override
    public String toString() {
        return "Laptop{model='" + model + "', ram=" + ram + " GB}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laptop laptop = (Laptop) o;
        return ram == laptop.ram && Objects.equals(model, laptop.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, ram);
    }
}