<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/html" lang="ru-RU">
<head>
    <title>Счета</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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

<a href="/">На главную</a>
<a href="/categories">Категории</a>
<a href="/income">Доходы</a>
<a href="/transfers">Переводы</a>


<h3>Счета</h3>

<#if accountsSize != 0>
<form method="post">
    <#list accounts?keys as account>
        <li><input type="checkbox" name=${account}> ${accounts[account]}</li>
    </#list>
    <input type="hidden" name="add" value="false">
    <input type="submit" value="удалить счет">

</form>
</#if>




<form method="post">
    <table>
        <tr>
            <td class="label">
                Название счета
            </td>
            <td>
                <input type="text" name="accountName" value="">
            </td>
            <td class="error">
            </td>
        </tr>
    </table>

    <input type="hidden" name="add" value="true">
    <input type="submit" value="Добавить счет">
</form>
<a href="/logout">Выйти</a>
</body>

</html>