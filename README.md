# HealthyFoods
An Android mobile application that provides recipe suggestions taking into account healthy eating. The mobile application consumes the Edamam recipe search API to provide the recipe suggestions based on selected diets and health preferences.

**Refactoring of app to follow recommended Android development app architecture and practices is ongoing. Find code on repository [HealthyFoods2.0](https://github.com/AnnaL001/healthyfoods2.0.git).**

#### By **[Lynn Nyangon](https://github.com/AnnaL001)**

## Description

The Mobile Application is still in development. Currently, the existing features include form validation, capturing inputs such as the name of the user, their preferred diets and any health preferences they might have and allowing users to search for recipes while taking into consideration their preferred diet and health preferences. Moreover, users can also view various meal types and their corresponding recipes, fetched via the Edamam API. Other functionalities included are: users being able to starr/save recipes that might have peaked their interest(for later cooking), reordering starred recipes, swiping to delete starred recipes and authenticating users. Aside from this, users can also search for recipes using keywords like 'Chicken' and others.

## Screenshots
![LIGHT-MODE-SLIDE](https://github.com/AnnaL001/healthy-foods/assets/43697008/68a6444f-6294-418c-aad1-4a848a5af4d7)


## Setup/Installation Requirements

- Using a mobile device/laptop ensure you have access to stable internet connection
- To access the application's code from your GitHub repository, you can fork the repository main's branch via the 'Fork' button.
- To access the application's code locally, you can clone the main branch or download the ZIP folder via the 'Code' button
- Once locally, you can view/run the application's code via IDEs such as Eclipse and AndroidStudio(mostly used).
- Before running the code locally<br>
  ### Prerequisites
  - Install [Sdkman](https://sdkman.io/install) that allows for management of multiple Java versions
  - Install [Java](https://sdkman.io/usage)
  ### Installing Android Studio
  - You can download installer from https://developer.android.com/studio#downloads
  - For Linux you can install via the command below
    ```
    $ sudo snap install android-studio --classic
    ```
  - For Linux(Ubuntu) you can install via Ubuntu Software
  ### Navigating within Android Studio
  - You can reference their user guide https://developer.android.com/studio/intro
  
  ### Running tests
  - The tests included are: Local tests and Instrumentation tests
  - To run tests in Android Studio you can reference their user guide https://developer.android.com/studio/test/test-in-android-studio

## Dependencies

- Java
- Espresso Testing Framework
- Roboelectric Testing Frameowrk
- JUnit Testing Framework
- Retrofit
- Glide
- Gson Converter
- Firebase

## Technologies Used

- Android Framework

## Support and contact details

In case of any queries you can reach out via email; lynn.nyangon@gmail.com

### License

MIT License
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.<br>
Copyright (c) 2022 **Lynn Nyangon**
