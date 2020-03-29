package com.tester.model;

public class GetCaseListCase {
//    CaseId,CaseName,Domain,CaseAddress,Method,Paramter
    private String CaseId;
    private String CaseName;
    private String Domain;
    private String CaseAddress;
    private String Method;
    private String Paramter;
    private String Result;
    private Integer Status;

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public String getCaseName() {
        return CaseName;
    }

    public void setCaseName(String caseName) {
        CaseName = caseName;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public String getCaseAddress() {
        return CaseAddress;
    }

    public void setCaseAddress(String caseAddress) {
        CaseAddress = caseAddress;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getParamter() {
        return Paramter;
    }

    public void setParamter(String paramter) {
        Paramter = paramter;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "GetCaseListCase{" +
                "CaseId='" + CaseId + '\'' +
                ", CaseName='" + CaseName + '\'' +
                ", Domain='" + Domain + '\'' +
                ", CaseAddress='" + CaseAddress + '\'' +
                ", Method='" + Method + '\'' +
                ", Paramter='" + Paramter + '\'' +
                ", Result='" + Result + '\'' +
                ", Status=" + Status +
                '}';
    }
}
