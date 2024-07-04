package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/sign_up")
public class Sign_Up extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("user_id");
		String password = request.getParameter("password");
		String userName = request.getParameter("user_name");

		boolean userIdExists = false;

		try (Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_homework", "root",
				"xu2580");
				PreparedStatement checkUserStmt = connect
						.prepareStatement("SELECT user_id FROM User WHERE user_id = ?");
				PreparedStatement insertUserStmt = connect.prepareStatement("INSERT INTO User VALUES (?, ?, 0, ?)")) {

			checkUserStmt.setString(1, userId);
			try (ResultSet rs = checkUserStmt.executeQuery()) {
				if (rs.next()) {
					userIdExists = true;
				}
			}

			if (!userIdExists) {
				insertUserStmt.setInt(1, Integer.parseInt(userId));
				insertUserStmt.setString(2, password);
				insertUserStmt.setString(3, userName);
				insertUserStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		if (userIdExists) {
			session.setAttribute("return_code", 4);
			response.sendRedirect(request.getContextPath() + "/sign_up");
		} else {
			session.setAttribute("return_code", 3);
			response.sendRedirect(request.getContextPath() + "/log_in");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer loginStatus = (Integer) session.getAttribute("return_code");
		// 如果登录状态为1，即已登录，则重定向到主页
		if (loginStatus != null && loginStatus == 1) {
			response.sendRedirect(request.getContextPath() + "/index");
			return; // 确保重定向后不再执行后续代码
		}

		request.getRequestDispatcher("/jsp/sign_up.jsp").forward(request, response);
	}
}
