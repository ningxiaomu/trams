package com.tester.domain;

public class Case {
    //caseId,caseName,requestAddress,method,contentType,parameter,exResult,status
    private String caseId;
    private String caseName;
    private String domain;



    private String requestAddress;
    private String method;
    private String contentType;
    private String parameter;
    private String exResult;
    private Integer status;
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getExResult() {
        return exResult;
    }

    public void setExResult(String exResult) {
        this.exResult = exResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Case{" +
                "caseId='" + caseId + '\'' +
                ", caseName='" + caseName + '\'' +
                ", domain='" + domain + '\'' +
                ", requestAddress='" + requestAddress + '\'' +
                ", method='" + method + '\'' +
                ", contentType='" + contentType + '\'' +
                ", parameter='" + parameter + '\'' +
                ", exResult='" + exResult + '\'' +
                ", status=" + status +
                '}';
    }
}
