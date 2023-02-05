# TTS Engine

A simple and efficient library that provides Text-to-Speech functionality for Android applications.

<a href="https://firebasestorage.googleapis.com/v0/b/aiyu-ayaan.appspot.com/o/demo.mp4?alt=media&token=3919f7d8-7a8e-406d-a5ea-5a3a00d914db" traget="_blank">
	<image src="https://firebasestorage.googleapis.com/v0/b/aiyu-ayaan.appspot.com/o/TTS-Engine.png?alt=media&token=2fb4886c-c9ed-4d73-8288-e9d13619f1d0"
       height="400" width="400"></image>
</a>

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
	        implementation 'com.github.aiyu-ayaan:tts-engine:Tag'
	}
```

## Usage

Using the library is straightforward and simple. Here is an example of how to use it in your code:

```kotlin
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

## Methods in TextToSpeechHelper class

- `Method 1: registerLifecycle(owner: LifecycleOwner)`
  Registers the lifecycle of the given owner.

- `Method 2: initTTS()`
  Initializes the Text-to-Speech engine.

- `Method 3: speak(message: String)`
  Generates speech from the given message.

- `Method 4: highlight()`
  Highlights the text in the TextView.

- `Method 5: removeHighlight()`
  Removes the highlight from the text in the TextView.

- `Method 6: destroy(action: (() -> Unit))`
  Destroys the Text-to-Speech engine and removes all listeners.

- `Method 7: onStart(onStartListener: () -> Unit)`
  Sets the onStart listener.

- `Method 8: onDone(onCompleteListener: () -> Unit)`
  Sets the onDone listener.

- `Method 9: onError(onErrorListener: (String) -> Unit)`
  Sets the onError listener.

- `Method 10: onHighlight(onHighlightListener: (Pair<Int, Int>) -> Unit)`
  Sets the onHighlight listener.

- `Method 11: setCustomActionForDestroy(action: () -> Unit)`
  Sets the custom action to be performed when the Text-to-Speech engine is destroyed.

- `Method 12: setLanguage(locale: Locale)`
  Sets the language for the Text-to-Speech engine.

- `Method 13: setPitchAndSpeed(pitch: Float, speed: Float)`
  Sets the pitch and speed of the speech generated by the Text-to-Speech engine.

- `Method 14: resetPitchAndSpeed()`
  Resets the pitch and speed of the speech generated by the Text-to-Speech engine to the default values.

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
```
