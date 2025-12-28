package solution.y2021.d02;

interface INavigationHandlerFactory {
    INavigationCommandHandler getNavigationCommandHandler(Instruction instruction);
}
