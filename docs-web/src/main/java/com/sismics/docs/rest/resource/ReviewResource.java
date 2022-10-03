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
     * @apiParam {String} academic Academic Score
     * @apiParam {String} extracurricular Extracurricular Score
     * @apiParam {String} athletics Athletics Score
     * @apiParam {String} personal Personal Fit Score
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
     * @param content Review content
     * @return Response
     */
    @PUT
    public Response add(@FormParam("id") String documentId,
            @FormParam("content") String content) {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        // Validate input data
        ValidationUtil.validateRequired(documentId, "id");
        content = ValidationUtil.validateLength(content, "content", 1, 4000, false);
        
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
        ReviewDao.create(review, principal.getId());
        
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
}
