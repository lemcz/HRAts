package HRAts.vo;

import HRAts.model.Candidate;

import java.util.List;

public class CandidateListVO {
    private int pagesCount;
    private long totalCandidates;

    private String actionMessage;
    private String searchMessage;

    private List<Candidate> candidates;

    public CandidateListVO() {
    }

    public CandidateListVO(int pages, long totalCandidates, List<Candidate> candidates) {
        this.pagesCount = pages;
        this.candidates = candidates;
        this.totalCandidates = totalCandidates;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }
}