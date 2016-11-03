package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Mark;

/**
 * Created by Алексей on 30.10.2016.
 */
public class MarkImpl implements Mark {

    private static final long serialVersionUID = 8523579258175747059L;
    private int value;
    private String description;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkImpl mark = (MarkImpl) o;

        if (value != mark.value) return false;
        return description != null ? description.equals(mark.description) : mark.description == null;

    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MarkImpl{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
