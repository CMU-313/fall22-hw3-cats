package com.sismics.docs.core.dao;

//import com.sismics.docs.core.constant.AuditLogType;
import com.sismics.docs.core.dao.dto.ReviewDto;
import com.sismics.docs.core.model.jpa.Review;
//import com.sismics.docs.core.util.AuditLogUtil;
import com.sismics.util.context.ThreadLocalContext;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Review DAO.
 * 
 */
public class ReviewDao {
    /**
     * Creates a new review.
     * 
     * @param review Review
     * @param userId User ID
     * @return New ID
     */
    public String create(Review review) {
        // Create the review
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        review.setCreateDate(new Date());
        System.out.println("dao:");
        System.out.println(review);
        em.persist(review);
        
        // Create audit log
        // AuditLogUtil.create(review, AuditLogType.CREATE, userId);
        
        return review.getId();
    }
    
    /**
     * Deletes a review.
     * 
     * @param id Review ID
     * @param userId User ID
     */
    public void delete(String id, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Get the review
        Query q = em.createQuery("select c from Review c where c.id = :id and c.deleteDate is null");
        q.setParameter("id", id);
        //Review reviewDb = (Review) q.getSingleResult();
        
        // Delete the review
        //Date dateNow = new Date();
        //reviewDb.setDeleteDate(dateNow);

        // Create audit log
        // AuditLogUtil.create(reviewDb, AuditLogType.DELETE, userId);
    }
    
    /**
     * Gets an active review by its ID.
     * 
     * @param id Review ID
     * @return Review
     */
    public Review getActiveById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            Query q = em.createQuery("select c from Review c where c.id = :id and c.deleteDate is null");
            q.setParameter("id", id);
            return (Review) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Get all reviews on a document.
     * 
     * @param documentId Document ID
     * @return List of reviews
     */
    public List<ReviewDto> getByDocumentId(String documentId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        StringBuilder sb = new StringBuilder("select c.REVIEWER_NAME, c.REVIEWED_DOCUMENT_ID, c.ACADEMIC_SCORE, c.EXTRACURRICULAR_SCORE, c.ATHLETICS_SCORE, c.PERSONAL_FIT_SCORE, c.DATE_CREATED from DOCUMENT_REVIEWS c");
        sb.append(" order by c.DATE_CREATED asc ");
        Query q = em.createNativeQuery(sb.toString());
        @SuppressWarnings("unchecked")
        List<Object[]> l = q.getResultList();
        
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Object[] o : l) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId((String) o[0]);
            reviewDto.setDocumentId((String) o[1]);
            reviewDto.setAcademic((int) o[2]);
            reviewDto.setExtracurricular((int) o[3]);
            reviewDto.setAthletic((int) o[4]);
            reviewDto.setPersonal((int) o[5]);
            reviewDto.setCreateDate(((Date) o[6]));
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }
}

// // create cached table DOCUMENT_REVIEWS ( 
// REVIEWER_NAME varchar(40) not null, 
// REVIEWED_DOCUMENT_ID int not null, 
// ACADEMIC_SCORE int not null constraint ONE_THROUGH_TEN check (ACADEMIC_SCORE between 0 and 11), 
// EXTRACURRICULR_SCORE int not null constraint ONE_THROUGH_TEN check (EXTRACURRICULR_SCORE between 0 and 11),
// ATHLETICS_SCORE int not null constraint ONE_THROUGH_TEN check (ATHLETICS_SCORE between 0 and 11), 
// PERSONAL_FIT_SCORE int not null constraint ONE_THROUGH_TEN check (PERSONAL_FIT_SCORE between 0 and 11), 
// DATE_CREATED datetime, primary key (REVIEWER_NAME) );
// // alter table DOCUMENT_REVIEWS add constraint FK_REVIEWED_DOCUMENT_ID foreign key (REVIEWED_DOCUMENT_ID) references T_DOCUMENT (DOC_ID_C) on delete restrict on update restrict;
// // update T_CONFIG set CFG_VALUE_C = '28' where CFG_ID_C = 'DB_VERSION';
