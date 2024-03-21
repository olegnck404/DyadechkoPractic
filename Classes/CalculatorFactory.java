public class CalculatorFactory {

    public AbstractCalculator createCalculator(String type) {
        return createCalculator(type);
    }

    public ResultFormatter createFormatter(String type) {
        switch (type) {
            case "Text":
                return new TextResultFormatter();
            case "Html":
                return new HtmlResultFormatter();
            case "Table":
                return new TextTableFormatter(20);
            default:
                throw new IllegalArgumentException("Unknown formatter type: " + type);
        }
    }
}

