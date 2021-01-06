package POM.Sidebar;

public enum ApplicationPages {

    MY_DATA("myData"),
    NEW_INVOICE("newInvoices");

    private final String page;

    ApplicationPages(String page){
        this.page=page;
    }

    public String getPage(){
        return page;
    }

}
