package model;

/**
 * Created by mertsimsek on 11/01/15.
 */
public class DrawerItem {
    String header;
    int icon_resource;

    public DrawerItem(String header, int icon_resource) {
        this.header = header;
        this.icon_resource = icon_resource;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getIcon_resource() {
        return icon_resource;
    }

    public void setIcon_resource(int icon_resource) {
        this.icon_resource = icon_resource;
    }
}
