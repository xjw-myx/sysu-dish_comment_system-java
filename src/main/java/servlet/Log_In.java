package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/log_in")
public class Log_In extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("user_id");
		String inputPassword = request.getParameter("password");

		boolean loginSuccess = false;

		try (Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_homework", "root",
				"xu2580");
				PreparedStatement stmt = connect.prepareStatement("SELECT password FROM User WHERE user_id = ?")) {

			stmt.setInt(1, Integer.parseInt(userId));
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String dbPassword = rs.getString("password");
					if (dbPassword.equals(inputPassword)) {
						loginSuccess = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		if (loginSuccess) {
			session.setAttribute("return_code", 1);
			session.setAttribute("user_id", userId);
			response.sendRedirect(request.getContextPath() + "/index");
		} else {
			session.setAttribute("return_code", 2);
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
			return;
		}

		// 根据时间确定登录界面背景
		LocalTime currentTime = LocalTime.now();
		String backgroundImage;
		String imagePath = request.getContextPath() + "/static/images/Background/";
		if (currentTime.isAfter(LocalTime.of(7, 0)) && currentTime.isBefore(LocalTime.of(14, 0))) {
			backgroundImage = imagePath + "day1.jpg";
		} else if (currentTime.isAfter(LocalTime.of(14, 0)) && currentTime.isBefore(LocalTime.of(18, 0))) {
			backgroundImage = imagePath + "day2.jpg";
		} else {
			backgroundImage = imagePath + "day3.jpg";
		}

		request.setAttribute("backgroundImage", backgroundImage);

		request.getRequestDispatcher("/jsp/log_in.jsp").forward(request, response);
	}

}
