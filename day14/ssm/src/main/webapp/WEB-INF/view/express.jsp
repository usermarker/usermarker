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
		<div id="toolbar" class="btn-group">
			<c:forEach items="${operationList}" var="oper">
				<privilege:operation operationId="${oper.operationid }" id="${oper.operationcode }" name="${oper.operationname }" clazz="${oper.iconcls }"  color="#093F4D"></privilege:operation>
			</c:forEach>
        </div>
        <div class="row">
			  <div class="col-lg-2">
				<div class="input-group">
			      <span class="input-group-addon">员工姓名 </span>
			      <input type="text" name="expressName" class="form-control" id="txt_search_username" >
				</div>
			  </div>
			  <div class="col-lg-2">
				<div class="input-group">
					<span class="input-group-addon">所属公司</span>
					<select class="form-control" name="expressTypeId" id = "txt_search_roleid">
						<option value="0">---请选择---</option>
						<c:forEach items="${clist }" var="c">
						 	<option value="${c.id}">${c.expressName }</option>
						</c:forEach>
                	</select>
				</div>
			 </div>
			 <div class="form-horizontal m-t">			  
			  <div class="form-group col-lg-7">
                 <label class="col-sm-2 control-label">入职时间</label>
                 <div class="col-sm-8">
                     <input placeholder="开始时间" id="txt_search_start" name="start" class="laydate-icon form-control layer-date"/>
                     <input placeholder="结束时间"id="txt_search_end" name="end" class="laydate-icon form-control layer-date"/>
                 </div>
	          </div>
		  	</div>
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
					<form id="form_user" method="post" action="reserveExpress.htm">
						<input type="hidden" name="id" id="hidden_txt_userid" value=""/>
						<table style="border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td>员工姓名：</td>
								<td><input type="text" id="expressName" name="expressName"
									class="form-control" aria-required="true" required/></td>
								<td>&nbsp;&nbsp;</td>
								<td>性别：</td>
								<td><input type="radio" id="sex" name="sex"  value="男" aria-required="true" required/>男
									<input type="radio" id="sex" name="sex"  value="女" aria-required="true" required/>女
									</td>
							</tr>
							<tr>
								<td>员工特点：</td>
								<td>
									<input type="checkbox" id="expressTrait1" name="expressTrait"  aria-required="true" required value="耐心"/>耐心
									<input type="checkbox" id="expressTrait2" name="expressTrait"  aria-required="true" required value="勤劳"/>勤劳
									<input type="checkbox" id="expressTrait3" name="expressTrait"  aria-required="true" required value="负责"/>负责
								</td>
								<td>&nbsp;&nbsp;</td>
								<td>所属公司：</td>
								<td colspan="4">
									<select class="form-control" name="expressTypeId" id = "expressTypeId" aria-required="true" required>
										<option value="">---请选择---</option>
										<c:forEach items="${clist }" var="c">
										 	<option value="${c.id }">${c.expressName }</option>
										</c:forEach>
				                	</select>
								</td>
							</tr>
							<tr>
								<td>入职时间：</td>
								<td><input placeholder="入职时间" id="entryTime" name="entryTime" class="laydate-icon form-control layer-date"/></td>
								<td>&nbsp;&nbsp;</td>
								<td>创建时间：</td>
								<td>
									<input placeholder="创建时间" id="createTime" name="createTime" class="laydate-icon form-control layer-date"/>
								</td>
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
	<!--报表对话框 -->
	<div class="modal fade" id="modal_user_echarts" role="dialog" aria-labelledby="modal_user_echarts" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					 <h4 class="modal-title" id="modal_user_del_head"> 报表  </h4>
				</div>
				<div class="modal-body">
					<div id="main" style="width: 600px;height: 300px"></div>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	<!--导入对话框 -->
	<div class="modal fade" id="modal_user_import" role="dialog" aria-labelledby="modal_user_import" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					 <h4 class="modal-title" id="modal_user_del_head"> 导入 </h4>
				</div>
				<div class="modal-body">
						<form id="expressId" action="importExpress.htm" method="post" enctype="multipart/form-data">
							<input type="file" name="expressXls" id="expressXls">
						</form>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-danger"  id="import_user_btn">导入</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	
	<!--导出对话框 -->
	<div class="modal fade" id="modal_user_export" role="dialog" aria-labelledby="modal_user_export" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					 <h4 class="modal-title" id="modal_user_del_head"> 导出  </h4>
				</div>
				<div class="modal-body">
							导出所有记录？
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-danger"  id="export_user_btn">导出</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	
	
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
    <!-- layerDate plugin javascript -->
	<script src="${path }/resources/js/plugins/layer/laydate/laydate.js"></script>
	<script type="text/javascript">
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

    //外部js调用
    laydate({
        elem: '#txt_search_start', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        format: 'YYYY-MM-DD'// 日期格式
    });
    
    laydate({
        elem: '#txt_search_end',
        event: 'focus',
        format: 'YYYY-MM-DD'
    });
    //外部js调用
    laydate({
        elem: '#entryTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        format: 'YYYY-MM-DD'// 日期格式
    });
    
    laydate({
        elem: '#createTime',
        event: 'focus',
        format: 'YYYY-MM-DD'
    });
	$(function () {
	    init();
	    $("#btn_search").bind("click",function(){
	    	//先销毁表格  
	        $('#table_user').bootstrapTable('destroy');
	    	init();
	    }); 
	    var validator = $("#form_user").validate({
    		submitHandler: function(form){
   		      $(form).ajaxSubmit({
   		    	dataType:"json",
   		    	success: function (data) {
   		    		
   		    		if(data.success && !data.errorMsg ){
   		    			validator.resetForm();
   		    			$('#modal_user_edit').modal('hide');
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
	    //导入
	    var validator = $("#expressId").validate({
    		submitHandler: function(form){
   		      $(form).ajaxSubmit({
   		    	dataType:"json",
   		    	success: function (data) {
   		    		if(data.success && !data.errorMsg ){
   		    			validator.resetForm();
   		    			$('#modal_user_import').modal('hide');
   		    			$("#btn_search").click();
   		    		}else{
   		    			$("#select_message").text(data.errorMsg);
   		    			$("#alertmod_table_user_mod").show();
   		    		}
                }
   		      });     
   		   }  
	    });
	    $("#import_user_btn").click(function(){
	    	$("#expressId").submit();
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
	        $('#table_user').bootstrapTable({
	            url: 'expressList.htm',         //请求后台的URL（*）
	            method: 'post',                      //请求方式（*）
	            contentType : "application/x-www-form-urlencoded",
	            toolbar: '#toolbar',                //工具按钮用哪个容器
	            striped: true,                      //是否显示行间隔色
	            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	            pagination: true,                   //是否显示分页（*）
	            sortable: true,                     //是否启用排序
	            sortName: "userid",
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
	            uniqueId: "userid",                     //每一行的唯一标识，一般为主键列
	            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
	            cardView: false,                    //是否显示详细视图
	            detailView: false,                   //是否显示父子表
	            columns: [{
	                checkbox: true
	            },
	            {
	                field: 'id',
	                title: '员工编号',
	                sortable:true
	            },
	            {
	                field: 'expressName',
	                title: '员工姓名',
	                sortable:true
	            },
	            {
	                field: 'sex',
	                title: '员工性别',
	            },
	            {
	                field: 'expressTrait',
	                title: '员工特点',
	            }, {
	                field: 'entryTime',
	                title: '入职时间',
	                sortable:true,
	                formatter:function(value,row,index){
	                	return new Date(value).Format("yyyy-MM-dd");
	                }
	            }, {
	                field: 'createTime',
	                title: '创建时间',
	                sortable:true,
	                formatter:function(value,row,index){
	                	return new Date(value).Format("yyyy-MM-dd");
	                }
	            },
	            {
	                field: 'cname',
	                title: '公司名称',
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
	            expressName: $("#txt_search_username").val(),
	            expressTypeId: $("#txt_search_roleid").val(),
	            start: $("#txt_search_start").val(),
	            end: $("#txt_search_end").val(),
	            search:params.search,
	            order: params.order,
	            ordername: params.sort
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
	    		$('#password').attr("readOnly",false).val(getSelection.password);
	    		$("#form_user").resetForm();
	    		document.getElementById("hidden_txt_userid").value='';
	    		$('#modal_user_edit').modal({backdrop: 'static', keyboard: false});
				$('#modal_user_edit').modal('show');
	        });
	        
	    	$("#btn_edit").click(function(){
	    		var getSelections = $('#table_user').bootstrapTable('getSelections');
	    		if(getSelections && getSelections.length==1){
	    			initEditUser(getSelections[0]);
	    			$('#modal_user_edit').modal({backdrop: 'static', keyboard: false});
					$('#modal_user_edit').modal('show');
	    		}else{
	    			$("#select_message").text("请选择其中一条数据");
	    			$("#alertmod_table_user_mod").show();
	    		}
	    		
	        });
	    	
	    	$("#btn_delete").click(function(){
	    		var getSelections = $('#table_user').bootstrapTable('getSelections');
	    		if(getSelections && getSelections.length>0){
	    			$('#modal_user_del').modal({backdrop: 'static', keyboard: false});
	    			$("#modal_user_del").show();
	    		}else{
	    			$("#select_message").text("请选择数据");
	    			$("#alertmod_table_user_mod").show();
	    		}
	        });
	    	//报表弹框
	    	$("#btn_echarts").click(function(){
	    		var myEcharts = echarts.init(document.getElementById("main"));
	    		
    			$('#modal_user_echarts').modal({backdrop: 'static', keyboard: false});
    			$("#modal_user_echarts").show();
    			$.ajax({
    			    url:"getEcharts.htm",
    			    dataType:"json",
    			    type:"post",
    			    success:function(res){
    			    	var xdata = new Array();
    			    	var ydata = new Array();
    			    	if(res.success){
    		    			//循环集合
    		    			res.elist.forEach(function(express){
    		    				xdata.push(express.cname);
    		    				ydata.push({value:express.num,name:express.cname});
    		    			});
    		    			//设置图表  饼状图
    		    			option = {
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
	    			    		        orient: 'vertical',
	    			    		        left: 'left',
	    			    		        data: xdata
	    			    		    },
	    			    		    series: [
	    			    		        {
	    			    		            name: '访问来源',
	    			    		            type: 'pie',
	    			    		            radius: '55%',
	    			    		            center: ['50%', '60%'],
	    			    		            data: ydata,
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
    			    		
    		    		}
    			    	//设置echarts图表
    			    	myEcharts.setOption(option);
    			    	
    			    }
    			});
	    		
	        });
	    	//导入弹框
	    	$("#btn_import").click(function(){
    			$('#modal_user_import').modal({backdrop: 'static', keyboard: false});
    			$("#modal_user_import").show();
	        });
	    	//导出弹框
	    	$("#btn_export").click(function(){
    			$('#modal_user_export').modal({backdrop: 'static', keyboard: false});
    			$("#modal_user_export").show();
	        });
	        
	        
	    };

	    return oInit;
	};
	
	$("#alertmod_table_user_mod_a").click(function(){
		$("#alertmod_table_user_mod").hide();
	});
	
	function initEditUser(getSelection){
		$('#hidden_txt_userid').val(getSelection.id);//员工id
		$('#expressTypeId').val(getSelection.expressTypeId);//公司id
		$('#expressName').val(getSelection.expressName);//员工姓名
		$("input[value='"+getSelection.sex+"']").prop("checked",true);//性别     勤劳，耐心，负责
		$("#expressTrait1").prop("checked",getSelection.expressTrait.indexOf("耐心")!=-1);
		$("#expressTrait2").prop("checked",getSelection.expressTrait.indexOf("勤劳")!=-1);
		$("#expressTrait3").prop("checked",getSelection.expressTrait.indexOf("负责")!=-1);
		$('#entryTime').val(new Date(getSelection.entryTime).Format("yyyy-MM-dd"));
		$('#createTime').val(new Date(getSelection.createTime).Format("yyyy-MM-dd"));
	}
	
	$("#del_user_btn").click(function(){
		var getSelections = $('#table_user').bootstrapTable('getSelections');
		var idArr = new Array();
		var ids;
		getSelections.forEach(function(item){
			idArr.push(item.id);
		});
		ids = idArr.join(",");
		$.ajax({
		    url:"deleteExpress.htm",
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
	//导出发送请求
	$("#export_user_btn").click(function(){
		//发送请求
		location.href="exportExpress.htm";
	    $('#modal_user_export').modal('hide');
	});
	</script>

</body>
</html>