<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/21 0021
  Time: 下午 6:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>欢迎页面-天天新闻1.0</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <%@include file="../commons/info.jsp"%>

</head>

<body>
<div class="x-body">
    <form class="layui-form">
        <%--<input type="hidden" id="typeId" name="typeId" required="" autocomplete="off" class="layui-input">--%>
        <div class="layui-form-item">
            <label for="typeName" class="layui-form-label">
                <span class="x-red">*</span>新闻类别
            </label>
            <div class="layui-input-inline">
                <input type="text" id="typeName" name="typeName" required="" lay-verify="required" placeholder="请输入类型"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="submitSave">保存</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<script>
    layui.use(['form','layer'], function(){
        $ = layui.jquery;
        var form = layui.form
            ,layer = layui.layer;
        //监听提交
        form.on('submit(submitSave)', function(data){
            var typeName = data.field.typeName;
            $.ajax({
                type:"post",
                url:"<%=request.getContextPath()%>/NewsTypeServlet",
                data:{
                    "action":"add",
                    "typeName":typeName
                },
                success:function (msg) {
                    if(msg=="1"){
                        //发异步，把数据提交给java
                        layer.alert("添加成功", {icon: 6},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                            window.parent.location.reload();
                        });
                    }else {
                        layer.msg("添加失败",{icon:2,time:2000})
                    }
                },
                error:function () {
                    layer.msg("添加异常")
                }
            })
            return false;
        });
    });
</script>
</body>
</html>
