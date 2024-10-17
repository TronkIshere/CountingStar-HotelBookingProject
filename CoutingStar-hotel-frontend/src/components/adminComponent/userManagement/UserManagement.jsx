import React, { useEffect, useState } from "react";
import "./userManagement.css";
import {
  getAllUserExceptAminRole,
  searchUserByKeyWord,
} from "../../utils/ApiFunction";
import UserEdit from "./userManagementComponent/userEdit/UserEdit";
import UserDelete from "./userManagementComponent/userDelete/UserDelete";

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const fetchUsers = async (pageNo = 0, pageSize = 8, keyword = "") => {
    let usersData;
    if (keyword) {
      usersData = await searchUserByKeyWord(pageNo, pageSize, keyword);
    } else {
      usersData = await getAllUserExceptAminRole(pageNo, pageSize);
    }
    setUsers(usersData.content);
    setTotalPages(usersData.totalPages);
  };

  useEffect(() => {
    fetchUsers(page, 8, searchKeyword);
  }, [page, searchKeyword]);

  const handleSearch = () => {
    setPage(0);
    fetchUsers(0, 8, searchKeyword);
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  const [currentUserId, setUserId] = useState(null);

  const handleEditClick = (id) => {
    setUserId(id);
  };

  const handleDeleteClick = (id) => {
    setUserId(id);
  };

  return (
    <div className="userManagement">
      <div className="container">
        <div className="searchUser-bar">
          <div className="row">
            <div className="col-sm-12 col-md-5 col-lg-5 col-12">
              <div className="mb-3">
                <label htmlFor="user-keyword" className="form-label">
                  Nhập từ khóa người dùng:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="user-keyword"
                  placeholder="Nhập từ khóa"
                  value={searchKeyword}
                  onChange={(e) => setSearchKeyword(e.target.value)}
                />
              </div>
            </div>

            <div className="col-sm-2 col-md-2 col-lg-2 col-12 d-flex align-items-center">
              <div className="button-container">
                <button className="main-btn" onClick={handleSearch}>
                  Tìm <i className="bi bi-search"></i>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div className="searchUserList">
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Họ</th>
                <th scope="col">Tên</th>
                <th scope="col">Email</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.lastName}</td>
                  <td>{user.firstName}</td>
                  <td>{user.email}</td>
                  <td>{user.phoneNumber}</td>
                  <td>
                    <button
                      className="btn btn-primary btn-sm"
                      data-bs-toggle="modal"
                      data-bs-target="#editUserModal"
                      onClick={() => handleEditClick(user.id)}
                    >
                      Chỉnh sửa
                    </button>
                    <UserEdit userId={currentUserId} />
                    <button
                      className="btn btn-danger btn-sm"
                      data-bs-toggle="modal"
                      data-bs-target="#deleteUserModel"
                      onClick={() => handleDeleteClick(user.id)}
                    >
                      Xóa
                    </button>
                    <UserDelete userId={currentUserId} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="pagination d-flex justify-content-center mt-4">
            <nav aria-label="Page navigation example">
              <ul className="pagination">
                <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
                  <button
                    className="page-link"
                    onClick={() => handlePageChange(page - 1)}
                  >
                    &laquo;
                  </button>
                </li>
                {Array.from({ length: totalPages }, (_, i) => (
                  <li
                    key={i}
                    className={`page-item ${page === i ? "active" : ""}`}
                  >
                    <button
                      className="page-link"
                      onClick={() => handlePageChange(i)}
                    >
                      {i + 1}
                    </button>
                  </li>
                ))}
                <li
                  className={`page-item ${
                    page === totalPages - 1 ? "disabled" : ""
                  }`}
                >
                  <button
                    className="page-link"
                    onClick={() => handlePageChange(page + 1)}
                  >
                    &raquo;
                  </button>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserManagement;
