<%--
  Created by IntelliJ IDEA.
  User: Geng xing
  Date: 2019/3/23
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../commons/info.jsp"%>
<html>
<head>
    <title>新闻信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />

    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="#"><cite>首页</cite></a>
        <a href="#"><cite>新闻信息维护</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <table class="layui-hide" id="test" lay-filter="test"></table>

    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delAll"><i class="layui-icon"></i>批量删除</button>
            <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
            <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        </div>
    </script>

    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
    <script type="text/html" id="publishTime">
        {{ dateFormat(d.publishDate) }}
    </script>
</div>
<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#test'
            ,url:'<%=request.getContextPath()%>/NewsServlet?action=queryPage'
            ,toolbar: '#toolbarDemo'
            ,title: '新闻信息表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'newsId', title:'新闻编号', width:'6%', fixed: 'left', unresize: true, sort: true}
                ,{field:'typeId', title:'类别编号', width:'6%', fixed: 'left', unresize: true, sort: true}
                ,{field:'typeName', title:'新闻类别', width:'7%', edit: 'text', sort: true}
                ,{field:'title', title:'标题', width:'11%'}
                ,{field:'author', title:'作者', width:'7%', sort: true}
                ,{field:'context', title:'内容'}
                ,{field:'publishDate', title:'发布时间', width:'10%', sort: true,templet:'#publishTime'}
                ,{field:'isImage', title:'图片新闻',  width:'6%',sort: true}
                ,{field:'imageUrl', title:'图片路径', width:'8%'}
                ,{field:'click', title:'访问次数', width:'6%', sort: true}
                ,{field:'isHot', title:'热点新闻', width:'6%', sort: true}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'8%'}
            ]]
            ,page: true
        });

        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch(obj.event){
                case 'getCheckData':
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'delAll':
                    data=checkStatus.data
                    if(data==""){
                        layer.msg('请至少选择1条数据',{icon:2});
                        return;
                    }
                    var ids="";
                    if(data.length>0){
                        for (var i=0;i<data.length;i++) {
                            ids+=data[i].newsId+","
                        }
                    }
                    layer.confirm('确认要删除这些信息吗？',function(index){
                        $.ajax({
                            type:"post",
                            url:"<%=request.getContextPath()%>/NewsServlet",
                            data:"action=deleteAll&ids="+ids,
                            success:function (msg) {
                                $(".layui-form-checked").not('.header').parents('tr').remove();
                                if(msg>0){
                                    //捉到所有被选中的，发异步进行删除
                                    layer.msg('成功删除'+msg+'条数据', {icon: 1,time:1000});
                                }else {
                                    layer.msg('已删除或不存在', {icon: 1});
                                }
                                setTimeout("location.reload()",1000)
                            }
                        })
                    });
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    layer.close(index);
                    $.ajax({
                        type:"post",
                        url:"<%=request.getContextPath()%>/NewsServlet",
                        data:"action=delete&newsId="+data.newsId,
                        success:function (msg) {
                            obj.del();
                            if(msg==1){
                                layer.msg('文章删除成功',{icon:1,time:2000});
                            }else {
                                layer.msg('文章已删除或不存在',{icon:2,time:2000});
                            }
                        }
                    })
                });
            }else if(obj.event === 'edit'){
                layer.open({
                    title: '信息修改',
                    type: 2,
                    closeBtn: 1, //不显示关闭按钮
                    area: ['1000px', '700px'],
                    offset: 'auto', //右下角弹出
                    anim: 2,
                    content: '<%=request.getContextPath()%>/NewsServlet?action=queryOneBack&newsId='+data.newsId
                })
            }
        });
    });
</script>
</body>
</html>