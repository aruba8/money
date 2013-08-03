<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Переводы</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="css/style.css" rel="stylesheet" type="text/css" media="screen"/>

</head>
<body>

<a href="/">На главную</a>
<a href="/accounts">Счета</a>
<a href="/categories">Категории</a>
<a href="/income">Доходы</a>


<form method="post" action="/transfers">
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
                    <option value="${account}">${accounts[account]}</option>
                </#list>
                </select>

            </td>
        </tr>
        <tr>
            <td>В:</td>
            <td>
                <select name="accountIn">
                <#list accounts?keys as account>
                    <option value="${account}">${accounts[account]}</option>
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