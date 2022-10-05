package com.sismics.docs.rest.resource;

import com.sismics.docs.core.constant.PermType;
import com.sismics.docs.core.dao.AclDao;
import com.sismics.docs.core.dao.ReviewDao; 
import com.sismics.docs.core.dao.dto.ReviewDto; 
import com.sismics.docs.core.model.jpa.Review; 
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;
import com.sismics.util.ImageUtil;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Reviews REST resource.
 * 
 */
@Path("/review")
public class ReviewResource extends BaseResource {
    /**
     * Add a review.
     *
     * @api {put} /review Add a review
     * @apiName PutReview
     * @apiGroup Review
     * @apiParam {String} id Document ID
     * @apiSuccess {String} academic Academic
     * @apiSuccess {String} extracurricular Extracurricular
     * @apiSuccess {String} athletics Athletics
     * @apiSuccess {String} personal Personal
     * @apiSuccess {String} creator Username
     * @apiSuccess {String} creator_gravatar Creator Gravatar hash
     * @apiSuccess {Number} create_date Create date (timestamp)
     * @apiError (client) ForbiddenError Access denied
     * @apiError (client) ValidationError Validation error
     * @apiError (client) NotFound Document not found
     * @apiPermission user
     * @apiVersion 1.5.0
     * 
     * @param documentId Document ID
     * @param academic Review academic score
     * @param extracurricular Review extracurricular score
     * @param athletic Review athletic score
     * @param personal Review personal score
     * @return Response
     */
    @PUT
    public Response add(@FormParam("id") String documentId,
            @FormParam("academic") String academic, @FormParam("extracurricular") String extracurricular,
            @FormParam("athletic") String athletic, @FormParam("personal") String personal) {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Validate input data
        ValidationUtil.validateRequired(documentId, "id");
        academic = ValidationUtil.validateLength(academic, "academic", 1, 3, false);
        academic = ValidationUtil.validateLength(extracurricular, "extracurricular", 1, 3, false);
        academic = ValidationUtil.validateLength(athletic, "athletic", 1, 3, false);
        academic = ValidationUtil.validateLength(personal, "personal", 1, 3, false);
        
        // Read access on doc gives access to write a review 
        AclDao aclDao = new AclDao();
        if (!aclDao.checkPermission(documentId, PermType.READ, getTargetIdList(null))) {
            throw new NotFoundException();
        }
        
        // Create the review
        Review review = new Review();
        review.setDocumentId(documentId);
        review.setAcademic(academic);
        review.setExtracurricular(extracurricular);
        review.setAthletic(athletic);
        review.setPersonal(personal);
        review.setUserId(principal.getId());
        ReviewDao reviewDao = new ReviewDao();
        reviewDao.create(review, principal.getId());
        
        // Returns the review
        JsonObjectBuilder response = Json.createObjectBuilder()
                .add("id", review.getId())
                .add("academic_score", review.getAcademic())
                .add("extracurricular_score", review.getExtracurricular())
                .add("athletic_score", review.getAthletic())
                .add("personal_score", review.getPersonal())
                .add("creator", principal.getName())
                .add("creator_gravatar", ImageUtil.computeGravatar(principal.getEmail()))
                .add("create_date", review.getCreateDate().getTime());
        return Response.ok().entity(response.build()).build();
    }

    /**
     * Delete a review.
     *
     * @api {delete} /review/:id Delete a review
     * @apiName DeleteReview
     * @apiGroup Review
     * @apiParam {String} id Review ID
     * @apiSuccess {String} status Status OK
     * @apiError (client) ForbiddenError Access denied
     * @apiError (client) NotFound Review or document not found
     * @apiPermission user
     * @apiVersion 1.5.0
     *
     * @param id review ID
     * @return Response
     */
    @DELETE
    @Path("{id: [a-z0-9\\-]+}")
    public Response delete(@PathParam("id") String id) {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Get the review
        ReviewDao reviewDao = new ReviewDao();
        Review review = reviewDao.getActiveById(id);
        if (review == null) {
            throw new NotFoundException();
        }
        
        // If the current user owns the review, skip ACL check
        if (!review.getUserId().equals(principal.getId())) {
            // Get the associated document
            AclDao aclDao = new AclDao();
            if (!aclDao.checkPermission(review.getDocumentId(), PermType.WRITE, getTargetIdList(null))) {
                throw new NotFoundException();
            }
        }
        
        // Delete the review
        reviewDao.delete(id, principal.getId());
        
        // Always return OK
        JsonObjectBuilder response = Json.createObjectBuilder()
                .add("status", "ok");
        return Response.ok().entity(response.build()).build();
    }
    
    /**
     * Get all reviews on a document.
     *
     * @api {get} /review/:id Get reviews
     * @apiName GetReview
     * @apiGroup Review
     * @apiParam {String} id Document ID
     * @apiParam {String} share Share ID
     * @apiSuccess {Object[]} reviews List of reviews
     * @apiSuccess {String} reviews.id Review ID
     * @apiSuccess {String} academic Academic
     * @apiSuccess {String} extracurricular Extracurricular
     * @apiSuccess {String} athletics Athletics
     * @apiSuccess {String} personal Personal
     * @apiSuccess {String} reviews.creator Username
     * @apiSuccess {String} reviews.creator_gravatar Creator Gravatar hash
     * @apiSuccess {Number} reviews.create_date Create date (timestamp)
     * @apiError (client) NotFound Document not found
     * @apiPermission none
     * @apiVersion 1.5.0
     *
     * @param documentId DocumentID
     * @return Response
     */
    @GET
    @Path("{documentId: [a-z0-9\\-]+}")
    public Response get(@PathParam("documentId") String documentId,
            @QueryParam("share") String shareId) {
        authenticate();
        
        // Read access on doc gives access to read reviews 
        AclDao aclDao = new AclDao();
        if (!aclDao.checkPermission(documentId, PermType.READ, getTargetIdList(shareId))) {
            throw new NotFoundException();
        }
        
        // Assemble results
        ReviewDao reviewDao = new ReviewDao();
        List<ReviewDto> reviewDtoList = reviewDao.getByDocumentId(documentId);
        JsonArrayBuilder reviews = Json.createArrayBuilder();
        for (ReviewDto reviewDto : reviewDtoList) {
            reviews.add(Json.createObjectBuilder()
                    .add("id", reviewDto.getId())
                    .add("academic_score", reviewDto.getAcademic())
                    .add("extracurricular_score", reviewDto.getExtracurricular())
                    .add("athletic_score", reviewDto.getAthletic())
                    .add("personal_score", reviewDto.getPersonal())
                    .add("creator", reviewDto.getCreatorName())
                    .add("creator_gravatar", ImageUtil.computeGravatar(reviewDto.getCreatorEmail()))
                    .add("create_date", reviewDto.getCreateTimestamp()));
        }
        
        // Always return OK
        JsonObjectBuilder response = Json.createObjectBuilder()
                .add("reviews", reviews);
        return Response.ok().entity(response.build()).build();
    }
}
