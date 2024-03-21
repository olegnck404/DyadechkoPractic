public class TextTableFormatter extends ResultFormatter {
    private int columnWidth = 15; // Default column width

    // Constructor for customization
    public TextTableFormatter(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    public String formatResult(CalculationResult result) {
        StringBuilder sb = new StringBuilder();
        String headerFormat = "| %-" + columnWidth + "s | %-" + columnWidth + "s |\n";
        String lineSeparator = String.format("+%s+%-s+\n", "-".repeat(columnWidth), "-".repeat(columnWidth));

        sb.append(lineSeparator);
        sb.append(String.format(headerFormat, "Type", "Value"));
        sb.append(lineSeparator);
        sb.append(String.format(headerFormat, result.getType(), result.getValue()));
        sb.append(lineSeparator);
        return sb.toString();
    }
}
