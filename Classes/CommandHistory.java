import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private List<Command> commands = new ArrayList<>();

    public void add(Command command) {
        commands.add(command);
    }

    public void undo() {
        if (!commands.isEmpty()) {
            Command command = commands.remove(commands.size() - 1);
            if (command instanceof UndoableCommand) {
                ((UndoableCommand) command).undo();
            }
        }
    }

    public boolean canUndo() {
        return !commands.isEmpty();
    }
}
