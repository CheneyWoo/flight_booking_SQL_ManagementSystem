var module="flight";
var sub="file";

jQuery(document).ready(function(){
    Record.init();
    Page.init();
});

var Record = function() {
    var userId=undefined;
    var userName=undefined;
    var userRole=undefined;
    var initRecordList = function(){
        getRecord();
    };
    var getRecord = function(){
        var srcPlace = localStorage["srcPlace"];
        var dstPlace = localStorage["dstPlace"];
        var srcDate = localStorage["srcDate"];
        var url="flight_file_servlet_action?action=get_record&srcPlace="+srcPlace+"&dstPlace="+dstPlace+"&srcDate="+srcDate;
        $.ajax({
            type:"get",
            url:url,
            dataType:'json',
            success:function (json) {
                Page.showResult(json);
            }
        });
    };

    var deleteRecord = function(flightID,srcDate,srcTime){
        if(confirm("您确定要删除此记录吗？")){
            var url="flight_file_servlet_action?action=delete_record&flightID="+flightID+"&srcDate="+srcDate+"&srcTime="+srcTime;
            $.ajax({
                type:"post",
                url:url,
                dataType:'json',
                success:function (json) {
                    if(json.result_code == 1){
                        alert("删除成功！");
                    }
                }
            });
        }
    };

    return {
        init: function() {
            initRecordList();
        },
        deleteRecord:function(id){
            deleteRecord(id);
        },
        search:function(){
            search();
        }
    };
}();

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
            if($("#record_list_tip"))
                $("#record_list_tip").html(tip);
            showRecordList(list);
            var userRole=$("#userRole").val();
            if(userRole=="admin"){
                var html_add="";
                html_add=html_add+"            <div class=\"p-table\">";
                html_add=html_add+"                        <div class=\"p-table-grids\">";
                html_add=html_add+"                            <div style=\"text-align:center;\" id=\"addicon\"><a href=\"ticket_add_div.jsp\"><img src=\"images/add.png\" style=\"height:40px;width:40px\"></a></div>";
                html_add=html_add+"                        </div> ";
                html_add=html_add+"            </div>";
                $("#add").html(html_add);
            }
        }
    };
    var showRecordList=function(list){
        for(var i=0;i<list.length;i++){
            showRecord(list[i]);
        }
        $("#record_list_div").html(html);
    };
    var showRecord = function(json){
        var company=json[6];
        var flightID=json[0];
        var dstTime=json[5];
        var srcTime=json[4];
        var srcAirport=json[9];
        var dstAirport=json[10];
        var price=json[3];
        var srcDate=json[1];
        var dstDate=json[2];
        var userRole=$("#userRole").val();
        //var userRole="admin";
        html=html+"            <div class=\"p-table\">";
        html=html+"                        <div class=\"p-table-grids\">";
        html=html+"                            <div class=\"col-md-3 p-table-grid\">";
        html=html+"                                <div class=\"p-table-grad-heading\">";
        html=html+"                                    <h6>航班信息</h6>";
        html=html+"                                </div>";
        html=html+"                                <div class=\"p-table-grid-info\">";
        html=html+"                                    <a href=\"#\"><img src=\"images/flight.png\" alt=\"\" style=\"width:40px;height:40px\"></a>";
        html=html+"                                    <div class=\"room-basic-info\">";
        html=html+"                                        <h6>" + company + "</h6>";
        html=html+"                                        <p>" + flightID + "</p>";
        html=html+"                                    </div>";
        html=html+"                                </div>";
        html=html+"                            </div>";
        html=html+"                            <div class=\"col-md-3 p-table-grid\">";
        html=html+"                                <div class=\"p-table-grad-heading\">";
        html=html+"                                    <h6>出发/到达时间</h6>";
        html=html+"                                </div>";
        html=html+"                                <div class=\"rate-features\">";
        html=html+"                                    <div style=\"margin:5px; font-size: 14px; color: rgb(125, 117, 117); display:inline-block;\">" + srcAirport + "</div>";
        html=html+"                                    <div style=\"margin:5px; font-size: 14px; color: rgb(125, 117, 117); display:inline-block;\">" + srcDate + "</div>";
        html=html+"                                    <div style=\"margin:5px; font-size: 14px; color: rgb(125, 117, 117); display:inline-block;\">" + srcTime + "</div>";
        html=html+"                                    <div><img style=\"width:20px; height: 30px; margin-left:35px;\" src=\"images/arrow.png\"></div>";
        html=html+"                                    <div style=\"margin:5px; font-size: 14px; color: rgb(125, 117, 117); display:inline-block;\">" + dstAirport + "</div>";
        html=html+"                                    <div style=\"margin:5px; font-size: 14px; color: rgb(125, 117, 117); display:inline-block;\">" + dstDate + "</div>";
        html=html+"                                    <div style=\"margin:5px; font-size: 14px; color: rgb(125, 117, 117); display:inline-block;\">" + dstTime + "</div>";
        html=html+"                                </div>";
        html=html+"                            </div>";
        html=html+"                            <div class=\"col-md-3 p-table-grid\">"
        html=html+"                                <div class=\"p-table-grad-heading\">";
        html=html+"                                    <h6>价格</h6>";
        html=html+"                                </div>";
        html=html+"                                <div class=\"avg-rate\">";
        html=html+"                                    <p>Price is now:</p>";
        html=html+"                                    <span class=\"p-offer\">" + price + "</span>";
        html=html+"                                </div>";
        html=html+"                            </div>";
        html=html+"                            <div class=\"col-md-3 p-table-grid\">";
        html=html+"                                <div class=\"p-table-grad-heading\">";
        html=html+"                                    <h6>预订</h6>";
        html=html+"                                </div>";
        html=html+"                                <div class=\"book-button-column\">";
        if(userRole=="normal"){
            html=html+"                                    <button type=\"button\" id=\"reserve_button\" style=\"background:#6fd508;color:white;padding:.5em 1em;font-size:1em;border:none\" title=\"预定\" onclick=\"Page.reserveRecord('" + flightID + "','" + srcDate + "','" + srcTime + "');\">预订</button>";
        }
        if(userRole=="admin"){
            html=html+"                                    <button type=\"button\" id=\"delete_button\" style=\"background:#f00;color:white;padding:.5em 1em;font-size:1em;border:none\" title=\"删除\" onclick=\"Page.deleteRecord('" + flightID + "','" + srcDate + "','" + srcTime + "');\">删除</button>";
            html=html+"                                    <button type=\"button\" id=\"modify_button\" style=\"background:#f00;color:white;padding:.5em 1em;font-size:1em;border:none\" title=\"修改\" onclick=\"Page.modifyRecord('" + json + "');\">修改</button>";
        }
        html=html+"                                </div>";
        html=html+"                            </div>";
        html=html+"                            <div class=\"clearfix\"> </div>";
        html=html+"                        </div> ";
        html=html+"                    </div>";
        
    };

    var modifyRecord=function(json){
        localStorage.flightID = flightID;
        localStorage.srcDate = srcDate;
        localStorage.dstDate = dstDate;
        localStorage.price = price;
        localStorage.srcTime = srcTime;
        localStorage.dstTime = dstTime;
        localStorage.srcAirport = srcAirport;
        localStorage.dstAirport = dstAirport;
        localStorage.company = company;
        localStorage.srcPlace = json[7];
        localStorage.dstPlace = json[8];
        window.open("ticket_modify_div.jsp");
    }

    var submitRecord=function(){
        if(checkInput()==true){
            page_form.action="flight_file_servlet_action";
            page_form.submit();
        }
    };
    function reserveRecord(flightID,srcDate,srcTime){
        var orderID="";
        for(var i=0;i<10;i++){
            orderID+=(parseInt)(Math.random()*10);
        }
        var valid=1;
        var used=0;
        $.ajax({
            type: "POST",
            url: 'order_file_servlet_action',
            dataType: "json",
            data:{orderID:orderID,flightID:flightID,srcDate:srcDate,srcTime:srcTime,valid:valid,used:used},
            success: function (json) {
                if (json.flag == 1) {
                    alert("预定成功！");
                }
                else{
                    alert("请先登录！");
                }
            },
            error: function (message) {
                alert("error");
            }
        });
    };

    var checkInput=function(){
        var bOk=true;
        var action=$("#action").val();
        if(action==null || action==""){Frame.showMsg("名称不能为空！");bOk=false;};
        return bOk;
    };
    var deleteRecord=function(flightID,srcDate,srcTime){
        Record.deleteRecord(flightID,srcDate,srcTime);
    };
    var confirmBack=function(){
        DraggableDialog.setId("confirm_back");
        DraggableDialog.setdelete(Page.ondelete);
        DraggableDialog.setButtonTitle("确定","取消");
        DraggableDialog.setOk(Page.returnBack);
        DraggableDialog.showMsg("确定要返回上一页吗？","提示");
    };
    var ondelete=function(){
        DraggableDialog.close();
    }
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
        deleteRecord:function(flightID,srcDate,srcTime){
            deleteRecord(flightID,srcDate,srcTime);
        },
        modifyRecord:function(json){
            modifyRecord(json);
        },
        reload:function(){
            window.location.reload();
        },
        confirmBack:function(){
            confirmBack();
        },
        reserveRecord:function(flightID,srcDate,srcTime){
            reserveRecord(flightID,srcDate,srcTime);
        },
        returnBack:function(){
            returnBack();
        }
    }
}();