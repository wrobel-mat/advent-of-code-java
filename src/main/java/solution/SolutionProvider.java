package solution;

import java.lang.reflect.InvocationTargetException;

public class SolutionProvider {

    private SolutionProvider() {
    }

    public static ISolution getSolution(int year, int day) {
        try {
            return (ISolution) Class.forName(String.format("solution.y%d.d%d.Solution", year, day)).getConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Day %d of year %d has not been initialized - rerun the application with `init` argument%n%s", day, year, e));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
