package com.anna.healthyfoods;

import static org.junit.Assert.*;

import com.google.android.material.tabs.TabLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
public class HomeActivityTest {
  private HomeActivity activity;
  
  @Before
  public void setUp()  {
    activity = Robolectric.buildActivity(HomeActivity.class)
            .create()
            .start()
            .resume()
            .get();
  }

  @Test
  public void validateTabs() {
    TabLayout tabLayout = activity.findViewById(R.id.tab_layout);

    // Get tabs
    TabLayout.Tab mealTypes = tabLayout.getTabAt(0);
    TabLayout.Tab search = tabLayout.getTabAt(1);
    TabLayout.Tab starred = tabLayout.getTabAt(2);

    assertEquals(activity.getString(R.string.meal_types), Objects.requireNonNull(mealTypes).getText());
    assertEquals(activity.getString(R.string.search), Objects.requireNonNull(search).getText());
    assertEquals(activity.getString(R.string.saved), Objects.requireNonNull(starred).getText());
  }

}
