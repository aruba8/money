<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Вход</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.0.3.js"></script>


</head>

<body>

<div class="container">


    <form class="form-signin" method="post" action="/login">
        Нужен аккаунт? <a href="/signup">Зарегистрироваться</a>

        <h2 class="form-signin-heading">Вход</h2>
        <input class="input-block-level" type="text" name="username" value="${username}" placeholder="логин"/>
        <input class="input-block-level" type="password" name="password" value="" placeholder="пароль"/>
        <button class="btn-primary" type="submit">Войти</button>
    </form>
</div>

</body>

</html>
