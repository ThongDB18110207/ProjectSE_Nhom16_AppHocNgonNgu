package com.example.languages_learning_app.DTO;

import android.text.format.DateFormat;

public class Practice {
    private String id;
    private String lessonId;
    private String sentence;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String correctAnswer;
    private Boolean status;
    private String createDate;

    public Practice(){
        // For firebase
    }

    public Practice(String lessonId, String sentence, String answer_1,
                    String answer_2,String answer_3, String correctAnswer) {
        this.lessonId = lessonId;
        this.sentence = sentence;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.correctAnswer = correctAnswer;
        status = false;
        createDate = DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
