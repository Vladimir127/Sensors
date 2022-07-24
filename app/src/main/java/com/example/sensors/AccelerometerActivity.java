package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    /** Позиция, ускорение и скорость по оси X */
    private float xPos, xAccel, xVel = 0.0f;

    /** Позиция, ускорение и скорость по оси Y */
    private float yPos, yAccel, yVel = 0.0f;

    /** Максимально возможные позиции по осям
     * X и Y (правая и нижняя границы экрана) */
    private float xMax, yMax;

    /** Шарик в виде картинки */
    private Bitmap ball;

    /** Объект для получения данных от датчиков */
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        setTitle("Акселерометр");

        // Устанавливаем требуемую ориентацию экрана,
        // чтобы приложение всегда работало в портретной ориентации
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Создаём поле, по которому будет кататься шарик,
        // и устанавливаем его в качестве макета данной активности
        BallView ballView = new BallView(this);
        setContentView(ballView);

        // Определяем размер экрана и вычисляем крайнюю
        // правую и крайнюю нижнюю позиции экрана,
        // чтобы шарик не выкатывался за пределы экрана
        xMax = ballView.getWidth();
        yMax = ballView.getHeight();

        // Инициализируем объект sensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * Вызывается, когда активность становится видна для пользователя
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Регистрируем обработчик изменения показаний датчика.
        // В качестве обработчика указываем данный класс (this),
        // поскольку он реализует интерфейс SensorEventListener
        // и содержит методы для обработки изменения показаний.
        // В качестве типа датчика указываем акселерометр.
        // Задержку указываем самую короткую - игровую.
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
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
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Получаем значения ускорения по осям
            // X и Y и обновляем положение шарика
            xAccel = event.values[0];
            yAccel = -event.values[1];
            updateBall();
        }
    }

/**
 * Обновляет положение шарика
 */
private void updateBall() {
    // Вычисляем скорость шарика на основании
    // значений ускорения, полученных от акселерометра
    float frameTime = 0.666f;
    xVel += (xAccel * frameTime);
    yVel += (yAccel * frameTime);

    // Вычисляем смещение по осям X и У
    float xS = (xVel / 2) * frameTime;
    float yS = (yVel / 2) * frameTime;

    // Вычисляем позицию шарика
    xPos -= xS;
    yPos -= yS;

    // Корректируем положение шарика с учётом границ экрана
    if (xPos > xMax) {
        xPos = xMax;
    } else if (xPos < 0) {
        xPos = 0;
    }

    if (yPos > yMax) {
        yPos = yMax;
    } else if (yPos < 0) {
        yPos = 0;
    }
}

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /** Поле, по которому будет кататься шарик,
     * в виде отдельного элемента управления */
    private class BallView extends View {

        /**
         * Инициализирует экземпляр класса
         * @param context Контекст
         */
        public BallView(Context context) {
            super(context);

            // Получаем файл изображения из папки drawable
            // и преобразуем его в картинку размером
            // 100 на 100 пикселей
            Bitmap ballSrc = BitmapFactory.decodeResource(
                    getResources(), R.drawable.ball2);
            final int dstWidth = 100;
            final int dstHeight = 100;
            ball = Bitmap.createScaledBitmap(
                    ballSrc,
                    dstWidth,
                    dstHeight,
                    true);
        }

        /**
         * Перерисовывает шарик
         * @param canvas Канва (поверхность для рисования)
         */
        @Override
        protected void onDraw(Canvas canvas) {
            xMax = getWidth() - 100;
            yMax = getHeight() - 100;
            canvas.drawBitmap(ball, xPos, yPos, null);
            invalidate();
        }
    }
}