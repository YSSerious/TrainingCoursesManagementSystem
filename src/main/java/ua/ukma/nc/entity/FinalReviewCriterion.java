package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public class FinalReviewCriterion implements Serializable{

    private static final long serialVersionUID = 2994392671709727059L;
    private Long id;
    private FinalReview finalReview;
    private Criterion criterion;
    private Mark mark;
    private String commentary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinalReview getFinalReview() {
        return finalReview;
    }

    public void setFinalReview(FinalReview finalReview) {
        this.finalReview = finalReview;
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalReviewCriterion that = (FinalReviewCriterion) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (finalReview != null ? !finalReview.equals(that.finalReview) : that.finalReview != null) return false;
        if (criterion != null ? !criterion.equals(that.criterion) : that.criterion != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        return commentary != null ? commentary.equals(that.commentary) : that.commentary == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (finalReview != null ? finalReview.hashCode() : 0);
        result = 31 * result + (criterion != null ? criterion.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FinalReviewCriterion{" +
                "id=" + id +
                ", finalReview=" + finalReview +
                ", criterion=" + criterion +
                ", mark=" + mark +
                ", commentary='" + commentary + '\'' +
                '}';
    }
}
