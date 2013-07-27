<!DOCTYPE html>

<html>
<head>
    <title>Home</title>
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
<a href="/accounts">Accounts</a>
<a href="/categories">Categories</a>

<form method="post">
    <table>
        <tbody>
        <tr>
            <td>Expenses</td>
            <td><input type="text" name="expenses" value=""></td>
        </tr>
        <tr>
            <td>Category</td>
            <td>
                <select name="category">
                <#list categories?keys as category>
                    <option value=${category}>${categories[category]}</option>
                </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td>Account</td>
            <td>
                <select name="account">
                <#list accounts?keys as account>
                    <option value=${account}>${accounts[account]}</option>
                </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td>Comment</td>
            <td><input type="text" name="comment"></td>
        </tr>
        <tr>
            <td><input type="submit" value="save"></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</form>

<#list accountsWithSum?keys as account>

<li>${account} : ${accountsWithSum[account]}</li>

</#list>

<a href="/logout">Logout</a>
</body>
</html>