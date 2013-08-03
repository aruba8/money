<html xmlns="http://www.w3.org/1999/html" lang="ru-RU">
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
Уже зарегистрированы? <a href="/login">Войти</a>

<h2>Регистрация</h2>

<form method="post" action="/signup">
    <table>
        <tr>
            <td class="label">
                Логин
            </td>
            <td>
                <input type="text" name="username" value="${username}"/>
            </td>
            <td class="error">
            ${username_error!""}

            </td>
        </tr>

        <tr>
            <td class="label">
                Пароль
            </td>
            <td>
                <input type="password" name="password" value=""/>
            </td>
            <td class="error">
            ${password_error!""}

            </td>
        </tr>

        <tr>
            <td class="label">
                Пароль еще раз
            </td>
            <td>
                <input type="password" name="verify" value=""/>
            </td>
            <td class="error">
            ${verify_error!""}

            </td>
        </tr>

        <tr>
            <td class="label">
                Email (опционально)
            </td>
            <td>
                <input type="text" name="email" value="${email}"/>
            </td>
            <td class="error">
            ${email_error!""}

            </td>
        </tr>
    </table>

    <input type="submit"/>
</form>
</body>

</html>
