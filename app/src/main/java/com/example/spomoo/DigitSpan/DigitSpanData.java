package com.example.spomoo.DigitSpan;

import com.google.gson.annotations.SerializedName;

public class DigitSpanData {

    //attributes
    @SerializedName("date")
    private String date;
    @SerializedName("digitSpanid")
    private int digitSpanId;
    @SerializedName("userid")
    private int userId;
    @SerializedName("beensent")
    private int beenSent;
    @SerializedName("max_sequence_length")
    private int max_sequence_length;
    @SerializedName("total_tasks")
    private int total_tasks;
    @SerializedName("correct_tasks")
    private int correct_tasks;

    public DigitSpanData() {}

    public DigitSpanData(int max_sequence_length, int total_tasks, int correct_tasks ,String date, int digitSpanId, int userId, int beenSent){
        setMax_sequence_length(max_sequence_length);
        setTotal_tasks(total_tasks);
        setCorrect_tasks(correct_tasks);
        setDate(date);
        setDigitSpanId(digitSpanId);
        setUserId(userId);
        setBeenSent(beenSent);
    }

    public int getDigitSpanId() {
        return digitSpanId;
    }

    public void setDigitSpanId(int digitSpanId) {
        this.digitSpanId = digitSpanId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBeenSent() {
        return beenSent;
    }

    public void setBeenSent(int beenSent) {
        this.beenSent = beenSent;
    }

    public int getMax_sequence_length() {
        return max_sequence_length;
    }

    public void setMax_sequence_length(int max_sequence_length) {
        this.max_sequence_length = max_sequence_length;
    }

    public int getTotal_tasks() {
        return total_tasks;
    }

    public void setTotal_tasks(int total_tasks) {
        this.total_tasks = total_tasks;
    }

    public int getCorrect_tasks() {
        return correct_tasks;
    }

    public void setCorrect_tasks(int correct_tasks) {
        this.correct_tasks = correct_tasks;
    }
}
