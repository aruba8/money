<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Доходы</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>

<a href="/">На главную</a>
<a href="/accounts">Счета</a>
<a href="/categories">Категории</a>
<a href="/transfers">Переводы</a>


<form method="post" action="/income">
    <table>
        <tbody>
        <tr>
            <td>Доход</td>
            <td><input type="text" name="sum"/></td>
            <td><input type="submit" value="ок"/></td>
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

</body>

</html>