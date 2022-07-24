package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Устанавливаем заголовок активности
        setTitle("Датчики");

// Объявляем массив датчиков
        String[] sensors = {"Компас", "Акселерометр", "Датчик освещённости",
                "Термометр", "Барометр", "Гигрометр"};

// Создаём адаптер для соединения массива со списком на экране
        ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, sensors);

// Находим список (элемент управления ListView), устанавливаем ему
// адаптер и задаём обработчик нажатия
        ListView sensorsListView = findViewById(R.id.list_view_sensors);
        sensorsListView.setAdapter(adapter);
        sensorsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // Объявляем объект intent для запуска нужной активности
                        Intent intent;

// Далее действуем в зависимости от выбранного пункта (position),
// который соответствует индексу элемента в массиве.
switch (position) {
    // Для первых двух датчиков (компас и акселерометр) созданы
    // отдельные активности (CompassActivity и AccelerometerActivity)
    case 0:
        intent = new Intent(MainActivity.this, CompassActivity.class);
        startActivity(intent);
        break;
    case 1:
        intent = new Intent(MainActivity.this, AccelerometerActivity.class);
        startActivity(intent);
        break;
    // Для всех остальных датчиков используется одна и та же
    // активность SensorActivity, но в неё мы в зависимости от
    // выбранного пункта передаём нужные данные: название
    // датчика, тип датчика, название измеряемой величины и
    // название единицы измерения.
    case 2:
        intent = new Intent(MainActivity.this, SensorActivity.class);
        intent.putExtra("title", "Освещённость");
        intent.putExtra("sensorName", "Датчик освещённости");
        intent.putExtra("sensorType", Sensor.TYPE_LIGHT);
        intent.putExtra("unit", "люкс");
        startActivity(intent);
        break;
    case 3:
        intent = new Intent(MainActivity.this, SensorActivity.class);
        intent.putExtra("title", "Температура");
        intent.putExtra("sensorName", "Термометр");
        intent.putExtra("sensorType",
                Sensor.TYPE_AMBIENT_TEMPERATURE);
        intent.putExtra("unit", "°C");
        startActivity(intent);
        break;
    case 4:
        intent = new Intent(MainActivity.this, SensorActivity.class);
        intent.putExtra("title", "Атмосферное давление");
        intent.putExtra("sensorName", "Барометр");
        intent.putExtra("sensorType", Sensor.TYPE_PRESSURE);
        intent.putExtra("unit", "Гектопаскаль");
        startActivity(intent);
        break;
    case 5:
        intent = new Intent(MainActivity.this, SensorActivity.class);
        intent.putExtra("title", "Относительная влажность воздуха");
        intent.putExtra("sensorName", "Гигрометр");
        intent.putExtra("sensorType", Sensor.TYPE_RELATIVE_HUMIDITY);
        intent.putExtra("unit", "%");
        startActivity(intent);
        break;
}
                    }
                });
    }
}