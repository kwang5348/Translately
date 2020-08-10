package com.kwang.dto;

import java.util.List;

public class ParseResultSet {
    private String parsedResult;
    private List<Transcript> tranlist;

    public ParseResultSet (String parsedResult, List<Transcript> tranlist){
        this.parsedResult = parsedResult;
        this.tranlist = tranlist;
    }
    public String getParsedResult() {
        return parsedResult;
    }

    public List<Transcript> getTranlist() {
        return tranlist;
    }

    public void setTranlist(List<Transcript> tranlist) {
        this.tranlist = tranlist;
    }

    public void setParsedResult(String parsedResult) {
        this.parsedResult = parsedResult;
    }

    
}