package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user_info")
public class User_Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = (String) request.getSession().getAttribute("user_id");
		if (userId == null) {
			response.sendRedirect("log_in");
			return;
		}

		Connection conn = null;
		PreparedStatement pstmtUser = null;
		PreparedStatement pstmtComments = null;
		ResultSet rsUser = null;
		ResultSet rsComments = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_homework", "root", "xu2580");

			// 获取用户信息
			String sqlUser = "SELECT username FROM User WHERE user_id = ?";
			pstmtUser = conn.prepareStatement(sqlUser);
			pstmtUser.setInt(1, Integer.parseInt(userId));
			rsUser = pstmtUser.executeQuery();

			Map<String, Object> userInfo = new HashMap<>();
			if (rsUser.next()) {
				userInfo.put("username", rsUser.getString("username"));
				userInfo.put("user_id", userId);
			}

			// 获取用户评论
			String sqlComments = "SELECT * FROM Comments WHERE username_id = ?";
			pstmtComments = conn.prepareStatement(sqlComments);
			pstmtComments.setInt(1, Integer.parseInt(userId));
			rsComments = pstmtComments.executeQuery();

			List<Map<String, Object>> comments = new ArrayList<>();
			while (rsComments.next()) {
				Map<String, Object> comment = new HashMap<>();
				comment.put("username_id", rsComments.getInt("username_id"));
				comment.put("score", rsComments.getInt("score"));
				comment.put("content", rsComments.getString("content"));
				comments.add(comment);
			}
			userInfo.put("comments", comments);

			request.setAttribute("user_info", userInfo);
			request.getRequestDispatcher("/jsp/user_info.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().setAttribute("return_code", 0);
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/log_in");
	}
}
