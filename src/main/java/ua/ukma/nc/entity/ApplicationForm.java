package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public class ApplicationForm implements Serializable{

    private static final long serialVersionUID = -5984140317464083037L;
    private User user;
    private String photoScope;

    public ApplicationForm() {
    }

    public ApplicationForm(User user, String photoScope) {
        this.user = user;
        this.photoScope = photoScope;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhotoScope() {
        return photoScope;
    }

    public void setPhotoScope(String photoScope) {
        this.photoScope = photoScope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationForm that = (ApplicationForm) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return photoScope != null ? photoScope.equals(that.photoScope) : that.photoScope == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (photoScope != null ? photoScope.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationForm{" +
                "user=" + user +
                ", photoScope='" + photoScope + '\'' +
                '}';
    }
}
