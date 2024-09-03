import React, { useContext, useEffect, useState } from "react";
import "./rating.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane, faStar } from "@fortawesome/free-solid-svg-icons";
import { AuthContext } from "../utils/AuthProvider";
import {
  addNewRating,
  checkIfUserCanComment,
  getAllRatingByHotelId,
} from "../utils/ApiFunction";

const Rating = ({ hotelId, onClose }) => {
  const userId = localStorage.getItem("userId");
  const [newComment, setNewComment] = useState("");
  const [newStars, setNewStars] = useState(0);
  const [canComment, setCanComment] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [ratings, setRatings] = useState([
    {
      id: "",
      star: "",
      comment: "",
      rateDay: "",
      userName: "",
      roomType: ""
    },
  ]);

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
      console.log(ratings);
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
    return `${String(day).padStart(2, '0')}/${String(month).padStart(2, '0')}/${year}`;
  };

  const calculateAverageRating = (ratings) => {
    if (ratings.length === 0) return 0;
    const totalStars = ratings.reduce((acc, rating) => acc + rating.star, 0);
    return (totalStars / ratings.length).toFixed(1);
  };

  const averageRating = calculateAverageRating(ratings);

  console.log("Check if can comment: " + userId + " " + canComment);

  return (
    <div className="ratingPopup">
      <div className="ratingContent">
        <span className="close" onClick={onClose}>
          &times;
        </span>
        <h2>Đánh giá khách sạn</h2>
        <div className="ratingOverView">
          <div className="ratingOverViewText">
            <div className="ratingOverViewText_header">
              <strong>{averageRating}</strong> trên 5
            </div>
            <div className="ratingOverView_star">
              {Array(5).fill().map((_, i) => (
                <FontAwesomeIcon
                  icon={faStar}
                  key={i}
                  className={i < Math.round(averageRating) ? "selectedStar" : ""}
                />
              ))}
            </div>
          </div>
          <div className="totalComments">{ratings.length} nhận xét</div>
        </div>
        <div className="ratingReviews">
          <h3>Nhận xét</h3>
          <div className="reviewList">
            {ratings.map((rating, index) => (
              <div className="reviewItem" key={index}>
                <p className="reviewUser">{rating.userName}</p>
                <div className="reviewStars">
                  {Array.from({ length: rating.star }, (_, i) => (
                    <FontAwesomeIcon icon={faStar} key={i} />
                  ))}
                </div>
                <p className="reviewText">{rating.comment}</p>
                <div className="reviewDetails">
                  <p className="reviewRoomType">Loại phòng: {rating.roomType}</p>
                  <p className="reviewDate">{formatDate(rating.rateDay)}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="newReview">
          {userId && canComment ? (
            <form onSubmit={handleCommentSubmit}>
              <div className="starRating">
                Hãy chọn số sao đánh giá: &#160;
                {Array(5)
                  .fill()
                  .map((_, i) => (
                    <FontAwesomeIcon
                      icon={faStar}
                      key={i}
                      className={newStars > i ? "selectedStar" : ""}
                      onClick={() => handleStarClick(i + 1)}
                    />
                  ))}
              </div>
              <div className="wrapper">
                <input
                  className="commentInput"
                  placeholder="Viết bình luận của bạn..."
                  value={newComment}
                  onChange={(e) => setNewComment(e.target.value)}
                  required
                />
                <button
                  type="submit"
                  className="submitIcon"
                  disabled={submitting}
                >
                  <FontAwesomeIcon icon={faPaperPlane} />
                </button>
              </div>
            </form>
          ) : (
            <div className="remindText">
              Bạn phải đăng nhặp và đặt phòng (và chưa đánh giá) để có thể đánh
              giá.
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Rating;
