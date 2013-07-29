<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/html" lang="ru-RU">
<head>
    <title>Вход</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <style type="text/css">
        .label {
            text-align: right
        }

        .error {
            color: red
        }
    </style>

</head>

<body>
Нужен аккаунт? <a href="/signup">Зарегистрироваться</a>

<p>

<h2>Вход</h2>

<form method="post">
    <table>
        <tr>
            <td class="label">
                Логин
            </td>
            <td>
                <input type="text" name="username" value="${username}">
            </td>
            <td class="error">
            </td>
        </tr>

        <tr>
            <td class="label">
                Пароль
            </td>
            <td>
                <input type="password" name="password" value="">
            </td>
            <td class="error">
            ${login_error}

            </td>
        </tr>

    </table>

    <input type="submit">
</form>
</body>

</html>
