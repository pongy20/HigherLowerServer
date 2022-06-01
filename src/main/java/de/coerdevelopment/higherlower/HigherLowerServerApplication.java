package de.coerdevelopment.higherlower;

import de.coerdevelopment.higherlower.repository.RankingRepository;
import de.coerdevelopment.higherlower.repository.SQL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class HigherLowerServerApplication {

    public static void main(String[] args) {
        JFrame frame = new JFrame("HigherLowerServer");
        frame.setSize(new Dimension(200, 100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SQL.newSQL("localhost", "higherlower", "higherlower", "higherlower", "3306");
        System.out.println("SQL Connection established: " + SQL.getSQL().initSQL());
        RankingRepository.getInstance().createTable();
        SpringApplication.run(HigherLowerServerApplication.class, args);

    }

}
