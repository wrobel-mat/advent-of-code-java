package solution.y2021.d02;

import java.util.Map;

class SimpleHandlerFactory implements INavigationHandlerFactory {

    private final Map<Command, INavigationCommandHandler> handlers =
            Map.of(
                    Command.FORWARD, new ForwardHandler(),
                    Command.DOWN, new DownHandler(),
                    Command.UP, new UpHandler()
            );

    @Override
    public INavigationCommandHandler getNavigationCommandHandler(Instruction instruction) {
        return handlers.getOrDefault(instruction.command(), new DoNothingHandler());
    }

    private record DoNothingHandler() implements INavigationCommandHandler {
        @Override
        public void handle(Submarine submarine, Instruction instruction) {
            // do nothing
        }
    }

    private record ForwardHandler() implements INavigationCommandHandler {
        @Override
        public void handle(Submarine submarine, Instruction instruction) {
            submarine.horizontalPosition += instruction.units();
        }
    }

    private record DownHandler() implements INavigationCommandHandler {
        @Override
        public void handle(Submarine submarine, Instruction instruction) {
            submarine.depth += instruction.units();
        }
    }

    private record UpHandler() implements INavigationCommandHandler {
        @Override
        public void handle(Submarine submarine, Instruction instruction) {
            submarine.depth -= instruction.units();
        }
    }
}
