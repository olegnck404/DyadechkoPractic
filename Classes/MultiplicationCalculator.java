public class MultiplicationCalculator extends AbstractCalculator {

    @Override
    public double calculate(double a, double b) {
        return a * b;
    }

    @Override
    public String getType() {
        return "Multiplication Calculator";
    }
}
