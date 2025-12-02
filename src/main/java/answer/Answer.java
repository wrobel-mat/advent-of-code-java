package answer;

class Answer {
    private int part;
    private String answer;
    private Status status;

    static Answer forPart(int part) {
        Answer answ = new Answer();
        answ.part = part;
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
        return String.format("{part: %d, answer: '%s', status: %s}", part, answer, status);
    }
}
