public class TextResultFormatter extends ResultFormatter {

    @Override
    public String formatResult(CalculationResult result) {
        return "Type: " + result.getType() + ", Value: " + result.getValue();
    }
}
