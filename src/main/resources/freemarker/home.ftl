<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная</title>
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
<a href="/accounts">Счета</a>
<a href="/categories">Категории</a>
<a href="/income">Доходы</a>
<a href="/transfers">Переводы</a>

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
            <td><input type="submit" value="ОК"/></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</form>

<#list accountsWithSum?keys as account>

<li>${account} : ${accountsWithSum[account]}</li>

</#list>

<a href="/logout">Выход</a>
</body>
</html>