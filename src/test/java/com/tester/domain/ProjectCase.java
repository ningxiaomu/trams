package com.tester.domain;

public class ProjectCase {
    private String ProjectId;
    private String CaseId;

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    @Override
    public String toString() {
        return "ProjectCase{" +
                "ProjectId='" + ProjectId + '\'' +
                ", CaseId='" + CaseId + '\'' +
                '}';
    }
}
