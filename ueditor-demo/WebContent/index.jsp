<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>ueditor-demo</title>
    <meta http-equiv="Content-Type" content="text/html charset=gb2312">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
    <script src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
    <script src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <style>
        #submit {
            width: 100px;
            height: 30px;
            line-height: 30px;
            font-size: 16px;
        }
    </style>
</head>
<body>
<h2>ueditor测试使用</h2>
<script id="editor" type="text/plain"></script>
<div style="margin-top: 20px; text-align: center;">
    <input type="button" class="btn btn-blue w-100" value="提 交" id="submit">
</div>

<script>

    $(function () {
        //实例化编辑器
        var ue = UE.getEditor('editor',{
            initialFrameWidth:"100%",   //初始化宽度
            initialFrameHeight:400,     //初始化高度
        });

        $('#submit').click(function () {
            //获取ueditor编辑框中的html文本内容
            var content = UE.getEditor('editor').getContent();
            /*$.ajax({
                url: 'http://172.16.4.160:8081/ssm_project/news/addNews.do',
                type: 'POST',
                data: {
                    content: content,
                },
                dataType: 'json',
                success: function (res) {
                    console.log(res);
                },
                error: function () {
                    console.log(res);
                }
            })*/
        })
    })

</script>
</body>

</html>