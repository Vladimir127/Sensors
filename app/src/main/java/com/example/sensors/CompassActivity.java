package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    /** Изображение стрелки, которое будет вращаться */
    private ImageView arrowImageView;

    /** Текущее значение азимута (отклонения от севера) в градусах */
    private float rotateDegree = 0f;

    /** Объект для доступа к датчикам */
    private SensorManager sensorManager;

    /** Текстовая надпись, в которой будет выводиться отклонения от севера */
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        setTitle("Компас");

        // Устанавливаем требуемую ориентацию экрана, чтобы приложение
        // всегда работало в портретной ориентации
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Находим элементы управления (изображение и текст), а также
        // инициализируем объект SensorManager
        arrowImageView = findViewById(R.id.image_view_compass_arrow);
        textView = findViewById(R.id.text_view_value);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    /**
     * Вызывается, когда активность становится видна для пользователя
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Регистрируем обработчик изменения показаний датчика. В качестве
        // обработчика указываем данный класс (this), поскольку он реализует
        // интерфейс SensorEventListener и содержит методы для обработки изменения
        // показаний. В качестве типа сенсора указываем сенсор ориентации.
        // Задержку указываем самую короткую - игровую.
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Вызывается, когда активность перестаёт быть видимой для пользователя
     */
    @Override
    protected void onPause() {
        super.onPause();

        // Отписываемся от обработчика изменений показания датчика,
        // чтобы он не тратил ресурсы устройства, когда приложение
        // закрыто или его экран не виден для пользователя.
        sensorManager.unregisterListener(this);
    }

    /**
     * Вызывается, когда произошло изменение показаний датчика
     * @param event Событие изменения показаний датчика
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Нужное нам значение хранится в нулевом элементе массива values,
        // который, в свою очередь, хранится в объекте event.
        // Сохраняем это значение в переменную degree
        float degree = Math.round(event.values[0]);

        // Выводим значение переменной degree на экран, в элемент TextView.
        textView.setText("Отклонение от севера: " + degree + " градусов");

// Создаём анимацию вращения для стрелки. Первые два параметра
// задают начальный и конечный углы: начальный угол сохранён
// в переменной rotateDegree, а конечный - получен от датчика.
// Чтобы вращение происходило по часовой стрелке, его нужно взять
// со знаком минус. В следующих четырёх параметрах задаётся
// ось вращения. Значение Animation.RELATIVE_TO_SELF означает,
// что положение точки объект задаёт относительного самого себя,
// а значения 0,5f означают, что она находится в середине объекта.
RotateAnimation rotateAnimation = new RotateAnimation(
        rotateDegree,
        -degree,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f);

// Задаём длительность анимации в миллисекундах
rotateAnimation.setDuration(200);

// Указываем, что после завершения анимации объект останется на новом месте
rotateAnimation.setFillAfter(true);

// Запускаем анимацию и сохранем новое значение
// угла в глобальную переменную rotateDegree
arrowImageView.startAnimation(rotateAnimation);
rotateDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}