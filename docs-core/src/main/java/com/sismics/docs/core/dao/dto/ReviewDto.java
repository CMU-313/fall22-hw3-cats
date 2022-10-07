package com.sismics.docs.core.dao.dto;

import java.util.Date;

/**
 * Review DTO.
 *
 */
public class ReviewDto {
    /**
     * Review ID.
     */
    private String id;

    /**
     * Document ID.
     */
    private String documentId;

    /**
     * Creation date.
     */
    private Date createDate;
    
    /**
     * Academic score.
     */
    private int academic;

    /**
     * Extracurricular score.
     */
    private int extracurricular;

    /**
     * Athletic score.
     */
    private int athletic;

    /**
     * Personal score.
     */
    private int personal;
    

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

}
