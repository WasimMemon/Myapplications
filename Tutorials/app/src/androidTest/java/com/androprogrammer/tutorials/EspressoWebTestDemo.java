package com.androprogrammer.tutorials;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.web.assertion.WebViewAssertions;
import android.support.test.espresso.web.sugar.Web;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;

import org.hamcrest.Matchers;

public class EspressoWebTestDemo {


    private void withTagHasTextInWebView(int webViewId, String tagName, String text) {
        Web.onWebView(ViewMatchers.withId(webViewId))
                .withElement(DriverAtoms.findElement(Locator.TAG_NAME, tagName))
                .withNoTimeout()
                .check(WebViewAssertions.webMatches(DriverAtoms.getText(),
                        Matchers.equalTo(text)));
    }

    private void withXPathHasTextInWebView(int webViewId, String xpath, String text) {
        Web.onWebView(ViewMatchers.withId(webViewId))
                .withElement(DriverAtoms.findElement(Locator.XPATH, xpath))
                .withNoTimeout()
                .check(WebViewAssertions.webMatches(DriverAtoms.getText(),
                        Matchers.equalTo(text)));
    }

    private void withIdHasTextInWebView(int webViewId, String containerId, String subcontainerId, String elementId, String text) {
        Web.onWebView(ViewMatchers.withId(webViewId))
                .withElement(DriverAtoms.findElement(Locator.ID, containerId))
                .withContextualElement(DriverAtoms.findElement(Locator.ID, subcontainerId))
                .withContextualElement(DriverAtoms.findElement(Locator.ID, elementId))
                .withNoTimeout().check(WebViewAssertions.webMatches(DriverAtoms.getText(),
                Matchers.equalTo(text)));
    }

}
