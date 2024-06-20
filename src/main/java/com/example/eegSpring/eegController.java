package com.example.eegSpring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class eegController {
    private String url = "/Users/bogdanderdz/java/eegClientServer/usereeg.db";

    @GetMapping("/eeg")
    public String image(@RequestParam String username, @RequestParam int electrode, Model model) {
        DBConnection.connect(url);

        String sql = "SELECT image FROM user_eeg WHERE username = ? AND electrode_number = ?";
        String image = null;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setInt(2, electrode);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                image = result.getString("image");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBConnection.disconnect();

        model.addAttribute("username", username);
        model.addAttribute("electrode", electrode);
        model.addAttribute("image", image);

        return "eeg";
    }
}
