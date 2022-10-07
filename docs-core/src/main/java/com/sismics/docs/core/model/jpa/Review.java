package com.sismics.docs.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

/**
 * Review entity.
 * 
 */
@Entity
@Table(name = "DOCUMENT_REVIEWS")
public class Review {
    /**
     * Review ID.
     */
    @Id
    @Column(name = "REVIEWER_NAME", length = 36)
    private String id;
    
    /**
     * Document ID.
     */
    @Column(name = "REVIEWED_DOCUMENT_ID", length = 36, nullable = false)
    private String documentId;

    /**
     * Academic Score.
     */
    @Column(name = "ACADEMIC_SCORE", nullable = false)
    private int academic;

    /**
     * Extracurricular Score.
     */
    @Column(name = "EXTRACURRICULAR_SCORE", nullable = false)
    private int extracurricular;

    /**
     * Academic Score.
     */
    @Column(name = "ATHLETICS_SCORE", nullable = false)
    private int athletic;

    /**
     * Academic Score.
     */
    @Column(name = "PERSONAL_FIT_SCORE", nullable = false)
    private int personal;
    
    /**
     * Creation date.
     */
    @Column(name = "DATE_CREATED", nullable = false)
    private Date createDate;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public int getAcademic() {
        return academic;
    }

    public void setAcademic(int academic) {
        this.academic = academic;
    }

    public int getExtracurricular() {
        return extracurricular;
    }

    public void setExtracurricular(int extracurricular) {
        this.extracurricular = extracurricular;
    }

    public int getAthletic() {
        return athletic;
    }

    public void setAthletic(int athletic) {
        this.athletic = athletic;
    }

    public int getPersonal() {
        return personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("documentId", documentId)
                .add("academic", academic)
                .add("extracurricular", extracurricular)
                .add("athletic", athletic)
                .add("personal", personal)
                .add("date", createDate)
                .toString();
    }
}
