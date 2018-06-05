//package edu.dartmouth.cs.login;
//
//public class Entry {
//    private String entryTitle, entryVal;
//
//    public Entry(String entryTitle, String entryVal){
//        this.entryTitle = entryTitle;
//        this.entryVal = entryVal;
//    }
//
//    public String getEntryTitle() {
//        return entryTitle;
//    }
//
//    public void setEntryTitle(String entryTitle) {
//        this.entryTitle = entryTitle;
//    }
//
//    public String getEntryVal() {
//        return entryVal;
//    }
//
//    public void setEntryVal(String entryVal) {
//        this.entryVal = entryVal;
//    }
//}

package edu.dartmouth.cs.login.ManualEntry;

public class Entry {
    private String title, value;

    public Entry(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String val) {
        this.value = val;
    }

}