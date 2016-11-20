package ua.ukma.nc.entity.impl.proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.ukma.nc.entity.Mark;
import ua.ukma.nc.entity.impl.real.MarkImpl;
import ua.ukma.nc.service.MarkService;

/**
 * Created by Алексей on 30.10.2016.
 */
@Component
@Scope("prototype")
@JsonIgnoreProperties("description")
public class MarkProxy implements Mark {

    private static final long serialVersionUID = 8917289155140308183L;
    private int value;
    private MarkImpl mark;
    
    @Autowired
    private MarkService markService;

    public MarkProxy() {
    }

    public MarkProxy(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value=value;
    }

    @Override
    public String getDescription() {
        downloadMark();
        return mark.getDescription();
    }

    @Override
    public void setDescription(String description) {
        downloadMark();
        mark.setDescription(description);
    }
    private void downloadMark() {
        if (mark == null) {
            mark = (MarkImpl) markService.getByValue(value);
        }
    }

    @Override
    public String toString() {
        return "Proxy " + value;
    }
}
