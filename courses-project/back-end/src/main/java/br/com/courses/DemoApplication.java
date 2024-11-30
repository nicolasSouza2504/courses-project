package br.com.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        String teste = "Airton Aa";

        System.out.println(teste.matches("^[A-Z][a-z]+ [A-Z][a-z]+$"));

    }

}
