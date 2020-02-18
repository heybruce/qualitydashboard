package com.solera.apac1.qualitydashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "screenshot")
public class Screenshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 16777215)
    private byte[] image;

    @OneToOne(mappedBy = "screenshot")
    @JsonBackReference
    private TestLog testLog;

    public Screenshot() {
    }

    public Long getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public TestLog getTestLog() {
        return testLog;
    }

    public void setTestLog(TestLog testLog) {
        this.testLog = testLog;
    }
}
