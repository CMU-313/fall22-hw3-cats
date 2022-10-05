package com.sismics.docs.core.dao.dto;

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
     * Creator name.
     */
    private String creatorName;
    
    /**
     * Creator email.
     */
    private String creatorEmail;
    
    /**
     * Academic score.
     */
    private String academic;

    /**
     * Extracurricular score.
     */
    private String extracurricular;

    /**
     * Athletic score.
     */
    private String athletic;

    /**
     * Personal score.
     */
    private String personal;
    
    /**
     * Creation date of this comment.
     */
    private Long createTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
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
        this.athletic = athletic;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
}
