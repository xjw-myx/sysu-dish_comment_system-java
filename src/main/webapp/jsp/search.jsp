<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>中大食评-搜索</title>
    <!-- 引入Bootstrap -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/bootstrap/css/bootstrap.min.css">
    <script src="<%= request.getContextPath() %>/static/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="<%= request.getContextPath() %>/static/bootstrap/js/bootstrap.min.js"></script>
    <script>      // 当页面加载时，检查并设置复选框的状态以及输入框的内容
	    window.onload = function () {
	        var form = document.getElementById('filterForm');
	        var checkboxes = form.querySelectorAll('input[type="checkbox"]');
	        var queryInput = document.getElementById('query');
	
	        
	        var urlParams = new URLSearchParams(window.location.search);     // 获取URL参数并解析为对象
	        
	        var savedQuery = urlParams.get('q');                             // 设置查询输入框的值
	        if (savedQuery) {
	            queryInput.value = savedQuery;
	        }
	
	        checkboxes.forEach(function(checkbox) {                          // 设置复选框的状态
	            if (urlParams.getAll(checkbox.name).includes(checkbox.value)) {
	                checkbox.checked = true;
	            } else {
	                checkbox.checked = false;
	            }
	        });
	
	        checkboxes.forEach(function(checkbox) {                          // 监听复选框状态变化，并更新URL参数
	            checkbox.addEventListener('change', function() {
	                updateURLParams();
	            });
	        });
	
	        queryInput.addEventListener('input', function() {                // 监听输入框内容变化，并更新URL参数
	            updateURLParams();
	        });
	
	        function updateURLParams() {
	            var url = new URL(window.location);
	            var params = new URLSearchParams(url.search);
	
	            params.set('q', queryInput.value);                           // 更新查询参数
	
	            params.delete('cafeteria');                                  // 清空并更新复选框参数
	            params.delete('time');
	            checkboxes.forEach(function(checkbox) {
	                if (checkbox.checked) {
	                    params.append(checkbox.name, checkbox.value);
	                }
	            });
	
	            window.history.replaceState({}, '', `${url.pathname}?${params}`);  // 更新浏览器URL但不刷新页面
	        }
	    };
	</script>
</head>

<body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-4 column">
                <h1>中大食评-搜索</h1>
                <form method="GET" action="search" id="filterForm" name='ddf'>
                    <input type="text" class="form-control" id="query" name="q" placeholder="输入搜索关键词" style="margin-right: 5px;">
                    <button type="submit" class="btn btn-primary">搜索</button>
                    
                    <br><br>
                    <a href="index">返回首页</a>
                    <br><br><br>

                    <label>餐厅：</label><br>
                    <div class="cafeteria-checkboxes">
                        <c:forEach var="cafeteria" items="${cafeterias}">
                            <label>
                                <input type="checkbox" id="${cafeteria.cafeteriaId}" name="cafeteria" value="${cafeteria.cafeteriaId}">
                                ${cafeteria.cafeteriaName}
                            </label>
                        </c:forEach>
                    </div>

                    <label>供应时间：</label><br>
                    <c:forEach var="time" items="${servedTimes}">
                        <label>
                            <input type="checkbox" id="${time.servedTimeId}" name="time" value="${time.servedTimeId}">
                            ${time.servedTimePeriod}
                        </label><br>
                    </c:forEach>

                    <button type="submit" class="btn btn-primary">筛选 & 组合</button>
                </form>
            </div>

            <div class="col-md-1 column"></div>

            <div class="col-md-7 column">
                <h1>美食区</h1>
                <div class="search_result">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>菜品名称</th>
                                <th>菜品价格</th>
                                <th>详情</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="result" items="${searchResult}">
                                <tr>
                                    <td><strong>${result.dishName}</strong></td>
                                    <td>${result.dishPrice}</td>
                                    <td><a href="${pageContext.request.contextPath}/detail?dish_id=${result.dishId}">查看详情</a></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty searchResult}">
                                <tr>
                                    <td colspan="3">暂无搜索结果</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
