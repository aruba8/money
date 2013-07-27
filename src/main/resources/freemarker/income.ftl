<!DOCTYPE html>
<html>
<head>
    <title>Доходы</title>

</head>
<body>

<a href="/">На главную</a>

<form method="post">
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
                    <option value=${category}>${categories[category]}</option>
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
                    <option value=${account}>${accounts[account]}</option>
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