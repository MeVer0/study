package UI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import api.WeatherRequestsManager;

public class WeatherAppUI {


    public static List<String> loadCitiesFromJson() throws IOException {
        String filename = "src\\main\\java\\UI\\city_coordinates.json";
        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File(filename);

        Map<String, Map<String, String>> cityData = objectMapper.readValue(jsonFile, Map.class);

        return cityData.keySet().stream().collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        FlatLightLaf.setup();

        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        String api_token = showInputDialog();
        if (Objects.equals(api_token, "")) {
            JOptionPane.showMessageDialog(null, "Токен не введен. Программа будет закрыта.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        WeatherRequestsManager weatherRequestsManager = new WeatherRequestsManager(api_token);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel("Введите название города:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField searchField = new JTextField();
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JList<String> suggestionsList = new JList<>();
        JScrollPane suggestionsPane = new JScrollPane(suggestionsList);
        suggestionsPane.setMaximumSize(new Dimension(300, 100));

        JButton selectButton = new JButton("Продолжить");
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.setEnabled(false);

        List<String> cities = loadCitiesFromJson();

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            private void updateSuggestions() {
                String input = searchField.getText().toLowerCase();
                if (input.isEmpty()) {
                    suggestionsList.setListData(new String[0]);
                    selectButton.setEnabled(false);
                    return;
                }

                List<String> filteredCities = new ArrayList<>();
                for (String city : cities) {
                    if (city.toLowerCase().contains(input)) {
                        filteredCities.add(city);
                    }
                }
                suggestionsList.setListData(filteredCities.toArray(new String[0]));

                selectButton.setEnabled(!filteredCities.isEmpty());
            }
        });

        suggestionsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !suggestionsList.isSelectionEmpty()) {
                String selectedCity = suggestionsList.getSelectedValue();
                searchField.setText(selectedCity);
                selectButton.setEnabled(true);
            }
        });

        selectButton.addActionListener(e -> {
            String selectedCity = searchField.getText();
            JOptionPane.showMessageDialog(frame, "Вы выбрали город: " + selectedCity);
            frame.dispose();
            try {
                showWeatherApp(selectedCity, weatherRequestsManager);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });


        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(searchField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(suggestionsPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(selectButton);

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static String showInputDialog() {
        return (String) JOptionPane.showInputDialog(null, "Введите свой Yandex API Token: Тестовый токен( 6a323067-4110-4675-9647-6e3e45f73059 )", "API Token", JOptionPane.PLAIN_MESSAGE, null, null,"6a323067-4110-4675-9647-6e3e45f73059");

    }


    private static void showWeatherApp(String city, WeatherRequestsManager weatherRequestsManager) throws IOException, InterruptedException {
        JFrame weatherFrame = new JFrame("Weather Info - " + city);
        weatherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        weatherFrame.setSize(400, 400);

        Map<String, String> response = weatherRequestsManager.getWeatherByCityName(city);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cityLabel = new JLabel("Погода для города: " + city);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tempLabel = new JLabel(String.format("Температура: %s°C", response.get("temp")));
        tempLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel feelsLikeLabel = new JLabel(String.format("Ощущается как: %s°C", response.get("feels_like")));
        feelsLikeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        feelsLikeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel conditionLabel = new JLabel("Условия: " + response.get("condition"));
        conditionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        conditionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel windSpeedLabel = new JLabel("Скорость ветра: " + response.get("wind_speed") + " м/с");
        windSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        windSpeedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel windDirLabel = new JLabel("Направление ветра: " + response.get("wind_dir"));
        windDirLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        windDirLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pressureLabel = new JLabel("Давление: " + response.get("pressure_mm") + " мм рт. ст.");
        pressureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pressureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(cityLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(tempLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(feelsLikeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(conditionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(windSpeedLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(windDirLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(pressureLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        weatherFrame.add(panel);
        weatherFrame.setVisible(true);
    }
}
