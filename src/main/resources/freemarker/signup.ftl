<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Регистрация</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.0.3.js"></script>

</head>

<body>
<div class="container">
    <form class="form-signin" method="post" action="/signup">
        Уже зарегистрированы? <a href="/login">Войти</a>

        <h2>Регистрация</h2>

        <div class="control-group ${username_error!""}">
            <input class="input-block-level" type="text" name="username" value="${username}" placeholder="логин"/>
        <#if username_error = 'error'>
            <span class="help-inline">${error_message}</span>
        </#if>
        </div>
        <input class="input-block-level" type="password" name="password" value="" placeholder="пароль"/>
        <input class="input-block-level" type="password" name="verify" value="" placeholder="пароль еще раз"/>
        <input class="input-block-level" type="text" name="email" value="${email}" placeholder="email (опционально)"/>
        <button class="btn-primary" type="submit"> Зарегистрироваться</button>
    </form>
</div>
</body>

</html>
