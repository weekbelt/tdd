package me.weekbelt.domain;

class BooleanAnswer {

    private final int questionId;

    private final boolean value;

    public BooleanAnswer(int questionId, boolean value) {
        this.questionId = questionId;
        this.value = value;
    }

    public int getQuestionId() {
        return questionId;
    }

    public boolean getValue() {
        return value;
    }
}
