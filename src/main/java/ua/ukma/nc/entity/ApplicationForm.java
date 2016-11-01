package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public class ApplicationForm implements Serializable{

    private static final long serialVersionUID = -5984140317464083037L;
    private Long id_user;
    private String photoScope;


    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
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

        if (id_user != null ? !id_user.equals(that.id_user) : that.id_user != null) return false;
        return photoScope != null ? photoScope.equals(that.photoScope) : that.photoScope == null;

    }

    @Override
    public int hashCode() {
        int result = id_user != null ? id_user.hashCode() : 0;
        result = 31 * result + (photoScope != null ? photoScope.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationForm{" +
                "id_user=" + id_user +
                ", photoScope='" + photoScope + '\'' +
                '}';
    }
}
