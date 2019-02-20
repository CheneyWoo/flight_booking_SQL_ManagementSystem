var module="user";
var sub="file";
jQuery(document).ready(function() {
    Record.init();
	Page.init();
});
var Record = function() {
	var initRecordStyle = function() {
	};
	var initRecordList = function(){
		getRecord();
	}
	var getRecord = function(){
		var orderID=$("#orderID").val();
		var existResultset=$("#exist_resultset").val();
		var url="user_file_servlet_action?action=get_record&orderID="+orderID+"&exist_resultset="+existResultset;
        $.ajax({
            type:"get",
            url:url,
            dataType:'json',
            success:function (json) {
                Page.showResult(json);
            }
        });
	};

	var deleteRecord = function(orderID){
		if(confirm("您确定要取消此订单吗？")){
			if(orderID>-1){
				$.post("user_file_servlet_action?action=delete_record&orderID="+orderID,function(json){
					if(json.result_code == 1){
						initRecordList();
					}
				})
			}
		}
	};
	return {
		init: function() {
			initRecordList();
			initRecordStyle();
		},
		deleteRecord:function(orderID){
			deleteRecord(orderID);
		},
		search:function(){
			search();
		}
	};
}();

Record.init();

var Page = function() {
	var html="";
	var processError=function(json){
		if(Frame!=null)
			Frame.processError(json);
	};
	var showResult=function(json){
		if(json!=null){
			var list=json.aaData;
			var tip="当前查询到了 "+list.length+" 条订单";
			//if($("#tip_div")) $("#tip_div").html(tip);
			if($("#record_list_tip")) $("#record_list_tip").html(tip);
			showRecordList(list);
		}
	};
	var showRecordList=function(list){
		for(var i=0;i<list.length;i++){
			showRecord(list[i]);
		}
		$("#record_list_div").html(html);
	};
	var showRecord = function(json){
		var orderID=json[1];
		var flightID=json[2];
		var srcDate=json[3];
		var srcTime=json[4];
		var valid=json[5];
		var price=json[7];
		html=html+"																<div class=\"p-table\">";
		html=html+"																	<div class=\"p-table-grids\">";
		html=html+"																		<div class=\"col-md-3 p-table-grid\">";
		html=html+"																			<div class=\"p-table-grad-heading\">";
		html=html+"																				<h6>订单号</h6>";
		html=html+"																			</div>";
		html=html+"																		<div class=\"p-table-grid-info\">";
		html=html+"																			<div class=\"room-basic-info\" id=\"orderID\">";
		html=html+"																				<h6>"+orderID+"</h6>";
		html=html+"																			</div>";
		html=html+"																		</div>";
		html=html+"																	</div>";
		html=html+"																	<div class=\"col-md-3 p-table-grid\">";
		html=html+"																		<div class=\"p-table-grad-heading\">";
		html=html+"																			<h6 id=\"flight\">订单信息</h6>";
		html=html+"																		</div>";
		html=html+"																		<div class=\"rate-features\">";
		html=html+"																			<ul>";
		html=html+"																				<li id=\"flightID\">航班号："+flightID+"</li>";
		html=html+"																			<li id=\"srcDate\">出发日期："+srcDate+"</li>";
		html=html+"																				<li id=\"srcTime\">出发时间："+srcTime+"</li>";
		html=html+"																			</ul>";
		html=html+"																		</div>";
		html=html+"																	</div>";
		html=html+"																	<div class=\"col-md-3 p-table-grid\">";
		html=html+"																		<div class=\"p-table-grad-heading\">";
		html=html+"																			<h6>价格</h6>";
		html=html+"																		</div>";
		html=html+"																		<div class=\"avg-rate\">";
		html=html+"																			<span class=\"p-offer\" id=\"price\">"+price+"</span>";
		html=html+"																		</div>";
		html=html+"																	</div>";
		html=html+"																	<div class=\"col-md-3 p-table-grid\">";
		html=html+"																		<div class=\"p-table-grad-heading\">";
		html=html+"																			<h6>取消订单</h6>";
		html=html+"																		</div>";
		html=html+"																		<div class=\"book-button-column\">";
		if(valid==1){
			html=html+"																			<button type=\"button\" id=\"delete_button\" style=\"background:#f00;color:white;padding:.5em 1em;font-size:1em;border:none\" title=\"取消订单\" onclick=\"Page.deleteRecord("+orderID+");\">";
			html=html+"																				取消";
			html=html+"																			</button>";
		}
		else if(valid==0){
			html=html+"																			<button type=\"button\" id=\"delete_button\" style=\"background:grey;color:white;padding:.5em 1em;font-size:1em;border:none\" title=\"取消订单\" onclick=\"Page.deleteRecord("+orderID+");\" disabled=\"true\">";
			html=html+"																				已取消";
			html=html+"																			</button>";
		}
		html=html+"																		</div>";
		html=html+"																	</div>";
		html=html+"																	<div class='clearfix'> </div>";
		html=html+"																</div>";
		html=html+"															</div>";
	};
	var submitRecord=function(){
		if(checkInput()==true){
			page_form.action="../../"+module+"_"+sub+"_servlet_action";
			page_form.submit();
		}
	};
	var checkInput=function(){
		var bOk=true;
		var action=$("#action").val();
		if(action==null || action==""){Frame.showMsg("名称不能为空！");bOk=false;};
		return bOk;
	};
	var deleteRecord=function(orderID){
		Record.deleteRecord(orderID);
	};
	var confirmBack=function(){
		DraggableDialog.setId("confirm_back");
		DraggableDialog.setdelete(Page.ondelete);
		DraggableDialog.setButtonTitle("确定","取消");
		DraggableDialog.setOk(Page.returnBack);
		DraggableDialog.showMsg("确定要返回上一页吗？","提示");
	};
	var returnBack=function(){
		history.go(-1);
	};
	return {
		init: function() {
			initPageStyle();
			handleButtonEvent();
		},
		processError:function(json){
			processError(json);
		},
		showResult:function(json){
			showResult(json);
		},
		showRecordList:function(list){
			showRecordList(list);
		},
		submitRecord:function(){
			submitRecord();
		},
		deleteRecord:function(orderID){
			deleteRecord(orderID);
		},
		reload:function(){
			window.location.reload();
		},
		confirmBack:function(){
			confirmBack();
		},
		returnBack:function(){
			returnBack();
		},
	}
}();