import components.stack.Stack;

/**
 * Model class.
 *
 * @author Kun Liu
 */
public final class AppendUndoModel1 implements AppendUndoModel {

    /**
     * Model variables.
     */
    private String input;
    private Stack<String> output;

    /**
     * Default constructor.
     */
    public AppendUndoModel1() {
        /*
         * Initialize model
         */
        this.input = "";

    }

    @Override
    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String input() {
        return this.input;
    }

    @Override
    public void addOutput(String output) {

        this.output.push(output);
    }

    @Override
    public void clearOutput() {

        this.output.clear();
    }

    @Override
    public String undoOutput() {

        String s = this.output.pop();
        return s;
    }

    @Override
    public Stack<String> output() {

        return this.output;
    }
}
