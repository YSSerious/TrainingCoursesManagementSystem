package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.ukma.nc.entity.Status;
import ua.ukma.nc.entity.impl.real.StatusImpl;
import ua.ukma.nc.service.StatusService;

/**
 * Created by Алексей on 30.10.2016.
 */
@Component
@Scope("prototype")
public class StatusProxy implements Status {

    private Long id;

    private StatusImpl status;

    @Autowired
    private StatusService statusService;

    public StatusProxy() {
    }

    public StatusProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        downloadStatus();
        return status.getTitle();
    }

    @Override
    public void setTitle(String title) {
        downloadStatus();
        status.setTitle(title);
    }

    @Override
    public String getDescription() {
        downloadStatus();
        return status.getDescription();
    }

    @Override
    public void setDescription(String description) {
        downloadStatus();
        status.setDescription(description);
    }

    private void downloadStatus() {
        if (status == null) {
            status = (StatusImpl) statusService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy " + id;
    }
}
