import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */
                if (s.lengthOfBlock() > 0) {
                    for (int i = 0; i < s.lengthOfBlock(); i++) {
                        Statement a = s.removeFromBlock(i);
                        count += countOfPrimitiveCalls(a);
                        s.addToBlock(i, a);
                    }
                }
                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                Statement a = s.newInstance();
                Condition c = s.disassembleIf(a);
                count += countOfPrimitiveCalls(a);
                s.assembleIf(c, a);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                Statement x = s.newInstance();
                Statement y = s.newInstance();
                Condition c = s.disassembleIfElse(x, y);
                count += countOfPrimitiveCalls(x) + countOfPrimitiveCalls(y);
                s.assembleIfElse(c, x, y);
                break;
            }

            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                Statement a = s.newInstance();
                Condition c = s.disassembleWhile(a);
                count += countOfPrimitiveCalls(a);
                s.assembleWhile(c, a);
                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String call = s.disassembleCall();
                if (call.equals("move") || call.equals("turnleft")
                        || call.equals("turnright") || call.equals("infect")
                        || call.equals("skip")) {
                    count++;
                }
                s.assembleCall(call);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                //Because all statements are included above
                break;
            }
        }
        return count;
    }

    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
     * switched. Every other statement is left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @updates s
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
            case BLOCK: {

                if (s.lengthOfBlock() > 0) {
                    for (int i = 0; i < s.lengthOfBlock(); i++) {
                        Statement a = s.removeFromBlock(i);
                        simplifyIfElse(a);
                        s.addToBlock(i, a);
                    }
                }

                break;
            }
            case IF: {

                Statement a = s.newInstance();
                Condition c = s.disassembleIf(a);
                simplifyIfElse(a);
                s.assembleIf(c, a);

                break;
            }
            case IF_ELSE: {

                Statement x = s.newInstance();
                Statement y = s.newInstance();
                Condition c = s.disassembleIfElse(x, y);
                if (c == Condition.NEXT_IS_NOT_EMPTY) {
                    Statement temp = x;
                    x = y;
                    y = temp;
                    c = Condition.NEXT_IS_EMPTY;
                }
                if (c == Condition.NEXT_IS_NOT_ENEMY) {
                    Statement temp = x;
                    x = y;
                    y = temp;
                    c = Condition.NEXT_IS_ENEMY;
                }
                if (c == Condition.NEXT_IS_NOT_FRIEND) {
                    Statement temp = x;
                    x = y;
                    y = temp;
                    c = Condition.NEXT_IS_FRIEND;
                }
                if (c == Condition.NEXT_IS_NOT_WALL) {
                    Statement temp = x;
                    x = y;
                    y = temp;
                    c = Condition.NEXT_IS_WALL;
                }
                s.assembleIfElse(c, x, y);
                break;
            }
            case WHILE: {

                Statement a = s.newInstance();
                Condition c = s.disassembleWhile(a);
                simplifyIfElse(a);
                s.assembleWhile(c, a);

                break;
            }
            case CALL: {
                // nothing to do here...can you explain why?
                // no IF_ELSE statement exists under CALL statement
                break;
            }
            default: {
                // this will never happen...can you explain why?
                //all statements are included above
                break;
            }
        }
    }
}
