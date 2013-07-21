<!DOCTYPE html>

<html>
<head>
    <title>Accounts</title>
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

<a href="/">Go home</a>

<h3>Accounts</h3>

<#if accountsSize != 0>
<form method="post">
    <#list accounts?keys as account>
        <li><input type="checkbox" name=${account}> ${accounts[account]}</li>
    </#list>
    <input type="hidden" name="add" value="false">
    <input type="submit" value="delete account">

</form>
</#if>




<form method="post">
    <table>
        <tr>
            <td class="label">
                account name
            </td>
            <td>
                <input type="text" name="accountName" value="">
            </td>
            <td class="error">
            </td>
        </tr>
    </table>

    <input type="hidden" name="add" value="true">
    <input type="submit" value="add account">
</form>
<a href="/logout">Logout</a>
</body>

</html>