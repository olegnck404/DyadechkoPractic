public class HtmlResultFormatter extends ResultFormatter {

    @Override
    public String formatResult(CalculationResult result) {
        return "<html>" +
                "<head><title>" + result.getType() + " Result</title></head>" +
                "<body>" +
                "<h1>" + result.getType() + "</h1>" +
                "<p>Value: " + result.getValue() + "</p>" +
                "</body>" +
                "</html>";
    }
}
