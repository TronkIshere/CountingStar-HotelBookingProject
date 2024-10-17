import React, { useContext, useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane, faStar } from "@fortawesome/free-solid-svg-icons";
import { AuthContext } from "../utils/AuthProvider";
import {
  addNewRating,
  checkIfUserCanComment,
  getAllRatingByHotelId,
} from "../utils/ApiFunction";
import "bootstrap/dist/css/bootstrap.min.css";

const Rating = ({ hotelId, onClose }) => {
  const userId = localStorage.getItem("userId");
  const [newComment, setNewComment] = useState("");
  const [newStars, setNewStars] = useState(0);
  const [canComment, setCanComment] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [ratings, setRatings] = useState([]);

  useEffect(() => {
    if (userId) {
      checkIfUserCanComment(userId, hotelId)
        .then((response) => {
          setCanComment(response);
        })
        .catch((error) => {
          console.error(error);
        });
    }
    getAllRatings(hotelId);
  }, [userId, hotelId]);

  const getAllRatings = async (hotelId) => {
    try {
      const ratings = await getAllRatingByHotelId(hotelId);
      setRatings(ratings);
    } catch (error) {
      console.error("Error fetching ratings:", error.message);
    }
  };

  const handleStarClick = (stars) => {
    setNewStars(stars);
  };

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);

    try {
      const rateDay = new Date().toISOString();
      const success = await addNewRating(
        userId,
        hotelId,
        newStars,
        newComment,
        rateDay
      );

      if (success) {
        console.log("Rating submitted successfully!");
        getAllRatings(hotelId); 
      } else {
        console.error("Failed to submit rating");
      }
    } catch (error) {
      console.error("Error submitting rating:", error.message);
    } finally {
      setSubmitting(false);
      setNewStars(0);
      setNewComment("");
    }
  };

  const formatDate = (dateArray) => {
    const [year, month, day] = dateArray;
    return `${String(day).padStart(2, "0")}/${String(month).padStart(
      2,
      "0"
    )}/${year}`;
  };

  const calculateAverageRating = (ratings) => {
    if (ratings.length === 0) return 0;
    const totalStars = ratings.reduce((acc, rating) => acc + rating.star, 0);
    return (totalStars / ratings.length).toFixed(1);
  };

  const averageRating = calculateAverageRating(ratings);

  return (
    <div
      className="ratingModal modal fade"
      id="ratingModal"
      tabIndex="-1"
      aria-labelledby="ratingModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="ratingModalLabel">
              Đánh giá khách sạn
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
              onClick={onClose}
            />
          </div>
          <div className="modal-body">
            <div className="d-flex justify-content-between align-items-center">
              <div className="text-center">
                <strong>{averageRating}</strong> trên 5
                <div className="text-warning">
                  {Array(5)
                    .fill()
                    .map((_, i) => (
                      <FontAwesomeIcon
                        icon={faStar}
                        key={i}
                        className={
                          i < Math.round(averageRating) ? "selectedStar" : ""
                        }
                      />
                    ))}
                </div>
              </div>
              <div>{ratings.length} nhận xét</div>
            </div>

            <div className="mt-3">
              <h6>Nhận xét</h6>
              <div className="overflow-auto" style={{ maxHeight: "300px" }}>
                {ratings.map((rating, index) => (
                  <div className="border-top pt-2" key={index}>
                    <p className="fw-bold">{rating.userName}</p>
                    <div className="text-warning">
                      {Array.from({ length: rating.star }, (_, i) => (
                        <FontAwesomeIcon icon={faStar} key={i} />
                      ))}
                    </div>
                    <p>{rating.comment}</p>
                    <small className="text-muted">
                      Loại phòng: {rating.roomType} -{" "}
                      {formatDate(rating.rateDay)}
                    </small>
                  </div>
                ))}
              </div>
            </div>

            {userId && canComment ? (
              <form className="mt-3" onSubmit={handleCommentSubmit}>
                <div className="mb-2">
                  Hãy chọn số sao đánh giá: &#160;
                  {Array(5)
                    .fill()
                    .map((_, i) => (
                      <FontAwesomeIcon
                        icon={faStar}
                        key={i}
                        className={newStars > i ? "text-warning" : ""}
                        onClick={() => handleStarClick(i + 1)}
                        style={{ cursor: "pointer" }}
                      />
                    ))}
                </div>
                <div className="input-group">
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Viết bình luận của bạn..."
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    required
                  />
                  <button
                    className="btn btn-primary"
                    type="submit"
                    disabled={submitting}
                  >
                    <FontAwesomeIcon icon={faPaperPlane} />
                  </button>
                </div>
              </form>
            ) : (
              <p className="mt-3 text-center">
                Bạn phải đăng nhập và đặt phòng để có thể đánh giá.
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Rating;
