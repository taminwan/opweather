Simple application which get response from OpenWeatherMaps API by region/city name

![image](https://user-images.githubusercontent.com/8493834/129487774-27c53132-54f6-43e9-b84c-407e38cd8bed.png)


✅ --> Done

❌ --> Not implemented yet


<h2>List of features</h2>


✅ 1. Programming language: Kotlin is required, Java is optional.

✅ 2. Design app's architecture (suggest MVVM)

✅ 3. Apply LiveData mechanism

✅ 4. UI should be looks like in attachment.

✅ 5. Write UnitTests

✅ 6. Acceptance Tests

✅ 7. Exception handling

❌ 8. Caching handling

9. Secure Android app from:
  
      ✅ a. Decompile APK
  
      ❌ b. Rooted device
  
      ❌ c. Data transmission via network
  
      ❌ d. Encryption for sensitive information
 
 10. Accessibility for Disability Supports:

      ❌ a. Talkback: Use a screen reader.
      
      ❌ b. Scaling Text: Display size and font size: To change the size of items on your screen, adjust the display size or font size.
      
      
 
 <h3>Brief explanation for the software development principles, patterns & practices being applied</h3>
 
  This project applied MVVM design pattern. With viewModel which will handle data for application/screen and drive UI.
  
  Also apply coroutine to simplify alot of works with asynchronous request
         
  Use JNI to hide some sensitive information (for appId key)
 
 <h3>Brief explanation for the code folder structure and the key Java/Kotlin libraries and frameworks being used</h3>
 
 💡<b>Folder structure</b>
 
 ![image](https://user-images.githubusercontent.com/8493834/129488139-a2a64bdc-e542-4bc3-a9f4-b4c086b272d6.png)
 
 api: For api service and resposne
 
 application: application class
 
 helper: for generic function
 
 loader: JNI loader
 
 model: for model class
 
 repo: for any source repo we need to get data from
 
 screen: for activity / screen
 
 ui (adapter / viewholder / view): contains all item we need to display on screen
 
 viewmodel: for viewModel which handlle logical between data and UI
 
 
 
 💡<b>Lib/framework used in this project</b>
 
 AndroiX
 
 Kotlint/Coroutine
 
 Retrofit
 
 ViewBinding
 
 Gson
 
 JUNIT
 
 MockServer
 
 Espresso
 
 
 
 
 <h3>All the required steps in order to get the application run on local computer</h3>
 
 Just with normal import with default AndroidSDK/NDK then you can use this application
 
 <b>For instrumented testing, please chagne flag IS_MOCK_TEST_ENABLE in buildgradle application from false to true<b>
      
      true : use mock response for testing
      
      false : use response from OpenWeather service (default)
  
