<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Категории</title>
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
                    <li class="active">
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

    <#if iCategoriesSize+expCategoriesSize gt 0 >
        <form method="post" action="/categories">
            <#if iCategoriesSize != 0>
                <h4>Доходы</h4>
                <ul>
                    <#list iCategories?keys as category>
                        <li><label class="checkbox inline"><input type="checkbox"
                                                                  name="${category}"/> ${iCategories[category]}</label>
                        </li>
                    </#list>
                </ul>
            </#if>

            <#if expCategoriesSize != 0>
                <h4>Расходы</h4>

                <ul>
                    <#list expCategories?keys as category>
                        <li><label class="checkbox inline"><input type="checkbox"
                                                                  name="${category}"/>${expCategories[category]}</label>
                        </li>
                    </#list>
                </ul>
                <input type="hidden" name="add" value="false"/>
            </#if>
            <br/>
            <br/>
            <button type="submit" class="btn">удалить категорию</button>
        </form>
    </#if>


        <form class="form-horizontal" method="post" action="/categories">
            <div class="control-group">
                <div class="control">
                    <input class="input-medium" type="text" name="categoryName" placeholder="добавить категорию"/>
                </div>
                <div class="control">
                    <select name="type">
                        <option value="expenses">Расход</option>
                        <option value="income">Доход</option>
                    </select>
                </div>
                <input type="hidden" name="add" value="true"/>

                <div class="control">
                    <button class="btn" type="submit">Добавить категорию</button>
                </div>
            </div>
        </form>
        <a href="/logout">Выход</a>
    </div>
</div>
</body>
</html>