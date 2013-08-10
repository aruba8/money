<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Анализ</title>
    <link href="css/style.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.0.3.js"></script>
    <script type="text/javascript" src="amcharts/amcharts.js"></script>
    <script type="text/javascript" src="js/charts.js"></script>
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
                    <li>
                        <a href="/transfers">Переводы</a>
                    </li>
                    <li class="active">
                        <a href="/charts">Анализ</a>
                    </li>
                </ul>
            </div>
        </div>

        <div id="chartdiv"></div>
    </div>
</div>


<script>
    $(document).ready(createData());
</script>

</body>
</html>