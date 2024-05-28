package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "Features", glue = "steps", plugin = { "pretty", "html:Report/report.html" }, tags = "@S3")
public class TestRunner {

}