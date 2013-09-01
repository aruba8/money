<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Счета</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.0.3.js"></script>

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
<div class="container">
    <div class="main">
        <div class="navbar">
            <div class="navbar-inner">
                <ul class="nav">
                    <li>
                        <a href="/">Главная</a>
                    </li>
                    <li class="active">
                        <a href="/accounts">Счета</a>
                    </li>
                    <li>
                        <a href="/categories">Категории</a>
                    </li>
                    <li>
                        <a href="/income">Доходы</a>
                    </li>
                    <li>
                        <a href="/transfers">Переводы</a>
                    </li>
                    <li>
                        <a href="/charts">Анализ</a>
                    </li>
                </ul>
            </div>
        </div>

        <#if accountsSize != 0>
            <form method="post" action="/accounts">
                <ul>
                    <#list accounts?keys as account>
                        <li><label class="checkbox inline"><input type="checkbox" name="${account}"/> ${accounts[account]}
                        </label></li>
                    </#list>
                </ul>
                <input type="hidden" name="add" value="false"/>
                <button type="submit" class="btn">удалить счет</button>

            </form>
        </#if>
        <div class="controls">
            <form method="post" action="/accounts">
                <div class="control">
                    <input type="text" name="accountName" placeholder="название счета"/>
                </div>
                <div class="control">
                    <button type="submit" class="btn">Добавить счет</button>
                </div>
                <input type="hidden" name="add" value="true"/>
            </form>
        </div>

        <a href="/logout">Выйти</a>
    </div>
</div>
</body>

</html>