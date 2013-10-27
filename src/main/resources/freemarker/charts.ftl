<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Анализ</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="css/style.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

    <script src="js/jquery-2.0.3.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <script type="text/javascript" src="js/j.js"></script>
</head>
<body>
<script src="js/highcharts.js"></script>
<script src="js/exporting.js"></script>


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

        <input name="startDate" id="startDate" class="date-picker" />
        <div id="chartdiv"></div>
    </div>
</div>

</body>
</html>