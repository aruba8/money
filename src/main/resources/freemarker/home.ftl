<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
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
                    <li class="active">
                        <a href="/">Главная</a>
                    </li>
                    <li>
                        <a href="/accounts">Счета</a>
                    </li>
                    <li>
                        <a href="/categories">Категории</a>
                    </li>
                    <li>
                        <a href="/income">Доходы</a>
                    </li>
                    <li>
                        <a href="/transfers">Переводы</a>
                    </li>
                </ul>
            </div>
        </div>

        <form method="post" action="/">
            <table>
                <tbody>
                <tr>
                    <td>Расход</td>
                    <td><input type="text" name="expenses" value=""/></td>
                </tr>
                <tr>
                    <td>Категория</td>
                    <td>
                        <select name="category">
                        <#list categories?keys as category>
                            <option value="${category}">${categories[category]}</option>
                        </#list>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Счет</td>
                    <td>
                        <select name="account">
                        <#list accounts?keys as account>
                            <option value="${account}">${accounts[account]}</option>
                        </#list>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Комментарий</td>
                    <td><input type="text" name="comment"/></td>
                </tr>
                <tr>
                    <td>
                        <button type="submit" class="btn">ОК</button>
                    </td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </form>

        <ul>
        <#list accountsWithSum?keys as account>
            <li>${account} : ${accountsWithSum[account]}</li>
        </#list>
        </ul>
        <a href="/logout">Выход</a>
    </div>
</div>
</body>
</html>