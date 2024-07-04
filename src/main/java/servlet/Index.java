package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "", "/index" })
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 生成幻灯片内容
		StringBuilder indicators = new StringBuilder();
		StringBuilder slides = new StringBuilder();
		List<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			numbers.add(i);
		}
		Collections.shuffle(numbers);
		List<Integer> randomNumbers = numbers.subList(0, 5);
		for (int i = 0; i < 5; i++) {
			int num = randomNumbers.get(i);
			String imagePathCafeteria = request.getContextPath() + "/static/images/Cafeteria/" + num + ".png";
			indicators.append("<li data-target='#myCarousel' data-slide-to='").append(i).append("'")
					.append(i == 0 ? " class='active'" : "").append("></li>");
			slides.append("<div class='item").append(i == 0 ? " active" : "").append("'>").append("<img src='")
					.append(imagePathCafeteria).append("' alt='Image ").append(num + 1).append("'>").append("</div>");
		}
		request.setAttribute("indicators", indicators.toString());
		request.setAttribute("slides", slides.toString());

		// 从数据库获取随机 20 条评论
		List<String[]> comments = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_homework", "root",
					"xu2580");
			String query = "SELECT d.dish_name, c.score, c.content " + "FROM Comments c "
					+ "JOIN Dish d ON c.dish_id = d.dish_id " + "ORDER BY RAND() LIMIT 9";
			PreparedStatement stmt = connect.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String[] comment = new String[3];
				comment[0] = rs.getString("dish_name");
				comment[1] = rs.getString("score");
				comment[2] = rs.getString("content");
				comments.add(comment);
			}
			rs.close();
			stmt.close();
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("comments", comments);

		// 获取用户登录状态
		HttpSession session = request.getSession();
		Integer loginStatus = (Integer) session.getAttribute("loginStatus");
		if (loginStatus == null) {
			loginStatus = 0;
		}
		request.setAttribute("loginStatus", loginStatus);

		// 转发请求到index.jsp
		request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	}
}
