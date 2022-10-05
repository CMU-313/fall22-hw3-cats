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
    public String create(Review review, String userId) {
        // Create the UUID
        review.setId(UUID.randomUUID().toString());
        
        // Create the review
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        review.setCreateDate(new Date());
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
        StringBuilder sb = new StringBuilder("select c.COM_ID_C, c.COM_CONTENT_C, c.COM_CREATEDATE_D, u.USE_USERNAME_C, u.USE_EMAIL_C from T_REVIEW c, T_USER u");
        sb.append(" where c.COM_IDDOC_C = :documentId and c.COM_IDUSER_C = u.USE_ID_C and c.COM_DELETEDATE_D is null ");
        sb.append(" order by c.COM_CREATEDATE_D asc ");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("documentId", documentId);
        @SuppressWarnings("unchecked")
        List<Object[]> l = q.getResultList();
        
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Object[] o : l) {
            int i = 0;
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId((String) o[i++]);
            reviewDto.setAcademic((String) o[i++]);
            reviewDto.setExtracurricular((String) o[i++]);
            reviewDto.setAthletic((String) o[i++]);
            reviewDto.setPersonal((String) o[i++]);
            reviewDto.setCreateTimestamp(((Timestamp) o[i++]).getTime());
            reviewDto.setCreatorName((String) o[i++]);
            reviewDto.setCreatorEmail((String) o[i]);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }
}

