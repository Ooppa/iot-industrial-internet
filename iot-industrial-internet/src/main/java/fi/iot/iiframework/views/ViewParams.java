/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.views;

/**
 * Defines the the parameters to be added to the view.
 */
public class ViewParams {

    private String pagetitle;
    private String navtype;
    private String footertype;
    private String contenttype;
    private String content;

    /**
     * Creates a new ViewParams object with no parameters.
     */
    public ViewParams() {
    }

    /**
     * Creates a new ViewParams object with default header and footer.
     *
     * @param pagetitle Title for the page
     * @param content Content for the page
     */
    public ViewParams(String pagetitle, String content) {
        this.pagetitle = pagetitle;
        this.navtype = "default";
        this.footertype = "default";
        this.contenttype = "default";
        this.content = content;
    }

    /**
     * Set all types to the given String.
     *
     * @param type Type as a String
     */
    public void setAllTypes(String type) {
        setNavtype(type);
        setFootertype(type);
        setContenttype(type);
    }

    public String getPagetitle() {
        return pagetitle;
    }

    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }

    public String getNavtype() {
        return navtype;
    }

    public void setNavtype(String headertype) {
        this.navtype = headertype;
    }

    public String getFootertype() {
        return footertype;
    }

    public void setFootertype(String footertype) {
        this.footertype = footertype;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
