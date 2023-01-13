<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"
+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
		$("#addBtn").click(function () {
			$.ajax({
				url:"workbench/activity/getUserList.do",
				type:"get",
				dataType:"json",
				success:function (list) {
					var html = "";
					$.each(list,function (i,n) {
						html += "<option value='"+n.id+"'>"+n.name+"</option>";
					})
					$("#create-owner").html(html);

					/*这一步得放在success方法里*/
					var id="${user.id}"; //el表达式在js里用得“”双引号，必须的。
					$("#create-owner").val(id);
				}
			})


			$("#createActivityModal").modal("show");
		})

		$(".time").datetimepicker({
			minView:"month",
			language:"zh-Cn",
			format:"yyyy-mm-dd",
			autoclose:true,
			todayBtn:true,
			pickerPosition:"buttom-left"
		})

		$("#saveBtn").click(function () {
			$.ajax({
				url:"workbench/activity/save.do",
				data:{
					"owner": $("#create-owner").val(),
					"name": $("#create-name").val(),
					"startDate" : $("#create-startDate").val(),
					"endDate": $("#create-endDate").val(),
					"cost": $("#create-cost").val(),
					"description": $("#create-description").val()
				},
				type:"post",
				dataType:"json",
				success:function (result) {
					alert(result.success);
					if(result.success){
						$("#createActivityModal").modal("hide");
						pageList(1,$("#activityPage").bs_pagination('getOption','rowsPerPage'));
						/*$("#addForm")[0].reset();*/
					}else if(result.success){
						alert(result.success);
						alert("失败");
						$("#createActivityModal").modal("hide");
					}
				}
			})
		})

		pageList(1,3);

		$("#searchActivity").click(function () {
			var name = $.trim($("#search-name").val());
			var owner = $.trim($("#search-owner").val());
			var startDate = $.trim($("#search-startDate").val());
			var endDate = $.trim($("#search-endDate").val());
			$("#hide-name").val(name);
			$("#hide-owner").val(owner);
			$("#hide-startDate").val(startDate);
			$("#hide-endDate").val(endDate);
			pageList(1,3)
		})

		/*

		jQuery函数：
		$对象.prop("属性"，条件)。 如果条件成立给jquery对象设置该属性。

		* */
		$("#qx").click(function () {
			$("input[name=xz]").prop("checked",this.checked);
		})

		//以下这种做法是不行的
		/*$("input[name=xz]").click(function () {

			alert(123);

		})*/

		//因为动态生成的元素，是不能够以普通绑定事件的形式来进行操作的
		/*

			动态生成的元素，我们要以on方法的形式来触发事件

			语法：
				$(需要绑定元素的有效的外层元素).on(绑定事件的方式,需要绑定的元素的jquery对象,回调函数)

		 */
		$("#pageActivityListTbody").on("click",$("input[name=xz]"),function () {
			$("#qx").prop("checked",$("input[name=xz]:checked").length==$("input[name=xz]").length);
		})

		$("#deleteBtn").click(function (){
			var ck=$("input[name=xz]:checked");
			var param = "";
			$.each(ck,function (i,n) {
				param+="id="
				param+=n.value;
				if(i<ck.length-1){
					param+="&";
				}
			})
			if(ck.length>0){
				$.ajax({
					url:"workbench/activity/delete.do",
					data:param,
					type:"get",
					dataType:"json",
					success:function (data) {
						if(data.success){
							alert("删除成功");
							pageList(1,3);
						}
					}

				})
			}else {
				alert("请选择")
			}
		})

		$("#editBtn").click(function () {
			var $xz=$("input[name=xz]:checked");
			if($xz.length==0){
				alert("前选择");
			}else if($xz.length>1){
				alert("只能选中一项");
			}else if($xz.length==1){
				$.ajax({
					url:"workbench/activity/getUpdateInfo.do",
					data:{
						"id":$xz.val(),
					},
					type:"get",
					dataType:"json",
					success:function (data) {
						var html = "";
						$.each(data.list,function (i,n) {
							html +='<option value="'+n.id+'">'+n.name+'</option>'
						})
						$("#edit-owner").html(html);
						$("#edit-name").val(data.info.name);
						$("#edit-startTime").val(data.info.startDate);
						$("#edit-endTime").val(data.info.endDate);
						$("#edit-cost").val(data.info.cost);
						$("#edit-description").val(data.info.description);
						$("#editActivityModal").modal("show");

					}
				})
			}


		})

		$("#updateBtn").click(function () {
			$.ajax({
				url:"workbench/activity/update.do",
				data:{
					"id":$("input[name=xz]:checked").val(),
					"owner": $("#edit-owner").val(),
					"name": $("#edit-name").val(),
					"startDate" : $("#edit-startTime").val(),
					"endDate": $("#edit-endTime").val(),
					"cost": $("#edit-cost").val(),
					"description": $("#edit-description").val()
				},
				type:"post",
				dataType:"json",
				success:function (result) {
					alert(result.success);
					if(result.success){
						$("#editActivityModal").modal("hide");
						/*
						* ($("#activityPage").bs_pagination('getOption','currentPage')
						* 表示展示所在当前页
						*
						* $("#activityPage").bs_pagination('getOption','rowsPerPage')
						* 表示维持设置展示页数
						*
						* */
						pageList($("#activityPage").bs_pagination('getOption','currentPage'),
								$("#activityPage").bs_pagination('getOption','rowsPerPage'));
						/*$("#addForm")[0].reset();*/
					}else if(result.success){
						alert("失败");
						$("#editActivityModal").modal("hide");
					}
				}
			})
		})
		
	});

	function pageList(pageNo,pageSize) {


		var name = $("#hide-name").val();
		var owner = $("#hide-owner").val();
		var startDate = $("#hide-startDate").val();
		var endDate = $("#hide-endDate").val();
		var html = "";
		$.ajax({
			url:"workbench/activity/pageActivityList.do",
			data:{
				"name":name,
				"owner":owner,
				"startDate":startDate,
				"endDate":endDate,
				"pageSize":pageSize,
				"pageNo":pageNo,
			},
			dataType:"json",
			type:"get",
			success:function (data) {
				$.each(data.list,function (i,n) {
					html += '<tr class="active">'
					html += '<td><input type="checkbox" name="xz" value="'+n.id+'" /></td>'
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>'
					html += '<td>'+n.owner+'</td>'
					html += '<td>'+n.startDate+'</td>'
					html += '<td>'+n.endDate+'</td>'
					html += '</tr>'
				})

				$("#pageActivityListTbody").html(html);

				/*
				<script language="javascript">
				var uu=Math.floor(5.36) //向下取整 结果为5
				var uu=Math.floor(5.88) //结果为5
				Math.ceil(5.33) //向上取整，结果为6
				Math.round(5.55) //四舍五入 结果为6
				math.round(5.22) //结果为5
				*/
				var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});
			}
		})
	}
	
</script>
</head>
<body>

	<input type="hidden" id="hide-name"/>
	<input type="hidden" id="hide-owner"/>
	<input type="hidden" id="hide-startDate"/>
	<input type="hidden" id="hide-endDate"/>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="addForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- wocao修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-Owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								</select>
							</div>
                            <label for="edit-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						<%--
						关于文本域 textarea，一定要以键值对的形式出现，还有里面不能有空格，
							取里面的内容的时候 val(),不是 html(),不过要用也可以
						--%>
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="startTime" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="endTime" id="search-endDate">
				    </div>
				  </div>
							<%--必须是botton 不然会乱提交的--%>
				  <button type="button" class="btn btn-default" id="searchActivity">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="pageActivityListTbody">

					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;" >
				<div id="activityPage"></div>

			</div>
			
		</div>
		
	</div>
</body>
</html>