package solution.y2021.d12;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class CaveTraversalRules {

    private final List<CaveTypeTraversalRule> rules;

    private CaveTraversalRules(List<CaveTypeTraversalRule> rules) {
        this.rules = rules;
    }

    boolean validate(List<Cave> cavePathSteps) {
        if (cavePathSteps.size() <= 1) {
            return true;
        }
        return rules.stream().allMatch(traversalRule -> traversalRule.isValidCavePath(cavePathSteps));
    }

    static class Builder {

        private final List<CaveTypeTraversalRule> rules = new ArrayList<>();

        CaveTypeTraversalRule.Builder addRuleForCaveType(CaveType caveType) {
            CaveTypeTraversalRule.Builder caveTypeRuleBuilder = new CaveTypeTraversalRule.Builder(this);
            caveTypeRuleBuilder.caveType = caveType;
            return caveTypeRuleBuilder;
        }

        CaveTraversalRules build() {
            return new CaveTraversalRules(rules);
        }
    }

    static class CaveTypeTraversalRule {
        private final CaveType caveType;
        private final CaveVisitLimit visitLimit;
        private final CavesPerPathLimit cavesPerPathLimit;

        private CaveTypeTraversalRule(CaveType caveType, CaveVisitLimit visitLimit, CavesPerPathLimit cavesPerPathLimit) {
            this.caveType = caveType;
            this.visitLimit = visitLimit;
            this.cavesPerPathLimit = cavesPerPathLimit;
        }

        boolean isValidCavePath(List<Cave> cavePathSteps) {
            Cave lastStep = cavePathSteps.getLast();
            if (lastStep.equals(cavePathSteps.getFirst())) {
                return false;
            }
            if (!caveType.matches(lastStep)) {
                return true;
            }
            Collection<Long> visitCounters = cavePathSteps.subList(1, cavePathSteps.size()).stream()
                    .filter(caveType::matches).toList().stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .values();
            return visitCounters.stream().allMatch(visitLimit::notExceeded)
                    && cavesPerPathLimit.notExceeded(visitCounters.stream().filter(visitLimit::isEqualOrWithinLimit).count());
        }

        static class Builder {
            private final CaveTraversalRules.Builder parentBuilder;
            private CaveType caveType;
            private CaveVisitLimit visitLimit;
            private CavesPerPathLimit cavesPerPathLimit;

            private Builder(CaveTraversalRules.Builder parentBuilder) {
                this.parentBuilder = parentBuilder;
            }

            Builder withCavesPerPathLimit(CavesPerPathLimit cavesPerPathLimit) {
                this.cavesPerPathLimit = cavesPerPathLimit;
                return this;
            }

            Builder withVisitLimit(CaveVisitLimit visitLimit) {
                this.visitLimit = visitLimit;
                return this;
            }

            CaveTraversalRules.Builder and() {
                parentBuilder.rules.add(new CaveTypeTraversalRule(caveType, visitLimit, cavesPerPathLimit));
                return parentBuilder;
            }

            CaveTraversalRules build() {
                parentBuilder.rules.add(new CaveTypeTraversalRule(caveType, visitLimit, cavesPerPathLimit));
                return parentBuilder.build();
            }
        }
    }

    enum CaveType {
        BIG_CAVE(cave -> cave.name().equals(cave.name().toUpperCase())),
        SMALL_CAVE(cave -> cave.name().equals(cave.name().toLowerCase()));

        private final Function<Cave, Boolean> caveVerifier;

        CaveType(Function<Cave, Boolean> caveVerifier) {
            this.caveVerifier = caveVerifier;
        }

        boolean matches(Cave cave) {
            return caveVerifier.apply(cave);
        }
    }

    enum CaveVisitLimit {
        UNLIMITED(Long.MAX_VALUE),
        AT_MOST_ONCE(1),
        AT_MOST_TWICE(2);

        private final long limit;

        CaveVisitLimit(long limit) {
            this.limit = limit;
        }

        boolean notExceeded(long visitCount) {
            return visitCount <= limit;
        }

        boolean isEqualOrWithinLimit(long visitCount) {
            if (this.equals(UNLIMITED)) {
                return visitCount > 1;
            }
            return visitCount == limit || (limit > 1 && visitCount > 1);
        }
    }

    enum CavesPerPathLimit {
        ALL_CAVES_PER_PATH(_ -> true),
        ONE_CAVE_PER_PATH(caveCount -> caveCount <= 1);

        private final Function<Long, Boolean> countVerifier;

        CavesPerPathLimit(Function<Long, Boolean> countVerifier) {
            this.countVerifier = countVerifier;
        }

        boolean notExceeded(long caveCount) {
            return countVerifier.apply(caveCount);
        }
    }
}
