import React, { useContext, useEffect, useState } from "react";
import "./rating.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane, faStar } from "@fortawesome/free-solid-svg-icons";
import { AuthContext } from "../utils/AuthProvider";
import { addNewRating, checkIfUserCanComment } from "../utils/ApiFunction";

const Rating = ({ hotelId, onClose }) => {
  const { user } = useContext(AuthContext);
  const isLoggedIn = user !== null;
  const [newComment, setNewComment] = useState("");
  const [newStars, setNewStars] = useState(0);
  const [canComment, setCanComment] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const userId = localStorage.getItem("userId");

  useEffect(() => {
    if (isLoggedIn) {
      checkIfUserCanComment(userId, hotelId)
        .then((response) => {
          setCanComment(response);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [userId, hotelId]);

  const comments = [
    {
      name: "Nguyễn Văn A",
      stars: 5,
      comment: "Phòng sạch sẽ, dịch vụ tốt.",
      date: "2024-06-15",
      roomType: "Phòng Deluxe",
    },
    {
      name: "Trần Thị B",
      stars: 4,
      comment: "Giá cả hợp lý, nhân viên thân thiện.",
      date: "2024-06-10",
      roomType: "Phòng Standard",
    },
    {
      name: "Lê Thị C",
      stars: 5,
      comment: "Vị trí thuận tiện, gần trung tâm.",
      date: "2024-06-05",
      roomType: "Phòng Superior",
    },
  ];

  const handleStarClick = (stars) => {
    setNewStars(stars);
  };

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true); // Bắt đầu gửi đánh giá

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

  console.log("Check if can comment: " + isLoggedIn + " " + canComment);

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
              <strong>5</strong> trên 5
            </div>
            <div className="ratingOverView_star">
              <FontAwesomeIcon icon={faStar} />
              <FontAwesomeIcon icon={faStar} />
              <FontAwesomeIcon icon={faStar} />
              <FontAwesomeIcon icon={faStar} />
              <FontAwesomeIcon icon={faStar} />
            </div>
          </div>
          <div className="totalComments">{comments.length} nhận xét</div>
        </div>
        <div className="ratingReviews">
          <h3>Nhận xét</h3>
          <div className="reviewList">
            {comments.map((comment, index) => (
              <div className="reviewItem" key={index}>
                <p className="reviewUser">{comment.name}</p>
                <div className="reviewStars">
                  {Array(comment.stars)
                    .fill()
                    .map((_, i) => (
                      <FontAwesomeIcon icon={faStar} key={i} />
                    ))}
                </div>
                <p className="reviewText">{comment.comment}</p>
                <div className="reviewDetails">
                  <p className="reviewRoomType">
                    Loại phòng: {comment.roomType}
                  </p>
                  <p className="reviewDate">{comment.date}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="newReview">
          {isLoggedIn && canComment ? (
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
            <div className="remindText">Bạn phải đăng nhặp và đặt phòng (và chưa đánh giá) để có thể đánh giá.</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Rating;
