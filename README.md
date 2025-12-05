### Description
[Advent of Code](https://adventofcode.com) Java utility tool to conveniently fetch puzzle inputs, test and submit solutions.

### Usage
Start from updating [aoc.properties](src/main/resources/aoc.properties):

- `session.key` - extract your key from a cookie after you login on [adventofcode.com](https://adventofcode.com)
- `user.agent` - needs to be set to comply with [author's request](https://www.reddit.com/r/adventofcode/comments/z9dhtd/please_include_your_contact_info_in_the_useragent/); this property is already provided in this repository and doesn't need to be updated
- `year` and `day` for each new day

There are 3 execution modes:

- `init` - initializes new day by creating `Solution.java` file from template under path `solution/y${year}/d${day}/Solution.java`, it also fetches and caches the puzzle input under path `resources/inputs/${year}/day*.txt`
- `test` - executes the solution without submitting
- `submit` - executes and submits the solution, after each try the result is updated and cached under path `resources/results/${year}/day*.json`

For those that use IntelliJ - run configurations for all execution modes are prepared under `.idea/runConfigurations/*.xml`

To start working on a solution, go to `Solution.java` file that is created after `init` execution.
Implement your code for level 1 and 2 in `Solution.solveLevelOne(List<String>)` and `Solution.solveLevelTwo(List<String>)` methods accordingly.