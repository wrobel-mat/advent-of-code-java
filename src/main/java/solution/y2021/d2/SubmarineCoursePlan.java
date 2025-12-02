package solution.y2021.d2;

import java.util.ArrayList;
import java.util.List;

class SubmarineCoursePlan {

    private final List<Instruction> instructions = new ArrayList<>();

    SubmarineCoursePlan(List<String> input) {
        for (String command : input) {
            String[] s = command.split(" ");
            instructions.add(new Instruction(Command.valueOf(s[0].toUpperCase()), Integer.parseInt(s[1])));
        }
    }

    List<Instruction> getInstructions() {
        return instructions;
    }
}
