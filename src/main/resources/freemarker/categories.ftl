<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/html" lang="ru-RU">
<head>
    <title>Категории</title>
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
<a href="/accounts">Счета</a>
<a href="/income">Доходы</a>
<a href="/transfers">Переводы</a>


<h3>Категории</h3>

<#if iCategoriesSize+expCategoriesSize gt 0 >
<form method="post">
    <#if iCategoriesSize != 0>
        <h4>Income</h4>
        <#list iCategories?keys as category>
            <li><input type="checkbox" name=${category}> ${iCategories[category]}</li>
        </#list>
    </#if>

    <#if expCategoriesSize != 0>
        <h4>Expenses</h4>
        <#list expCategories?keys as category>
            <li><input type="checkbox" name=${category}> ${expCategories[category]}</li>
        </#list>
        <input type="hidden" name="add" value="false">
    </#if>
    </br>
    </br>
    <input type="submit" value="удалить категорию">
</#if>

</form>

<br/>
<br/>


<form method="post">
    <table>
        <tr>
            <td class="label">
                Добавить категорию
            </td>
            <td>
                <input type="text" name="categoryName" value="">
            </td>
            <td>
                <select name="type">
                    <option value="expenses">Расход</option>
                    <option value="income">Доход</option>
                </select>
            </td>
            <td class="error">
            </td>
        </tr>
    </table>

    <input type="hidden" name="add" value="true">
    <input type="submit" value="Добавить категорию">
</form>
<a href="/logout">Выход</a>
</body>
</html>