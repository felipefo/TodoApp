package com.todo.cucumber;

import com.todo.Todo2App;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = Todo2App.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
