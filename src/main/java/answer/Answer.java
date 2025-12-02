package answer;

class Answer {
    private int level;
    private String answer;
    private Status status;

    static Answer forLevel(int level) {
        Answer answ = new Answer();
        answ.level = level;
        answ.status = Status.INCOMPLETE;
        return answ;
    }

    void setAnswer(String answer) {
        this.answer = answer;
    }

    void setCompleted() {
        this.status = Status.COMPLETE;
    }

    boolean isCompleted() {
        return this.status == Status.COMPLETE;
    }

    enum Status {
        COMPLETE,
        INCOMPLETE
    }

    @Override
    public String toString() {
        return String.format("{level: %d, answer: '%s', status: %s}", level, answer, status);
    }
}
