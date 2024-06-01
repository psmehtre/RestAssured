// package com.example.fancode;

// import org.junit.runner.RunWith;

// import io.cucumber.junit.Cucumber;
// import io.cucumber.junit.CucumberOptions;
// import io.cucumber.junit.CucumberOptions.SnippetType;

// @RunWith(Cucumber.class)
// @CucumberOptions(
// 		plugin = {"pretty:target/cucumber/cucumber.txt",
// 				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
// 				//"html:target/cucumber/report",
// 				"json:target/cucumber/cucumber.json",
// 				"com.api.utils.MyTestListener"
// 		}
// 		,features= {"src/test/resources/features"}
// 		,glue = {"com.api.stepdefinition"}
// 		//,dryRun = true
// 		,monochrome = true
// 		,snippets = SnippetType.CAMELCASE
// 		,tags = "@bookerAPI"
// 		//,publish = true
// 		)
// public class TestRunner {

// }

package com.example.fancode;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.example.fancode",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestRunner {
}
