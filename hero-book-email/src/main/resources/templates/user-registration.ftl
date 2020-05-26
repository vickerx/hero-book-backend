<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>邮件消息</title>
</head>
<style type="text/css">
    table {
        margin: auto;
        border: 1px solid gray;
    }
</style>
<body>
<table cellspacing="10px">
    <tr>
        <td width="20px"></td>
        <td>${username}，您好：</td>
        <td width="20px"></td>
    </tr>
    <tr>
        <td width="20px"></td>
        <td>感谢您注册Hero Stories，请点击下方按钮激活您的邮箱地址：</td>
        <td width="20px"></td>
    </tr>
    <tr>
        <td width="20px"></td>
        <td style="text-align: center">
            <a href="${activationLink}" target="_blank">前往激活</a>
        </td>
        <td width="20px"></td>
    </tr>
    <tr>
        <td width="20px"></td>
        <td width="435px">
            如果点击无效，请复制以下网址并粘贴到浏览器窗口中直接访问：${activationLink}
        </td>
        <td width="20px"></td>
    </tr>
    <tr>
        <td colspan="3" height="60px"></td>
    </tr>
</table>
</body>
</html>
