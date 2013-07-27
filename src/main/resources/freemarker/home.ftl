<!DOCTYPE html>

<html>
<head>
    <title>Главная</title>
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

<form method="post">
    <table>
        <tbody>
        <tr>
            <td>Расход</td>
            <td><input type="text" name="expenses" value=""></td>
        </tr>
        <tr>
            <td>Категория</td>
            <td>
                <select name="category">
                <#list categories?keys as category>
                    <option value=${category}>${categories[category]}</option>
                </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td>Счет</td>
            <td>
                <select name="account">
                <#list accounts?keys as account>
                    <option value=${account}>${accounts[account]}</option>
                </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td>Комментарий</td>
            <td><input type="text" name="comment"></td>
        </tr>
        <tr>
            <td><input type="submit" value="ОК"></td>
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