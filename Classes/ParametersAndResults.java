import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParametersAndResults implements Serializable {

    private double[] parameters;
    private List<CalculationResult> results;

    public ParametersAndResults(double[] parameters, double[] results) {
        this.parameters = parameters;
        this.results = new ArrayList<>();
        for (double result : results) {
            this.results.add(new CalculationResult("Default", result));
        }
    }

    public void addResult(CalculationResult result) {
        results.add(result);
    }

    public List<CalculationResult> getResults() {
        return results;
    }
}
