package com.example.sensors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * Наименование датчика
     */
    private String sensorName;

    /**
     * Объект, отвечающий за работу с датчиками
     */
    private SensorManager sensorManager;

    /**
     * Текстовая надпись для отображения значения датчика
     */
    private TextView valueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

// Получаем из интента заголовок активности и устанавливаем его
        sensorName = getIntent().getStringExtra("sensorName");
        setTitle(sensorName);

// Находим элемент управления TextView
// для отображения названия измеряемой величины
        TextView headerTextView = findViewById(R.id.text_view_header);

// Получаем из интента это название
        String title = getIntent().getStringExtra("title");

// Устанавливаем его в качестве текста элементу TextView
        headerTextView.setText(title);

// Аналогичным образом находим TextView
// для отображения единицы измерения, получаем и устанавливаем её
        TextView footerTextView = findViewById(R.id.text_view_footer);
        String unit = getIntent().getStringExtra("unit");
        footerTextView.setText(unit);

// Находим TextView для отображения значения.
// В нём будут отображаться показания датчика
        valueTextView = findViewById(R.id.text_view_value);

// Инициализируем объект SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    /**
     * Вызывается, когда активность становится видна для пользователя
     */
    @Override
    protected void onResume() {
        super.onResume();

// Получаем нужный датчик. Поскольку устройство
// может иметь несколько подобных датчиков,
// получаем датчик по умолчанию
        int sensorType = getIntent().getIntExtra("sensorType", 0);
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);

// Если датчик не найден, отображаем для
// пользователя диалоговое окно с сообщением об ошибке
        if (sensor == null) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(SensorActivity.this);
            builder.setTitle("Ошибка");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage("На вашем устройстве отсутствует " + sensorName);
            builder.setCancelable(false);

            // Устанавливаем диалоговому окну кнопку "OK"
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // При нажатии на кнопку OK завершаем активность
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

// Если же нужный датчик нашёлся,
// регистрируем обработчик изменения показаний датчика.
// В качестве обработчика указываем данный класс (this),
// поскольку он реализует интерфейс SensorEventListener
// и содержит методы для обработки изменения показаний.
// Задержку указываем среднюю.
        sensorManager.registerListener(this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
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
     *
     * @param event Событие изменения показаний датчика
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Получаем нужное значение из нулевого элемента массива event.values
        String value = String.valueOf(event.values[0]);

        // Выводим значение переменной degree на экран, в элемент TextView.
        valueTextView.setText(value);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}