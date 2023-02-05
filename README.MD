# TTS Engine

A simple and efficient library that provides Text-to-Speech functionality for Android applications.

## Features

- Support for multiple languages and voices
- Easy-to-use API

## Installation

Add the library to your project using Gradle:

```groovy
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
    
```groovy
dependencies {
		implementation 'com.github.User:Repo:Tag'
	}
```

## Usage
Using the library is straightforward and simple. Here is an example of how to use it in your code:

``` kotlin
    TextToSpeechHelper
        .getInstance(activity)
        .registerLifecycle(owner)
        .speak(message)
        .highlight()
        .onHighlight { pair ->
            Log.d(TAG, "speak: ${pair.first} - ${pair.second}")
        }
        .onDone {
            Log.d(TAG, "speak: done")
        }
        .onError {
            Log.d(TAG, "speak: $it")
        }
```
- `activity` is the activity that will be used to initialize the TTS engine.

- `owner` is the lifecycle owner that will be used to register the TTS engine lifecycle observer and can handle the lifecycle events.

- `message` is the text that will be spoken.

- `highlight()` is an optional method that will highlight the spoken words in the text.

- `onHighlight()` is an optional method that will be called when a word is spoken.

- `onDone()` is an method that will be called when the text is spoken.

- `onError()` is an method that will be called when an error occurs.

## Code Example

### For Jetpack Compose, check out the example below:

`MainActivity.kt`
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TTSLibraryTheme {
                val viewModel = MainViewModel()
                val resource = stringResource(id = R.string.des)
                val text = remember { resource }

//                screen
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(
                        state = text,
                        viewModel = viewModel,
                    ) {
                        speak(
                            this,
                            this as LifecycleOwner,
                            text,
                            viewModel
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    state: String,
    onButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val s = viewModel.getState().value
        TTSText(
            textAlign = TextAlign.Center,
            textHighlightBuilder = TextHighlightBuilder(
                text = state,
                s
            ),
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = onButtonClick) {
            Text(text = stringResource(id = R.string.speak))
        }
    }
}


private fun speak(
    activity: Activity,
    owner: LifecycleOwner,
    message: String,
    viewModel: MainViewModel
) {
    TextToSpeechHelper
        .getInstance(activity)
        .registerLifecycle(owner)
        .speak(message)
        .highlight()
        .onHighlight { pair ->
            viewModel.updateState(pair)
        }
        .onDone {
            Log.d(TAG, "speak: done")
        }
        .onError {
            Log.d(TAG, "speak: $it")
        }
}

```
`MainViewModel.kt`
```kotlin
class MainViewModel : ViewModel() {
    private val state = mutableStateOf(Pair(0, 0))

    fun updateState(pair: Pair<Int, Int>) {
        state.value = pair
    }

    fun getState(): State<Pair<Int, Int>> = state
}
```

### For XML layouts, check out the example below:
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text_view)
        val button = findViewById<Button>(R.id.button)
        val message = getString(R.string.des)
        button.setOnClickListener {
            speak(
                this,
                this as LifecycleOwner,
                message,
                textView
            )
        }
    }

    private fun speak(
        activity: Activity,
        owner: LifecycleOwner,
        message: String,
        textView: TextView
    ) {
        TextToSpeechHelper
            .getInstance(activity)
            .registerLifecycle(owner)
            .speak(message)
            .highlight()
            .onHighlight { pair ->
                textView.highlightText(pair.first, pair.second)
            }
            .onDone {
                Log.d(TAG, "speak: done")
            }
            .onError {
                Log.d(TAG, "speak: $it")
            }
    }
}
```
- `TextView.highlightText()` is an extension method that highlights the spoken words in the text.

## Contributing

If you would like to contribute to the library, please fork the repository and create a pull request.

## License
```
MIT License

Copyright (c) 2023 Ayaan

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
SOFTWARE.
`