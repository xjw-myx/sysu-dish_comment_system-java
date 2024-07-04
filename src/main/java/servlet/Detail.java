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

@WebServlet("/detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/my_homework?useUnicode=true&characterEncoding=UTF-8";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "xu2580";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dishId = request.getParameter("dish_id");

		if (dishId == null || dishId.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "菜品ID缺失");
			return;
		}

		Map<String, String> dishInfo = getDishDetails(dishId);
		List<Map<String, String>> dishComments = getDishComments(dishId);

		if (dishInfo == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "未找到菜品");
			return;
		}

		request.setAttribute("dish_info", dishInfo);
		request.setAttribute("dish_comments", dishComments);
		request.getRequestDispatcher("/jsp/detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String dishId = request.getParameter("dish_id");
		String scoreStr = request.getParameter("score");
		String content = request.getParameter("content");

		// 检查用户是否登录
		if (request.getSession().getAttribute("user_id") == null) {
			request.setAttribute("comment_code", 2); // 设置评论失败代码
			doGet(request, response); // 重新加载详情页
			return;
		}

		// 检查评分和评论内容是否为空
		if (scoreStr == null || content == null || scoreStr.isEmpty() || content.isEmpty()) {
			request.setAttribute("comment_code", 3); // 设置评论失败代码
			doGet(request, response); // 重新加载详情页
			return;
		}

		try {
			int score = Integer.parseInt(scoreStr);

			// 保存评论到数据库
			saveComment(dishId, (String) request.getSession().getAttribute("user_id"), score, content);

			request.setAttribute("comment_code", 1); // 设置评论成功代码
			doGet(request, response); // 重新加载详情页

		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("comment_code", 3); // 设置评论失败代码
			doGet(request, response); // 重新加载详情页
		}
	}

	private void saveComment(String dishId, String userId, int score, String content) {
		String getMaxIdSql = "SELECT MAX(comment_id) FROM Comments";
		String insertSql = "INSERT INTO Comments (comment_id, score, content, dish_id, username_id) VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement getMaxIdStmt = connection.prepareStatement(getMaxIdSql);
				PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

			// 获取当前表中最大 comment_id 的值
			ResultSet resultSet = getMaxIdStmt.executeQuery();
			int maxCommentId = 0;
			if (resultSet.next()) {
				maxCommentId = resultSet.getInt(1);
			}

			// 新的 comment_id 为最大值加 1
			int newCommentId = maxCommentId + 1;

			// 设置插入新评论的参数
			insertStmt.setInt(1, newCommentId);
			insertStmt.setInt(2, score);
			insertStmt.setString(3, content);
			insertStmt.setInt(4, Integer.parseInt(dishId));
			insertStmt.setInt(5, Integer.parseInt(userId));

			// 执行插入操作
			insertStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> getDishDetails(String dishId) {
		Map<String, String> dish = null;
		String sql = "SELECT d.dish_name, d.dish_price, c.cafeteria_name, s.served_time_period " + "FROM Dish d "
				+ "JOIN Cafeteria c ON d.cafeteria_id = c.cafeteria_id "
				+ "JOIN ServedTime s ON d.served_time_id = s.served_time_id " + "WHERE d.dish_id = ?";

		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, Integer.parseInt(dishId));
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				dish = new HashMap<>();
				dish.put("dish_name", resultSet.getString("dish_name"));
				dish.put("dish_price", String.valueOf(resultSet.getInt("dish_price")));
				dish.put("cafeteria_name", resultSet.getString("cafeteria_name"));
				dish.put("served_time_period", resultSet.getString("served_time_period"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dish;
	}

	private List<Map<String, String>> getDishComments(String dishId) {
		List<Map<String, String>> comments = new ArrayList<>();
		String sql = "SELECT c.username_id, c.score, c.content " + "FROM Comments c " + "WHERE c.dish_id = ?";

		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, Integer.parseInt(dishId));
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Map<String, String> comment = new HashMap<>();
				comment.put("username_id", resultSet.getString("username_id"));
				comment.put("score", String.valueOf(resultSet.getInt("score")));
				comment.put("content", resultSet.getString("content"));
				comments.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return comments;
	}
}
