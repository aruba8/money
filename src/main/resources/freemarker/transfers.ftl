<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Переводы</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.0.3.js"></script>

</head>
<body>
<div class="container">
    <div class="main">
        <div class="navbar">
            <div class="navbar-inner">
                <ul class="nav">
                    <li>
                        <a href="/">Главная</a>
                    </li>
                    <li>
                        <a href="/accounts">Счета</a>
                    </li>
                    <li>
                        <a href="/categories">Категории</a>
                    </li>
                    <li>
                        <a href="/income">Доходы</a>
                    </li>
                    <li class="active">
                        <a href="/transfers">Переводы</a>
                    </li>
                </ul>
            </div>
        </div>


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

        <ul>
        <#list accountsWithSum?keys as account>
            <li>${account} : ${accountsWithSum[account]}</li>
        </#list>
        </ul>

        <a href="/logout">Выход</a>
    </div>
</div>

</body>
</html>