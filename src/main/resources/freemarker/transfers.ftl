<!DOCTYPE html>
<html>
<head>
    <title>Переводы</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" media="screen">

</head>
<body>

<a href="/">На главную</a>


<form method="post">
    <table>
        <tbody>
        <tr>
            <td>Сумма</td>
            <td><input type="text" name="sum"/></td><#if emptySumError??>
            <td class="error">${emptySumError}</td></#if>
        </tr>
        <tr>
            <td>Из:</td>
            <td>
                <select name="accountFrom">
                <#list accounts?keys as account>
                    <option value=${account}>${accounts[account]}</option>
                </#list>
                </select>

            </td>
        </tr>
        <tr>
            <td>В:</td>
            <td>
                <select name="accountIn">
                <#list accounts?keys as account>
                    <option value=${account}>${accounts[account]}</option>
                </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Перевести"/></td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td></td>
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