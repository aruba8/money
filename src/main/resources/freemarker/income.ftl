<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Доходы</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.0.3.js"></script>

</head>
<body>
<div class="container">
    <div class="main">
        <div class="navbar">
            <div class="navbar-inner">
                <ul class="nav">
                    <li>
                        <a href="/">Главная</a>
                    </li>
                    <li>
                        <a href="/accounts">Счета</a>
                    </li>
                    <li>
                        <a href="/categories">Категории</a>
                    </li>
                    <li class="active">
                        <a href="/income">Доходы</a>
                    </li>
                    <li>
                        <a href="/transfers">Переводы</a>
                    </li>
                    <li>
                        <a href="/charts">Анализ</a>
                    </li>
                </ul>
            </div>
        </div>

            <form method="post" action="/income">
                <table>
                    <tbody>
                    <tr>
                        <td>Доход</td>
                        <td><input type="text" name="sum"/></td>
                        <td>
                            <button class="btn" type="submit">OK</button>
                        </td>
                    </tr>
                    <tr>
                        <td>Категория</td>
                        <td>
                            <select name="category">
                            <#list categories?keys as category>
                                <option value='${category}'>${categories[category]}</option>
                            </#list>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Счет</td>
                        <td>
                            <select name="account">
                            <#list accounts?keys as account>
                                <option value='${account}'>${accounts[account]}</option>
                            </#list>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Комментарий</td>
                        <td><input type="text" name="comment"/></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        <a href="/logout">Выход</a>
    </div>
</div>
</body>

</html>