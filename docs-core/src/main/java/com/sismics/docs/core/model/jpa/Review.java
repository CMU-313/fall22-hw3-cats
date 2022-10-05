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
@Table(name = "T_REVIEW")
public class Review {
    /**
     * Review ID.
     */
    @Id
    @Column(name = "COM_ID_C", length = 36)
    private String id;
    
    /**
     * Document ID.
     */
    @Column(name = "COM_IDDOC_C", length = 36, nullable = false)
    private String documentId;
    
    /**
     * User ID.
     */
    @Column(name = "COM_IDUSER_C", length = 36, nullable = false)
    private String userId;
    
    /**
     * Academic Score.
     */
    @Column(name = "COM_ACADEMIC_C", nullable = false)
    private String academic;

    /**
     * Extracurricular Score.
     */
    @Column(name = "COM_EXTRACURRICULAR_C", nullable = false)
    private String extracurricular;

    /**
     * Academic Score.
     */
    @Column(name = "COM_ATHLETIC_C", nullable = false)
    private String athletic;

    /**
     * Academic Score.
     */
    @Column(name = "COM_PERSONAL_C", nullable = false)
    private String personal;
    
    /**
     * Creation date.
     */
    @Column(name = "COM_CREATEDATE_D", nullable = false)
    private Date createDate;

    /**
     * Deletion date.
     */
    @Column(name = "COM_DELETEDATE_D")
    private Date deleteDate;
    
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
    
    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getExtracurricular() {
        return extracurricular;
    }

    public void setExtracurricular(String extracurricular) {
        this.extracurricular = extracurricular;
    }

    public String getAthletic() {
        return athletic;
    }

    public void setAthletic(String athletic) {
        this.academic = athletic;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("documentId", documentId)
                .add("userId", userId)
                .toString();
    }
}
