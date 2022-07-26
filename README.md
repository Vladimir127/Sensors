# Sensors

Приложение было разработано в 2021 году в качестве расчётно-графической работы по мобильным информационным технологиям. 

Мобильное приложение взаимодействует с датчиками следующих типов: 
* Датчик ориентации (TYPE_ORIENTATION)
*	Акселерометр (TYPE_ACCELEROMETER)
*	Датчик освещения (TYPE_LIGHT)
*	Барометр (TYPE_PRESSURE)
*	Датчик температуры окружающей среды (TYPE_AMBIENT_TEMPERATURE)
*	Измеритель относительной влажности (TYPE_RELATIVE_HUMIDITY)

Приложение не просто отображает показания датчиков, а демонстрирует их работу в наглядной форме. Если какие-либо из датчиков отсутствуют на устройстве, приложение корректно обрабатывает такие ситуации и выводит для пользователя соответствующее сообщение об ошибке.

Разработанное мобильное приложение, взаимодействующее с датчиками, состоит из четырёх экранов (активностей, Activities)

## Главный экран приложения

При запуске приложения открывается главный экран (MainActivity)

<img src="https://user-images.githubusercontent.com/76528795/180640836-74f16a1a-a166-420e-8514-132545fa33dd.png" width="32%">

Экран содержит список сенсоров, который представлен элементом ListView. При касании каждого пункта списка откроется новый экран, отображающий показания выбранного датчика. 

## Компас

При выборе пункта «Компас» запускается операция CompassActivity, и на экране отображается компас с вращающейся стрелкой, а также надписью, указывающей точное отклонение от севера в градусах.

<img src="https://user-images.githubusercontent.com/76528795/180640983-2be5e63c-b9b3-4528-85ab-8fa3acb20006.png" width="32%">

Для отображения циферблата и стрелки используются два компонента ImageView, а для отображения надписи – элемент TextView. 

Доступ к датчикам осуществляется с помощью объекта SensorManager. Когда показания датчика меняются, вызывается метод onSensorChanged(); в качестве параметра в этот метод передаётся объект SensorEvent. Этот объект содержит массив values, в котором и хранятся изменённые показания датчика. В данном случае мы получаем ориентацию устройства относительно севера в градусах, выводим это значение в текстовую надпись на экран, а также настраиваем и запускаем анимацию вращения (RotateAnimation), которая повернёт изображение стрелки на нужный угол. 

## Акселерометр

Ещё один важный датчик в любом Android-устройстве – это акселерометр. Для демонстрации его работы используется третий экран приложения – AccelerometerActivity, который можно открыть, выбрав пункт «Акселерометр» в списке на главном экране. На белом экране появится изображение небольшого серого шарика, который будет перекатываться по экрану из стороны в сторону в зависимости от угла наклона устройства. 

<img src="https://user-images.githubusercontent.com/76528795/180641396-d51c8ad9-1555-40f3-9974-6012fc6c92cc.png" width="32%">

Данная активность не имеет собственного XML-макета: для задания внешнего вида здесь используется отдельный класс BallView, унаследованный от класса View. В данном случае в методе onSensorChanged() мы получаем не одно, а два значения – ускорения по осям X и Y. Далее вызывается метод updateBall(), в котором на основании полученных ускорений вычисляется скорость и смещение шарика по осям X и Y, а затем, наконец, вычисляются его координаты, и шарик перерисовывается.

## Другие датчики

Кроме компаса и акселерометра в приложении демонстрируется работа ещё четырёх датчиков: датчика освещённости, термометра, барометра и измерителя влажности (гигрометра). Поскольку показания всех четырёх датчиков считываются и отображаются одним и тем же способом, работа с этими датчиками выполняется в одной активности SensorActivity, только в зависимости от выбранного датчика в неё передаются те или иные входные данные: тип датчика, название датчика, название измеряемой величины, а также название единицы измерения для отображения. Например, отображение показаний датчика освещённости представлено на рисунке ниже.

<img src="https://user-images.githubusercontent.com/76528795/180641606-e9a60f02-1a96-4e43-9632-91c1b6319481.png" width="32%">

Остальные три датчика представлены не на всех устройствах, поэтому в случае, если выбранный датчик на устройстве отсутствует, для пользователя отображается диалоговое окно с соответствующим сообщением. 

<img src="https://user-images.githubusercontent.com/76528795/180641649-4ceaaffd-6166-40ef-8c61-a2996ef63c77.png" width="32%">

