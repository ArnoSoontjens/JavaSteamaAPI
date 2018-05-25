package be.w3technics.javasteamaapi.mappings;

public class PageRoot {
    private int count;
    private String next;
    private String previous;
    private String resultsString;

    public PageRoot() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getResultsString() {
        return resultsString;
    }

    public void setResults(String results) {
        this.resultsString = results;
    }

    @Override
    public String toString() {
        return "BitHarvesters{" + "count=" + count + ", next=" + next + ", previous=" + previous + ", results=" + resultsString + '}';
    }
    
    
}
