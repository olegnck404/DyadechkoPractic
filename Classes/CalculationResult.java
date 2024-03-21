import java.io.Serializable;

public class CalculationResult implements Serializable {

    private String type;
    private double value;

    public CalculationResult(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}
