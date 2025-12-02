package solution.y2021.d2;

interface INavigationHandlerFactory {
    INavigationCommandHandler getNavigationCommandHandler(Instruction instruction);
}
