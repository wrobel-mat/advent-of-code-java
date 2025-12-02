package solution.y2021.d2;

class Submarine {

    private final INavigationHandlerFactory navigationHandlerFactory;
    int horizontalPosition = 0;
    int depth = 0;
    int aim = 0;

    Submarine(INavigationHandlerFactory navigationHandlerFactory) {
        this.navigationHandlerFactory = navigationHandlerFactory;
    }

    void navigate(SubmarineCoursePlan coursePlan) {
        coursePlan.getInstructions().forEach(instruction -> {
            INavigationCommandHandler handler = navigationHandlerFactory.getNavigationCommandHandler(instruction);
            handler.handle(this, instruction);
        });
    }
}
