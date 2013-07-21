<!DOCTYPE html>

<html>
<head>
    <title>Categories</title>
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

<h3>Categories</h3>

<#if iCategoriesSize != 0>
<form method="post">
    <#list iCategories?keys as category>
        <li><input type="checkbox" name=${category}> ${iCategories[category]}</li>
    </#list>
    <input type="hidden" name="add" value="false">
    <input type="submit" value="delete category">

</form>
</#if>

<#if expCategoriesSize != 0>
<form method="post">
    <#list expCategories?keys as category>
        <li><input type="checkbox" name=${category}> ${expCategories[category]}</li>
    </#list>
    <input type="hidden" name="add" value="false">
    <input type="submit" value="delete category">
</form>
</#if>


<form method="post">
    <table>
        <tr>
            <td class="label">
                Add category
            </td>
            <td>
                <input type="text" name="categoryName" value="">
            </td>
            <td>
                <select name="type">
                    <option value="expenses">Expenses</option>
                    <option value="income">Income</option>
                </select>
            </td>
            <td class="error">
            </td>
        </tr>
    </table>

    <input type="hidden" name="add" value="true">
    <input type="submit" value="add category">
</form>
<a href="/logout">Logout</a>
</body>
</html>