package com.anna.healthyfoods;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
  private MainActivity activity;

  @Before
  public void setUp()  {
    activity = Robolectric.buildActivity(MainActivity.class)
            .create()
            .start()
            .resume()
            .get();
  }

  @Test
  public void validateTextViewContent_true() {
    TextView title = activity.findViewById(R.id.splash_screen_title);
    TextView subtitle = activity.findViewById(R.id.splash_screen_subtitle);
    assertEquals(activity.getString(R.string.app_name), title.getText().toString());
    assertEquals(activity.getString(R.string.splash_screen_subtitle), subtitle.getText().toString());
  }

  @Test
  public void validateButtonTextContent_true() {
    Button button = activity.findViewById(R.id.btn_get_started);
    assertEquals(activity.getString(R.string.get_started),button.getText().toString());
  }

  @Test
  public void handleButtonClick_startsSecondActivity_true() {
    activity.findViewById(R.id.btn_get_started).performClick();
    Intent expectedIntent = new Intent(activity, UserDetailsActivity.class);
    Intent actualIntent = shadowOf(activity).getNextStartedActivity();
    assertTrue(actualIntent.filterEquals(expectedIntent));
  }
}