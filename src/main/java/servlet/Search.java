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

@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/my_homework";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "xu2580";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取查询参数
		String query = request.getParameter("q");
		String[] cafeteriaIds = request.getParameterValues("cafeteria");
		String[] servedTimeIds = request.getParameterValues("time");

		List<Map<String, String>> cafeterias = getCafeterias();
		List<Map<String, String>> servedTimes = getServedTimes();
		List<Map<String, String>> searchResult = searchDishes(query, cafeteriaIds, servedTimeIds);

		// 设置请求属性
		request.setAttribute("cafeterias", cafeterias);
		request.setAttribute("servedTimes", servedTimes);
		request.setAttribute("searchResult", searchResult);

		// 转发请求到JSP页面
		request.getRequestDispatcher("/jsp/search.jsp").forward(request, response);
	}

	private List<Map<String, String>> getCafeterias() {
		List<Map<String, String>> cafeterias = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement statement = connection
						.prepareStatement("SELECT cafeteria_id, cafeteria_name FROM Cafeteria");
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Map<String, String> cafeteria = new HashMap<>();
				cafeteria.put("cafeteriaId", String.valueOf(resultSet.getInt("cafeteria_id")));
				cafeteria.put("cafeteriaName", resultSet.getString("cafeteria_name"));
				cafeterias.add(cafeteria);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cafeterias;
	}

	private List<Map<String, String>> getServedTimes() {
		List<Map<String, String>> servedTimes = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement statement = connection
						.prepareStatement("SELECT served_time_id, served_time_period FROM ServedTime");
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Map<String, String> servedTime = new HashMap<>();
				servedTime.put("servedTimeId", String.valueOf(resultSet.getInt("served_time_id")));
				servedTime.put("servedTimePeriod", resultSet.getString("served_time_period"));
				servedTimes.add(servedTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return servedTimes;
	}

	private List<Map<String, String>> searchDishes(String query, String[] cafeteriaIds, String[] servedTimeIds) {
		List<Map<String, String>> dishes = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"SELECT dish_id, dish_name, dish_price, cafeteria_id, served_time_id FROM Dish WHERE 1=1");

		if (query != null && !query.trim().isEmpty()) {
			sql.append(" AND dish_name LIKE ?");
		}
		if (cafeteriaIds != null && cafeteriaIds.length > 0) {
			sql.append(" AND cafeteria_id IN (").append(String.join(",", cafeteriaIds)).append(")");
		}
		if (servedTimeIds != null && servedTimeIds.length > 0) {
			sql.append(" AND served_time_id IN (").append(String.join(",", servedTimeIds)).append(")");
		}

		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql.toString())) {

			int paramIndex = 1;
			if (query != null && !query.trim().isEmpty()) {
				statement.setString(paramIndex++, "%" + query + "%");
			}

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Map<String, String> dish = new HashMap<>();
				dish.put("dishId", String.valueOf(resultSet.getInt("dish_id")));
				dish.put("dishName", resultSet.getString("dish_name"));
				dish.put("dishPrice", String.valueOf(resultSet.getInt("dish_price")));
				dish.put("cafeteriaId", String.valueOf(resultSet.getInt("cafeteria_id")));
				dish.put("servedTimeId", String.valueOf(resultSet.getInt("served_time_id")));
				dishes.add(dish);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dishes;
	}
}
