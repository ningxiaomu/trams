package com.tester.domain;

public class ResultSize {
    String Passed;
    String Failed;
    String Skipped;

    public String getPassed() {
        return Passed;
    }

    public void setPassed(String passed) {
        Passed = passed;
    }

    public String getFailed() {
        return Failed;
    }

    public void setFailed(String failed) {
        Failed = failed;
    }

    public String getSkipped() {
        return Skipped;
    }

    public void setSkipped(String skipped) {
        Skipped = skipped;
    }

    @Override
    public String toString() {
        return "ResultSize{" +
                "Passed='" + Passed + '\'' +
                ", Failed='" + Failed + '\'' +
                ", Skipped='" + Skipped + '\'' +
                '}';
    }
}
