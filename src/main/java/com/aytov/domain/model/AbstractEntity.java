package com.aytov.domain.model;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.Date;

public abstract class AbstractEntity<ID> {
    @Version
    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationTime;

    public abstract ID getId();

    public abstract void setId(ID id);

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime){
        this.creationTime =  creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        this.creationTime = now;
        this.modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationTime = new Date();
    }
}
