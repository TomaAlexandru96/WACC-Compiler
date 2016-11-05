package visitor;

import symobjects.SymbolTable;

import java.util.LinkedList;
import java.util.List;

public abstract class Node {
    protected SymbolTable currentST;
    protected List<CompileTimeError> errors;

    public Node(SymbolTable currentST) {
        this.currentST = currentST;
        this.errors = new LinkedList<>();

        checkErrors();
        printErrors();
        instantiate();
    }

    /**
     * Returns weather there are any errors
     * @return boolean
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Returns the list of errors
     * @return List
     */
    public List<CompileTimeError> getErrors() {
        return errors;
    }

    /**
     * Prints the errors if there are any
     */
    private void printErrors() {
        errors.forEach(CompileTimeError::print);
    }

    /**
     * Updates the list of errors if there are any
     */
    protected abstract void checkErrors();

    /**
     * If there are no errors populate the semantic fields
     * and update the current SymbolTable
     */
    protected abstract void instantiate();
}
