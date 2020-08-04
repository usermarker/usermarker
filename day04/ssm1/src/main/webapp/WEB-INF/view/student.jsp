<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户主页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/common.jsp"%>

<link
	href="${path }/resources/css/plugins/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet">
<link href="${path }/resources/css/animate.css" rel="stylesheet">
<link href="${path }/resources/css/style.css?v=4.1.0" rel="stylesheet">

</head>
<body class="gray-bg">
	<div class="panel-body">
	<!-- ========================增删改查的按钮，从数据库查出来=============================== -->
		<div id="toolbar" class="btn-group">
			<c:forEach items="${operationList}" var="oper">
				<privilege:operation operationId="${oper.operationid }" id="${oper.operationcode }" name="${oper.operationname }" clazz="${oper.iconcls }"  color="#093F4D"></privilege:operation>
			</c:forEach>
        </div>
        <!-- ========================多条件查询的内容，根据具体的要求=============================== -->
        <div class="row">
			  <div class="col-lg-2">
				<div class="input-group">
			      <span class="input-group-addon">姓名 </span>
			      <input type="text" name="username" class="form-control" id="txt_search_name" >
				</div>
			  </div>
			  <div class="col-lg-2">
				<div class="input-group">
			      <span class="input-group-addon">生日 </span>
			      <input placeholder="请选择开始时间" id="txt_search_start" name="start" class="laydate-icon form-control layer-date"/>
			      <input placeholder="请选择结束时间" id="txt_search_end" name="end" class="laydate-icon form-control layer-date"/>
				</div>
			  </div>
			  <div class="col-lg-2">
				<div class="input-group">
					<span class="input-group-addon">专业</span>
					<select class="form-control" name="txt_search_roleid" id = "txt_search_maid">
						<option value="0">---请选择---</option>
						<c:forEach items="${majorList }" var="r">
						 	<option value="${r.maid }">${r.sName }</option>
						</c:forEach>
                	</select>
				</div>
			 </div>
			 <!-- ========================搜索按钮=============================== -->
            <button id="btn_search" type="button" class="btn btn-default">
            	<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
            </button>
	  	</div>
        
        <table id="table_user"></table>
		
	</div>
	
	<!-- 新增和修改对话框 -->
	<div class="modal fade" id="modal_user_edit" role="dialog" aria-labelledby="modal_user_edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<form id="form_user" method="post" action="reserveStudent.htm">
						<input type="hidden" name="sid" id="hidden_txt_userid" value=""/>
						<table style="border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td>姓名：</td>
								<td><input type="text" id="sname" name="sName"
									class="form-control" aria-required="true" required/></td>
								<td>&nbsp;&nbsp;</td>
								<td>性别：</td>
								<td><input type="radio" name="sex" value="男" checked="checked">男
								<input type="radio" name="sex" value="女">女</td>
							</tr>
							
							<tr>
								<td>爱好：</td>
								<td><input type="checkbox" name="hobby" value="篮球" >篮球
								<input type="checkbox" name="hobby" value="足球" >足球
								<input type="checkbox" name="hobby" value="排球" >排球</td>
							</tr>
							<tr>
								<td>专业：</td>
								<td colspan="4">
									<select class="form-control" name="maid" id = "maid" aria-required="true" required>
										<option value="">---请选择---</option>
										<c:forEach items="${majorList }" var="r">
										 	<option value="${r.maid }">${r.sName }</option>
										</c:forEach>
				                	</select>
								</td>
							</tr>
							
							<tr>
								<td>生日：</td>
								<td><input placeholder="请选择生日" id="birthday" name="birthday" class="laydate-icon form-control layer-date"/></td>
							</tr>
							<tr>
								<td>状态：</td>
								<td><select name="state" id="state">
								
									<option value="">--请选择--</option>
									<option value="学习">学习</option>
									<option value="毕业">毕业</option>
								</select></td>
							
							</tr>
						</table>
						
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"  id="submit_form_user_btn">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</form>

				</div>
				
			</div>

		</div>

	</div>
	
	
	<!-- 新增和修改对话框 -->
	<div class="modal fade" id="modal_user_in" role="dialog" aria-labelledby="modal_user_edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<form id="form_in" method="post" action="inStudent.htm" enctype="multipart/form-data">
						<table style="border-collapse:separate; border-spacing:0px 10px;">
							<tr>
							<td>
							<input type="file" name="file">
							</td>
							</tr>
						</table>
						
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"  id="submit_form_in_btn">导入</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</form>

				</div>
				
			</div>

		</div>

	</div>
	
	
	<!--删除对话框 -->
	<div class="modal fade" id="modal_user_del" role="dialog" aria-labelledby="modal_user_del" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					 <h4 class="modal-title" id="modal_user_del_head"> 刪除  </h4>
				</div>
				<div class="modal-body">
							删除所选记录？
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-danger"  id="del_user_btn">刪除</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	<!--删除对话框 -->
	<div class="modal fade" id="modal_user_zz" role="dialog" aria-labelledby="modal_user_del" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div id="main1" style="width: 600px;height:400px;"></div>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
			</div>
		</div>
	</div>
	<!--删除对话框 -->
	<div class="modal fade" id="modal_user_bb" role="dialog" aria-labelledby="modal_user_del" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div id="main2" style="width: 600px;height:400px;"></div>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
			</div>
		</div>
	</div>
	
	<!-- 错误的弹出框 -->
	<div class="ui-jqdialog modal-content" id="alertmod_table_user_mod"
		dir="ltr" role="dialog"
		aria-labelledby="alerthd_table_user" aria-hidden="true"
		style="width: 200px; height: auto; z-index: 2222; overflow: hidden;top: 274px; left: 534px; display: none;position: absolute;">
		<div class="ui-jqdialog-titlebar modal-header" id="alerthd_table_user"
			style="cursor: move;">
			<span class="ui-jqdialog-title" style="float: left;">注意</span> <a id ="alertmod_table_user_mod_a"
				class="ui-jqdialog-titlebar-close" style="right: 0.3em;"> <span
				class="glyphicon glyphicon-remove-circle"></span></a>
		</div>
		<div class="ui-jqdialog-content modal-body" id="alertcnt_table_user">
			<div id="select_message"></div>
			<span tabindex="0"> <span tabindex="-1" id="jqg_alrt"></span></span>
		</div>
		<div
			class="jqResize ui-resizable-handle ui-resizable-se glyphicon glyphicon-import"></div>
	</div>
	
	<!-- Peity-->
	<script src="${path }/resources/js/plugins/peity/jquery.peity.min.js"></script>
	
	<!-- Bootstrap table-->
	<script src="${path }/resources/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<script src="${path }/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

	<!-- 自定义js-->
	<script src="${path }/resources/js/content.js?v=1.0.0"></script>
	
	 <!-- jQuery Validation plugin javascript-->
    <script src="${path }/resources/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${path }/resources/js/plugins/validate/messages_zh.min.js"></script>
   
   	<!-- jQuery form  -->
    <script src="${path }/resources/js/jquery.form.min.js"></script>
    <!-- 时间插件的外部js文件 -->
    <script src="${path }/resources/js/plugins/layer/laydate/laydate.js"></script>
	<script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
	<script src="${path }/resources/js/echarts.js"></script>
    
	<script type="text/javascript">
	
	// 自定义的时间格式化类
	Date.prototype.Format = function (fmt) {
	    var o = {  
	        "M+": this.getMonth() + 1, //月份   
	        "d+": this.getDate(), //日   
	        "H+": this.getHours(), //小时   
	        "m+": this.getMinutes(), //分   
	        "s+": this.getSeconds(), //秒   
	        "S": this.getMilliseconds() //毫秒   
	    };  
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
	    for (var k in o)  
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
	    return fmt;  
	};
    //外部日期插件的js调用
    laydate({
        elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        format: 'YYYY-MM-DD'// 日期格式
    });
    laydate({
        elem: '#txt_search_start', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        format: 'YYYY-MM-DD'// 日期格式
    });
    laydate({
        elem: '#txt_search_end', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        format: 'YYYY-MM-DD'// 日期格式
    });
	// created：直接加载
	$(function () {
	    init();
	    // 绑定一个事件
	    $("#btn_search").bind("click",function(){
	    	//先销毁表格  
	        $('#table_user').bootstrapTable('destroy');
	    	init();
	    }); 
	    
	    // 表单在点击提交之后，并且还没有发送到后台之前
	    // 验证表单数据的方法
	    var validator = $("#form_user").validate({
    		// 提交处理器
	    	submitHandler: function(form){
    		  // ajaxSubmit 以ajax的方式进行表单提交
   		      $(form).ajaxSubmit({
   		    	dataType:"json",
   		    	success: function (data) {
   		    		// 如果你返回的信息中有success为true ， 并且没有errorMsg
   		    		if(data.success && !data.errorMsg ){
   		    			validator.resetForm();
   		    			// 隐藏添加的弹出框
   		    			$('#modal_user_edit').modal('hide');
   		    			// 刷新表格和页面
   		    			$("#btn_search").click();
   		    		}else{
   		    			$("#select_message").text(data.errorMsg);
   		    			$("#alertmod_table_user_mod").show();
   		    		}
                }
   		      });     
   		   }  
	    });
	    $("#submit_form_user_btn").click(function(){
	    	$("#form_user").submit();
	    });
	    
	    
	    // 表单在点击提交之后，并且还没有发送到后台之前
	    // 验证表单数据的方法
	    var validator1 = $("#form_in").validate({
    		// 提交处理器
	    	submitHandler: function(form){
    		  // ajaxSubmit 以ajax的方式进行表单提交
   		      $(form).ajaxSubmit({
   		    	dataType:"json",
   		    	success: function (data) {
   		    		// 如果你返回的信息中有success为true ， 并且没有errorMsg
   		    		if(data.success && !data.errorMsg ){
   		    			validator1.resetForm();
   		    			// 隐藏添加的弹出框
   		    			$('#modal_user_in').modal('hide');
   		    			// 刷新表格和页面
   		    			$("#btn_search").click();
   		    		}else{
   		    			$("#select_message").text(data.errorMsg);
   		    			$("#alertmod_table_user_mod").show();
   		    		}
                }
   		      });     
   		   }  
	    });
	    $("#submit_form_in_btn").click(function(){
	    	$("#form_in").submit();
	    });
	});
	
	var init = function () {
		//1.初始化Table
	    var oTable = new TableInit();
	    oTable.Init();
	    //2.初始化Button的点击事件
	    var oButtonInit = new ButtonInit();
	    oButtonInit.Init();
	};
	
	var TableInit = function () {
	    var oTableInit = new Object();
	    //初始化Table
	    oTableInit.Init = function () {
	    	// 自带ajax的表格构建方法，自带查询参数传递
	        $('#table_user').bootstrapTable({
	            url: 'studentList.htm',         //请求后台的URL（*）
	            method: 'post',                      //请求方式（*）
	            contentType : "application/x-www-form-urlencoded",
	            toolbar: '#toolbar',                //工具按钮用哪个容器
	            striped: true,                      //是否显示行间隔色
	            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	            pagination: true,                   //是否显示分页（*）
	            sortable: true,                     //是否启用排序
	            sortName: "sid",
	            sortOrder: "desc",                   //排序方式
	            queryParams: oTableInit.queryParams,//传递参数（*）
	            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	            pageNumber:1,                       //初始化加载第一页，默认第一页
	            pageSize: 10,                       //每页的记录行数（*）
	            pageList: [10, 25, 50, 75, 100],    //可供选择的每页的行数（*）
	            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	            strictSearch: true,
	            showColumns: true,                  //是否显示所有的列
	            showRefresh: false,                  //是否显示刷新按钮
	            minimumCountColumns: 2,             //最少允许的列数
	            clickToSelect: true,                //是否启用点击选中行
	           // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	            uniqueId: "sid",                     //每一行的唯一标识，一般为主键列
	            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
	            cardView: false,                    //是否显示详细视图
	            detailView: false,                   //是否显示父子表
	            // 列，表格中的td
	            columns: [{
	                checkbox: true
	            },
	            {
	                field: 'sid',
	                title: '学生编号',
	                sortable:true
	            },
	            {
	                field: 'sName',
	                title: '学生姓名',
	                sortable:true
	            },{
	                field: 'sex',
	                title: '学生性别',
	                sortable:true
	            },{
	                field: 'hobby',
	                title: '学生爱好',
	                sortable:true
	            }, {
	                field: 'birthday',
	                title: '生日',
	                sortable:true,
	                formatter:function(value,row,index){
	                	return new Date(value).Format('yyyy-MM-dd');
	                }
	            },{
	                field: 'state',
	                title: '学生状态',
	                sortable:true
	            }, {
	                field: 'maid',
	                title: '专业',
	                sortable:true,
	                formatter:function(value,row,index){
	                	return row.major.sName;
	                }
	            }],
	            onClickRow: function (row) {
	            	$("#alertmod_table_user_mod").hide();
	            }
	        });
	    };

	    //得到查询的参数
	    oTableInit.queryParams = function (params) {
	        var temp = {//这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
	            limit: params.limit,   //页面大小
	            offset: params.offset,  //页码
	            // 姓名  专业
	            sName: $("#txt_search_name").val(),
	            maid: $("#txt_search_maid").val(),
	            start:$("#txt_search_start").val(),
	            end:$("#txt_search_end").val()
	        };
	        return temp;
	    };
	    return oTableInit;
	};
	
	var ButtonInit = function () {
	    var oInit = new Object();
	    var postdata = {};

	    oInit.Init = function () {
	        //初始化页面上面的按钮事件
	    	$("#btn_add").click(function(){
	    		// 重置form表单中的内容
	    		$("#form_user").resetForm();
	    		// 将id也给清空了。
	    		document.getElementById("hidden_txt_userid").value='';
	    		// 让弹出框显示
	    		$('#modal_user_edit').modal({backdrop: 'static', keyboard: false});
				$('#modal_user_edit').modal('show');
	        });
	        
	    	$("#btn_update").click(function(){
	    		// 获取到被选中的条目 集合
	    		var getSelections = $('#table_user').bootstrapTable('getSelections');
	    		if(getSelections && getSelections.length==1){
	    			// 用于修改回显的方法  参数：被选中那一条数据的具体的值，对象
	    			initEditUser(getSelections[0]);
	    			// 让弹出框显示 -- 回显没做
	    			$('#modal_user_edit').modal({backdrop: 'static', keyboard: false});
					$('#modal_user_edit').modal('show');
	    		}else{
	    			$("#select_message").text("请选择其中一条数据");
	    			$("#alertmod_table_user_mod").show();
	    		}
	    		
	        });
	    	
	    	$("#btn_del").click(function(){
	    		var getSelections = $('#table_user').bootstrapTable('getSelections');
	    		if(getSelections && getSelections.length>0){
	    			$('#modal_user_del').modal({backdrop: 'static', keyboard: false});
	    			$("#modal_user_del").show();
	    		}else{
	    			$("#select_message").text("请选择数据");
	    			$("#alertmod_table_user_mod").show();
	    		}
	        });
	    	
	    	$("#btn_in").click(function(){
	    		$('#modal_user_in').modal({backdrop: 'static', keyboard: false});
				$('#modal_user_in').modal('show');
	    	});
	    	
	    	$("#btn_out").click(function(){
	    		// 直接请求后台
	    		$.ajax({
	    		    url:"outStudent.htm",
	    		    dataType:"json",
	    		    type:"post",
	    		    success:function(res){
	    		    	if(res.success){
	    	    			alert("导出成功！");
	    	    		}else{
	    	    			$("#select_message").text(res.errorMsg);
	    	    			$("#alertmod_table_user_mod").show();
	    	    		}
	    		    }
	    		});
	    		
	    	});
	    	$("#btn_zz").click(function(){
	    		$('#modal_user_zz').modal({backdrop: 'static', keyboard: false});
    			$("#modal_user_zz").show();
    			// 基于准备好的dom，初始化echarts实例
    			var myChart = echarts.init(document.getElementById('main1'));

    			// 指定图表的配置项和数据 config
    			var option = {
    			    title: { // 图标的标题
    			        text: 'ECharts 入门示例'
    			    },
    			    tooltip: {}, // 悬浮框
    			    legend: { // 上部用于表示具体颜色代表什么内容的一个小标题
    			        data:['销量']
    			    },
    			    xAxis: {// X轴的内容
    			        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
    			    },
    			    yAxis: {},// Y轴的内容
    			    series: [{// 数据
    			        name: '销量',
    			        type: 'bar',
    			        data: [5, 20, 36, 10, 10, 20]
    			    }]
    			};

    			// 使用刚指定的配置项和数据显示图表。
    			myChart.setOption(option);

	    	});
	        
	    	$("#btn_bb").click(function(){
	    		$('#modal_user_bb').modal({backdrop: 'static', keyboard: false});
    			$("#modal_user_bb").show();
    			// 基于准备好的dom，初始化echarts实例
    			var myChart = echarts.init(document.getElementById('main2'));

    			// 指定图表的配置项和数据 config
    			var option = {
    			    title: {
    			        text: '某站点用户访问来源',
    			        subtext: '纯属虚构',
    			        left: 'center'
    			    },
    			    tooltip: {
    			        trigger: 'item',
    			        formatter: '{a} <br/>{b} : {c} ({d}%)'
    			    },
    			    legend: {
    			        orient: 'vertical',// 排布顺序
    			        left: 'left',
    			        data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
    			    },
    			    series: [
    			        {
    			            name: '访问来源',
    			            type: 'pie',
    			            radius: '55%',
    			            center: ['50%', '60%'],
    			            data: [
    			                {value: 200, name: '304'},
    			                {value: 310, name: '邮件营销'},
    			                {value: 234, name: '联盟广告'},
    			                {value: 135, name: '视频广告'},
    			                {value: 1548, name: '搜索引擎'}
    			            ],
    			            emphasis: {
    			                itemStyle: {
    			                    shadowBlur: 10,
    			                    shadowOffsetX: 0,
    			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
    			                }
    			            }
    			        }
    			    ]
    			};

    			// 使用刚指定的配置项和数据显示图表。
    			myChart.setOption(option);
	    	});
	        
	        
	    };

	    return oInit;
	};
	
	$("#alertmod_table_user_mod_a").click(function(){
		$("#alertmod_table_user_mod").hide();
	});
	
	// 用于修改的回显
	function initEditUser(getSelection){
		// 重置一下form表单
		$("#form_user").resetForm();
		// 修改必须把id进行回显
		$('#hidden_txt_userid').val(getSelection.sid);
		$("#sname").val(getSelection.sName);
		$("#maid").val(getSelection.maid);
		$("#birthday").val(new Date(getSelection.birthday).Format("yyyy-MM-dd"));
		$("#state").val(getSelection.state);
		
		// 性别回显
		$("input[value='"+getSelection.sex+"']").prop("checked",true);
		
		// 多选框
		var hobbys = getSelection.hobby.split(",");
		for(var i = 0 ;i<hobbys.length;i++){
			$("input[value='"+hobbys[i]+"']").prop("checked",true);
		}
	}
	
	$("#del_user_btn").click(function(){
		// 获取所有被选中的条目
		var getSelections = $('#table_user').bootstrapTable('getSelections');
		// 将所有的被选中的id，存入此数组中
		var idArr = new Array();
		var ids;
		getSelections.forEach(function(item){
			idArr.push(item.sid);
		});
		// 将数组，加入分隔符，转变成字符串
		ids = idArr.join(",");
		$.ajax({
		    url:"deleteStudent.htm",
		    dataType:"json",
		    data:{"ids":ids},
		    type:"post",
		    success:function(res){
		    	if(res.success){
	    			$('#modal_user_del').modal('hide');
	    			$("#btn_search").click();
	    		}else{
	    			$("#select_message").text(res.errorMsg);
	    			$("#alertmod_table_user_mod").show();
	    		}
		    }
		});
	});
	</script>

</body>
</html>